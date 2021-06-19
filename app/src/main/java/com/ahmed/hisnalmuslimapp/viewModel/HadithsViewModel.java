package com.ahmed.hisnalmuslimapp.viewModel;

import com.ahmed.hisnalmuslimapp.data.local.sqliteDb.DBManager;
import com.ahmed.hisnalmuslimapp.data.local.entity.Hadiths;
import com.ahmed.hisnalmuslimapp.ui.base.BaseViewModel;

import java.util.List;

import javax.inject.Inject;

/**
 * @author Ahmed Eid Hefny
 * @date 4/2/21
 * <p>
 * ahmedeid2026@gmail.com
 **/
public class HadithsViewModel extends BaseViewModel {

    private DBManager dbManager ;

    @Inject
    public HadithsViewModel(DBManager dbManager) {
        this.dbManager =  dbManager;
    }


    public List<Hadiths> getHadiths(String collectionId) {
        return dbManager.getAzkarByCollectionId(collectionId);
    }

}
