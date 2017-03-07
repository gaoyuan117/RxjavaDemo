package com.gaoyuan.rxjavademo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.gaoyuan.rxjavademo.retrofit.RetrofitClient;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import okhttp3.ResponseBody;

public class ExampleActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);
    }

    @Override
    protected void init() {

    }

    @Override
    protected void initData() {
        login();

    }

    @Override
    protected void setData() {

    }


    private void login() {
        Map<String, String> map = new HashMap<>();
        map.put("user", "14763766689");
        map.put("pwd", "123456");
        map.put("driver", "android");
        RetrofitClient.getInstence().creatApi().login("api//User/login", map, new Observer<ResponseBody>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
                showProgressDialog("加载中");
            }

            @Override
            public void onNext(ResponseBody value) {
                try {
                    Log.e("gy", value.string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(ExampleActivity.this, "登陆失败", Toast.LENGTH_SHORT).show();
                dismissProgressDialog();
            }

            @Override
            public void onComplete() {
                Toast.makeText(ExampleActivity.this, "加载完成", Toast.LENGTH_SHORT).show();
                dismissProgressDialog();
            }
        });
    }

}
