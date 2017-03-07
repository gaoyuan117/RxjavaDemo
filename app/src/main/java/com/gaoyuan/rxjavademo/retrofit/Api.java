package com.gaoyuan.rxjavademo.retrofit;

import java.util.Map;

import io.reactivex.Observable;

import okhttp3.ResponseBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * Created by gaoyuan on 2017/3/4.
 */

public interface Api {

    @FormUrlEncoded
    @POST
    Observable<ResponseBody> login(@Url String url, @FieldMap Map<String, String> map);
}
