package com.example.yuriitsap.customlauncher;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.ClipData;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity
        implements View.OnClickListener, RecyclerViewAdapter.ClickCallbacks {

    private final Intent mMainIntent = new Intent(Intent.ACTION_MAIN, null)
            .addCategory(Intent.CATEGORY_LAUNCHER);
    private ImageView mMenuLauncher;
    private boolean mItemsShown = false;
    private SpotlightView mSpotlightView;
    private ArrayList<AppInfo> mMainPageItemsDummy, mInstalledApps;
    private Button[] mDots;
    private int mMatrixDimension[] = {3, 3};
    private LinearLayout mDotsLayout;
    private ViewPager mDesktopItems, mMenuItems;
    private boolean mResolutionsSaved = false;
    private int mPageResolution[] = new int[2];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        initAppList();
        mMenuLauncher = (ImageView) findViewById(R.id.menu_popup_launcher);
        mMenuLauncher.setOnClickListener(this);
        mDotsLayout = (LinearLayout) findViewById(R.id.dots_layout);
        mDesktopItems = (ViewPager) findViewById(R.id.desktop_items);
        mDesktopItems.setAdapter(new MenuPagerAdapter(getSupportFragmentManager()));
        mDesktopItems.setOnPageChangeListener(new PageStateListener());
        initDots();
        for (Button button : mDots) {
            mDotsLayout.addView(button);
        }
        if (!this.getSharedPreferences(getString(R.string.preference_file_name), MODE_PRIVATE)
                .getBoolean(getString(R.string.resolutions_saved), false)) {
            mDesktopItems.getViewTreeObserver().addOnPreDrawListener(
                    new ViewTreeObserver.OnPreDrawListener() {
                        @Override
                        public boolean onPreDraw() {
                            mDesktopItems.getViewTreeObserver().removeOnPreDrawListener(this);
                            SharedPreferences.Editor editor = MainActivity.this
                                    .getSharedPreferences(getString(R.string.preference_file_name),
                                            MODE_PRIVATE).edit();
                            editor.putInt(getString(R.string.page_width), mDesktopItems.getWidth());
                            editor.putInt(getString(R.string.page_height),
                                    mDesktopItems.getHeight());
                            editor.putBoolean(getString(R.string.resolutions_saved), true);
                            editor.commit();
                            mResolutionsSaved = true;
                            return true;
                        }
                    });

        } else {
            mPageResolution[0] = this
                    .getSharedPreferences(getString(R.string.preference_file_name), MODE_PRIVATE)
                    .getInt(getString(R.string.page_width), 0);
            mPageResolution[1] = this
                    .getSharedPreferences(getString(R.string.preference_file_name), MODE_PRIVATE)
                    .getInt(getString(R.string.page_height), 0);


        }
        mMenuItems = (ViewPager) findViewById(R.id.all_items_menu);
        mSpotlightView = (SpotlightView) findViewById(R.id.spot_view);
    }

    @Override
    public void onClick(View v) {
        mItemsShown = true;
        mSpotlightView.createShader();
        mSpotlightView.setMaskX(v.getLeft());
        mSpotlightView.setMaskY(v.getBottom());
        findViewById(R.id.spot_view).setVisibility(View.VISIBLE);
        mMenuLauncher.animate().alpha(0.0f).start();
        mDesktopItems.animate().alpha(0.0f).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                final ObjectAnimator superScale = ObjectAnimator
                        .ofFloat(mSpotlightView, "maskScale",
                                mSpotlightView.computeMaskScale(
                                        Math.max(mSpotlightView.getHeight(),
                                                mSpotlightView.getWidth()) * 2.7f));
                superScale.setDuration(250);
                AnimatorSet set = new AnimatorSet();
                set.play(superScale);
                set.start();
                set.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mDesktopItems.setVisibility(View.INVISIBLE);
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

    public void initDots() {
        mDots = new Button[mDesktopItems.getAdapter().getCount()];
        for (int i = mDesktopItems.getAdapter().getCount() - 1; i >= 0; i--) {
            mDots[i] = new Button(this);
            mDots[i].setBackground(getResources().getDrawable(R.drawable.rounded_cell));
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(25, 25);
            layoutParams.setMargins(3, 3, 3, 3);
            mDots[i].setLayoutParams(layoutParams);
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

    public void initAppList() {
        mMainPageItemsDummy = new ArrayList<>();
        mInstalledApps = new ArrayList<>();
        for (ResolveInfo resolveInfo : MainActivity.this.getPackageManager()
                .queryIntentActivities(mMainIntent, 0)) {
            Bitmap bitmap = ((BitmapDrawable) resolveInfo.loadIcon(getPackageManager()))
                    .getBitmap();
            mInstalledApps.add(new AppInfo()
                    .setLabel(resolveInfo.loadLabel(MainActivity.this.getPackageManager()))
                    .setPackageName(resolveInfo.activityInfo.packageName)
                    .setClsName(resolveInfo.activityInfo.name).setHolder(false));
        }
    }

    private void hideMenu(final View view) {
        mSpotlightView.createShader();
        mMenuLauncher.animate().alpha(1.0f).start();
        mDesktopItems.animate().alpha(1.0f).setListener(new AnimatorListenerAdapter() {
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
                        findViewById(R.id.desktop_items).setVisibility(View.VISIBLE);
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

    private class MenuPagerAdapter extends FragmentStatePagerAdapter {


        public MenuPagerAdapter(FragmentManager fm) {
            super(fm);
        }


        @Override
        public Fragment getItem(int position) {
            int[] y = {3, 3};
            return PageFragment.newInstance(y, mPageResolution, mInstalledApps, position + 1);
        }


        @Override
        public int getCount() {
            return 6;
        }
    }

    private class PageStateListener extends ViewPager.SimpleOnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            mDots[position].getBackground()
                    .setColorFilter(Color.rgb((int) (255 * positionOffset),
                                    (int) (255 * positionOffset), (int) (255 * positionOffset)),
                            PorterDuff.Mode.MULTIPLY);
            mDots[position == mDots.length - 1 ? position - 1 : position + 1].getBackground()
                    .setColorFilter(Color.rgb((int) (255 - 255 * positionOffset),
                                    (int) (255 - 255 * positionOffset),
                                    (int) (255 - 255 * positionOffset)),
                            PorterDuff.Mode.MULTIPLY);
        }
    }
}
