package com.alexandroukyriakos.streetbeescodechallenge.helpers.dropbox;

import android.content.Context;
import android.util.Log;

import com.alexandroukyriakos.streetbeescodechallenge.models.Comic;
import com.dropbox.client2.DropboxAPI;

public class DownloadComicPicture implements DownloadPicture.DownloadPictureCallback {
    private final DownloadComicPictureCallback mDownloadComicPictureCallback;
    private final Comic mComic;
    private final DownloadPicture mDownloadPicture;

    public DownloadComicPicture(Context context, DropboxAPI<?> api,
                                String dropboxPath, String imageFileName,
                                Comic comic, DownloadComicPictureCallback downloadComicPictureCallback) {
        mComic = comic;
        mDownloadComicPictureCallback = downloadComicPictureCallback;
        mDownloadPicture = new DownloadPicture(context, api, dropboxPath, imageFileName, this);
    }

    public void execute() {
        mDownloadPicture.execute();
    }

    @Override
    public void onDownloadPictureStart() {
        /*
        explicitly say that the comic has a custom thumbnail even if it hasn't as we don't know yet
        if the comic has a custom thumbnail. As soon as the final response comes back from this
        download procedure then we will know if an image was found.
        TODO as this is not the most efficient way maybe store the comics ids locally the first time
        the app runs and then based on those ids try to download the thumbnails of those comics
         */
        mComic.getThumbnail().hasCustomThumbnail(true);
        mDownloadComicPictureCallback.onDownloadComicPictureStart(mComic);
    }

    @Override
    public void onDownloadPictureProgressUpdate(int percent) {
        Log.v("kiki", "onDownloadPictureProgressUpdate: " + percent);
        mDownloadComicPictureCallback.onDownloadComicPictureProgressUpdate(percent, mComic);
    }

    @Override
    public void onDownloadPictureFinish(boolean success, String cachePath) {
        if (success) {
            mComic.getThumbnail().setCustomThumbnail(cachePath);
        } else {
            mComic.getThumbnail().hasCustomThumbnail(false);
        }

        mDownloadComicPictureCallback.onDownloadComicPictureFinished(success, mComic);
    }

    public interface DownloadComicPictureCallback {
        void onDownloadComicPictureStart(Comic comic);

        void onDownloadComicPictureProgressUpdate(int percent, Comic comic);

        void onDownloadComicPictureFinished(boolean success, Comic comic);
    }
}