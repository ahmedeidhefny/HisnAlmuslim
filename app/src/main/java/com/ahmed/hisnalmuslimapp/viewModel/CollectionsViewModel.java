package com.ahmed.hisnalmuslimapp.viewModel;

import com.ahmed.hisnalmuslimapp.data.local.sqliteDb.DBManager;
import com.ahmed.hisnalmuslimapp.data.local.entity.Collections;
import com.ahmed.hisnalmuslimapp.ui.base.BaseViewModel;

import java.util.List;

import javax.inject.Inject;

/**
 * @author Islam Elshnawey
 * @date 4/10/20
 * <p>
 * is.elshnawey@gmail.com
 **/
public class CollectionsViewModel extends BaseViewModel {

    private DBManager dbManager;


    @Inject
    public CollectionsViewModel(DBManager dbManager) {
        this.dbManager = dbManager;
    }

    public List<Collections> getCollectionsList(String lang) {
        return dbManager.getCollectionsByLanguage(lang);
    }


}
