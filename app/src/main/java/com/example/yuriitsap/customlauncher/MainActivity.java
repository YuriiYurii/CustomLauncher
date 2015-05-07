package com.example.yuriitsap.customlauncher;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.ClipData;
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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class MainActivity extends ActionBarActivity
        implements View.OnClickListener, RecyclerViewAdapter.OnLongClickCallback {

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
        Log.e("TAG", "onClick");
        mItemsShown = true;
        mSpotlightView.setMaskX(mSpotlightView.getWidth() / 2.0f);
        mSpotlightView.setMaskY(mSpotlightView.getHeight() / 2.0f);

        mMainPageItems.animate().alpha(0.0f).withLayer().withEndAction(new Runnable() {
            @Override
            public void run() {
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
        });
    }


    @Override
    public void onBackPressed() {
        if (mItemsShown) {
            hideMenu();
            mItemsShown = false;
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (mItemsShown) {
            hideMenu();
            mItemsShown = false;
        }
    }

    @Override
    public void onItemLongClick(final View item) {
        hideMenu();
        final ClipData clipData = ClipData.newPlainText("descripion",
                (CharSequence) ((TextView) item.findViewById(R.id.app_text)).getText());
        final View.DragShadowBuilder dragShadowBuilder = new View.DragShadowBuilder(item);
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
                        item.startDrag(clipData, dragShadowBuilder, item, 0);
                    }
                });
            }
        });


    }

    public List<AppInfo> getAppList() {
        List<AppInfo> appInfoList = new LinkedList<>();
        mMainPageItemsDummy = new ArrayList<>(16);
        int i = 0;
        for (ResolveInfo resolveInfo : MainActivity.this.getPackageManager()
                .queryIntentActivities(mMainIntent, 0)) {
            i++;
            appInfoList.add(new AppInfo()
                    .setIcon(resolveInfo.loadIcon(MainActivity.this.getPackageManager()))
                    .setName(resolveInfo.loadLabel(MainActivity.this.getPackageManager())));
            mMainPageItemsDummy.add(new AppInfo()
                    .setIcon(getResources().getDrawable(R.drawable.shape))
                    .setName(""));
        }
        return appInfoList;
    }

    private void hideMenu() {
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
                    }
                });
            }
        });
    }
}
