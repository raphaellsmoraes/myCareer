package com.rm.mycareer.model;

/**
 * Created by vntramo on 10/8/2014.
 */
public class Location {

    public String id;
    public String name;

    public Location(String id, String name) {
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
