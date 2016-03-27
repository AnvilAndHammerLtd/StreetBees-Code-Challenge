package com.alexandroukyriakos.streetbeescodechallenge.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alexandroukyriakos.streetbeescodechallenge.R;
import com.alexandroukyriakos.streetbeescodechallenge.UiUtil;
import com.alexandroukyriakos.streetbeescodechallenge.models.Comic;

public class ComicDetailsFragment extends BaseFragment {
    public static final String TAG = ComicDetailsFragment.class.getName();
    private Comic mComic;

    private ImageView mThumbnail;
    private TextView mTitle;
    private TextView mDescription;

    public ComicDetailsFragment() {
    }

    public static ComicDetailsFragment newInstance(Comic comic) {
        ComicDetailsFragment fragment = new ComicDetailsFragment();
        fragment.initialiseComic(comic);
        return fragment;
    }

    private void initialiseComic(Comic comic) {
        mComic = comic;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comic_details, container, false);
        bindViews(view);
        setViewsValues();
        return view;
    }

    private void bindViews(View view) {
        mThumbnail = (ImageView) view.findViewById(R.id.thumbnail);
        mTitle = (TextView) view.findViewById(R.id.title_value);
        mDescription = (TextView) view.findViewById(R.id.description_value);
    }

    private void setViewsValues() {
        setComicThumbnail();
        mTitle.setText(mComic.getTitle());
        mDescription.setText(mComic.getDescription());
    }

    private void setComicThumbnail() {
        String thumbnailPath = mComic.getThumbnail().getPath();
        String thumbnailFinalUrl = thumbnailPath + getString(R.string.dot)
                + mComic.getThumbnail().getExtension();
        UiUtil.loadImageInto(
                getContext(), thumbnailFinalUrl,
                R.drawable.comic_thumbnail_placeholder, mThumbnail);
    }
}