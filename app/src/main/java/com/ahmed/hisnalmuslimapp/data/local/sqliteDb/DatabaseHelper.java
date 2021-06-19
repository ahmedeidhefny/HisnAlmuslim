package com.ahmed.hisnalmuslimapp.data.local.sqliteDb;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author Ahmed Eid Hefny
 * @date 4/2/21
 * <p>
 * ahmedeid2026@gmail.com
 **/

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    public static final String TABLE_FAVORITE_NAME = "favorite";
    public static final String FAVORITE_ID_COULMN= "id";
    public static final String FAVORITE_COLLECTION_ID_COULMN= "collectionId";
    public static final String FAVORITE_COLLECTION_NAME_COULMN = "collectionName";
    public static final String FAVORITE_COLLECTION_LANG_COULMN = "collectionLang";




    String DB_PATH = null;
    public static String DB_NAME = "hisnalmuslim1.db";
    public static int DB_VERSION = 2;
    public SQLiteDatabase myDataBase;
    private final Context myContext;
    private Cursor cursor;
    public Boolean myExist = false;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.myContext = context;
        DB_PATH = "/data/data/" + context.getPackageName() + "/" + "databases/";
    }

    public void createDataBase() throws IOException {
        boolean dbExist = checkDataBase();
        if (dbExist) {
            // do nothing - database already exist
            Log.e(TAG, "dbExist: " + dbExist);
            myExist = true;
        } else {
            // By calling this method and empty database will be created into
            // the default system path
            // of your application so we are gonna be able to overwrite that
            // database with our database.
            this.getReadableDatabase();
            try {
                this.close();
                copyDataBase();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
                //throw new Error("Error copying database");
            }
        }
    }

    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            //File file = myContext.getDatabasePath(DB_NAME);
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null,
                    SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
            e.printStackTrace();
            // database does\'t exist yet.
        }
        if (checkDB != null) {
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }

    /**
     * Copies your database from your local assets-folder to the just created
     * empty database in the system folder, from where it can be accessed and
     * handled. This is done by transfering bytestream.
     */

    private void copyDataBase() throws IOException {
        // Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(DB_NAME);
        // Path to the just created empty db
        String outFileName = DB_PATH + DB_NAME;
        // Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);
        // transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        Log.e(TAG, "myInput: " + myInput);
        while ((length = myInput.read(buffer)) > 0) {
            Log.e(TAG, "length: " + length);
            myOutput.write(buffer, 0, length);
        }
        //myDataBase.rawQuery(String.valueOf(myOutput), null);
        Log.e(TAG, "myOutput: " + myOutput);

        // Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    public void openDataBase() throws SQLException {
        // Open the database
        String myPath = DB_PATH + DB_NAME;
        //File file = myContext.getDatabasePath(DB_NAME);

        // SQLiteDatabase.NO_LOCALIZED_COLLATORS
        myDataBase = SQLiteDatabase.openDatabase(myPath, null,
                SQLiteDatabase.OPEN_READONLY
                        | SQLiteDatabase.NO_LOCALIZED_COLLATORS
                        | SQLiteDatabase.CREATE_IF_NECESSARY);


        Log.e(TAG, "myDataBase: " + myDataBase);

    }

    @Override
    public synchronized void close() {
        if (myDataBase != null)
            myDataBase.close();
        super.close();
    }

    public void open() {
        if (myDataBase != null)
            myDataBase.isOpen();
    }

    public Cursor rawQuery(String query) {
        try {
            if (myDataBase.isOpen()) {
                myDataBase.close();
            }
            myDataBase = this.getWritableDatabase();
            Log.e(TAG, "myDataBase:Read " + myDataBase);
            cursor = null;
            cursor = myDataBase.rawQuery(query, null);
            Log.e(TAG, "cursor: " + myDataBase);
        } catch (Exception e) {
            Log.e(TAG, "rawQuery:e: " + e.getMessage());
            System.out.println("DB ERROR  " + e.getMessage());
            e.printStackTrace();
        }
        return cursor;
    }

    // return cursor
    public Cursor query(String table, String[] columns, String selection,
                        String[] selectionArgs, String groupBy, String having,
                        String orderBy) {
        return myDataBase.query(table, null, null, null, null, null,
                null);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        Log.e(TAG, "onCreate");
        //db = getWritableDatabase();

        String createFavoriteTable = "CREATE TABLE "+ TABLE_FAVORITE_NAME + "("+
                FAVORITE_ID_COULMN + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                FAVORITE_COLLECTION_ID_COULMN+ " INTEGER NOT NULL, "+
                FAVORITE_COLLECTION_NAME_COULMN+" TEXT NOT NULL, "+
                FAVORITE_COLLECTION_LANG_COULMN + " TEXT NOT NULL"+
                ");";

        db.execSQL(createFavoriteTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Log.e(TAG, "onUpgrade");
        //db = getWritableDatabase();

        if (newVersion > oldVersion) {
            try {
                copyDataBase();
            } catch (IOException e) {
                e.printStackTrace();            }
        }

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITE_NAME);
        onCreate(db);
    }

}


