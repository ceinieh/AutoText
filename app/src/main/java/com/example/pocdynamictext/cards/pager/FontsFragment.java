package com.example.pocdynamictext.cards.pager;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.pocdynamictext.AppController;
import com.example.pocdynamictext.R;
import com.example.pocdynamictext.helper.FileHelper;
import com.example.pocdynamictext.model.Item;
import com.example.pocdynamictext.model.Result;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class FontsFragment extends Fragment implements FontsAdapter.Callback {
    private static final String TAG = FontsFragment.class.getSimpleName() + "_TAG";
    private static final String URL = "https://api.myjson.com/bins/be2aj";

    private ProgressBar mProgressBar;
    private FontsAdapter mFontsAdapter;
    private Result mResult;
    private OnFontChangeListener mFontChangeListener;
    private DownloadManager mDownloadManager;
    private long enqueue;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mFontChangeListener = (OnFontChangeListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + " must implement OnFontChangeListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fonts_fragment, container, false);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progress);
        mProgressBar.setIndeterminate(true);
        initRecyclerView(view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mProgressBar.setVisibility(View.VISIBLE);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        mResult = gson.fromJson(response.toString(), Result.class);
                        mResult.getItems().get(0).setSelected(true);
                        mFontsAdapter.updateItems(mResult.getItems());
                        mProgressBar.setVisibility(View.GONE);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.getCause().printStackTrace();
                    }
                });
        AppController.getInstance().addToRequestQueue(request);
    }

    @Override
    public void onItemSelected(int position, String url) {
        Toast.makeText(getActivity(), "Item selected " + position, Toast.LENGTH_SHORT).show();
        FontDownloadTask download = new FontDownloadTask();
        download.execute(url);
    }

    private void initRecyclerView(View view) {
        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.fonts_recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mFontsAdapter = new FontsAdapter(new ArrayList<Item>(0), this);
        mRecyclerView.setAdapter(mFontsAdapter);
    }

    public interface OnFontChangeListener {
        void onFontSelected(Typeface font);
    }

    public class FontDownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            int count;

            try {
                java.net.URL url = new URL(params[0]);
                String filename = params[0].substring(params[0].lastIndexOf('/') + 1);
                URLConnection conexion = url.openConnection();
                conexion.connect();

                int lenghtOfFile = conexion.getContentLength();
                Log.d(TAG, "Lenght of file: " + lenghtOfFile);

                InputStream input = new BufferedInputStream(url.openStream());
                File file = FileHelper.getSaveFilePath(filename);
                OutputStream output = new FileOutputStream(file);

                byte data[] = new byte[1024];
                while ((count = input.read(data)) != -1) {
                    output.write(data, 0, count);
                }

                output.flush();
                output.close();
                input.close();
                return file.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            mFontChangeListener.onFontSelected(Typeface.createFromFile(s));
        }
    }
}
