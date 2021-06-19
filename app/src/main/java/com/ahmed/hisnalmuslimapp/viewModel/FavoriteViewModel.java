package com.ahmed.hisnalmuslimapp.viewModel;

import com.ahmed.hisnalmuslimapp.data.local.sqliteDb.DBManager;
import com.ahmed.hisnalmuslimapp.data.local.entity.Collections;
import com.ahmed.hisnalmuslimapp.data.local.entity.Favorite;
import com.ahmed.hisnalmuslimapp.ui.base.BaseViewModel;

import java.util.List;

import javax.inject.Inject;

/**
 * @author Ahmed Eid Hefny
 * @date 15/2/21
 * <p>
 * ahmedeid2026@gmail.com
 **/
public class FavoriteViewModel extends BaseViewModel {

    private DBManager dbManager;

    @Inject
    public FavoriteViewModel(DBManager dbManager) {

        this.dbManager = dbManager;

    }

    public List<Favorite> getFavorites() {
        return dbManager.getAllFavorites();
    }

    public Collections getCollectionsList(String collectionId) {
        return dbManager.getCollectionById(collectionId);
    }
}
