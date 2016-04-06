package com.alexandroukyriakos.streetbeescodechallenge.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.alexandroukyriakos.streetbeescodechallenge.ComicUtil;
import com.alexandroukyriakos.streetbeescodechallenge.R;
import com.alexandroukyriakos.streetbeescodechallenge.models.Comic;
import com.alexandroukyriakos.streetbeescodechallenge.ui.customcomponents.ThumbnailChangeDialog;

import java.util.ArrayList;
import java.util.List;

public class ComicsAdapter extends BaseAdapter {
    private List<Comic> mComics = new ArrayList<>();
    private Context mContext;
    private ThumbnailChangeDialog mThumbnailChangeDialog;

    private ThumbnailChangeDialog.ThumbnailChangeDialogCallback mThumbnailChangeDialogCallback;

    public ComicsAdapter(Context context, List<Comic> comics, ThumbnailChangeDialog.ThumbnailChangeDialogCallback thumbnailChangeDialogCallback) {
        mContext = context;
        mComics = comics;
        mThumbnailChangeDialogCallback = thumbnailChangeDialogCallback;
        mThumbnailChangeDialog = new ThumbnailChangeDialog(mContext);
    }

    @Override
    public int getCount() {
        return mComics.size();
    }

    @Override
    public Comic getItem(int position) {
        return mComics.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_comic, null);
            viewHolder = new ViewHolder();
            bindViews(convertView, viewHolder);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Comic comic = mComics.get(position);
        setViewsValues(viewHolder, comic);

        return convertView;
    }

    private void bindViews(View convertView, ViewHolder viewHolder) {
        viewHolder.thumbnail = (ImageView) convertView.findViewById(R.id.image);
        viewHolder.title = (TextView) convertView.findViewById(R.id.title);
    }

    private void setViewsValues(ViewHolder viewHolder, final Comic comic) {
        ComicUtil.loadComicThumbnail(mContext, comic, viewHolder.thumbnail);
        viewHolder.title.setText(comic.getTitle());

        viewHolder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mThumbnailChangeDialog.show(comic, mThumbnailChangeDialogCallback);
            }
        });
    }

    private class ViewHolder {
        ImageView thumbnail;
        TextView title;
    }
}
