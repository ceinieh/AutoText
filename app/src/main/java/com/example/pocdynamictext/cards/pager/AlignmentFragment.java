package com.example.pocdynamictext.cards.pager;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.ContentFrameLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.pocdynamictext.R;


  public class AlignmentFragment extends Fragment implements View.OnClickListener {
      private static final String TAG = "Alignment_Fragment";
      private InterfaceFontAlignment interfaceFontAlignment;
    private GridView gridView;
    private RecyclerView mRecyclerView;
    private int[] imageIds;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //inflater =  LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.alignment_fragment, container, false);
        gridView = (GridView) view.findViewById(R.id.gridview_item);
        getImages ();
        gridView.setAdapter(new ImageAdapterGridView(view.getContext()));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onClick: position " + position);
                interfaceFontAlignment.onFontAlignmentPass(position);
              //  return true;
            }
        });



        return view;
    }


    private void getImages (){
        imageIds =  new int[]  {R.drawable.ic_format_align_center_black_24px,
                R.drawable.ic_format_align_left_black_24px,
                R.drawable.ic_format_align_right_black_24px,
                R.drawable.ic_vertical_align_bottom_black_24px,
                R.drawable.ic_vertical_align_center_black_24px,
                R.drawable.ic_vertical_align_top_black_24px};
    }

      @Override
      public void onClick(View view) {
          Log.d(TAG, "onClick: ");
      }


      @Override
      public void onAttach(Context context) {
          super.onAttach(context);
          interfaceFontAlignment = (InterfaceFontAlignment) context;
      }


      public interface InterfaceFontAlignment {
        public void onFontAlignmentPass(int data);
    }

    public class ImageAdapterGridView extends BaseAdapter {
        private Context mContext;

        public ImageAdapterGridView(Context c) {
            mContext = c;
        }

        public int getCount() {
            return imageIds.length;
        //    return 0;
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView mImageView;

            if (convertView == null) {
                mImageView = new ImageView(mContext);
                mImageView.setLayoutParams(new GridView.LayoutParams(130, 130));
                mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                mImageView.setPadding(16, 16, 16, 16);
            } else {
                mImageView = (ImageView) convertView;
            }
            mImageView.setImageResource(imageIds[position]);
            return mImageView;
        }


    }

}
