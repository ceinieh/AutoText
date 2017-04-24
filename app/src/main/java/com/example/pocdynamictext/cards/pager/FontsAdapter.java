package com.example.pocdynamictext.cards.pager;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pocdynamictext.R;
import com.example.pocdynamictext.model.Item;

import java.util.List;

public class FontsAdapter extends RecyclerView.Adapter<FontsAdapter.ViewHolder> {
    private List<Item> mItems;
    private int mSelectedItem;
    private Callback callback;

    public FontsAdapter(List<Item> items, Callback callback) {
        this.mItems = items;
        this.callback = callback;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.font_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Item item = mItems.get(position);
        holder.fontName.setText(item.getFamily());
        if(item.isSelected()){
            mSelectedItem = position;
            holder.selected.setVisibility(View.VISIBLE);
        } else {
            holder.selected.setVisibility(View.GONE);
        }
    }

    public void updateItems(List<Item> items) {
        this.mItems = items;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView fontName;
        ImageView selected;

        public ViewHolder(View itemView) {
            super(itemView);
            fontName = (TextView) itemView.findViewById(R.id.font_name);
            selected = (ImageView) itemView.findViewById(R.id.font_selected);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            updateSelection(position);
            callback.onItemSelected(position, mItems.get(position).getFiles().getRegular());
        }

        private void updateSelection(int position) {
            if(position != mSelectedItem) {
                mItems.get(mSelectedItem).setSelected(false);
                notifyItemChanged(mSelectedItem);
                mSelectedItem = position;
                mItems.get(position).setSelected(true);
                notifyItemChanged(position);
            }
        }
    }

    public interface Callback {
        void onItemSelected(int position, String url);
    }

}
