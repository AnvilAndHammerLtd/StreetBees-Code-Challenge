package com.alexandroukyriakos.streetbeescodechallenge.dropbox;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;

import com.alexandroukyriakos.streetbeescodechallenge.UiUtil;
import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.android.AuthActivity;
import com.dropbox.client2.session.AccessTokenPair;
import com.dropbox.client2.session.AppKeyPair;

public class DropboxHelper {
    public static final String TAG = DropboxHelper.class.getName();

    private static final String ACCOUNT_PREFS_NAME = "prefs";
    private static final String ACCESS_KEY_NAME = "ACCESS_KEY";
    private static final String ACCESS_SECRET_NAME = "ACCESS_SECRET";

    private boolean mLoggedIn;
    final static private String APP_KEY = "4kzdsdchrnt6cfs";
    final static private String APP_SECRET = "8qhqkr7xb8cfo5t";
    private DropboxAPI<AndroidAuthSession> mDBApi;
    private static final boolean USE_OAUTH1 = false;

    private Context mContext;

    public DropboxAPI<AndroidAuthSession> getDBApi() {
        return mDBApi;
    }

    public DropboxHelper(Context context) {
        mContext = context;

        AndroidAuthSession session = buildSession();
        mDBApi = new DropboxAPI<AndroidAuthSession>(session);

        checkAppKeySetup();
        mLoggedIn = mDBApi.getSession().isLinked();
    }

    /**
     * start dropbox authentication. Also, the {@link #finishAuthentication()} method needs to be called
     * in the onResume() of the activity to complete the authentication process
     */
    public void startRemoteAuthentication() {
        if (!mLoggedIn) {
            if (USE_OAUTH1) {
                mDBApi.getSession().startAuthentication(mContext);
            } else {
                mDBApi.getSession().startOAuth2Authentication(mContext);
            }
        }
    }

    /**
     * Must be inserted in the onResume() method of the
     * activity from which {@link #startRemoteAuthentication()} was called, so
     * that Dropbox authentication completes properly.
     */
    public void finishAuthentication() {
        AndroidAuthSession session = mDBApi.getSession();

        if (session.authenticationSuccessful()) {
            try {
                // Mandatory call to complete the auth
                session.finishAuthentication();

                // Store it locally in our app for later use
                storeAuth(session);
                mLoggedIn = true;
            } catch (IllegalStateException e) {
                UiUtil.showToastMessageCentered(
                        mContext,
                        "Couldn't authenticate with Dropbox:" + e.getLocalizedMessage());
                Log.i(TAG, "Error authenticating", e);
            }
        }
    }

    public void logOut() {
        // Remove credentials from the session
        mDBApi.getSession().unlink();

        // Clear our stored keys
        clearKeys();
        mLoggedIn = false;
    }

    private AndroidAuthSession buildSession() {
        AppKeyPair appKeyPair = new AppKeyPair(APP_KEY, APP_SECRET);
        AndroidAuthSession session = new AndroidAuthSession(appKeyPair);
        loadAuth(session);
        return session;
    }

    /**
     * Shows keeping the access keys returned from Trusted Authenticator in a local
     * store, rather than storing user name & password, and re-authenticating each
     * time (which is not to be done, ever).
     */
    private void loadAuth(AndroidAuthSession session) {
        SharedPreferences prefs = mContext.getSharedPreferences(ACCOUNT_PREFS_NAME, 0);
        String key = prefs.getString(ACCESS_KEY_NAME, null);
        String secret = prefs.getString(ACCESS_SECRET_NAME, null);
        if (key == null || secret == null || key.length() == 0 || secret.length() == 0) return;

        if (key.equals("oauth2:")) {
            // If the key is set to "oauth2:", then we can assume the token is for OAuth 2.
            session.setOAuth2AccessToken(secret);
        } else {
            // Still support using old OAuth 1 tokens.
            session.setAccessTokenPair(new AccessTokenPair(key, secret));
        }
    }

    private void checkAppKeySetup() {
        // Check to make sure that we have a valid app key
        if (!APP_KEY.startsWith("4kzdsdchrnt6cfs") ||
                !APP_SECRET.startsWith("8qhqkr7xb8cfo5t")) {
            UiUtil.showToastMessageCentered(
                    mContext,
                    "You must apply for an app key and secret from developers.dropbox.com, and add" +
                            "them to the DBRoulette ap before trying it.");
            return;
        }

        // Check if the app has set up its manifest properly.
        Intent testIntent = new Intent(Intent.ACTION_VIEW);
        String scheme = "db-" + APP_KEY;
        String uri = scheme + "://" + AuthActivity.AUTH_VERSION + "/test";
        testIntent.setData(Uri.parse(uri));
        PackageManager pm = mContext.getPackageManager();
        if (0 == pm.queryIntentActivities(testIntent, 0).size()) {
            UiUtil.showToastMessageCentered(mContext, "URL scheme in your app's " +
                    "manifest is not set up correctly. You should have a " +
                    "com.dropbox.client2.android.AuthActivity with the " +
                    "scheme: " + scheme);
        }
    }

    /**
     * Shows keeping the access keys returned from Trusted Authenticator in a local
     * store, rather than storing user name & password, and re-authenticating each
     * time (which is not to be done, ever).
     */
    private void storeAuth(AndroidAuthSession session) {
        // Store the OAuth 2 access token, if there is one.
        String oauth2AccessToken = session.getOAuth2AccessToken();
        if (oauth2AccessToken != null) {
            SharedPreferences prefs = mContext.getSharedPreferences(ACCOUNT_PREFS_NAME, 0);
            SharedPreferences.Editor edit = prefs.edit();
            edit.putString(ACCESS_KEY_NAME, "oauth2:");
            edit.putString(ACCESS_SECRET_NAME, oauth2AccessToken);
            edit.commit();
            return;
        }
        // Store the OAuth 1 access token, if there is one.  This is only necessary if
        // you're still using OAuth 1.
        AccessTokenPair oauth1AccessToken = session.getAccessTokenPair();
        if (oauth1AccessToken != null) {
            SharedPreferences prefs = mContext.getSharedPreferences(ACCOUNT_PREFS_NAME, 0);
            SharedPreferences.Editor edit = prefs.edit();
            edit.putString(ACCESS_KEY_NAME, oauth1AccessToken.key);
            edit.putString(ACCESS_SECRET_NAME, oauth1AccessToken.secret);
            edit.commit();
            return;
        }
    }

    private void clearKeys() {
        SharedPreferences prefs = mContext.getSharedPreferences(ACCOUNT_PREFS_NAME, 0);
        SharedPreferences.Editor edit = prefs.edit();
        edit.clear();
        edit.commit();
    }
}