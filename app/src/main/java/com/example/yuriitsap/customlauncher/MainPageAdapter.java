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
 * Created by yuriitsap on 07.05.15.
 */
public class MainPageAdapter extends RecyclerView.Adapter<MainPageAdapter.ItemHolder> {

    private List<AppInfo> mMainPageItems;

    public MainPageAdapter(List<AppInfo> mainPageItems) {
        mMainPageItems = mainPageItems;
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        holder.mImageView.setImageDrawable(mMainPageItems.get(position).getIcon());
        holder.mTextView.setText(mMainPageItems.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mMainPageItems.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder implements View.OnDragListener {

        public ImageView mImageView;
        public TextView mTextView;
        private View mParent;

        public ItemHolder(View itemView) {
            super(itemView);

            mParent = itemView;
            mImageView = (ImageView) itemView.findViewById(R.id.app_icon_logo);
            mTextView = (TextView) itemView.findViewById(R.id.app_text);
            itemView.setOnDragListener(this);
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
                    mMainPageItems.set(getPosition(), new AppInfo().setIcon(
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

    public void insertItem(AppInfo item) {
        mMainPageItems.add(item);
        notifyItemChanged(mMainPageItems.size() - 1);
    }

    public void deleteItem(AppInfo item) {
        notifyItemRemoved(mMainPageItems.indexOf(item));
        mMainPageItems.remove(item);
    }

    public void setMainPageItems(
            List<AppInfo> mainPageItems) {
        mMainPageItems = mainPageItems;
        notifyDataSetChanged();
    }
}
