package com.alexandroukyriakos.streetbeescodechallenge.helpers.dropbox;

import android.content.Context;

import com.alexandroukyriakos.streetbeescodechallenge.models.Comic;
import com.dropbox.client2.DropboxAPI;

import java.io.File;

public class UploadComicPicture extends UploadPicture {
    private final UploadComicPictureCallback mUploadComicPictureCallback;
    private final Comic mComic;

    public UploadComicPicture(Context context, DropboxAPI<?> api,
                              String dropboxPath, File imageFile,
                              Comic comic, UploadComicPictureCallback uploadComicPictureCallback) {

        super(context, api, dropboxPath, imageFile);
        mComic = comic;
        mUploadComicPictureCallback = uploadComicPictureCallback;
    }

    @Override
    public void onUploadPictureFinished(String imagePath) {
        mComic.getThumbnail().setCustomThumbnail(imagePath);
        mUploadComicPictureCallback.onUploadComicPictureFinished(mComic);
    }

    public interface UploadComicPictureCallback {
        void onUploadComicPictureFinished(Comic comic);
    }
}