package com.example.yuriitsap.customlauncher;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;

/**
 * Created by yuriitsap on 14.05.15.
 */
public class PageFragment extends Fragment {

    private static final String MATRIX_DEMENSION_KEY = "MATRIX_DEMENSION";
    private static final String PAGE_RESOLUTION_KEY = "PAGE_RESOLUTION";
    private LinearLayout mIconHolder;
    private int mMatrixDimension[] = new int[2];
    private int mScreenResolution[] = new int[2];

    public static PageFragment newInstance(int[] matrixDimension, int[] pageResolution) {
        Bundle args = new Bundle();
        PageFragment pageFragment = new PageFragment();
        args.putIntArray(MATRIX_DEMENSION_KEY, matrixDimension);
        args.putIntArray(PAGE_RESOLUTION_KEY, pageResolution);
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
        mMatrixDimension = getArguments().getIntArray(MATRIX_DEMENSION_KEY);
        mScreenResolution = getArguments().getIntArray(PAGE_RESOLUTION_KEY);
        ((GridLayout) view).setRowCount(mMatrixDimension[0]);
        ((GridLayout) view).setColumnCount(mMatrixDimension[1]);
        for (int i = mMatrixDimension[0] * mMatrixDimension[1] - 1; i >= 0; i--) {
            ((GridLayout) view).addView(LayoutInflater.from(view.getContext())
                    .inflate(R.layout.icon_item, (ViewGroup) view, false));
        }

    }
}

