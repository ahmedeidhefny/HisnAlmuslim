package com.ahmed.hisnalmuslimapp.ui.favorite;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ahmed.hisnalmuslimapp.R;
import com.ahmed.hisnalmuslimapp.data.binding.FragmentDataBindingComponent;
import com.ahmed.hisnalmuslimapp.data.local.entity.Collections;
import com.ahmed.hisnalmuslimapp.data.local.entity.Favorite;
import com.ahmed.hisnalmuslimapp.databinding.FragmentCollectionListBinding;
import com.ahmed.hisnalmuslimapp.ui.base.BaseFragment;
import com.ahmed.hisnalmuslimapp.ui.base.DataBoundListAdapter;
import com.ahmed.hisnalmuslimapp.util.AutoClearedValue;
import com.ahmed.hisnalmuslimapp.util.Utils;
import com.ahmed.hisnalmuslimapp.viewModel.FavoriteViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ahmed Eid Hefny
 * @date 10/2/21
 * <p>
 * ahmedeid2026@gmail.com
 **/
public class favoriteFragment extends BaseFragment implements DataBoundListAdapter.DiffUtilDispatchedInterface {

    private final androidx.databinding.DataBindingComponent dataBindingComponent =
            new FragmentDataBindingComponent(this);

    private FavoriteViewModel favoriteViewModel;

    public FavoriteAdapter nvAdapter;

    private List<Collections> collectionsList;


    @VisibleForTesting
    private AutoClearedValue<FragmentCollectionListBinding> binding;
    private AutoClearedValue<FavoriteAdapter> adapter;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentCollectionListBinding dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_collection_list, container, false, dataBindingComponent);
        binding = new AutoClearedValue<>(this, dataBinding);

        setHasOptionsMenu(true);
        return binding.get().getRoot();
    }

    @Override
    public void onDispatched() {
        if (favoriteViewModel.loadingDirection == Utils.LoadingDirection.top) {

            if (binding.get().notificationList != null) {

                LinearLayoutManager layoutManager = (LinearLayoutManager)
                        binding.get().notificationList.getLayoutManager();

                if (layoutManager != null) {
                    layoutManager.scrollToPosition(0);
                }
            }
        }
    }

    @Override
    protected void initUIAndActions() {
        collectionsList = new ArrayList<>();

    }

    @Override
    protected void initViewModels() {
        favoriteViewModel = new ViewModelProvider(this, viewModelFactory).get(FavoriteViewModel.class);
    }

    @Override
    protected void initAdapters() {

        nvAdapter = new FavoriteAdapter(dataBindingComponent,
                collections ->
                        navigationController.navigateToHadithsActivity(getActivity(),
                                collections.getCollectionId(),
                                collections.getLanguage(),
                                collections.getTotal()),
                this,
                pref,
                dbManager);

        this.adapter = new AutoClearedValue<>(this, nvAdapter);
        binding.get().notificationList.setAdapter(nvAdapter);
    }

    @Override
    protected void initData() {
        LoadData();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_search) {
            navigationController.navigateToSettingsActivity(getActivity());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void LoadData() {


        List<Favorite> favorites = favoriteViewModel.getFavorites();

        if (favorites != null) {
            // Update the data
            for (int i = 0; i < favorites.size(); i++) {
                String collectionId = favorites.get(i).getCollectionId();
                Collections collections = favoriteViewModel.getCollectionsList(collectionId);
                if (collections != null) {
                    collectionsList.add(collections);
                }
            }
            replaceData(collectionsList);

        } else {
            Utils.log("Favorite list are empty");
            Toast.makeText(getActivity(), "No Favorites Found..!", Toast.LENGTH_SHORT).show();
        }


        favoriteViewModel.setLoadingState(false);


    }

    private void replaceData(List<Collections> list) {

        adapter.get().replace(list);
        binding.get().executePendingBindings();

    }
}
