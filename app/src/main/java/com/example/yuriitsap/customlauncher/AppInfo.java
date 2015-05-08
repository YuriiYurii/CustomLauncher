package com.example.yuriitsap.customlauncher;

import android.graphics.drawable.Drawable;

/**
 * Created by yuriitsap on 29.04.15.
 */
public class AppInfo {

    private CharSequence mLabel;
    private Drawable mIcon;
    private String mPackageName;
    private String mClsName;
    private boolean mHolder;

    public AppInfo(AppInfo app) {
        mLabel = app.getLabel();
        mIcon = app.getIcon();
        mPackageName = app.getPackageName();
        mClsName = app.getClsName();
        mHolder = app.isHolder();
    }

    public AppInfo() {
    }

    public CharSequence getLabel() {
        return mLabel;
    }

    public AppInfo setLabel(CharSequence name) {
        mLabel = name;
        return this;
    }

    public Drawable getIcon() {
        return mIcon;
    }

    public AppInfo setIcon(Drawable icon) {
        mIcon = icon;
        return this;
    }

    public String getPackageName() {
        return mPackageName;
    }

    public AppInfo setPackageName(String packageName) {
        mPackageName = packageName;
        return this;
    }

    public String getClsName() {
        return mClsName;
    }

    public AppInfo setClsName(String appName) {
        mClsName = appName;
        return this;
    }

    public boolean isHolder() {
        return mHolder;
    }

    public AppInfo setHolder(boolean holder) {
        mHolder = holder;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AppInfo appInfo = (AppInfo) o;

        if (mHolder != appInfo.mHolder) {
            return false;
        }
        if (mLabel != null ? !mLabel.equals(appInfo.mLabel) : appInfo.mLabel != null) {
            return false;
        }
        if (mIcon != null ? !mIcon.equals(appInfo.mIcon) : appInfo.mIcon != null) {
            return false;
        }
        if (mPackageName != null ? !mPackageName.equals(appInfo.mPackageName)
                : appInfo.mPackageName != null) {
            return false;
        }
        return !(mClsName != null ? !mClsName.equals(appInfo.mClsName) : appInfo.mClsName != null);

    }

    @Override
    public int hashCode() {
        int result = mLabel != null ? mLabel.hashCode() : 0;
        result = 31 * result + (mIcon != null ? mIcon.hashCode() : 0);
        result = 31 * result + (mPackageName != null ? mPackageName.hashCode() : 0);
        result = 31 * result + (mClsName != null ? mClsName.hashCode() : 0);
        result = 31 * result + (mHolder ? 1 : 0);
        return result;
    }
}
