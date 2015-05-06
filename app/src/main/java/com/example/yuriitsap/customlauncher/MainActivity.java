package com.example.yuriitsap.customlauncher;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
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
    private GestureDetector mGestureDetector;

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
        mGestureDetector = new GestureDetector(new LauncherGestureListener());
        mRecyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return mGestureDetector.onTouchEvent(event);
            }
        });
    }


    @Override
    public void onClick(View v) {
        Log.e("TAG", "onClick");
        mItemsShown = true;

        mSpotlightView.animate().alpha(1.0f).withLayer().withEndAction(new Runnable() {
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
                        findViewById(R.id.items_menu).setVisibility(View.VISIBLE);
                        findViewById(R.id.spot_view).setVisibility(View.GONE);
                        mMenuLauncher.setVisibility(View.GONE);
                    }
                });
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
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
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

    private class LauncherGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public void onLongPress(final MotionEvent e) {
            Log.e("TAG", "onLongPress");
            final View view = mRecyclerView.findChildViewUnder(e.getX(), e.getY());
            if (view != null) {
                view.animate().scaleX(1.2f).scaleY(1.2f).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        view.setX(e.getX());
                        view.setY(e.getY());
                        mMenuItems.setVisibility(View.GONE);
                    }
                }).start();

            }
        }
        @Override
        public boolean onDown(MotionEvent e) {
            Log.e("TAG", "onDown");
            return super.onDown(e);
        }
    }
}
