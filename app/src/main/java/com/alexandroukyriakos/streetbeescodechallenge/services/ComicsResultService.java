package com.alexandroukyriakos.streetbeescodechallenge.services;

import android.util.Log;

import com.alexandroukyriakos.streetbeescodechallenge.events.ComicsResultEvent;
import com.alexandroukyriakos.streetbeescodechallenge.events.ErrorEvent;
import com.alexandroukyriakos.streetbeescodechallenge.models.ComicsResult;
import com.alexandroukyriakos.streetbeescodechallenge.ui.fragments.ComicsFragment;

import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.http.GET;

public class ComicsResultService {
    private static final String TAG = ComicsResultService.class.getName();
    private IComicsResultService mService;

    public ComicsResultService(RestAdapter restAdapter) {
        mService = restAdapter.create(IComicsResultService.class);
    }

    /**
     * sends a request to retrieve all the comics.
     * <p/>
     * on success the {@link ComicsFragment#onEventMainThread(ComicsResultEvent event)}
     * <br/>
     * on failure the {@link ComicsFragment#onEventMainThread(ErrorEvent event)}
     *
     * @param event the ComicsResultEvent to store the retrieved data
     */
    public void getComicsResultRequest(final ComicsResultEvent event) {

        mService.getComicsResult(
                new Callback<ComicsResult>() {
                    @Override
                    public void success(ComicsResult comicsResult, Response response) {
                        Log.v(TAG, "getComicsResultRequest success");
                        event.setComicsResult(comicsResult);
                        EventBus.getDefault().postSticky(event);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.v(TAG, "getComicsResultRequest failure");
                        EventBus.getDefault().postSticky(event.getErrorEvent());
                    }
                }
        );
    }

    public interface IComicsResultService {
        @GET("/v1/public/comics")
        void getComicsResult(Callback<ComicsResult> response);
    }
}