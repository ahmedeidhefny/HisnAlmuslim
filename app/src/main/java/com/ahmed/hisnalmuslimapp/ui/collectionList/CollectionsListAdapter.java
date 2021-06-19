package com.ahmed.hisnalmuslimapp.ui.collectionList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import androidx.databinding.DataBindingComponent;
import androidx.databinding.DataBindingUtil;

import com.ahmed.hisnalmuslimapp.R;
import com.ahmed.hisnalmuslimapp.data.local.sqliteDb.DBManager;
import com.ahmed.hisnalmuslimapp.data.local.entity.Collections;
import com.ahmed.hisnalmuslimapp.data.local.entity.Favorite;
import com.ahmed.hisnalmuslimapp.databinding.ItemCollectionListAdapterBinding;
import com.ahmed.hisnalmuslimapp.ui.base.DataBoundListAdapter;
import com.ahmed.hisnalmuslimapp.util.Objects;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Ahmed Eid Hefny
 * @date 5/2/21
 * <p>
 * ahmedeid2026@gmail.com
 **/
public class CollectionsListAdapter extends DataBoundListAdapter<Collections, ItemCollectionListAdapterBinding> implements Filterable {

    private final androidx.databinding.DataBindingComponent dataBindingComponent;
    private BankClickCallback callback;
    private DataBoundListAdapter.DiffUtilDispatchedInterface diffUtilDispatchedInterface;
    private Context mContext;
    private List<Collections> contactList;
    private List<Collections> contactListFiltered;
    private DBManager dbManager;

    public CollectionsListAdapter(DataBindingComponent dataBindingComponent,
                                  BankClickCallback callback,
                                  DiffUtilDispatchedInterface diffUtilDispatchedInterface,
                                  DBManager dbManager) {
        this.dataBindingComponent = dataBindingComponent;
        this.callback = callback;
        this.dbManager = dbManager;
        this.diffUtilDispatchedInterface = diffUtilDispatchedInterface;
    }

    @Override
    protected ItemCollectionListAdapterBinding createBinding(ViewGroup parent) {

        ItemCollectionListAdapterBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.item_collection_list_adapter, parent, false, dataBindingComponent);
        mContext = parent.getContext();

        binding.getRoot().setOnClickListener(v -> {
            Collections notification = binding.getCollections();
            if (notification != null && callback != null) {
                callback.onClick(notification);
            }
        });
        return binding;
    }

    @Override
    protected void dispatched() {
        if (diffUtilDispatchedInterface != null) {
            diffUtilDispatchedInterface.onDispatched();
        }
    }

    @Override
    protected void bind(ItemCollectionListAdapterBinding binding, Collections item, int position) {
        binding.setCollections(item);

        String lang = item.getLanguage();
        if (lang.equalsIgnoreCase("ar")) {
            binding.tvTitle.setVisibility(View.GONE);
        } else {
            binding.tvTitleAr.setVisibility(View.GONE);
        }
        binding.tvTitle.setText(item.getTitle());
        binding.tvTitleAr.setText(item.getTitle());

        Favorite favorite = dbManager.searchFavorite(item.getCollectionId(), item.getLanguage());

        if (favorite != null) {
            binding.bookmark.setVisibility(View.VISIBLE);
            binding.bookmarkBorder.setVisibility(View.GONE);
        } else {
            binding.bookmark.setVisibility(View.GONE);
            binding.bookmarkBorder.setVisibility(View.VISIBLE);
        }


        binding.bookmarkBorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveAsFavorite(binding, item);

            }
        });

        binding.bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteAsFavorite(binding, item);
            }
        });


    }

    private void saveAsFavorite(ItemCollectionListAdapterBinding binding, Collections item) {
        Favorite favorite = new Favorite(item.getCollectionId(), item.getTitle(), item.getLanguage());
        Boolean result = dbManager.saveFavorite(favorite);
        if (result == true) {
            binding.bookmark.setVisibility(View.VISIBLE);
            binding.bookmarkBorder.setVisibility(View.GONE);
            Toast.makeText(mContext, mContext.getString(R.string.added_fav), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mContext, mContext.getString(R.string.cannot_added_fav), Toast.LENGTH_SHORT).show();
        }

    }

    private void deleteAsFavorite(ItemCollectionListAdapterBinding binding, Collections item) {

        Boolean result = dbManager.deleteFavorite(item.getCollectionId(), item.getLanguage());
        if (result == true) {
            binding.bookmarkBorder.setVisibility(View.VISIBLE);
            binding.bookmark.setVisibility(View.GONE);
            Toast.makeText(mContext, mContext.getString(R.string.delete_fav), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mContext, mContext.getString(R.string.cannot_delete_fav), Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected boolean areItemsTheSame(Collections oldItem, Collections newItem) {
        return Objects.equals(oldItem.getTitle(), newItem.getTitle())
                && oldItem.getCollectionId() == (newItem.getCollectionId());
    }

    @Override
    protected boolean areContentsTheSame(Collections oldItem, Collections newItem) {
        return Objects.equals(oldItem.getTitle(), newItem.getTitle())
                && oldItem.getCollectionId() == (newItem.getCollectionId());
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    contactListFiltered = contactList;
                } else {
                    List<Collections> filteredList = new ArrayList<>();
                    for (Collections row : contactList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getTitle().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    contactListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = contactListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                contactListFiltered = (ArrayList<Collections>) filterResults.values;

                // refresh the list with filtered data
                notifyDataSetChanged();
            }
        };
    }

    public interface BankClickCallback {
        void onClick(Collections notification);
    }
}
