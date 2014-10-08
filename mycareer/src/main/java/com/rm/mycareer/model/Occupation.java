package com.rm.mycareer.model;

import java.util.List;

/**
 * Created by vntramo on 10/8/2014.
 */
public class Occupation {

    String id;
    String onet_soc;
    String title;
    String description;
    List<String> similarOccupations;

    public Occupation() {
    }

    public Occupation(String onet_soc, String title, String description, List<String> similarOccupations) {
        this.onet_soc = onet_soc;
        this.title = title;
        this.description = description;
        this.similarOccupations = similarOccupations;
    }

    public Occupation(String id, String onet_soc, String title, String description, List<String> similarOccupations) {
        this.id = id;
        this.onet_soc = onet_soc;
        this.title = title;
        this.description = description;
        this.similarOccupations = similarOccupations;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOnet_soc() {
        return onet_soc;
    }

    public void setOnet_soc(String onet_soc) {
        this.onet_soc = onet_soc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getSimilarOccupations() {
        return similarOccupations;
    }

    public void setSimilarOccupations(List<String> similarOccupations) {
        this.similarOccupations = similarOccupations;
    }
}
