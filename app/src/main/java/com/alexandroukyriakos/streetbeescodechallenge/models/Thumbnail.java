
package com.alexandroukyriakos.streetbeescodechallenge.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Thumbnail {

    @SerializedName("path")
    @Expose
    private String path;
    @SerializedName("extension")
    @Expose
    private String extension;

    private transient String customThumbnail = null;
    private transient boolean hasCustomThumbnail = false;

    /**
     * @return The path
     */
    public String getPath() {
        return path;
    }

    /**
     * @param path The path
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * @return The extension
     */
    public String getExtension() {
        return extension;
    }

    /**
     * @param extension The extension
     */
    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getCustomThumbnail() {
        return customThumbnail;
    }

    public void setCustomThumbnail(String customThumbnail) {
        this.customThumbnail = customThumbnail;
    }

    public void hasCustomThumbnail(boolean hasCustomThumbnail) {
        this.hasCustomThumbnail = hasCustomThumbnail;
    }

    public boolean hasCustomThumbnail() {
        return hasCustomThumbnail;
    }
}
