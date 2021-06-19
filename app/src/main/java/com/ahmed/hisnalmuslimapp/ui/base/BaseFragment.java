package com.ahmed.hisnalmuslimapp.ui.base;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.ahmed.hisnalmuslimapp.R;
import com.ahmed.hisnalmuslimapp.data.local.shardPref.PreferencesHelper;
import com.ahmed.hisnalmuslimapp.data.local.sqliteDb.DBManager;
import com.ahmed.hisnalmuslimapp.di.Injectable;
import com.ahmed.hisnalmuslimapp.util.Connectivity;
import com.ahmed.hisnalmuslimapp.util.NavigationController;

import javax.inject.Inject;

/**
 * @author Ahmed Eid Hefny
 * @date 1/2/21
 * <p>
 * ahmedeid2026@gmail.com
 **/

public abstract class BaseFragment extends Fragment implements Injectable {

    @Inject
    protected Connectivity connectivity;

    @Inject
    protected PreferencesHelper pref;

    @Inject
    protected DBManager dbManager;

    @Inject
    protected ViewModelProvider.Factory viewModelFactory;

    @Inject
    protected NavigationController navigationController;

    private BaseActivity mActivity;
    private Dialog mProgressDialog;

    private boolean isFadeIn = false;
    protected String cameraType;


    //region Override Methods

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (mActivity != null) {
            //Tools.setSystemBarColorPrimaryDark(mActivity);
        }

        initViewModels();

        initUIAndActions();

        initAdapters();

        initData();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity) {
            BaseActivity activity = (BaseActivity) getActivity();
            this.mActivity = activity;
        }
    }

    //endregion

    protected abstract void initUIAndActions();

    protected abstract void initViewModels();

    protected abstract void initAdapters();

    protected abstract void initData();

    protected void fadeIn(View view) {

        if (!isFadeIn) {
            view.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fade_in));
            isFadeIn = true; // Fade in will do only one time.
        }
    }

    //endregion


    public void hideKeyboard() {
        if (mActivity != null) {
            mActivity.hideKeyboard();
        }
    }

    @Override
    public void onDetach() {
        mActivity = null;
        super.onDetach();
    }

    public BaseActivity getBaseActivity() {
        return mActivity;
    }



}
