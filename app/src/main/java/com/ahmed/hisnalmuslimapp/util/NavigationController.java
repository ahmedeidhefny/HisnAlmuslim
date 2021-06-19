package com.ahmed.hisnalmuslimapp.util;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import androidx.fragment.app.FragmentActivity;

import com.ahmed.hisnalmuslimapp.Constants;
import com.ahmed.hisnalmuslimapp.ui.collectionList.CollectionsListActivity;
import com.ahmed.hisnalmuslimapp.ui.settings.SettingsActivity;
import com.ahmed.hisnalmuslimapp.ui.hadithsList.HadithsListActivity;

import javax.inject.Inject;

/**
 * @author Ahmed Eid Hefny
 * @date 1/2/21
 * <p>
 * ahmedeid2026@gmail.com
 **/
public class NavigationController {

    //region Variables

    //private final int containerId;
    public Uri photoURI;

    //endregion


    //region Constructor
    @Inject
    public NavigationController() {

        // This setup is for MainActivity
        //this.containerId = R.id.content_frame;
    }

    //endregion

    public void navigateToSettingsActivity(FragmentActivity activity) {
        Intent intent = new Intent(activity, SettingsActivity.class);
        activity.startActivity(intent);
    }

    public void navigateToCollectionActivity(Activity activity) {
        Intent intent = new Intent(activity, CollectionsListActivity.class);
        activity.startActivity(intent);
    }

    public void navigateToHadithsActivity(FragmentActivity activity, String collectionId, String lang, String total ) {
        Intent intent = new Intent(activity, HadithsListActivity.class);
        intent.putExtra(Constants.COLLECTION_ID, collectionId);
        intent.putExtra(Constants.lang, lang);
        intent.putExtra(Constants.TOTAL, total);
        activity.startActivity(intent);
    }

}
