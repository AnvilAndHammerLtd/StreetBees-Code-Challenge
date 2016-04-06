package com.alexandroukyriakos.streetbeescodechallenge.ui.customcomponents;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;

import com.alexandroukyriakos.streetbeescodechallenge.ComicUtil;
import com.alexandroukyriakos.streetbeescodechallenge.R;
import com.alexandroukyriakos.streetbeescodechallenge.models.Comic;

public class ThumbnailChangeDialog {
    private final Context mContext;

    public ThumbnailChangeDialog(Context context) {
        mContext = context;
    }

    public interface ThumbnailChangeDialogCallback {
        void askToChangeThumbnailDialogResponse(final boolean success, final Comic comic);
    }

    public void show(final Comic comic, ThumbnailChangeDialogCallback thumbnailChangeDialogCallback) {
        HeaderComponent header = createHeaderComponent(comic);
        AlertDialog dialog = createDialog(header, comic, thumbnailChangeDialogCallback);
        dialog.show();
    }

    private AlertDialog createDialog(final HeaderComponent header, final Comic comic, final ThumbnailChangeDialogCallback thumbnailChangeDialogCallback) {
        return new AlertDialog.Builder(mContext)
                .setCustomTitle(header)
                .setPositiveButton(R.string.ask_to_change_thumbnail_positive_btn, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        thumbnailChangeDialogCallback.askToChangeThumbnailDialogResponse(true, comic);
                    }
                })
                .setNegativeButton(R.string.ask_to_change_thumbnail_negative_btn, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        thumbnailChangeDialogCallback.askToChangeThumbnailDialogResponse(false, comic);
                    }
                })
                .create();
    }

    @NonNull
    private HeaderComponent createHeaderComponent(Comic comic) {
        final HeaderComponent header = new HeaderComponent(mContext);
        header.setTitle(R.string.thumbnail_dialog_change_title);
        header.setTextColorResId(R.color.thumbnail_change_dialog_header_text);
        header.setBackgroundResource(R.color.thumbnail_change_dialog_header_background);
        header.setDividerColorResId(R.color.thumbnail_change_dialog_header_divider);
        ComicUtil.loadComicThumbnail(mContext, comic, header.getIconImageView());
        return header;
    }
}