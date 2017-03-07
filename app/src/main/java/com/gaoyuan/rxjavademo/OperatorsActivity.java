
package com.gaoyuan.rxjavademo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.gaoyuan.rxjavademo.bean.LoginBean;
import com.gaoyuan.rxjavademo.retrofit.RetrofitClient;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class OperatorsActivity extends AppCompatActivity {

    private Map<String, String> map;
    String s[] = {"1","2","3"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operators);
        map = new HashMap<>();
        map.put("user", "14763766689");
        map.put("pwd", "123456");
        map.put("driver", "android");
//        operatorFrom();
//        operatorMap();
//        opeeratorFlatMap();
//        operatorZip();
//        operatorInterval();
//        timer();
//        operatorLast();
//        operatorDistinct();
        operatorMerge();
    }

    /**
     * Join操作符
     */
    private void operatorMerge(){
        Observable<String> just = Observable.fromArray(s);
        Observable<Integer> just1 = Observable.just(5, 6, 7, 8);
        Observable.merge(just,just1).subscribe(new Consumer<Serializable>() {
            @Override
            public void accept(Serializable serializable) throws Exception {
                Log.e("gy","merge "+serializable);
            }
        });

        Observable.concat(just1,just).subscribe(new Consumer<Serializable>() {
            @Override
            public void accept(Serializable serializable) throws Exception {
                Log.e("gy","concat "+serializable);
            }
        });
    }

    /**
     * Distinct操作符
     */
    private void operatorDistinct(){
        Observable.just(1,1,1,1,1)
                .distinct()
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.e("gy","distinct "+integer);
                    }
                });
    }

    /**
     * last操作符
     */
    private void operatorLast(){
        Observable.just(1,2,3)
                .lastElement()
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.e("gy","last "+integer);
                    }
                });
    }

    /**
     * Range操作符
     */
    private void operatorRange(){
        Observable.range(0,10).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.e("gy","range "+integer);
            }
        });
    }

    /**
     * timer操作符
     */
    private void timer(){
        Observable.timer(3000,TimeUnit.MILLISECONDS)
                .subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long strings) throws Exception {
                Log.e("gy",strings+"");
            }
        });
    }

    /**
     * Just操作符
     */
    private void operatorJust(){

        Observable.just(s).subscribe(new Consumer<String[]>() {
            @Override
            public void accept(String[] strings) throws Exception {

            }
        });
    }

    /**
     * From操作符
     */
    private void operatorFrom(){
        List<String> list = new ArrayList<>();
        String s[] = {"1","2","3"};
        list.add("1");
        list.add("2");
        list.add("3");
        Observable.fromIterable(list).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {

            }
        });
    }


    /**
     * Interval操作符
     */
    private void operatorInterval(){
        Flowable.interval(1000, TimeUnit.MILLISECONDS)
                .onBackpressureDrop()//加上背压策略
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Log.e("gy", "数字：" + aLong);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }

    /**
     * Zip操作符
     */
    private void operatorZip() {
        Observable<Integer> observable1 = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
//                e.onNext(2);
//                e.onNext(3);
//                e.onNext(4);
            }
        }).subscribeOn(Schedulers.io());

        Observable<Integer> observable2 = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onNext(4);
                e.onError(new Throwable());
            }
        }).subscribeOn(Schedulers.io());

        Observable.zip(observable1, observable2, new BiFunction<Integer, Integer, Integer>() {
            @Override
            public Integer apply(Integer integer, Integer integer2) throws Exception {
                return integer + integer2;
            }
        }) .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer value) {
                        Log.e("gy", "zip:" + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(OperatorsActivity.this, "zip失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    /**
     * FlatMap操作符
     */
    private void opeeratorFlatMap() {
        RetrofitClient.getInstence().creatApi2().login("api//User/login", map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                    }
                })
                .filter(new Predicate<ResponseBody>() {
                    @Override
                    public boolean test(ResponseBody responseBody) throws Exception {
                        LoginBean bean = new Gson().fromJson(responseBody.string(),LoginBean.class);
                        return bean.getCode()==200;
                    }
                })
                .observeOn(Schedulers.io())
                .flatMap(new Function<ResponseBody, ObservableSource<ResponseBody>>() {
                    @Override
                    public ObservableSource<ResponseBody> apply(ResponseBody responseBody) throws Exception {
                        Map<String, String> maps = new HashMap<String, String>();
                        map.put("user", "1476375559");
                        map.put("pwd", "123456");
                        map.put("driver", "android");
                        return RetrofitClient.getInstence().creatApi2().login("api//User/login", maps);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBody>() {

                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                        Log.e("gy", "这又是什么+" + responseBody.string());
                    }
                });
    }

    /**
     * map操作符
     */
    private void operatorMap() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext("1");
            }
        }).map(new Function<String, Integer>() {
            @Override
            public Integer apply(String s) throws Exception {
                int i = Integer.parseInt(s);
                return i + 1;
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Toast.makeText(OperatorsActivity.this, "map:" + integer, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
