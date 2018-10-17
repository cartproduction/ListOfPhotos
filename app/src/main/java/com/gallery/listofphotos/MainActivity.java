package com.gallery.listofphotos;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

import com.gallery.listofphotos.adapter.ImageAdapter;
import com.gallery.listofphotos.adapter.RetrofitAdapter;
import com.gallery.listofphotos.api.ApiInterface;
import com.gallery.listofphotos.model.Example;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity {
    public List<Example> array = new ArrayList<Example>();
    GridView gridview;
    ProgressDialog pd;
    public int resCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridview = (GridView) findViewById(R.id.gridview);


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


                ApiInterface apiService = RetrofitAdapter.getClient().create(ApiInterface.class);

                Call<List<Example>> news = apiService.getImages();

                news.enqueue(new Callback<List<Example>>() {
                    @Override
                    public void onResponse(Call<List<Example>> call, Response<List<Example>> response) {

                        array=response.body();

                        resCount++;

                    }

                    @Override
                    public void onFailure(Call<List<Example>> call, Throwable t) {
                        System.out.println(t.getMessage());
                    }
                });


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
                pd.dismiss();

            }
        }.execute();



    }
}
