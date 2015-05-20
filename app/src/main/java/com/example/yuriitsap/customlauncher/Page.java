package com.example.yuriitsap.customlauncher;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by yuriitsap on 20.05.15.
 */
public class Page extends RealmObject {

    @PrimaryKey
    private int id;

    private int position;

    private RealmList<AppInfo> apps;

    public Page(Page page) {
        id = page.getId();
        position = page.getPosition();
        apps = page.getApps();
    }

    public Page() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public RealmList<AppInfo> getApps() {
        return apps;
    }

    public void setApps(RealmList<AppInfo> apps) {
        this.apps = apps;
    }
}
