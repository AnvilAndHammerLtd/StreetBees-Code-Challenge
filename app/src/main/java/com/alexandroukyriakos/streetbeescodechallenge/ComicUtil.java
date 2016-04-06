package com.alexandroukyriakos.streetbeescodechallenge;

import android.content.Context;
import android.widget.ImageView;

import com.alexandroukyriakos.streetbeescodechallenge.models.Comic;

import java.io.File;

public class ComicUtil {
    private static String getComicDefaultThumbnailPath(Context context, Comic comic) {
        final String thumbnailPath = comic.getThumbnail().getPath();
        final String thumbnailFinalUrl = thumbnailPath + context.getString(R.string.dot)
                + comic.getThumbnail().getExtension();

        return thumbnailFinalUrl;
    }

    public static void loadComicThumbnail(Context context, Comic comic, final ImageView into) {
        if (comic.getThumbnail().getCustomThumbnail() != null) {
            UiUtil.loadImageInto(
                    context,
                    new File(comic.getThumbnail().getCustomThumbnail()),
                    R.drawable.comic_thumbnail_placeholder,
                    into
            );
        } else {
            UiUtil.loadImageInto(
                    context,
                    ComicUtil.getComicDefaultThumbnailPath(context, comic),
                    R.drawable.comic_thumbnail_placeholder,
                    into
            );
        }
    }
}