package com.gaoyuan.rxjavademo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class BasicActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic);
        rxjava1();
    }

    /**
     * rxjava线程调度
     */
    private void rxjava3() {
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                Log.e("gy", "Observable所在线程：" + Thread.currentThread().getName());
                e.onNext("1");
            }
        });

        Consumer<String> observer = new Consumer<String>() {

            @Override
            public void accept(String s) throws Exception {
                Log.e("gy", "Observer所在线程：" + Thread.currentThread().getName());
            }
        };
        /**
         Schedulers.io() 代表io操作的线程, 通常用于网络,读写文件等io密集型的操作
         Schedulers.computation() 代表CPU计算密集型的操作, 例如需要大量计算的操作
         Schedulers.newThread() 代表一个常规的新线程
         AndroidSchedulers.mainThread() 代表Android的主线程
         */
        observable.subscribeOn(Schedulers.io())//指定Observable的线程，多次调用该方法只有第一次有用
                .observeOn(AndroidSchedulers.mainThread())//指定observer的线程，每调用一次observer的线程就会切换一次
                .subscribe(observer);
    }

    private void rxjava2() {
        /**
         public final Disposable subscribe() {}
         public final Disposable subscribe(Consumer<? super T> onNext) {}
         public final Disposable subscribe(Consumer<? super T> onNext, Consumer<? super Throwable> onError) {}
         public final Disposable subscribe(Consumer<? super T> onNext, Consumer<? super Throwable> onError, Action onComplete) {}
         public final Disposable subscribe(Consumer<? super T> onNext, Consumer<? super Throwable> onError, Action onComplete, Consumer<? super Disposable> onSubscribe) {}
         public final void subscribe(Observer<? super T> observer) {}
         */
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {

            }
        }).subscribe(new Consumer<String>() {//只关心onNext方法
            @Override
            public void accept(String s) throws Exception {
                Log.e("gy", "Observer所在线程：" + Thread.currentThread().getName());

            }
        });
    }

    private void rxjava1() {
        //被观察者
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {// 发送事件
                e.onNext("1");
                e.onNext("2");
                e.onComplete();//当observer接收到时，停止接收后续的, 但observable仍然发送
//                e.onError(new Exception());//observable 停止发送
                e.onNext("3");

            }
        });
        //观察者
        Observer<String> observer = new Observer<String>() {
            private Disposable disposable;

            @Override
            public void onSubscribe(Disposable d) {//该方法先执行，Disposable相当于开关
                Log.e("gy", "onSubscribe");
                disposable = d;
            }

            @Override
            public void onNext(String value) {
                Log.e("gy", value);
                if (value.equals("2")) {
                    disposable.dispose();//该方法执行后，不再接受，但是仍然在发送
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.e("gy", "onError");
            }

            @Override
            public void onComplete() {
                Log.e("gy", "onComplete");
            }
        };
        observable.subscribe(observer);
    }
}
