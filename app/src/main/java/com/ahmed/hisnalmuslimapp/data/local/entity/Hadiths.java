package com.ahmed.hisnalmuslimapp.data.local.entity;

import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author Ahmed Eid Hefny
 * @date 1/2/21
 * <p>
 * ahmedeid2026@gmail.com
 **/

public class Hadiths {

    @Expose
    @PrimaryKey
    @SerializedName("id")
    private int id;
    @Expose
    @SerializedName("collectionId")
    private String collectionId;
    @Expose
    @SerializedName("azkarId")
    private String azkarId;
    @Expose
    @SerializedName("title")
    private String title;
    @Expose
    @SerializedName("text")
    private String text;
    @Expose
    @SerializedName("repeat")
    private String repeat;
    @Expose
    @SerializedName("language")
    private String language;
    @Expose
    @SerializedName("audio_url")
    private String audio_url;

    public Hadiths() {
    }

    public Hadiths(int id, String collectionId, String azkarId, String title, String text, String repeat, String language, String audio_url) {
        this.id = id;
        this.collectionId = collectionId;
        this.azkarId = azkarId;
        this.title = title;
        this.text = text;
        this.repeat = repeat;
        this.language = language;
        this.audio_url = audio_url;
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

    public String getAzkarId() {
        return azkarId;
    }

    public void setAzkarId(String azkarId) {
        this.azkarId = azkarId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getRepeat() {
        return repeat;
    }

    public void setRepeat(String repeat) {
        this.repeat = repeat;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getAudio_url() {
        return audio_url;
    }

    public void setAudio_url(String audio_url) {
        this.audio_url = audio_url;
    }

}
