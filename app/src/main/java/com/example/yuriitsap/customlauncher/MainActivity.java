package com.example.yuriitsap.customlauncher;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.ClipData;
import android.content.ComponentName;
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


public class MainActivity extends ActionBarActivity
        implements View.OnClickListener, RecyclerViewAdapter.ClickCallbacks {

    private final Intent mMainIntent = new Intent(Intent.ACTION_MAIN, null)
            .addCategory(Intent.CATEGORY_LAUNCHER);
    private ImageView mMenuLauncher;
    private RecyclerView mMenuItems, mMainPageItems;
    private boolean mItemsShown = false;
    private SpotlightView mSpotlightView;
    private List<AppInfo> mMainPageItemsDummy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        getAppList();
        mMenuLauncher = (ImageView) findViewById(R.id.menu_popup_launcher);
        mMenuLauncher.setOnClickListener(this);
        mMainPageItems = (RecyclerView) findViewById(R.id.main_page_items);
        mMainPageItems.setAdapter(new RecyclerViewAdapter(mMainPageItemsDummy, this));
        mMainPageItems.setLayoutManager(new GridLayoutManager(MainActivity.this, 4,
                LinearLayoutManager.HORIZONTAL, false));
        mMenuItems = (RecyclerView) findViewById(R.id.recycler_view);
        mMenuItems.setAdapter(new RecyclerViewAdapter(getAppList(), this));
        mMenuItems.setLayoutManager(new GridLayoutManager(MainActivity.this, 4,
                LinearLayoutManager.HORIZONTAL, false));
        mSpotlightView = (SpotlightView) findViewById(R.id.spot_view);
    }

    @Override
    public void onClick(View v) {
        mItemsShown = true;
        mSpotlightView.createShader();
        mSpotlightView.setMaskX(mSpotlightView.getWidth() / 2.0f);
        mSpotlightView.setMaskY(mSpotlightView.getHeight() / 2.0f);
        findViewById(R.id.spot_view).setVisibility(View.VISIBLE);
        mMenuLauncher.animate().alpha(0.0f).start();
        mMainPageItems.animate().alpha(0.0f).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                final ObjectAnimator superScale = ObjectAnimator
                        .ofFloat(mSpotlightView, "maskScale",
                                mSpotlightView.computeMaskScale(
                                        Math.max(mSpotlightView.getHeight(),
                                                mSpotlightView.getWidth()) * 1.7f));
                superScale.setDuration(250);
                AnimatorSet set = new AnimatorSet();
                set.play(superScale);
                set.start();
                set.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mMainPageItems.setVisibility(View.INVISIBLE);
                        mMenuLauncher.setVisibility(View.INVISIBLE);
                        findViewById(R.id.spot_view).setVisibility(View.INVISIBLE);
                        findViewById(R.id.items_menu).setVisibility(View.VISIBLE);
                    }
                });
            }
        }).start();
    }

    @Override
    public void onBackPressed() {
        if (mItemsShown) {
            hideMenu(null);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (mItemsShown) {
            hideMenu(null);
        }
    }

    @Override
    public void onItemLongClick(final View item) {
            hideMenu(item);
    }

    @Override
    public void onItemClick(AppInfo info) {
        ComponentName componentName = new ComponentName(info.getPackageName(), info.getClsName());
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        intent.setComponent(componentName);
        startActivity(intent);
    }

    public List<AppInfo> getAppList() {
        List<AppInfo> appInfoList = new LinkedList<>();
        mMainPageItemsDummy = new LinkedList<>();
        for (ResolveInfo resolveInfo : MainActivity.this.getPackageManager()
                .queryIntentActivities(mMainIntent, 0)) {
            appInfoList.add(new AppInfo()
                    .setIcon(resolveInfo.loadIcon(MainActivity.this.getPackageManager()))
                    .setLabel(resolveInfo.loadLabel(MainActivity.this.getPackageManager()))
                    .setPackageName(resolveInfo.activityInfo.packageName)
                    .setClsName(resolveInfo.activityInfo.name).setHolder(false));
            mMainPageItemsDummy.add(new AppInfo()
                    .setIcon(getResources().getDrawable(R.drawable.shape))
                    .setLabel("").setHolder(true));
        }
        return appInfoList;
    }

    private void hideMenu(final View view) {
        mSpotlightView.createShader();
        mMenuLauncher.animate().alpha(1.0f).start();
        mMainPageItems.animate().alpha(1.0f).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mSpotlightView.setVisibility(View.VISIBLE);
                findViewById(R.id.items_menu).setVisibility(View.INVISIBLE);
                final ObjectAnimator superScale = ObjectAnimator
                        .ofFloat(mSpotlightView, "maskScale",
                                0.0f);
                superScale.setDuration(250);
                AnimatorSet set = new AnimatorSet();
                Log.e("TAG", "entered");
                set.play(superScale);
                set.start();
                set.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mSpotlightView.setVisibility(View.INVISIBLE);
                        findViewById(R.id.main_page_items).setVisibility(View.VISIBLE);
                        mMenuLauncher.setVisibility(View.VISIBLE);
                        mItemsShown = false;
                        if (view != null) {
                            AppInfo appInfo = (AppInfo) view.getTag(R.integer.APP_VIEW_KEY);
                            final ClipData clipData = ClipData.newPlainText("label",
                                    appInfo.getLabel());
                            final View.DragShadowBuilder dragShadowBuilder
                                    = new View.DragShadowBuilder(view);
                            view.startDrag(clipData, dragShadowBuilder, view, 0);
                        }
                    }
                });
            }
        });
    }
}
