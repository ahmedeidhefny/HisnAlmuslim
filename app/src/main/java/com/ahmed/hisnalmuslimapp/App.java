package com.ahmed.hisnalmuslimapp;

import android.content.Context;

import androidx.multidex.MultiDexApplication;

import com.ahmed.hisnalmuslimapp.data.local.sqliteDb.DatabaseHelper;
import com.ahmed.hisnalmuslimapp.di.AppInjector;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasAndroidInjector;


/**
 * @author Ahmed Eid Hefny
 * @date 1/2/21
 * <p>
 * ahmedeid2026@gmail.com
 **/
public class App extends MultiDexApplication implements HasAndroidInjector {

    @Inject
    DispatchingAndroidInjector<Object> dispatchingAndroidInjector;

    @Override
    public AndroidInjector<Object> androidInjector() {
        return dispatchingAndroidInjector;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        AppInjector.init(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        DatabaseHelper myDbHelper = new DatabaseHelper(this);

        try {
            myDbHelper.createDataBase();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Error("Unable to create database");
        }

        try {
            myDbHelper.openDataBase();

        } catch (Exception e) {
            e.printStackTrace();
            throw new Error("Unable to Open database");
        }

        //myDbHelper.getWritableDatabase();

        if (myDbHelper.myExist == false){
            myDbHelper.onUpgrade(myDbHelper.myDataBase, myDbHelper.DB_VERSION, myDbHelper.DB_VERSION);
        }

        //myDbHelper.onCreate(myDbHelper.myDataBase);

    }
}
