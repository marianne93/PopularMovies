package com.example.popularmovies.common.base;

import android.support.v4.app.Fragment;
import android.view.View;

import com.linkdev.gafi_eservices.common.helpers.AppPreferences;
import com.linkdev.gafi_eservices.common.helpers.LocalizationHelper;
import com.linkdev.gafi_eservices.common.helpers.Parser;

public abstract class FragmentBase extends Fragment {
    
    protected abstract void initializeViews(View v);

    protected abstract void setListeners();

    public String getLang() {
        return LocalizationHelper.getLanguage(getActivity());
    }

    public String getToken() {
        try {
            return Parser.getInstance().getUserObject(AppPreferences.getString(AppPreferences.KEY_USER_INFO, getActivity(), null)).getToken();
        } catch (Exception exc) {
            return null;
        }

    }
}
