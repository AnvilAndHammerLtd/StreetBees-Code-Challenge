package com.alexandroukyriakos.streetbeescodechallenge.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Responsible for displaying all the comics
 */
public class ComicsFragment extends BaseFragment {
    public static final String TAG = ComicsFragment.class.getName();
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
        ComicsAdapter mComicsAdapter = new ComicsAdapter(getContext(), comics);
        mComicsList.setAdapter(mComicsAdapter);
        mComicsAdapter.notifyDataSetChanged();
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
}