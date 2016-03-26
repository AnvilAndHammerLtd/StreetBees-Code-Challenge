package com.alexandroukyriakos.streetbeescodechallenge.ui.activities.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.alexandroukyriakos.streetbeescodechallenge.R;
import com.alexandroukyriakos.streetbeescodechallenge.helpers.BaseProgressBarHelper;

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
        return view;
    }

    private void bindViews(View view) {
        mComicsList = (ListView) view.findViewById(R.id.comics_list);
    }
}
