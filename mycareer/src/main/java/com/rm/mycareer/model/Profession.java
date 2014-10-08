package com.rm.mycareer.model;

/**
 * Created by vntramo on 10/8/2014.
 */
public class Profession {

    private String id;
    private Occupation occupation;
    private double rating;

    public Profession() {
    }

    public Profession(Occupation occupation, double rating) {
        this.occupation = occupation;
        this.rating = rating;
    }

    public Profession(String id, Occupation occupation, double rating) {
        this.id = id;
        this.occupation = occupation;
        this.rating = rating;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Occupation getOccupation() {
        return occupation;
    }

    public void setOccupation(Occupation occupation) {
        this.occupation = occupation;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
