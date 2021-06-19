package com.ahmed.hisnalmuslimapp.ui.collectionList;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.ui.AppBarConfiguration;

import com.google.android.material.navigation.NavigationView;
import com.ahmed.hisnalmuslimapp.R;
import com.ahmed.hisnalmuslimapp.databinding.ActivityCollectionsBinding;
import com.ahmed.hisnalmuslimapp.ui.base.BaseActivity;
import com.ahmed.hisnalmuslimapp.ui.favorite.favoriteFragment;
import com.ahmed.hisnalmuslimapp.ui.settings.SettingsFragment;

import java.util.Locale;
/**
 * @author Ahmed Eid Hefny
 * @date 5/2/21
 * <p>
 * ahmedeid2026@gmail.com
 **/
public class CollectionsListActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {


    private AppBarConfiguration mAppBarConfiguration;
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    //region Variables
    //endregion

    //region Override Methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collections);

        ActivityCollectionsBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_collections);

        // Init all UI
        initUI(binding);

    }

    @Override
    protected void onStart() {
        super.onStart();
        // handle app language
        setLanguage();
    }


    //endregion

    //region Private Methods

    private void initUI(ActivityCollectionsBinding binding) {

        toolbar = binding.appBarMain.mainToolbar;
        drawer = binding.drawerLayout;
        navigationView = binding.navView;

        // Toolbar
        initToolbar(toolbar, getString(R.string.app_name));

        // side navigation menu
        initSideNavigationMenu();

    }

    private void setLanguage() {

        String lang = pref.getLang();
        setLocalization(lang);
    }

    private void setLocalization(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        getResources().updateConfiguration(configuration, getResources().getDisplayMetrics());

    }

    private void initSideNavigationMenu() {//ActivityDetailsBinding binding
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawer,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close) {
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        //navigationView.getMenu().getItem(0).setChecked(true);
        navigationView.setNavigationItemSelectedListener(this);
        onNavigationItemSelected(navigationView.getMenu().getItem(0));

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        mAppBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.nav_home, R.id.nav_fav)
//                .setDrawerLayout(drawer)
//                .build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        NavigationUI.setupWithNavController(toolbar, navController);
//        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
//        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Toast.makeText(CollectionsListActivity.this, "" + item.getItemId(), Toast.LENGTH_SHORT).show();
        int id = item.getItemId();
        if (id == R.id.nav_home) {

            Toast.makeText(CollectionsListActivity.this, "Home", Toast.LENGTH_SHORT).show();
            setupFragment(new CollectionsListFragment());
            drawer.closeDrawers();
            return true;
        } else if (id == R.id.nav_fav) {

            Toast.makeText(CollectionsListActivity.this, "Favorites", Toast.LENGTH_SHORT).show();
            setupFragment(new favoriteFragment());
            drawer.closeDrawers();
            return true;
        } else if (id == R.id.nav_settings) {

            Toast.makeText(CollectionsListActivity.this, "Settings", Toast.LENGTH_SHORT).show();
            setupFragment(new SettingsFragment());
            drawer.closeDrawers();
            return true;
        }


        // open drawer at start
        //drawer.openDrawer(GravityCompat.START);
        return false;
    }

//    @Override
//    public boolean onSupportNavigateUp() {
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
//                || super.onSupportNavigateUp();
//    }


}

