package com.gallery.listofphotos;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.gallery.listofphotos.adapter.ImageAdapter;
import com.gallery.listofphotos.adapter.RetrofitAdapter;
import com.gallery.listofphotos.api.ApiInterface;
import com.gallery.listofphotos.model.Response;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity {
    public List<Response> array = new ArrayList<Response>();
    GridView gridview;
    ProgressDialog pd;
    public int resCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridview = (GridView) findViewById(R.id.gridview);


        getResponseAsync();


    }

    public void getResponseAsync() {
        new AsyncTask<String, Void, String>() {

            @Override
            protected void onPreExecute() {

                pd = new ProgressDialog(MainActivity.this);
                pd.setMessage("Loading..");
                pd.setCancelable(false);
                pd.show();

            }

            @Override
            protected String doInBackground(String... params) {


                retrofitCallback();


                while(resCount != 1){
                    try {
                        sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }


                return null;
            }

            @Override
            protected void onPostExecute(String result) {
                gridview.setAdapter(new ImageAdapter(MainActivity.this,array));
                gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);

                        // Setting Dialog Message
                        final ImageView img = new ImageView(MainActivity.this);

                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.MATCH_PARENT);
                        img.setLayoutParams(lp);
                        Picasso.with(MainActivity.this).load(array.get(i).getUrls().getRegular()).into(img);
                        alertDialog.setView(img);
                        alertDialog.setCancelable(true);
                        alertDialog.setNegativeButton("Close",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // Write your code here to execute after dialog
                                        dialog.cancel();
                                    }
                                });
                        alertDialog.show();
                    }
                });
                pd.dismiss();

            }
        }.execute();
    }

    public void retrofitCallback() {
        ApiInterface apiService = RetrofitAdapter.getClient().create(ApiInterface.class);

        Call<List<Response>> news = apiService.getImages();

        news.enqueue(new Callback<List<Response>>() {

            @Override
            public void onResponse(Call<List<Response>> call, retrofit2.Response<List<Response>> response) {
                array=response.body();

                resCount++;
            }

            @Override
            public void onFailure(Call<List<Response>> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }
}
