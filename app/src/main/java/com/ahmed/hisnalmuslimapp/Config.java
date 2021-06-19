package com.ahmed.hisnalmuslimapp;

public class Config {

    /**
     * API Version
     * For your app, you need to change according based on your apip version
     */
    public static String API_VERSION = "v1/";

    /**
     * API KEY
     */
    public static String API_KEY = "eN6jMPrGlF96aF7h9kNt96UBAHwI47NG57gLfQND";

    /**
     * BASE URL
     */
    public static String BASE_URl = "https://hisnalmuslim.helloworldtechs.com/public/hisn/api/v1/";
            //"https://api.sunnah.com/" + API_VERSION;

    /**
     * APP Setting
     * Set false, your app is production
     * It will turn off the logging Process
     */
    public static boolean IS_DEVELOPMENT = true;

    /**
     * Loading Limit Count Setting
     */
    public static int NOTI_LIST_COUNT = 30;

    public static int PAGING_LIMIT = 20;

    /**
     * Region playstore
     */
    public static String PLAYSTORE_MARKET_URL_FIX = "market://details?id=";
    public static String PLAYSTORE_HTTP_URL_FIX = "http://play.google.com/store/apps/details?id=";

    /**
     * Image Cache and Loading
     */
    public static int IMAGE_CACHE_LIMIT = 250; // Mb
    public static boolean PRE_LOAD_FULL_IMAGE = true;

    /**
     * Policy Url
     */
    public static String POLICY_URL = "";

    /**
     * URI Authority File
     */
    public static String AUTHORITYFILE = ".fileprovider";


}
