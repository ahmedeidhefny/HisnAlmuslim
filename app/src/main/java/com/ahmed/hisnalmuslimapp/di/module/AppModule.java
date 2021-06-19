package com.ahmed.hisnalmuslimapp.di.module;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.ahmed.hisnalmuslimapp.Constants;
import com.ahmed.hisnalmuslimapp.data.local.sqliteDb.DBManager;
import com.ahmed.hisnalmuslimapp.data.local.shardPref.AppPreferencesHelper;
import com.ahmed.hisnalmuslimapp.data.local.shardPref.PreferenceInfo;
import com.ahmed.hisnalmuslimapp.data.local.shardPref.PreferencesHelper;
import com.ahmed.hisnalmuslimapp.util.Connectivity;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author Ahmed Eid Hefny
 * @date 1/2/21
 * <p>
 * ahmedeid2026@gmail.com
 **/
@Module
public class AppModule {

    @Singleton
    @Provides
    Connectivity provideConnectivity(Application app) {
        return new Connectivity(app);
    }



    @Singleton
    @Provides
    DBManager provideDBManager(Application application) {
        return new DBManager(application);
    }

    @Singleton
    @Provides
    SharedPreferences provideSharedPreferences(Application app) {
        return PreferenceManager.getDefaultSharedPreferences(app.getApplicationContext());
    }


    @Provides
    @Singleton
    Context provideContext(Application application) {
        return application;
    }

    @Provides
    @PreferenceInfo
    String providePreferenceName() {
        return Constants.PREF_NAME;
    }

    @Provides
    @Singleton
    PreferencesHelper providePreferencesHelper(AppPreferencesHelper appPreferencesHelper) {
        return appPreferencesHelper;
    }



}
