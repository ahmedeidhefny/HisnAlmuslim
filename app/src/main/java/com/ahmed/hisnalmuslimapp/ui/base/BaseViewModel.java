package com.ahmed.hisnalmuslimapp.ui.base;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ahmed.hisnalmuslimapp.util.Utils;

/**
 * @author Ahmed Eid Hefny
 * @date 1/2/21
 * <p>
 * ahmedeid2026@gmail.com
 **/
public class BaseViewModel extends ViewModel {

    public Utils.LoadingDirection loadingDirection = Utils.LoadingDirection.none;
    private final MutableLiveData<Boolean> loadingState = new MutableLiveData<>();

    public int offset = 0;

    public int page = 1;

    public boolean forceEndLoading = false;
    public boolean isLoading = false;


    //region For loading status
    public void setLoadingState(Boolean state) {
        isLoading = state;
        loadingState.setValue(state);
    }

    public MutableLiveData<Boolean> getLoadingState() {
        return loadingState;
    }

    //endregion
}
