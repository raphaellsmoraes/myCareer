package com.rm.mycareer.model;

import java.util.ArrayList;

/**
 * Created by vntramo on 10/7/2014.
 */
public class User {

    private String id;

    private String name;

    private String facebookId;

    private String birthday;

    private ArrayList<FavoriteBooks> books;

    private ArrayList<FavoriteMovies> movies;

    private ArrayList<FavoriteMusics> music;

    private ArrayList<FavoriteAthletes> favorite_athletes;

    private Location location;

    private String gender;

    private Personality personality;

    private ArrayList<Profession> professions;


    public User(String name, String facebookId, String birthday, ArrayList<FavoriteBooks> books, ArrayList<FavoriteMovies> movies, ArrayList<FavoriteMusics> music, ArrayList<FavoriteAthletes> favorite_athletes, Location location, String gender) {
        this.name = name;
        this.facebookId = facebookId;
        this.birthday = birthday;
        this.books = books;
        this.movies = movies;
        this.music = music;
        this.favorite_athletes = favorite_athletes;
        this.location = location;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public ArrayList<FavoriteBooks> getBooks() {
        return books;
    }

    public void setBooks(ArrayList<FavoriteBooks> books) {
        this.books = books;
    }

    public ArrayList<FavoriteMovies> getMovies() {
        return movies;
    }

    public void setMovies(ArrayList<FavoriteMovies> movies) {
        this.movies = movies;
    }

    public ArrayList<FavoriteMusics> getMusic() {
        return music;
    }

    public void setMusic(ArrayList<FavoriteMusics> music) {
        this.music = music;
    }

    public ArrayList<FavoriteAthletes> getFavorite_athletes() {
        return favorite_athletes;
    }

    public void setFavorite_athletes(ArrayList<FavoriteAthletes> favorite_athletes) {
        this.favorite_athletes = favorite_athletes;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Personality getPersonality() {
        return personality;
    }

    public void setPersonality(Personality personality) {
        this.personality = personality;
    }

    public ArrayList<Profession> getProfessions() {
        return professions;
    }

    public void setProfessions(ArrayList<Profession> professions) {
        this.professions = professions;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User(String id, String name, String facebookId, String birthday, ArrayList<FavoriteBooks> books, ArrayList<FavoriteMovies> movies, ArrayList<FavoriteMusics> music, ArrayList<FavoriteAthletes> favorite_athletes, Location location, String gender, ArrayList<Profession> professions) {
        this.id = id;
        this.name = name;
        this.facebookId = facebookId;
        this.birthday = birthday;
        this.books = books;
        this.movies = movies;
        this.music = music;
        this.favorite_athletes = favorite_athletes;
        this.location = location;
        this.gender = gender;
        this.professions = professions;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", facebookId='" + facebookId + '\'' +
                ", birthday='" + birthday + '\'' +
                ", books=" + books +
                ", movies=" + movies +
                ", music=" + music +
                ", favorite_athletes=" + favorite_athletes +
                ", location=" + location +
                ", gender='" + gender + '\'' +
                ", personality=" + personality +
                ", professions=" + professions +
                '}';
    }
}
