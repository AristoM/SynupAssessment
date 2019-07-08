package com.synupassessment.remote;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lenovo on 3/18/2018.
 */

public class RetroFitClass {

//    private static String BASEURL = "http://192.168.132.2";
    private static String BASEURL = "https://api.myjson.com/";

    private  static Retrofit getRetrofitinstance()
    {

        return  new Retrofit.Builder().baseUrl(BASEURL).addConverterFactory(GsonConverterFactory.create()).build();


    }


    public static  APICALL getAPIinstance()
    {

        return getRetrofitinstance().create(APICALL.class);

    }



}
