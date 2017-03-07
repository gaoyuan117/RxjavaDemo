package com.gaoyuan.rxjavademo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.subscribers.SubscriberResourceWrapper;
import io.reactivex.schedulers.Schedulers;

public class FlowableExampleActivity extends AppCompatActivity {

    private Subscription subscription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flo);
        readTxt();
    }

    /**
     * 读取文件
     */
    private void readTxt() {
        Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(FlowableEmitter<String> e) throws Exception {
                InputStream file = getResources().getAssets().open("test.txt");
                InputStreamReader inputStreamReader = new InputStreamReader(file);
                BufferedReader br = new BufferedReader(inputStreamReader);
                String string = "";

                while ((string = br.readLine()) != null && !e.isCancelled()) {
                    e.onNext(string);
                }

                br.close();
                inputStreamReader.close();
                e.onComplete();
            }
        }, BackpressureStrategy.ERROR)
                .observeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        subscription = s;
                        s.request(1);
                    }

                    @Override
                    public void onNext(String s) {
                        Log.e("gy", s);
                        try {
                            Thread.sleep(1000);
                            subscription.request(2);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.w("gy",t);
                    }

                    @Override
                    public void onComplete() {

                    }

                });
    }
}
