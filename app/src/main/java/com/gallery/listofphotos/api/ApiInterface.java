package com.gallery.listofphotos.api;

import com.gallery.listofphotos.adapter.RetrofitAdapter;
import com.gallery.listofphotos.model.Response;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by ETMaster on 23/06/2017.
 */

public interface ApiInterface {

    @GET(RetrofitAdapter.BASE_URL + RetrofitAdapter.QUERİES)
    Call<List<Response>> getImages();
}