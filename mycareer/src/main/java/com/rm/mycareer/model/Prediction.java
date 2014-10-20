package com.rm.mycareer.model;

/**
 * Created by vntramo on 10/20/2014.
 */
public class Prediction {

    /* Occupation ID */
    String id;

    Occupation occupation;

    /* Prediction value */
    Double prediction;

    public Prediction(String id, Double prediction) {
        this.id = id;
        this.prediction = prediction;
    }

    public Prediction(String id, Occupation occupation, Double prediction) {
        this.id = id;
        this.occupation = occupation;
        this.prediction = prediction;
    }

    public Prediction() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getPrediction() {
        return prediction;
    }

    public void setPrediction(double prediction) {
        this.prediction = prediction;
    }

    public Occupation getOccupation() {
        return occupation;
    }

    public void setOccupation(Occupation occupation) {
        this.occupation = occupation;
    }

    public void setPrediction(Double prediction) {
        this.prediction = prediction;
    }
}
