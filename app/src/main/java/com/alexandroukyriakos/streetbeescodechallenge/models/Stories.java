
package com.alexandroukyriakos.streetbeescodechallenge.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Stories {

    @SerializedName("available")
    @Expose
    private String available;
    @SerializedName("returned")
    @Expose
    private String returned;
    @SerializedName("collectionURI")
    @Expose
    private String collectionURI;
    @SerializedName("items")
    @Expose
    private List<ItemWithType> items = new ArrayList<ItemWithType>();

    /**
     * @return The available
     */
    public String getAvailable() {
        return available;
    }

    /**
     * @param available The available
     */
    public void setAvailable(String available) {
        this.available = available;
    }

    /**
     * @return The returned
     */
    public String getReturned() {
        return returned;
    }

    /**
     * @param returned The returned
     */
    public void setReturned(String returned) {
        this.returned = returned;
    }

    /**
     * @return The collectionURI
     */
    public String getCollectionURI() {
        return collectionURI;
    }

    /**
     * @param collectionURI The collectionURI
     */
    public void setCollectionURI(String collectionURI) {
        this.collectionURI = collectionURI;
    }

    /**
     * @return The items
     */
    public List<ItemWithType> getItems() {
        return items;
    }

    /**
     * @param items The items
     */
    public void setItems(List<ItemWithType> items) {
        this.items = items;
    }

}
