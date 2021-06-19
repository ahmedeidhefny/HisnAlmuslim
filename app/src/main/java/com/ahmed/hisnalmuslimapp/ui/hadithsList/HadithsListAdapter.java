package com.ahmed.hisnalmuslimapp.ui.hadithsList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.databinding.DataBindingUtil;

import com.ahmed.hisnalmuslimapp.R;
import com.ahmed.hisnalmuslimapp.data.local.entity.Collections;
import com.ahmed.hisnalmuslimapp.data.local.entity.Hadiths;
import com.ahmed.hisnalmuslimapp.data.local.shardPref.PreferencesHelper;
import com.ahmed.hisnalmuslimapp.databinding.ItemHadithListAdapterBinding;
import com.ahmed.hisnalmuslimapp.ui.base.DataBoundListAdapter;
import com.ahmed.hisnalmuslimapp.util.Objects;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Ahmed Eid Hefny
 * @date 1/2/21
 * <p>
 * ahmedeid2026@gmail.com
 **/
public class HadithsListAdapter extends DataBoundListAdapter<Hadiths, ItemHadithListAdapterBinding> implements Filterable {

    private final androidx.databinding.DataBindingComponent dataBindingComponent;
    private BankClickCallback callback;
    private DiffUtilDispatchedInterface diffUtilDispatchedInterface;
    private Context mContext;
    private List<Collections> contactList;
    private List<Collections> contactListFiltered;

    private String total;
    PreferencesHelper pref;
    private int counter = 1;

    public HadithsListAdapter(androidx.databinding.DataBindingComponent dataBindingComponent,
                              BankClickCallback callback,
                              String total,
                              PreferencesHelper pref,
                              DiffUtilDispatchedInterface diffUtilDispatchedInterface) {
        this.dataBindingComponent = dataBindingComponent;
        this.callback = callback;
        this.pref = pref;
        this.total = total;
        this.diffUtilDispatchedInterface = diffUtilDispatchedInterface;
    }

    @Override
    protected ItemHadithListAdapterBinding createBinding(ViewGroup parent) {

        ItemHadithListAdapterBinding binding = (ItemHadithListAdapterBinding) DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.item_hadith_list_adapter, parent, false, dataBindingComponent);
        mContext = parent.getContext();
        //        binding.getRoot().setOnClickListener(v -> {
//            Hadiths notification = binding.getHadiths();
//            if (notification != null && callback != null) {
//                callback.onClick(notification);
//            }
//        });
        return binding;
    }

    @Override
    protected void dispatched() {
        if (diffUtilDispatchedInterface != null) {
            diffUtilDispatchedInterface.onDispatched();
        }
    }

    @Override
    protected void bind(ItemHadithListAdapterBinding binding, Hadiths item, int position) {
        binding.setHadiths(item);
        //String lang = sharedPreferences.getString(SP_LANGUAGE, "both");
        int fontSize = pref.getFontSize();

//        if (lang.equalsIgnoreCase("ar")){
//            binding.tvTitle.setVisibility(View.GONE);
//            binding.tvInfoEn.setVisibility(View.GONE);
//        }else if (lang.equalsIgnoreCase("en")){
//            binding.tvTitleAr.setVisibility(View.GONE);
//            binding.tvInfoAr.setVisibility(View.GONE);
//        }

        final int[] progress = {0};
        final int[] repeat = {0};
        int totalRepeat = Integer.valueOf(item.getRepeat());

        binding.tvInfoEn.setText(item.getTitle());
        binding.tvInfoAr.setText(item.getText());
        binding.tvTotal.setText("CollectionId: " + item.getCollectionId());
        binding.tvData.setText("AzkarId: " + item.getAzkarId());

        binding.tvInfoEn.setTextSize(fontSize);
        binding.tvInfoAr.setTextSize(fontSize);
        binding.tvTotal.setTextSize(fontSize - 4);
        binding.tvData.setTextSize(fontSize - 4);

        int zkrNumber = position + 1;
        binding.numberZkrFromTo.setText("Zkr " + zkrNumber + " from " + total);
        binding.numberOfRepeat.setText("Repeat: " + item.getRepeat());


        binding.repeat.setText("" + repeat[0]);
        Log.e("reBe", "" + repeat[0]);
        binding.loadingRepeat.setProgress(progress[0]);
        Log.e("prBe", "" + progress[0]);

        binding.loadingLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //handle(totalRepeat, repeat, progress, binding, position);
                if (repeat[0] < totalRepeat) {
                    repeat[0] = repeat[0] + 1;
                    Log.e("rAf", "" + repeat[0]);
                    binding.repeat.setText("" + repeat[0]);
                    progress[0] += ((repeat[0] / totalRepeat) * 100);
                    Log.e("prAf", "" + progress[0]);
                    binding.loadingRepeat.setProgress(progress[0]);

                    if (repeat[0] == totalRepeat) {
                        if (callback != null) {
                            // if(total < (position + 1)) {
                            callback.onNavigateToNextItem(position);
                            Log.e("x2", String.valueOf(position));
                            // }
                        }
                    }

                }
            }
        });


//        if (repeat[0] == totalRepeat) {
//            Log.e("x", String.valueOf(repeat[0]));
//            if (callback != null) {
//                callback.onNavigateToNextItem(counter);
//                Log.e("c2", String.valueOf(counter));
//            }
//


        // counter++;
        //Log.e("c3", String.valueOf(counter));


    }

    private void handle(int totalRepeat, int repeat, int progress, ItemHadithListAdapterBinding binding, int position) {

    }

    @Override
    protected boolean areItemsTheSame(Hadiths oldItem, Hadiths newItem) {
        return Objects.equals(oldItem.getCollectionId(), newItem.getCollectionId())
                && oldItem.getAzkarId().equalsIgnoreCase(newItem.getAzkarId());
    }

    @Override
    protected boolean areContentsTheSame(Hadiths oldItem, Hadiths newItem) {
        return Objects.equals(oldItem.getCollectionId(), newItem.getCollectionId())
                && oldItem.getAzkarId().equalsIgnoreCase(newItem.getAzkarId());
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
        void onNavigateToNextItem(int position);
    }
}
