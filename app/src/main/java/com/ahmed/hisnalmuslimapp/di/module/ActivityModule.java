package com.ahmed.hisnalmuslimapp.di.module;


import com.ahmed.hisnalmuslimapp.ui.favorite.FavoriteActivity;
import com.ahmed.hisnalmuslimapp.ui.favorite.favoriteFragment;
import com.ahmed.hisnalmuslimapp.ui.settings.SettingsActivity;
import com.ahmed.hisnalmuslimapp.ui.settings.SettingsFragment;
import com.ahmed.hisnalmuslimapp.ui.hadithsList.HadithsListActivity;
import com.ahmed.hisnalmuslimapp.ui.hadithsList.HadithsListFragment;
import com.ahmed.hisnalmuslimapp.ui.collectionList.CollectionsListActivity;
import com.ahmed.hisnalmuslimapp.ui.collectionList.CollectionsListFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Since we are using the dagger-android support library,
 * we can make use of Android Injection.
 * The ActivityModule generates AndroidInjector(this is the new dagger-android class which exist in dagger-android framework)
 * for Activities defined in this class.
 * This allows us to inject things into Activities using AndroidInjection.
 * inject(this) in the onCreate() method.
 */
@Module
public abstract class ActivityModule {

    @ContributesAndroidInjector()
    abstract CollectionsListFragment contributeBankListFragment();

    @ContributesAndroidInjector()
    abstract CollectionsListActivity contributeWorldListActivity();

    @ContributesAndroidInjector()
    abstract favoriteFragment contributeBooksListFragment();

    @ContributesAndroidInjector()
    abstract FavoriteActivity contributeBooksListActivity();

    @ContributesAndroidInjector()
    abstract HadithsListFragment contributeHadithsListFragment();

    @ContributesAndroidInjector()
    abstract HadithsListActivity contributehadithsListActivity();

    @ContributesAndroidInjector()
    abstract SettingsFragment contributePersonalInfoFragment();

    @ContributesAndroidInjector()
    abstract SettingsActivity contributeDetailsActivity();


}