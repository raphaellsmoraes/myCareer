package com.rm.mycareer.model;

/**
 * Created by vntramo on 10/8/2014.
 */
public class Personality {

    private Integer realistic;
    private Integer investigative;
    private Integer artistic;
    private Integer social;
    private Integer enterprising;
    private Integer conventional;

    public Personality(Integer realistic, Integer investigative, Integer artistic, Integer social, Integer enterprising, Integer conventional) {
        this.realistic = realistic;
        this.investigative = investigative;
        this.artistic = artistic;
        this.social = social;
        this.enterprising = enterprising;
        this.conventional = conventional;
    }

    public Integer getRealistic() {
        return realistic;
    }

    public void setRealistic(Integer realistic) {
        this.realistic = realistic;
    }

    public Integer getInvestigative() {
        return investigative;
    }

    public void setInvestigative(Integer investigative) {
        this.investigative = investigative;
    }

    public Integer getArtistic() {
        return artistic;
    }

    public void setArtistic(Integer artistic) {
        this.artistic = artistic;
    }

    public Integer getSocial() {
        return social;
    }

    public void setSocial(Integer social) {
        this.social = social;
    }

    public Integer getEnterprising() {
        return enterprising;
    }

    public void setEnterprising(Integer enterprising) {
        this.enterprising = enterprising;
    }

    public Integer getConventional() {
        return conventional;
    }

    public void setConventional(Integer conventional) {
        this.conventional = conventional;
    }
}
