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
    private LinearLayout mIconHolder;
    private int mMatrixDimension[] = new int[2];

    public static PageFragment newInstance(int[] matrixDimension) {
        Bundle args = new Bundle();
        PageFragment pageFragment = new PageFragment();
        args.putIntArray(MATRIX_DEMENSION_KEY, matrixDimension);
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
        int[] matrix = getArguments().getIntArray(MATRIX_DEMENSION_KEY);
        ((GridLayout) view).setRowCount(4);

        for (int i = 10; i >= 0; i--) {

            GridLayout.Spec row1 = GridLayout.spec(1);
            GridLayout.Spec col0 = GridLayout.spec(1);
            GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams(row1, col0);
            ((GridLayout) view).addView(LayoutInflater.from(view.getContext())
                    .inflate(R.layout.icon_item, (GridLayout) view, false));
        }
    }
//        int[] matrix = getArguments().getIntArray(MATRIX_DEMENSION_KEY);
//        for (int i = matrix[0]; i > 0; i--) {
//            LinearLayout linearLayout = new LinearLayout(view.getContext());
//            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
//                    ViewGroup.LayoutParams.MATCH_PARENT,
//                    0, 1.0f);
//            linearLayout.setLayoutParams(layoutParams);
//            for (int j = matrix[1]; j > 0; j--) {
//                linearLayout.addView(LayoutInflater.from(view.getContext()).inflate(
//                        R.layout.icon_item, linearLayout, false));
//            }
//            ((LinearLayout) view).addView(linearLayout);
}

