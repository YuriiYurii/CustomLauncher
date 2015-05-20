package com.example.yuriitsap.customlauncher;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by yuriitsap on 29.04.15.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.AppHolder> {

    private List<AppInfo> mInstalledApps;
    private ClickCallbacks mClickCallbacks;

    public RecyclerViewAdapter(List<AppInfo> installedApps,
            ClickCallbacks ClickCallbacks) {
        mInstalledApps = installedApps;
        mClickCallbacks = ClickCallbacks;
    }

    @Override
    public AppHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AppHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.icon_item, parent, false));
    }

    @Override
    public void onBindViewHolder(AppHolder holder, int position) {
        holder.mAppName.setText(mInstalledApps.get(position).getLabel());
    }

    @Override
    public int getItemCount() {
        return mInstalledApps.size();
    }

    public class AppHolder extends RecyclerView.ViewHolder
            implements View.OnLongClickListener, View.OnDragListener, View.OnClickListener {

        private ImageView mAppLogo;
        private TextView mAppName;

        public AppHolder(View itemView) {
            super(itemView);

            mAppLogo = (ImageView) itemView.findViewById(R.id.app_icon_logo);
            mAppName = (TextView) itemView.findViewById(R.id.app_text);
            itemView.setOnLongClickListener(this);
            itemView.setOnDragListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public boolean onLongClick(View v) {
            v.setTag(R.integer.APP_VIEW_KEY, mInstalledApps.get(getPosition()));
            mClickCallbacks.onItemLongClick(v);
            return true;
        }

        @Override
        public boolean onDrag(View v, DragEvent event) {
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    v.setBackgroundResource(R.drawable.shape_drop_target);
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    v.setBackgroundResource(R.drawable.shape);
                    break;
                case DragEvent.ACTION_DROP:
                    View view = (View) event.getLocalState();
                    if (mInstalledApps.get(getPosition())
                            .equals(view.getTag(R.integer.APP_VIEW_KEY))) {
                        break;
                    }
                    mInstalledApps.set(getPosition(), new AppInfo(
                            (AppInfo) view.getTag(R.integer.APP_VIEW_KEY)));
                    notifyItemChanged(getPosition());
                    Log.e("TAG", "size = " + mInstalledApps.size());
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    v.setBackgroundResource(R.drawable.shape);
                    break;
                default:
                    break;
            }
            return true;
        }

        @Override
        public void onClick(View v) {
//            if (!mInstalledApps.get(getPosition()).isHolder()) {
//                mClickCallbacks.onItemClick(mInstalledApps.get(getPosition()));
//            }
        }
    }

    public interface ClickCallbacks {

        void onItemLongClick(View item);

        void onItemClick(AppInfo info);
    }

}
