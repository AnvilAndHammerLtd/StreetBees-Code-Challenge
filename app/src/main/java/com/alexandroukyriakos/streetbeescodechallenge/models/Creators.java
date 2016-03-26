
package com.alexandroukyriakos.streetbeescodechallenge.models;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Creators {

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
    private List<ItemWithRole> itemWithRoles = new ArrayList<ItemWithRole>();

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
     * @return The itemWithRoles
     */
    public List<ItemWithRole> getItemWithRoles() {
        return itemWithRoles;
    }

    /**
     * @param itemWithRoles The itemWithRoles
     */
    public void setItemWithRoles(List<ItemWithRole> itemWithRoles) {
        this.itemWithRoles = itemWithRoles;
    }

}
