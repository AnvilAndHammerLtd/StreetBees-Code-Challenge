package com.alexandroukyriakos.streetbeescodechallenge.ui.fragments;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alexandroukyriakos.streetbeescodechallenge.R;
import com.alexandroukyriakos.streetbeescodechallenge.UiUtil;
import com.alexandroukyriakos.streetbeescodechallenge.helpers.dropbox.DownloadComicPicture;
import com.alexandroukyriakos.streetbeescodechallenge.helpers.dropbox.DropboxHelper;
import com.alexandroukyriakos.streetbeescodechallenge.helpers.dropbox.UploadPicture;
import com.alexandroukyriakos.streetbeescodechallenge.events.ComicsResultEvent;
import com.alexandroukyriakos.streetbeescodechallenge.events.ErrorEvent;
import com.alexandroukyriakos.streetbeescodechallenge.helpers.progressbar.BaseProgressBarHelper;
import com.alexandroukyriakos.streetbeescodechallenge.models.Comic;
import com.alexandroukyriakos.streetbeescodechallenge.services.ComicsResultService;
import com.alexandroukyriakos.streetbeescodechallenge.ui.activities.BaseActivity;
import com.alexandroukyriakos.streetbeescodechallenge.ui.adapters.ComicsAdapter;
import com.alexandroukyriakos.streetbeescodechallenge.ui.customcomponents.ThumbnailChangeDialog;

import java.io.File;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Responsible for displaying all the comics
 */
public class ComicsFragment extends BaseFragment implements
        ThumbnailChangeDialog.ThumbnailChangeDialogCallback,
        DownloadComicPicture.DownloadComicPictureCallback {
    public static final String TAG = ComicsFragment.class.getName();
    private static final int CAMERA_IMAGE_CAPTURE_REQUEST_CODE = 1;
    private ListView mComicsList;
    private DropboxHelper mDropboxHelper;

    private String mDropboxPhotoDirectoryFullPath;
    private String mLocalPhotoFullPath;
    private ComicsAdapter mComicsAdapter;

    /*
    custom thumbnails directory path
    /streetbeesChallengeTest/[folder_with_the_comic_id_as_a_name]/currentCustomThumbnail.jpg
    */
    public static final String DROPBOX_START_FOLDER_PATH = "/streetbeesCodeChallenge/";
    public static final String CUSTOM_THUMBNAIL_NAME = "currentCustomThumbnail";
    public static final String CUSTOM_THUMBNAIL_EXTENSION = ".jpg";

    public ComicsFragment() {
    }

    public static ComicsFragment newInstance(BaseProgressBarHelper baseProgressBarHelper) {
        ComicsFragment fragment = new ComicsFragment();
        fragment.setProgressBarHelper(baseProgressBarHelper);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDropboxHelper = new DropboxHelper(getContext());
        mDropboxHelper.startRemoteAuthentication();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comics, container, false);
        bindViews(view);
        getComics();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mComicsList.getAdapter() == null) {
            getComics();
        }
        mDropboxHelper.finishAuthentication();
    }

    private void bindViews(View view) {
        mComicsList = (ListView) view.findViewById(R.id.comics_list);
    }

    private void getComics() {
        getProgressBarHelper().showProgressBar();
        ComicsResultService mComicsResultService = new ComicsResultService(BaseActivity.REST_ADAPTER);
        ErrorEvent errorEvent = new ErrorEvent(getResources().getString(R.string.get_comics_request_failure));
        mComicsResultService.getComicsResultRequest(new ComicsResultEvent(errorEvent));
    }

    public void onEventMainThread(ComicsResultEvent event) {
        EventBus.getDefault().removeStickyEvent(event);
        setComics(event.getComicsResult().getData().getComics());
        getProgressBarHelper().hideProgressBar();
    }

    private void setComics(final List<Comic> comics) {
        downloadCustomThumbnailsFromDropbox(comics);

        mComicsAdapter = new ComicsAdapter(getContext(), comics, this);
        mComicsList.setAdapter(mComicsAdapter);
        mComicsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                goToComicDetailsFragment(mComicsAdapter.getItem(position));
            }
        });

        mComicsAdapter.notifyDataSetChanged();
    }

    private void downloadCustomThumbnailsFromDropbox(final List<Comic> comics) {
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                for (Comic comic : comics) {
                    final String customThumbnailDirectoryPath = DROPBOX_START_FOLDER_PATH + comic.getId() + "/";
                    String finalThumbnailName = CUSTOM_THUMBNAIL_NAME + comic.getId() + CUSTOM_THUMBNAIL_EXTENSION;

                    DownloadComicPicture download = new DownloadComicPicture(
                            getContext(),
                            mDropboxHelper.getDBApi(),
                            customThumbnailDirectoryPath,
                            finalThumbnailName,
                            comic,
                            ComicsFragment.this
                    );

//                    DownloadPicture download = new DownloadPicture(
//                            getContext(),
//                            mDropboxHelper.getDBApi(),
//                            customThumbnailDirectoryPath,
//                            finalThumbnailName,
//                            comic.getThumbnail()
//                    );

                    download.execute();
                }
            }
        });
    }

    private void goToComicDetailsFragment(Comic comic) {
        FragmentManager fm = getFragmentManager();
        ComicDetailsFragment comicDetailsFragment = ComicDetailsFragment.newInstance(comic);
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.fragment, comicDetailsFragment, ComicDetailsFragment.TAG);
        ft.addToBackStack(ComicDetailsFragment.TAG);
        ft.commit();
        fm.executePendingTransactions();
    }

    public void onEventMainThread(ErrorEvent event) {
        EventBus.getDefault().removeStickyEvent(event);
        getProgressBarHelper().hideProgressBar();
        UiUtil.showToastMessageCentered(getContext(), event.getErrorMessage());
    }

    @Override
    public void onStart() {
        EventBus.getDefault().registerSticky(this);
        super.onStart();
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    public void askToChangeThumbnailDialogResponse(boolean success, final Comic comic) {
        if (success) {
            mDropboxPhotoDirectoryFullPath = DROPBOX_START_FOLDER_PATH + comic.getId() + "/";
            Intent intent = initialiseCameraForImageResult(comic);
            openCameraForImageResult(intent);
        }
    }

    private Intent initialiseCameraForImageResult(Comic comic) {
        String finalThumbnailName = CUSTOM_THUMBNAIL_NAME + comic.getId() + CUSTOM_THUMBNAIL_EXTENSION;

        String outPath = new File(Environment.getExternalStorageDirectory() + "/" + comic.getId() + "/", finalThumbnailName).getPath();
        File outFile = new File(outPath);
        mLocalPhotoFullPath = outFile.toString();
        Uri outuri = Uri.fromFile(outFile);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outuri);
        return intent;
    }

    private void openCameraForImageResult(Intent intent) {
        Log.i(TAG, "Importing New Picture: " + mLocalPhotoFullPath);
        try {
            startActivityForResult(intent, CAMERA_IMAGE_CAPTURE_REQUEST_CODE);
        } catch (ActivityNotFoundException e) {
            UiUtil.showToastMessageCentered(getContext(),
                    "There doesn't seem to be a camera.");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case Activity.RESULT_OK:
                File file = new File(mLocalPhotoFullPath);

                UploadPicture upload =
                        new UploadPicture(
                                getContext(),
                                mDropboxHelper.getDBApi(),
                                mDropboxPhotoDirectoryFullPath,
                                file);
                upload.execute();
                break;
        }
    }

    @Override
    public void onDownloadComicPicture(Comic comic) {
        if (mComicsAdapter != null) {
            mComicsAdapter.notifyDataSetChanged();
        }
    }
}