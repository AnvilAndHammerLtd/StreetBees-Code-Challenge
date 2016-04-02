package com.alexandroukyriakos.streetbeescodechallenge.ui.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
import com.alexandroukyriakos.streetbeescodechallenge.events.ComicsResultEvent;
import com.alexandroukyriakos.streetbeescodechallenge.events.ErrorEvent;
import com.alexandroukyriakos.streetbeescodechallenge.helpers.BaseProgressBarHelper;
import com.alexandroukyriakos.streetbeescodechallenge.models.Comic;
import com.alexandroukyriakos.streetbeescodechallenge.services.ComicsResultService;
import com.alexandroukyriakos.streetbeescodechallenge.ui.activities.BaseActivity;
import com.alexandroukyriakos.streetbeescodechallenge.ui.adapters.ComicsAdapter;
import com.alexandroukyriakos.streetbeescodechallenge.ui.customcomponents.ThumbnailChangeDialog;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Responsible for displaying all the comics
 */
public class ComicsFragment extends BaseFragment implements ThumbnailChangeDialog.ThumbnailChangeDialogCallback {
    public static final String TAG = ComicsFragment.class.getName();
    private static final int CAMERA_IMAGE_CAPTURE_REQUEST_CODE = 1;
    private ListView mComicsList;

    public ComicsFragment() {
    }

    public static ComicsFragment newInstance(BaseProgressBarHelper baseProgressBarHelper) {
        ComicsFragment fragment = new ComicsFragment();
        fragment.setProgressBarHelper(baseProgressBarHelper);
        return fragment;
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
    }

    private void bindViews(View view) {
        mComicsList = (ListView) view.findViewById(R.id.comics_list);
    }

    private void getComics() {
        getProgressBarHelper().showProgressBar();
        ComicsResultService mCompanyService = new ComicsResultService(BaseActivity.REST_ADAPTER);
        ErrorEvent errorEvent = new ErrorEvent(getResources().getString(R.string.get_comics_request_failure));
        mCompanyService.getComicsResultRequest(new ComicsResultEvent(errorEvent));
    }

    public void onEventMainThread(ComicsResultEvent event) {
        EventBus.getDefault().removeStickyEvent(event);
        setComics(event.getComicsResult().getData().getComics());
        getProgressBarHelper().hideProgressBar();
    }

    private void setComics(List<Comic> comics) {
        final ComicsAdapter mComicsAdapter = new ComicsAdapter(getContext(), comics, this);
        mComicsList.setAdapter(mComicsAdapter);
        mComicsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                goToComicDetailsFragment(mComicsAdapter.getItem(position));
            }
        });

        mComicsAdapter.notifyDataSetChanged();
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
            openCameraForImageResult();
        }
    }

    private void openCameraForImageResult() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA_IMAGE_CAPTURE_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (CAMERA_IMAGE_CAPTURE_REQUEST_CODE == requestCode) {
            //TODO get image, resize it, compress it, map it with the comic cover image, store it to dropbox
            switch (resultCode) {
                case Activity.RESULT_OK:
                    Log.v("kiki", "onActivityResult CAMERA_IMAGE_CAPTURE_REQUEST_CODE RESULT_OK");
                    // /streetbeesChallengeTest/[comic_id_folder]/customThumbnails
//                    String DROPBOX_START_PATH = "/streetbeesChallengeTest/";
//                    String CUSTOM_THUMBNAIL_DROPBOX_PATH = "/customThumbnails";
//
//                    String finalPathForCustomThumbnails = DROPBOX_START_PATH + comic.getId() + CUSTOM_THUMBNAIL_DROPBOX_PATH;
                    break;

                case Activity.RESULT_CANCELED:
                    Log.v("kiki", "onActivityResult CAMERA_IMAGE_CAPTURE_REQUEST_CODE RESULT_CANCELED");
                    break;
            }
        }
    }
}