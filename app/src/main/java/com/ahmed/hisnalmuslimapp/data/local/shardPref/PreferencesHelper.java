package com.ahmed.hisnalmuslimapp.data.local.shardPref;


/**
 * @author Ahmed Eid Hefny
 * @date 4/2/21
 * <p>
 * ahmedeid2026@gmail.com
 **/
public interface PreferencesHelper {

    void saveLang(String lang);

    String getLang();

    void setFontSize(int fontSize);

    int getFontSize();

    void saveHourTime(int hour);

    int getHourTime();

    void saveMinuteTime(int minute);

    int getMinuteTime();



}
