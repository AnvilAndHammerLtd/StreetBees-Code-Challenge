package com.alexandroukyriakos.streetbeescodechallenge.helpers.dropbox;

import android.content.Context;

import com.alexandroukyriakos.streetbeescodechallenge.models.Comic;
import com.dropbox.client2.DropboxAPI;

import java.io.File;

public class UploadComicPicture implements UploadPicture.UploadPictureCallback {
    private final UploadComicPictureCallback mUploadComicPictureCallback;
    private final Comic mComic;
    private final UploadPicture mUploadPicture;

    public UploadComicPicture(Context context, DropboxAPI<?> api,
                              String dropboxPath, File imageFile,
                              Comic comic, UploadComicPictureCallback uploadComicPictureCallback) {

        mUploadPicture = new UploadPicture(context, api, dropboxPath, imageFile, this);
        mComic = comic;
        mUploadComicPictureCallback = uploadComicPictureCallback;
    }

    public void execute() {
        mUploadPicture.execute();
    }

    @Override
    public void onUploadPictureStart() {
    }

    @Override
    public void onUploadPictureProgressUpdate(int percent) {
    }

    @Override
    public void onUploadPictureFinished(boolean success, String imagePath) {
        if (success) {
            mComic.getThumbnail().setCustomThumbnail(imagePath);
        }
        mUploadComicPictureCallback.onUploadComicPictureFinished(success, mComic);
    }

    public interface UploadComicPictureCallback {
        void onUploadComicPictureFinished(boolean success, Comic comic);
    }
}