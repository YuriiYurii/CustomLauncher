package com.example.yuriitsap.customlauncher;


import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by yuriitsap on 14.05.15.
 */
public class PageFragment extends Fragment
        implements View.OnDragListener, AdapterView.OnItemLongClickListener,
        AdapterView.OnItemClickListener, View.OnLongClickListener {

    private static final String MATRIX_DEMENSION_KEY = "MATRIX_DEMENSION";
    private static final String PAGE_RESOLUTION_KEY = "PAGE_RESOLUTION";
    private int mMatrixDimension[] = new int[2];
    private int mScreenResolution[] = new int[2];
    private ClickCallbacks mClickCallbacks;
    private Page mPage;

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
        ((GridView) view).setNumColumns(mMatrixDimension[0]);
        ((GridView) view).setAdapter(new DesktopAdapter());
        ((GridView) view).setOnItemClickListener(this);
        ((GridView) view).setOnItemLongClickListener(this);

    }


    @Override
    public boolean onDrag(View v, DragEvent event) {
        return false;
    }

    public PageFragment setCallbacks(ClickCallbacks callbacks) {
        mClickCallbacks = callbacks;
        return this;
    }

    public PageFragment setPage(Page page) {
        mPage = page;
        return this;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        view.setTag(R.integer.APP_VIEW_KEY, mPage.getApps().get(position));
        mClickCallbacks.singleClick(view);

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        view.setTag(R.integer.APP_VIEW_KEY, mPage.getApps().get(position));
        mClickCallbacks.longClick(view);
        return true;
    }

    @Override
    public boolean onLongClick(View v) {
        Log.e("TAG", "onLongClick");
        return true;
    }

    public interface ClickCallbacks {

        boolean longClick(View view);

        void singleClick(View view);
    }

    private class DesktopAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return mPage.getApps().size();
        }

        @Override
        public Object getItem(int position) {
            return mPage.getApps().get(position);
        }

        @Override
        public long getItemId(int position) {
            return mPage.getApps().get(position).getId();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Holder holder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.icon_item, parent, false);
                holder = new Holder();
                holder.mTextView = (TextView) convertView.findViewById(R.id.app_text);
                holder.mImageView = (ImageView) convertView.findViewById(R.id.app_icon_logo);
                ViewGroup.LayoutParams layoutParams = convertView.getLayoutParams();
                layoutParams.width = mScreenResolution[0] / mMatrixDimension[0];
                layoutParams.height = mScreenResolution[1] / mMatrixDimension[1];
                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }
            try {
                holder.mTextView.setText(mPage.getApps().get(position).getLabel());
                holder.mImageView.setImageDrawable(getActivity().getPackageManager()
                        .getApplicationIcon(mPage.getApps().get(position).getPackageName()));
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }

            return convertView;
        }
    }

    private static class Holder {

        public ImageView mImageView;
        public TextView mTextView;

    }
}

