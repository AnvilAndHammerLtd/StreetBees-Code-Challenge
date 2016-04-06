package com.alexandroukyriakos.streetbeescodechallenge.helpers.dropbox;

import android.content.Context;

import com.alexandroukyriakos.streetbeescodechallenge.helpers.dropbox.DownloadPicture;
import com.alexandroukyriakos.streetbeescodechallenge.models.Comic;
import com.dropbox.client2.DropboxAPI;

public class DownloadComicPicture extends DownloadPicture {
    private final DownloadComicPictureCallback mDownloadComicPictureCallback;
    private final Comic mComic;

    public DownloadComicPicture(Context context, DropboxAPI<?> api,
                                String dropboxPath, String imageFileName,
                                Comic comic, DownloadComicPictureCallback downloadComicPictureCallback) {

        super(context, api, dropboxPath, imageFileName);
        mComic = comic;
        mDownloadComicPictureCallback = downloadComicPictureCallback;
    }

    @Override
    public void onDownloadPicture(String cachePath) {
        mComic.getThumbnail().setCustomThumbnail(cachePath);
        mDownloadComicPictureCallback.onDownloadComicPicture(mComic);
    }

    public interface DownloadComicPictureCallback {
        void onDownloadComicPicture(Comic comic);
    }
}