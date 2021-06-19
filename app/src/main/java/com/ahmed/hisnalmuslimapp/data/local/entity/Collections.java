package com.ahmed.hisnalmuslimapp.data.local.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author Ahmed Eid Hefny
 * @date 1/2/21
 * <p>
 * ahmedeid2026@gmail.com
 **/
public class Collections implements Parcelable {

//    {
//        "id": 9,
//            "collectionId": "5",
//            "title": "What to say when undressing",
//            "audio_url": "http://www.hisnmuslim.com/audio/ar/ar_7esn_AlMoslem_by_Doors_006.mp3",
//            "language": "en",
//            "total": "1"
//    }

    @Expose
    @PrimaryKey
    @SerializedName("id")
    private int id;
    @Expose
    @SerializedName("collectionId")
    private String collectionId;
    @Expose
    @SerializedName("title")
    private String title;
    @Expose
    @SerializedName("audio_url")
    private String audio_url;
    @Expose
    @SerializedName("language")
    private String language;
    @Expose
    @SerializedName("total")
    private String total;

    public Collections() {
    }

    public Collections(int id, String collectionId, String title, String audio_url, String language, String total) {
        this.id = id;
        this.collectionId = collectionId;
        this.title = title;
        this.audio_url = audio_url;
        this.language = language;
        this.total = total;
    }

    protected Collections(Parcel in) {
        id = in.readInt();
        collectionId = in.readString();
        title = in.readString();
        audio_url = in.readString();
        language = in.readString();
        total = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(collectionId);
        dest.writeString(title);
        dest.writeString(audio_url);
        dest.writeString(language);
        dest.writeString(total);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Collections> CREATOR = new Creator<Collections>() {
        @Override
        public Collections createFromParcel(Parcel in) {
            return new Collections(in);
        }

        @Override
        public Collections[] newArray(int size) {
            return new Collections[size];
        }
    };

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

    public String getAudio_url() {
        return audio_url;
    }

    public void setAudio_url(String audio_url) {
        this.audio_url = audio_url;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

}

