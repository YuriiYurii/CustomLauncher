package com.example.yuriitsap.customlauncher;


import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by yuriitsap on 14.05.15.
 */
public class PageFragment extends Fragment
        implements View.OnTouchListener, View.OnClickListener {

    private static final String MATRIX_DEMENSION_KEY = "MATRIX_DEMENSION";
    private static final String PAGE_RESOLUTION_KEY = "PAGE_RESOLUTION";
    private static final String PAGE_POSITION = "PAGE_POSITION";
    private LinearLayout mIconHolder;
    private static ArrayList<AppInfo> mApps;
    private int mMatrixDimension[] = new int[2];
    private int mScreenResolution[] = new int[2];
    private GestureDetector mGestureDetector = new GestureDetector(new WallpaperGestureListener());

    public static PageFragment newInstance(int[] matrixDimension, int[] pageResolution,
            ArrayList<AppInfo> pageItems, int page) {
        Bundle args = new Bundle();
        PageFragment pageFragment = new PageFragment();
        args.putIntArray(MATRIX_DEMENSION_KEY, matrixDimension);
        args.putIntArray(PAGE_RESOLUTION_KEY, pageResolution);
        mApps = pageItems;
        args.putInt(PAGE_POSITION, page);
        pageFragment.setArguments(args);

        return pageFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.desktop_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.setOnTouchListener(this);

        mMatrixDimension = getArguments().getIntArray(MATRIX_DEMENSION_KEY);
        mScreenResolution = getArguments().getIntArray(PAGE_RESOLUTION_KEY);
        int page = getArguments().getInt(PAGE_POSITION);
        int items = mMatrixDimension[0] * mMatrixDimension[1];
        ((GridLayout) view).setRowCount(mMatrixDimension[1]);
        ((GridLayout) view).setColumnCount(mMatrixDimension[0]);

        for (int i = items * page - 1; i >= (items * page) - items; i--) {
            GridLayout.Spec rowSpec = GridLayout
                    .spec((i % (items)) / mMatrixDimension[0]);
            Log.e("TAG", "i = " + i);
            GridLayout.Spec columnSpec = GridLayout.spec(i % mMatrixDimension[1]);
            GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams(rowSpec, columnSpec);
            layoutParams.height = mScreenResolution[1] / mMatrixDimension[1];
            layoutParams.width = mScreenResolution[0] / mMatrixDimension[0];
            View appHolder = LayoutInflater.from(view.getContext())
                    .inflate(R.layout.icon_item, (ViewGroup) view, false);
            appHolder.findViewById(R.id.app_icon_logo).setOnClickListener(this);
            try {
                ((ImageView) appHolder.findViewById(R.id.app_icon_logo)).setImageDrawable(
                        getActivity().getPackageManager()
                                .getApplicationIcon(mApps.get(i).getPackageName()));
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            ((TextView) appHolder.findViewById(R.id.app_text))
                    .setText(mApps.get(i).getLabel());
            ((GridLayout) view).addView(appHolder, layoutParams);
        }

    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.e("TAG", "only when child return false!");
        return mGestureDetector.onTouchEvent(event);
    }

    @Override
    public void onClick(View v) {
        Log.e("TAG", "child click");
    }

    private class WallpaperGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public void onLongPress(MotionEvent e) {
            Log.e("TAG", "onLongPress");

        }
        

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            Log.e("TAG", "onScroll");
            return super.onScroll(e1, e2, distanceX, distanceY);
        }
    }
}

