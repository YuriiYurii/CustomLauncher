package com.example.yuriitsap.customlauncher;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by yuriitsap on 14.05.15.
 */
public class PageFragment extends Fragment {

    private static final String MATRIX_DEMENSION_KEY = "MATRIX_DEMENSION";

    private int mMatrixDimension[] = new int[2];

    public static PageFragment newInstance(int[] matrixDimension) {
        Bundle args = new Bundle();
        PageFragment pageFragment = new PageFragment();
        args.putIntArray(MATRIX_DEMENSION_KEY, matrixDimension);
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
        LinearLayout linearLayout = new LinearLayout(view.getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                0, 1.0f);
        linearLayout.setLayoutParams(layoutParams);
    }
}
