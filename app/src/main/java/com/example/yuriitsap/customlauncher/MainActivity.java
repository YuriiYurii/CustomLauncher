package com.example.yuriitsap.customlauncher;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.util.LinkedList;
import java.util.List;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    private final Intent mMainIntent = new Intent(Intent.ACTION_MAIN, null)
            .addCategory(Intent.CATEGORY_LAUNCHER);
    private ImageView mMenuLauncher;
    private RecyclerView mRecyclerView;
    private boolean mItemsShown = false;
    private SpotlightView mSpotlightView;
    private View mMenuItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        getAppList();
        mMenuLauncher = (ImageView) findViewById(R.id.menu_popup_launcher);
        mMenuLauncher.setOnClickListener(this);
        mMenuItems = findViewById(R.id.items_menu);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setAdapter(new RecyclerViewAdapter(getAppList()));
        GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this, 4,
                LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mSpotlightView = (SpotlightView) findViewById(R.id.spot_view);
    }

    @Override
    public void onClick(View v) {
        Log.e("TAG", "onClick");
        mItemsShown = true;
        mMenuLauncher.animate().scaleX(1.2f).scaleY(1.2f).setDuration(100).setListener(
                new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mMenuLauncher.setVisibility(View.GONE);
                        mMenuItems.setVisibility(View.VISIBLE);
                        ObjectAnimator superScale = ObjectAnimator.ofFloat(mSpotlightView, "maskScale",
                                mSpotlightView.computeMaskScale(
                                        Math.max(mSpotlightView.getHeight(), mSpotlightView.getWidth()) * 1.7f));
                        superScale.setDuration(2000);
                        ObjectAnimator scaleUp = ObjectAnimator
                                .ofFloat(mSpotlightView, "maskScale", 0.5f, 0.5f * 3.0f);
                        scaleUp.setDuration(2000);

                    }
                });
    }

    @Override
    public void onBackPressed() {
        if (mItemsShown) {
            mMenuLauncher.setVisibility(View.VISIBLE);
            mMenuItems.setVisibility(View.INVISIBLE);
            mItemsShown = false;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mItemsShown) {
            mMenuLauncher.setVisibility(View.VISIBLE);
            mMenuItems.setVisibility(View.INVISIBLE);
            mItemsShown = false;
        }
    }

    private List<AppInfo> getAppList() {
        List<AppInfo> appInfoList = new LinkedList<>();
        for (ResolveInfo resolveInfo : MainActivity.this.getPackageManager()
                .queryIntentActivities(mMainIntent, 0)) {
            appInfoList.add(new AppInfo()
                    .setIcon(resolveInfo.loadIcon(MainActivity.this.getPackageManager()))
                    .setName(resolveInfo.loadLabel(MainActivity.this.getPackageManager())));
        }

        return appInfoList;
    }
}
