package com.example.yuriitsap.customlauncher;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by yuriitsap on 29.04.15.
 */
public class AppInfo extends RealmObject {

    @PrimaryKey
    private int id;
    private String label;
    private String packageName;
    private String clsName;

    public AppInfo(AppInfo appInfo) {
        this.id = appInfo.getId();
        this.label = appInfo.getLabel();
        this.packageName = appInfo.getPackageName();
        this.clsName = appInfo.getClsName();
    }

    public AppInfo() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getClsName() {
        return clsName;
    }

    public void setClsName(String clsName) {
        this.clsName = clsName;
    }
}

