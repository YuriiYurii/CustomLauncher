package com.example.yuriitsap.customlauncher;

import android.graphics.drawable.Drawable;

/**
 * Created by yuriitsap on 29.04.15.
 */
public class AppInfo {

    private CharSequence mName;
    private Drawable mIcon;

    public CharSequence getName() {
        return mName;
    }

    public AppInfo setName(CharSequence name) {
        mName = name;
        return this;
    }

    public Drawable getIcon() {
        return mIcon;
    }

    public AppInfo setIcon(Drawable icon) {
        mIcon = icon;
        return this;
    }
}
