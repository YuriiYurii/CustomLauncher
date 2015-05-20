package com.example.yuriitsap.customlauncher;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;


public class SettingsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_settings);
        getFragmentManager().beginTransaction()
                .add(R.id.fragment_holder, SettingsFragment.newInstance()).commit();

    }
}
