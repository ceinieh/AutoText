package com.example.pocdynamictext.cards.pager;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pocdynamictext.R;
import com.example.pocdynamictext.model.FontSize;
import com.example.pocdynamictext.model.Item;
import com.example.pocdynamictext.model.Result;

import java.util.ArrayList;
import java.util.List;


public class FontSizesFragment extends Fragment implements FontSizeAdapter.Callback {
    private OnFontSizeChangeListener mFontSizeChangeListener;
    private FontSizeAdapter mFontSizerAdapter;
    private RecyclerView  mRecyclerView;
    private FontSize fSize;
    private int[] sizes = new int[]{14,24,36,44, 48, 56, 62, 72};
    private List<FontSize> sizeList;
    private int mFontSizeResult;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.font_sizes_fragment, container, false);
        sizeList = new ArrayList<>();
        for(int size: sizes){
            fSize = new FontSize();
            fSize.setFontSize(size);
            if(sizeList.size() == 0)
                fSize.setSelected(true);
            sizeList.add(fSize);
        }
        initRecyclerView(view);
        return view;

    }


    private void initRecyclerView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.font_size_recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mFontSizerAdapter = new FontSizeAdapter(sizeList, this);
        mRecyclerView.setAdapter(mFontSizerAdapter);

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mFontSizeChangeListener = (OnFontSizeChangeListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + " must implement OnFontSizeChangeListener");
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mRecyclerView.findViewHolderForAdapterPosition(0).itemView.performClick();

            }
        },100);
    }

    @Override
    public void onItemSelected(int position) {
        mFontSizeChangeListener.onFontSizeSelected(sizeList.get(position).getFontSize());
    }

    public interface OnFontSizeChangeListener {
        void onFontSizeSelected(int fontSize);
    }
}
