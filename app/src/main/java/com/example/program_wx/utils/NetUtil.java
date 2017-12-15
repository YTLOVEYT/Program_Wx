package com.example.program_wx.utils;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 网络请求
 * Created by YinTao on 2017/12/7.
 */

public class NetUtil
{
    private static Retrofit retrofit;
    private static OkHttpClient okHttpClient;

    private static void init()
    {
        if (okHttpClient==null)
        {
            okHttpClient=new OkHttpClient.Builder()
                    .addInterceptor(new HttpLoggingInterceptor())
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10,TimeUnit.SECONDS)
                    .retryOnConnectionFailure(true)
                    .build();
        }
        if (retrofit==null)
        {
            retrofit=new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
    }

    public static void post(String url, Map<String,String> map)
    {

    }


}
