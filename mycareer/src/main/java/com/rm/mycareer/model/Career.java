package com.rm.mycareer.model;

import java.util.ArrayList;

/**
 * Created by vntramo on 7/29/2014.
 */
public class Career {

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ArrayList<String> getTitle() {
        return title;
    }

    public void setTitle(ArrayList<String> title) {
        this.title = title;
    }

    public String getWhat_they_do() {
        return what_they_do;
    }

    public void setWhat_they_do(String what_they_do) {
        this.what_they_do = what_they_do;
    }

    public ArrayList<String> getOn_the_job() {
        return on_the_job;
    }

    public void setOn_the_job(ArrayList<String> on_the_job) {
        this.on_the_job = on_the_job;
    }

    public String getKnowledgeUrl() {
        return knowledgeUrl;
    }

    public void setKnowledgeUrl(String knowledgeUrl) {
        this.knowledgeUrl = knowledgeUrl;
    }

    public String getSkillsUrl() {
        return skillsUrl;
    }

    public void setSkillsUrl(String skillsUrl) {
        this.skillsUrl = skillsUrl;
    }

    public String getAbilitiesUrl() {
        return abilitiesUrl;
    }

    public void setAbilitiesUrl(String abilitiesUrl) {
        this.abilitiesUrl = abilitiesUrl;
    }

    public String getPersonalityUrl() {
        return personalityUrl;
    }

    public void setPersonalityUrl(String personalityUrl) {
        this.personalityUrl = personalityUrl;
    }

    public String getTechnologyUrl() {
        return technologyUrl;
    }

    public void setTechnologyUrl(String technologyUrl) {
        this.technologyUrl = technologyUrl;
    }

    public String getEducationUrl() {
        return educationUrl;
    }

    public void setEducationUrl(String educationUrl) {
        this.educationUrl = educationUrl;
    }

    public String getJobOutlookUrl() {
        return jobOutlookUrl;
    }

    public void setJobOutlookUrl(String jobOutlookUrl) {
        this.jobOutlookUrl = jobOutlookUrl;
    }

    public String getCareer() {
        return career;
    }

    public void setCareer(String career) {
        this.career = career;
    }

    public String code;
    public String career;
    public ArrayList<String> title;
    public String what_they_do;
    public ArrayList<String> on_the_job;
    public String knowledgeUrl;
    public String skillsUrl;
    public String abilitiesUrl;
    public String personalityUrl;
    public String technologyUrl;
    public String educationUrl;
    public String jobOutlookUrl;

    public Career() {
    }

}
