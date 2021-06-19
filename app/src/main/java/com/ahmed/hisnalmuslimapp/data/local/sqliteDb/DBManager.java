package com.ahmed.hisnalmuslimapp.data.local.sqliteDb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.ahmed.hisnalmuslimapp.data.local.entity.Collections;
import com.ahmed.hisnalmuslimapp.data.local.entity.Favorite;
import com.ahmed.hisnalmuslimapp.data.local.entity.Hadiths;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Ahmed Eid Hefny
 * @date 4/2/21
 * <p>
 * ahmedeid2026@gmail.com
 **/
public class DBManager {
    private static final String TAG = "DBManager";

    private DatabaseHelper dbHelper;

    private Context mContext;

    public DBManager(Context context) {
        mContext = context;
        dbHelper = new DatabaseHelper(mContext);
    }


    public void close() {
        dbHelper.close();
    }


    /*
     *get All Collection By Language
     * if lang = "ar" get all Arabic collections
     * if lang = "en" get all English collections
     */
    public List<Collections> getCollectionsByLanguage(String lang) {

        List<Collections> collections = new ArrayList<>();
        try {
            dbHelper.open();
            SQLiteDatabase db = null;
            db = dbHelper.getReadableDatabase();
            //String[] args = new String[]{""};

            String selection = "language=?";
            String[] selectionArgs = new String[1];
            selectionArgs[0] = lang;

            Cursor c = db.query("Collections", null, selection, selectionArgs, null, null, null);
            if (c.moveToFirst()) {
                do {
                    int id = c.getInt(c.getColumnIndex("id"));
                    String collectionId = c.getString(c.getColumnIndex("collectionId"));
                    String title = c.getString(c.getColumnIndex("title"));
                    String audio_url = c.getString(c.getColumnIndex("audio_url"));
                    String language = c.getString(c.getColumnIndex("language"));
                    String total = c.getString(c.getColumnIndex("total"));
                    //Log.e("main", " " + id + " " + collectionId + " " + title + " " + audio_url + " " + language + " " + total);

                    Collections collection = new Collections(id, collectionId, title, audio_url, language, total);
                    collections.add(collection);
                } while (c.moveToNext());
            }
            c.close();
            db.close();
            dbHelper.close();
        } catch (SQLException sqle) {
            throw new Error("Unable to Query database: " + sqle);
        }

        return collections;
    }

    /*
     * get all Arabic and English collections
     */
    public List<Collections> getCollections() {

        List<Collections> collections = new ArrayList<>();
        try {
            dbHelper.open();
            SQLiteDatabase db = null;
            db = dbHelper.getReadableDatabase();

            Cursor c = db.query("Collections", null, null, null, null, null, null);
            if (c.moveToFirst()) {
                do {
                    int id = c.getInt(c.getColumnIndex("id"));
                    String collectionId = c.getString(c.getColumnIndex("collectionId"));
                    String title = c.getString(c.getColumnIndex("title"));
                    String audio_url = c.getString(c.getColumnIndex("audio_url"));
                    String language = c.getString(c.getColumnIndex("language"));
                    String total = c.getString(c.getColumnIndex("total"));
                    Log.e("main", " " + id + " " + collectionId + " " + title + " " + audio_url + " " + language + " " + total);

                    Collections collection = new Collections(id, collectionId, title, audio_url, language, total);
                    collections.add(collection);
                } while (c.moveToNext());
            }
            c.close();
            db.close();
            dbHelper.close();
        } catch (SQLException sqle) {
            throw new Error("Unable to Query database: " + sqle);
        }

        return collections;

    }

    public Collections getCollectionById(String CollectionId) {
        //List<Collections> collections = new ArrayList<>();

        Collections collections = new Collections();
        collections = null;

        try {
            dbHelper.open();
            SQLiteDatabase db = null;
            db = dbHelper.getReadableDatabase();

            String selection = "collectionId=?";
            String[] selectionArgs = new String[1];
            selectionArgs[0] = CollectionId;

            Cursor c = db.query("Collections", null, selection, selectionArgs, null, null, null);
            if (c.moveToFirst()) {
                do {
                    int id = c.getInt(c.getColumnIndex("id"));
                    String collectionId = c.getString(c.getColumnIndex("collectionId"));
                    String title = c.getString(c.getColumnIndex("title"));
                    String audio_url = c.getString(c.getColumnIndex("audio_url"));
                    String language = c.getString(c.getColumnIndex("language"));
                    String total = c.getString(c.getColumnIndex("total"));

                    collections = new Collections(id, collectionId, title, audio_url, language, total);
                    //collections.add(collection);
                } while (c.moveToNext());
            }
            c.close();
            db.close();
            dbHelper.close();
        } catch (SQLException sqle) {
            throw new Error("Unable to Query database: " + sqle);
        }
        return collections;
    }

    public List<Hadiths> getAzkarByCollectionId(String CollectionId) {
        List<Hadiths> hadiths = new ArrayList<>();

        try {
            dbHelper.open();
            SQLiteDatabase db = null;
            db = dbHelper.getReadableDatabase();

            String selection = "collectionId=?";
            String[] selectionArgs = new String[1];
            selectionArgs[0] = CollectionId;

            Cursor c = db.query("Hadiths", null, selection, selectionArgs, null, null, null);
            if (c.moveToFirst()) {
                do {
                    int id = c.getInt(c.getColumnIndex("id"));
                    String collectionId = c.getString(c.getColumnIndex("collectionId"));
                    String azkarId = c.getString(c.getColumnIndex("azkarId"));
                    String title = c.getString(c.getColumnIndex("title"));
                    String text = c.getString(c.getColumnIndex("text"));
                    String repeat = c.getString(c.getColumnIndex("repeat"));
                    String language = c.getString(c.getColumnIndex("language"));
                    String audio_url = c.getString(c.getColumnIndex("audio_url"));
                    //Log.e("main", " /" + id + " /" + collectionId + " /" + azkarId + " /" + title + " " + audio_url + " " + language + " " + text);

                    Hadiths hadith = new Hadiths(id, collectionId, azkarId, title, text, repeat, language, audio_url);
                    hadiths.add(hadith);
                } while (c.moveToNext());
            }
            c.close();
            db.close();
            dbHelper.close();
        } catch (SQLException sqle) {
            throw new Error("Unable to Query database: " + sqle);
        }
        return hadiths;
    }

    public boolean saveFavorite(Favorite favorite) {
        Boolean resultBoolean = false;

        try {
            dbHelper.open();
            SQLiteDatabase db = null;
            db = dbHelper.getWritableDatabase();

            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseHelper.FAVORITE_COLLECTION_ID_COULMN, favorite.getCollectionId());
            contentValues.put(DatabaseHelper.FAVORITE_COLLECTION_NAME_COULMN, favorite.getTitle());
            contentValues.put(DatabaseHelper.FAVORITE_COLLECTION_LANG_COULMN, favorite.getLanguage());

            long result = db.insert(DatabaseHelper.TABLE_FAVORITE_NAME, null, contentValues);
            Log.e(TAG, "InsertRowNum: " + result);
            db.close();
            dbHelper.close();

            if (result > 0) {
                resultBoolean = true;
            } else {
                resultBoolean = false;
            }


        } catch (SQLException sqle) {
            throw new Error("Unable to Query database: " + sqle);
        }

        return resultBoolean;

    }

    public boolean deleteAllFavorites() {
        Boolean resultBoolean = false;
        try {


            dbHelper.open();
            SQLiteDatabase db = null;
            db = dbHelper.getWritableDatabase();

            int result = db.delete(DatabaseHelper.TABLE_FAVORITE_NAME, null, null);
            Log.e(TAG, "DeleteRow: " + result);
            if (result > 0) {
                resultBoolean = true;

            } else {
                resultBoolean = false;
            }

            db.close();
            dbHelper.close();

        } catch (SQLException sqle) {
            throw new Error("Unable to Query database: " + sqle);
        }

        return resultBoolean;
    }

    public boolean deleteFavorite(String collectionId, String language) {
        Boolean resultBoolean = false;
        try {


            dbHelper.open();
            SQLiteDatabase db = null;
            db = dbHelper.getWritableDatabase();

            String selection = DatabaseHelper.FAVORITE_COLLECTION_ID_COULMN + "=?"; // & "
            //+ DatabaseHelper.FAVORITE_COLLECTION_LANG_COULMN + "=?";
            String[] selectionArgs = new String[1];
            selectionArgs[0] = collectionId;
            //selectionArgs[1] = language;

            int result = db.delete(DatabaseHelper.TABLE_FAVORITE_NAME, selection, selectionArgs);
            Log.e(TAG, "DeleteRow: " + result);
            if (result > 0) {
                resultBoolean = true;

            } else {
                resultBoolean = false;
            }

            db.close();
            dbHelper.close();

        } catch (SQLException sqle) {
            throw new Error("Unable to Query database: " + sqle);
        }

        return resultBoolean;
    }

    public Favorite searchFavorite(String collectionId, String language) {
        Favorite favorite = new Favorite();
        favorite = null;
        try {
            dbHelper.open();
            SQLiteDatabase db = null;
            db = dbHelper.getReadableDatabase();

            String selection = DatabaseHelper.FAVORITE_COLLECTION_ID_COULMN + "=?";
            String[] selectionArgs = new String[1];
            selectionArgs[0] = collectionId;
            //selectionArgs[1] = language;

            Cursor c = db.query(DatabaseHelper.TABLE_FAVORITE_NAME, null, selection, selectionArgs, null, null, null);
            if (c.moveToFirst()) {
                do {

                    String cId = c.getString(c.getColumnIndex(DatabaseHelper.FAVORITE_COLLECTION_ID_COULMN));
                    String name = c.getString(c.getColumnIndex(DatabaseHelper.FAVORITE_COLLECTION_NAME_COULMN));
                    String lang = c.getString(c.getColumnIndex(DatabaseHelper.FAVORITE_COLLECTION_LANG_COULMN));

                    favorite = new Favorite(cId, name, lang);
                } while (c.moveToNext());
            }

            c.close();
            db.close();
            dbHelper.close();
        } catch (SQLException sqle) {
            throw new Error("Unable to Query database: " + sqle);
        }

        return favorite;
    }

    public List<Favorite> getAllFavorites() {
       List<Favorite> favorites = new ArrayList<>();

        try {
            dbHelper.open();
            SQLiteDatabase db = null;
            db = dbHelper.getReadableDatabase();

            Cursor c = db.query(DatabaseHelper.TABLE_FAVORITE_NAME, null, null, null, null, null, null);
            if (c.moveToFirst()) {
                do {

                    String cId = c.getString(c.getColumnIndex(DatabaseHelper.FAVORITE_COLLECTION_ID_COULMN));
                    String name = c.getString(c.getColumnIndex(DatabaseHelper.FAVORITE_COLLECTION_NAME_COULMN));
                    String lang = c.getString(c.getColumnIndex(DatabaseHelper.FAVORITE_COLLECTION_LANG_COULMN));

                    Favorite favorite = new Favorite(cId, name, lang);
                    favorites.add(favorite);
                } while (c.moveToNext());
            }

            c.close();
            db.close();
            dbHelper.close();
        } catch (SQLException sqle) {
            throw new Error("Unable to Query database: " + sqle);
        }

        return favorites;
    }



}
