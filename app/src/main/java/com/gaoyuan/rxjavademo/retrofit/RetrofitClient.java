package com.gaoyuan.rxjavademo.retrofit;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by gaoyuan on 2017/3/4.
 */

public class RetrofitClient {
    private static Retrofit retrofit;
    public  Api api;
    public static RetrofitClient instances;

    public RetrofitClient() {
    }

    public static RetrofitClient getInstence(){
        instances = new RetrofitClient();

        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        builder.readTimeout(10, TimeUnit.SECONDS);
        builder.connectTimeout(9, TimeUnit.SECONDS);

        retrofit = new Retrofit.Builder().baseUrl("http://lxls.jzbwlkj.com/")
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return instances;
    }

    public RetrofitClient creatApi(){
        api = retrofit.create(Api.class);
        return instances;
    }
    public Api creatApi2(){
        api = retrofit.create(Api.class);
        return api;
    }

    public void login(String url, Map<String,String> map, Observer<ResponseBody> subscriber){
        api.login(url,map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);

    }
}
