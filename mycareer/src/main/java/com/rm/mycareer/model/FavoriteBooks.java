package com.rm.mycareer.model;

/**
 * Created by vntramo on 10/8/2014.
 */
public class FavoriteBooks {

    private String id;
    private String name;

    public FavoriteBooks() {
    }

    public FavoriteBooks(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
