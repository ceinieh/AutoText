package com.example.pocdynamictext.cards.pager;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pocdynamictext.R;
import com.example.pocdynamictext.model.FontSize;
import com.example.pocdynamictext.model.Item;

import java.util.ArrayList;
import java.util.List;

public class FontSizeAdapter extends RecyclerView.Adapter<FontSizeAdapter.FontSizeViewHolder> {
    private List<FontSize> mFontSizeList;
    private int mSelectedItem = 0;
    private FontSizeViewHolder fontSizeViewHolder;
    private Callback callback;


    public FontSizeAdapter(List<FontSize> sizeList, Callback callback) {
        this.mFontSizeList = sizeList;
        this.callback = callback;
    }


    @Override
    public FontSizeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.font_size_item, parent, false);
        fontSizeViewHolder = new FontSizeViewHolder(itemView);
        return fontSizeViewHolder;
    }

    @Override
    public void onBindViewHolder(FontSizeViewHolder holder, int position) {
        FontSize item = mFontSizeList.get(position);
        holder.fontSize.setText(item.getFontSize()+"pt");

        if(item.isSelected()){
            mSelectedItem = position;
            holder.selected.setVisibility(View.VISIBLE);
            holder.imgInfo.setVisibility(View.VISIBLE);
        } else {
            holder.selected.setVisibility(View.INVISIBLE);
            holder.imgInfo.setVisibility(View.INVISIBLE);
        }
    }

    public void updateItems(List<FontSize> items) {
        this.mFontSizeList = items;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mFontSizeList.size();
    }

    public class FontSizeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView fontSize;
        ImageView selected;
        ImageView imgInfo;

        public FontSizeViewHolder(View itemView) {
            super(itemView);
            fontSize = (TextView) itemView.findViewById(R.id.font_size);
            selected = (ImageView) itemView.findViewById(R.id.img_check);
            imgInfo = (ImageView) itemView.findViewById(R.id.img_information);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            updateSelection(position);
           callback.onItemSelected(position);
        }

        private void updateSelection(int position) {
            if(position != mSelectedItem) {
                mFontSizeList.get(mSelectedItem).setSelected(false);
                notifyItemChanged(mSelectedItem);
                mSelectedItem = position;
                mFontSizeList.get(position).setSelected(true);
                notifyItemChanged(position);
            }
        }
    }

    public interface Callback {
        void onItemSelected(int position);
    }

}
