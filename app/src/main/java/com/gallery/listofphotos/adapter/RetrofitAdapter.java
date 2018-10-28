package com.gallery.listofphotos.adapter;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ETMaster on 23/06/2017.
 */

public class RetrofitAdapter {

    public static final String BASE_URL = "https://api.unsplash.com/photos/";
    public static final String CLIENT_ID = "2f9ec60bf00b6795fd14008461fac686ad448d28fe97af937c38860186ad5016";
    public static final String QUERİES = "?client_id="+CLIENT_ID+"&page=1";

    private static Retrofit retrofit = null;


    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}