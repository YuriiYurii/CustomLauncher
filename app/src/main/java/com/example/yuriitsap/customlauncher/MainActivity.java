package com.example.yuriitsap.customlauncher;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.LinkedList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    final Intent mainIntent = new Intent(Intent.ACTION_MAIN, null)
            .addCategory(Intent.CATEGORY_LAUNCHER);

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        getAppList();
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setAdapter(new RecyclerViewAdapter(getAppList()));
        GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this, 4,
                LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(gridLayoutManager);


    }

    private List<AppInfo> getAppList() {
        List<AppInfo> appInfoList = new LinkedList<>();
        for (ResolveInfo resolveInfo : MainActivity.this.getPackageManager()
                .queryIntentActivities(mainIntent, 0)) {
            appInfoList.add(new AppInfo()
                    .setIcon(resolveInfo.loadIcon(MainActivity.this.getPackageManager()))
                    .setName(resolveInfo.loadLabel(MainActivity.this.getPackageManager())));
        }

        return appInfoList;
    }
}
