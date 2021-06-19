package com.ahmed.hisnalmuslimapp.ui.settings;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.VisibleForTesting;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.ahmed.hisnalmuslimapp.R;
import com.ahmed.hisnalmuslimapp.data.binding.FragmentDataBindingComponent;
import com.ahmed.hisnalmuslimapp.databinding.FragmentSettingsBinding;
import com.ahmed.hisnalmuslimapp.ui.base.BaseFragment;
import com.ahmed.hisnalmuslimapp.ui.base.DataBoundListAdapter;
import com.ahmed.hisnalmuslimapp.util.AutoClearedValue;
import com.ahmed.hisnalmuslimapp.util.Utils;

import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends BaseFragment implements DataBoundListAdapter.DiffUtilDispatchedInterface {

    //region Variables

    private final androidx.databinding.DataBindingComponent dataBindingComponent =
            new FragmentDataBindingComponent(this);

    @VisibleForTesting
    private AutoClearedValue<FragmentSettingsBinding> binding;

    private String country;

    //endregion

    //region Constructor

    public SettingsFragment() {
        // Required empty public constructor
    }

    //endregion

    //region Overrides
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentSettingsBinding dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false, dataBindingComponent);
        binding = new AutoClearedValue<>(this, dataBinding);

        return binding.get().getRoot();
    }

    @Override
    public void onDispatched() {
    }

    @Override
    protected void initUIAndActions() {
    }

    @Override
    protected void initViewModels() {

    }

    @Override
    protected void initAdapters() {

    }

    @Override
    protected void initData() {

        selectLanguage();

        fontSize();

        loadData();

        try {
            Utils.log(">>>> On initData.");
        } catch (NullPointerException ne) {
            Utils.errorLog("Null Pointer Exception.", ne);
        } catch (Exception e) {
            Utils.errorLog("Error in getting notification flag data.", e);
        }
    }

    private void selectLanguage() {
        binding.get().selectLanguageLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CharSequence[] items = new CharSequence[]{
                        getString(R.string.english_language),
                        getString(R.string.arabic_language),
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(getString(R.string.choose_language));
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (i == 0) {
                            setLocate("en");
                        } else if (i == 1) {
                            setLocate("ar");
                        }

                    }
                });
                builder.show();
            }
        });
    }

    private void fontSize() {
        binding.get().fontSizeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CharSequence[] items = getActivity().getResources().getStringArray(R.array.font_size);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(getString(R.string.choose_font));
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int fontSize = Integer.parseInt((String) items[i]);
                        changeFontSize(fontSize);
                    }
                });
                builder.show();
            }

        });
    }

    private void changeFontSize(int fontSize) {
        pref.setFontSize(fontSize);
        navigationController.navigateToCollectionActivity(getActivity());
    }

    private void setLocate(String lang) {

        setLocalization(lang);

        saveStateToLocal(lang);
    }

    private void setLocalization(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        getBaseActivity().getResources().updateConfiguration(configuration, getBaseActivity().getResources().getDisplayMetrics());

    }

    private void saveStateToLocal(String lang) {
        pref.saveLang(lang);
        navigationController.navigateToCollectionActivity(getActivity());
    }

    //endregion

    //region Private Methods

    private void loadData() {
        getLanguage();
        getFontSize();

        //Toast.makeText(getActivity(), "" + lang, Toast.LENGTH_SHORT).show();
    }

    private void getFontSize() {
        int fontSize = pref.getFontSize();
        if (fontSize == 16) {
            binding.get().fontSizeValue.setText(fontSize + "sp Default");
        } else {
            binding.get().fontSizeValue.setText(fontSize + "sp");
        }
        //binding.get().fontSizeValue.setTextSize(fontSize);
        //binding.get().languageValue.setTextSize(fontSize);
        //binding.get().settingsTxt.setTextSize(fontSize + 2);
        //binding.get().selectLangTxt.setTextSize(fontSize);
        //binding.get().fontSizeTxt.setTextSize(fontSize);
    }

    private void getLanguage() {
        String lang = pref.getLang();
        if (lang.equalsIgnoreCase("ar")) {
            binding.get().languageValue.setText(getString(R.string.arabic_language));
        } else if (lang.equalsIgnoreCase("en")) {
            binding.get().languageValue.setText(getString(R.string.english_language));
        }
    }

    //endregion

}
