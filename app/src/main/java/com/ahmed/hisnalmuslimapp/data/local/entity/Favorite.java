package com.ahmed.hisnalmuslimapp.data.local.entity;

public class Favorite {

    private int id;

    private String collectionId;

    private String title;

    private String language;


    public Favorite() {
    }

    public Favorite(String collectionId, String title, String language) {
        this.collectionId = collectionId;
        this.title = title;
        this.language = language;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(String collectionId) {
        this.collectionId = collectionId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
