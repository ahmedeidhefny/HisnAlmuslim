package com.ahmed.hisnalmuslimapp.data.local.shardPref;

import android.content.Context;
import android.content.SharedPreferences;

import com.ahmed.hisnalmuslimapp.Constants;

import javax.inject.Inject;

/**
 * @author Ahmed Eid Hefny
 * @date 5/2/21
 * <p>
 * ahmedeid2026@gmail.com
 **/

public class AppPreferencesHelper implements PreferencesHelper {


    private final SharedPreferences mPrefs;

    @Inject
    public AppPreferencesHelper(Context context, @PreferenceInfo String prefFileName) {
        mPrefs = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE);
    }


    @Override
    public void saveLang(String lang) {
        mPrefs.edit().putString(Constants.SP_LANGUAGE, lang).apply();
    }

    @Override
    public String getLang() {
        return mPrefs.getString(Constants.SP_LANGUAGE, "en");
    }

    @Override
    public void setFontSize(int fontSize) {
        mPrefs.edit().putInt(Constants.SP_FONT_SIZE, fontSize).apply();
    }

    @Override
    public int getFontSize() {
        return mPrefs.getInt(Constants.SP_FONT_SIZE, 16);
    }

    @Override
    public void saveHourTime(int hour) {
        mPrefs.edit().putInt(Constants.SP_HOUR, hour).apply();

    }

    @Override
    public int getHourTime() {
        return mPrefs.getInt(Constants.SP_HOUR, 0);
    }

    @Override
    public void saveMinuteTime(int minute) {
        mPrefs.edit().putInt(Constants.SP_MINUTE, minute).apply();

    }

    @Override
    public int getMinuteTime() {
        return mPrefs.getInt(Constants.SP_MINUTE, 0);
    }
}
