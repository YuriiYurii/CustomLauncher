package com.example.yuriitsap.customlauncher;

import android.support.v7.widget.RecyclerView;
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

    public RecyclerViewAdapter(List<AppInfo> installedApps) {
        mInstalledApps = installedApps;
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

    public class AppHolder extends RecyclerView.ViewHolder {

        private ImageView mAppLogo;
        private TextView mAppName;

        public AppHolder(View itemView) {
            super(itemView);

            mAppLogo = (ImageView) itemView.findViewById(R.id.app_icon_logo);
            mAppName = (TextView) itemView.findViewById(R.id.app_text);
        }
    }
}
