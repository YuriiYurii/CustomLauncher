package com.example.yuriitsap.customlauncher;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by yuriitsap on 25.05.15.
 */
public class DesktopPageFragment extends Fragment implements View.OnDragListener {

    private Page mPage;

    public static DesktopPageFragment newInstance() {

        return new DesktopPageFragment();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.desktop_item_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.setOnDragListener(this);

    }

    @Override
    public boolean onDrag(View v, DragEvent event) {
        switch (event.getAction()) {
            case DragEvent.ACTION_DRAG_STARTED:
                Log.e("TAG", "ACTION_DRAG_STARTED");
                break;
            case DragEvent.ACTION_DRAG_ENTERED:
                v.setBackgroundResource(R.drawable.shape_drop_target);
                Log.e("TAG", "ACTION_DRAG_ENTERED");
                break;
            case DragEvent.ACTION_DRAG_EXITED:
                v.setBackgroundResource(R.drawable.shape);
                break;
            case DragEvent.ACTION_DROP:
                View view = (View) event.getLocalState();
                Log.e("TAG", "ACTION_DROP");
                break;
            case DragEvent.ACTION_DRAG_ENDED:
                v.setBackgroundResource(R.drawable.shape);
                break;
            default:
                break;
        }
        return true;
    }

    public DesktopPageFragment setPage(Page page) {
        mPage = page;
        return this;
    }
}
