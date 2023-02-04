package com.example.hazardcrowdsourcing;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hazardcrowdsourcing.Model.Articles;
import com.example.hazardcrowdsourcing.Model.Headlines;
import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NewsFragment extends Fragment {

    class News {
        int id;
        String date, time, name, description;
        double lat, lng;
        String reporter, imgurl;
    }

    class Response {
        boolean success;
        String message;
        News[] news;
    }

    LinearLayoutCompat newslist;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_news, container, false);

        newslist = rootView.findViewById(R.id.newslist);


        Executors.newSingleThreadExecutor().execute(() -> {
            String jsonText = RequestHandler.sendGetRequest("http://192.168.0.8/ict602gp/news.php");
            Response response = new Gson().fromJson(jsonText, Response.class);
            if (!response.success) {
                requireActivity().runOnUiThread(() -> Toast.makeText(this.getContext(), response.message, Toast.LENGTH_LONG).show());
            } else {
                if(getActivity() != null) requireActivity().runOnUiThread(() -> {
                    newslist.removeAllViews();
                });
                Log.d("HTTP/API", String.format("Got %d news!", response.news.length));
                for (News news : response.news) {
                    Bitmap bitmap = getImageFromURL(news.imgurl);
                    if(getActivity() != null) requireActivity().runOnUiThread(() -> {
                        View view = inflater.inflate(R.layout.template_news, null);
                        (( TextView ) view.findViewById(R.id.news_name)).setText(news.name);
                        (( TextView ) view.findViewById(R.id.news_description)).setText(news.description);
                        (( ImageView ) view.findViewById(R.id.news_image)).setImageBitmap(bitmap);
                        String datetime = String.format("%s %s", news.date, news.time);
                        (( TextView ) view.findViewById(R.id.news_datetime)).setText(datetime);
                        newslist.addView(view);
                    });
                }
            }
        });

        return rootView;
    }

    static Bitmap getImageFromURL(String url) {
        try {
            HttpURLConnection conn = ( HttpURLConnection ) new URL(url).openConnection();
            conn.setDoInput(true);
            conn.connect();
            if (conn.getResponseCode() != 200) throw new Error("Cannot get url!");
            return BitmapFactory.decodeStream(conn.getInputStream());
        } catch (Exception e) {
            Log.e("getImageFromURL", "Failed to fetch bitmap", e);
        }
        return null;
    }


}