package com.ahmed.hisnalmuslimapp.ui.hadithsList;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.ahmed.hisnalmuslimapp.R;
import com.ahmed.hisnalmuslimapp.databinding.ActivityHomeBinding;
import com.ahmed.hisnalmuslimapp.ui.base.BaseActivity;

/**
 * @author Ahmed Eid Hefny
 * @date 1/2/21
 * <p>
 * ahmedeid2026@gmail.com
 **/
public class HadithsListActivity extends BaseActivity {

    //region Variables
    //endregion

    //region Override Methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityHomeBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_home);

        // Init all UI
        initUI(binding);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.content_frame);
        if (fragment != null) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    //endregion

    //region Private Methods

    private void initUI(ActivityHomeBinding binding) {

        // Toolbar
        initToolbar(binding.toolbar,getString(R.string.hadiths));

        // setup Fragment
        setupFragment(new HadithsListFragment());
        // Or you can call like this
        //setupFragment(new NewsListFragment(), R.id.content_frame);

    }

    //endregion



}

