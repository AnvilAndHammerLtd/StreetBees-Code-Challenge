package com.alexandroukyriakos.streetbeescodechallenge;

import android.content.Context;

import com.alexandroukyriakos.streetbeescodechallenge.models.Comic;

public class ComicUtil {
    public static String getComicThumbnailPath(Context context, Comic comic) {
        final String thumbnailPath = comic.getThumbnail().getPath();
        final String thumbnailFinalUrl = thumbnailPath + context.getString(R.string.dot)
                + comic.getThumbnail().getExtension();

        return thumbnailFinalUrl;
    }
}