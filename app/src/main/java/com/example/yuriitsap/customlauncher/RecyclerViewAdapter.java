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
    private OnLongClickCallback mOnLongClickCallback;

    public RecyclerViewAdapter(List<AppInfo> installedApps,
            OnLongClickCallback onLongClickCallback) {
        mInstalledApps = installedApps;
        mOnLongClickCallback = onLongClickCallback;
    }

    @Override
    public AppHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AppHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item, parent, false));
    }

    @Override
    public void onBindViewHolder(AppHolder holder, int position) {
        holder.mAppLogo.setImageDrawable(mInstalledApps.get(position).getIcon());
        holder.mAppName.setText(mInstalledApps.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mInstalledApps.size();
    }

    public class AppHolder extends RecyclerView.ViewHolder
            implements View.OnLongClickListener,View.OnDragListener{

        private ImageView mAppLogo;
        private TextView mAppName;
        private View mParent;

        public AppHolder(View itemView) {
            super(itemView);

            mParent = itemView;
            mAppLogo = (ImageView) itemView.findViewById(R.id.app_icon_logo);
            mAppName = (TextView) itemView.findViewById(R.id.app_text);
            itemView.setOnLongClickListener(this);
            itemView.setOnDragListener(this);
        }

        @Override
        public boolean onLongClick(View v) {
            mOnLongClickCallback.onItemLongClick(v);
            return true;
        }

        @Override
        public boolean onDrag(View v, DragEvent event) {
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    Log.e("TAG", "ACTION_DRAG_STARTED");
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    Log.e("TAG", "ACTION_DRAG_ENTERED");
                    mParent.setBackgroundResource(R.drawable.shape_drop_target);
                    //no action necessary
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    Log.e("TAG", "ACTION_DRAG_EXITED");
                    mParent.setBackgroundResource(R.drawable.shape);
                    //no action necessary
                    break;
                case DragEvent.ACTION_DROP:
                    View view = (View) event.getLocalState();
                    mInstalledApps.set(getPosition(), new AppInfo().setIcon(
                            ((ImageView) view.findViewById(R.id.app_icon_logo)).getDrawable())
                            .setName(((TextView) view.findViewById(R.id.app_text)).getText()));
                    notifyItemChanged(getPosition());
                    Log.e("TAG", "ACTION_DROP");
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    Log.e("TAG", "ACTION_DRAG_ENDED");
                    mParent.setBackgroundResource(R.drawable.shape);
                    //no action necessary
                    break;
                default:
                    break;
            }
            return true;
        }
    }

    public interface OnLongClickCallback {

        void onItemLongClick(View item);
    }
}
