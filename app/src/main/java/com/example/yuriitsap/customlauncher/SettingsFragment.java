package com.example.yuriitsap.customlauncher;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Created by yuriitsap on 20.05.15.
 */
public class SettingsFragment extends PreferenceFragment {

    public static PreferenceFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.settings_fragment);
    }
}
