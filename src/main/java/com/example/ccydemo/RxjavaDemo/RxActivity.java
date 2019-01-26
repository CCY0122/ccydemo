package com.example.ccydemo.RxjavaDemo;

import android.annotation.SuppressLint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ccydemo.BaseActivity;
import com.example.ccydemo.R;
import com.example.ccydemo.RecyclerViewHeader.Bean;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * Created by ccy on 2017-09-22.
 */
public class RxActivity extends BaseActivity {

    private static final String TAG = "ccy";
    @BindView(R.id.rx_et1)
    EditText et1;
    @BindView(R.id.rx_et2)
    EditText et2;
    @BindView(R.id.rx_tv1)
    TextView tv1;
    @BindView(R.id.popup_btn)
    Button popBtn;
    @BindView(R.id.state_test)
    TextView stateTv;
    private CompositeDisposable disposables = new CompositeDisposable();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rx_act);
        ButterKnife.bind(this);
        PublishSubject<String> s1 = PublishSubject.create();
        PublishSubject<String> s2 = PublishSubject.create();
        et1.addTextChangedListener(new MyTextWatcher(s1));
        et2.addTextChangedListener(new MyTextWatcher(s2));
        combineLatestDemo(s1, s2);
//        doOnSubscribeThreadDemo();//        doOnSubscribeThreadDemo2();

//        flowableCacheDemo();

//        map();
//        map1();

//        subjectErrorDemo();

//        nullValueDemo();

//        test();
//        test2();

//        get();

//        interTest();

//        delayErrorTest();

//        dofinallyTest();

//        retryAndRepeatTest();

//        demo1();

//        demo2();

        demo3();


    }

    /**
     * 结论，先"do finally 1" 再flatmap再"onnext"再"do finally 2"
     */
    @SuppressLint("CheckResult")
    private void dofinallyTest() {
        Observable.timer(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.d("ccy", "do finally 1");
                    }
                })
                .subscribeOn(Schedulers.io())
                .flatMap(new Function<Long, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(Long aLong) throws Exception {
                        return Observable.timer(1, TimeUnit.SECONDS);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.d("ccy", "do finally 2");
                    }
                })
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        Log.d("ccy", "onnext");
                    }
                });
    }


    @OnClick(R.id.rx_btn)
    public void flatmap() {
        flatMapDemo();
    }

    @OnClick(R.id.rx_b2)
    public void ownThread() {
        subscribeInOwnThreadDemo();
    }

    @OnClick(R.id.rx_b3)
    public void ownThreadInZIP() {
        zipInOwnThreadDemo();
    }

    @OnClick(R.id.rx_b4)
    public void onErrorResumeNext() {
        onErrorResumeNextDemo();
    }

    @OnClick(R.id.rx_b5)
    public void subject() {
        subjectDemo();
    }


    /**
     * popupwindow测试，无关RXJAVA
     */
    @OnClick(R.id.popup_btn)
    public void openPopupWindow() {
        View v = LayoutInflater.from(this).inflate(R.layout.popupwindow, null);
        final PopupWindow pop = new PopupWindow(v, 500, 300);
        View content = v.findViewById(R.id.content);
        TextView t = (TextView) v.findViewById(R.id.pop_t1);
        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RxActivity.this, "asdasd", Toast.LENGTH_LONG).show();
                if (pop != null) {
                    pop.dismiss();
                }
            }
        });
        pop.setFocusable(true);
        pop.setOutsideTouchable(true);
        pop.setBackgroundDrawable(new ColorDrawable());
        pop.setAnimationStyle(R.style.in_out_anim);
        int[] location1 = new int[2];
        int[] location2 = new int[2];
        popBtn.getLocationOnScreen(location1);
        popBtn.getLocationInWindow(location2);
//        Log.d("ccy","onScreen:"+location1[0]+";"+location1[1]);
//        Log.d("ccy","inWindow:"+location2[0]+";"+location2[1]);

//        Log.d("ccy","height = " + content.getHeight()+";width = " + content.getWidth());
//        Log.d("ccy","btn height = " + popBtn.getHeight());
        v.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        pop.showAtLocation(popBtn, Gravity.NO_GRAVITY, location1[0], location1[1] - v
                .getMeasuredHeight());
    }


    /**
     * 状态位测试，无关RXJAVA
     *
     * @param v
     */
    @OnClick({R.id.state_1, R.id.state_2})
    public void stateTest(View v) {
        switch (v.getId()) {
            case R.id.state_1:
                stateTv.setEnabled(!stateTv.isEnabled());
                break;
            case R.id.state_2:
                stateTv.setSelected(!stateTv.isSelected());
                break;
        }
    }


    /**
     * 测试一个疑问：创建一个Observable时通过subscribeOn制定一个发射线程，
     * 当flatMap创建的新的Observable后在外部再一次通过subscribeOn指定线程的话
     * 新的Observable是不是又在另一个线程了？
     * 如果是在flatMap内部直接将新的Observable通过subscribeOn指定新线程后作为返回值return的话，效果同上吗？
     * <br/>
     * <br/>
     * 测试结论：
     * 1、在flatMap之后再次调用subscribeOn是无效的
     * 2、在flatMap返回新Observable之前直接给他subscribeOn可以达到再次切换线程
     */
    private void flatMapDemo() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
                Log.d("ccy", "origin thread = " + Thread.currentThread());
                SystemClock.sleep(1000);
                e.onNext(1);
//                SystemClock.sleep(1000);
                e.onNext(2);
//                SystemClock.sleep(1000);
                e.onNext(3);
                e.onComplete();
            }
        }).subscribeOn(Schedulers.newThread())
                .flatMap(new Function<Integer, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(@NonNull final Integer integer) throws
                            Exception {
                        return Observable.create(new ObservableOnSubscribe<String>() {
                            @Override
                            public void subscribe(@NonNull ObservableEmitter<String> e) throws
                                    Exception {
                                Log.d("ccy", "flat map thread = " + Thread.currentThread());
                                String str1 = "flat mapA + " + integer;
                                String str2 = "flat mapB + " + integer;
                                SystemClock.sleep(2000);
                                e.onNext(str1);
                                SystemClock.sleep(2000);
                                e.onNext(str2);
                                e.onComplete();  //应该没用的
                            }
                        })
//                                .subscribeOn(Schedulers.newThread())
                                ;
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {

                    private long lastTime = 0;

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.d("ccy", "onSubscribe");
                    }

                    @Override
                    public void onNext(@NonNull String s) {
//                        Log.d("ccy", "observe thread = " + Thread.currentThread());
                        Log.d("ccy", "onNext,str = " + s);
                        Log.d("ccy", "onNext,time = " + (System.currentTimeMillis() - lastTime));
                        lastTime = System.currentTimeMillis();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                        Log.d("ccy", "onComplete");
                        Log.d("ccy", "onComplete,time = " + (System.currentTimeMillis() -
                                lastTime));
                    }
                });
    }


    /**
     * FunSDK的接口命令内部已经是开启线程去请求数据并在接受到数据后切换回主线程回调方法
     * 那等同于需要在新建Observable时就手动去切换线程了，
     * 这样的话整体流程会不一样吗？时间顺序、线程等会出什么问题吗？
     * 如果再再后面调用subscribeOn，那么onNext最终会在哪个线程里发射出去？
     * <p/>
     * 结论：e.onNext是在callBack自己的线程里发射的
     * <br/>
     * 当然这只是为了兼容FunSDK接口而这么做，其他时候还是不要手动切线程
     */
    private void subscribeInOwnThreadDemo() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull final ObservableEmitter<String> e) throws Exception {
                Log.d("ccy", "origin thread = " + Thread.currentThread());
                fakeFunSDK(3000, new OnCallback() {
                    @Override
                    public void callBack(String str) {             //模拟请求数据，回调会自己切到主线程
                        Log.d("ccy", "emit onNext Thread = " + Thread.currentThread());
                        e.onNext(str);
                    }
                });
            }
        }).subscribeOn(Schedulers.newThread())         //e.onNext被手动切到主线程发起了，那么subscribeOn还有效吗？
                .observeOn(Schedulers.newThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.d("ccy", "observe thread = " + Thread.currentThread());

                    }
                });
    }

    /**
     * 同subscribeInOwnThreadDemo，模拟手动自建线程情况下zip会不会出什么奇怪的问题
     * <p/>
     * 结论：没问题
     */
    private void zipInOwnThreadDemo() {
        Observable<String> o1 = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull final ObservableEmitter<String> e) throws Exception {
                Log.d("ccy", "Observable:1 , origin thread = " + Thread.currentThread());
                fakeFunSDK(2000, new OnCallback() {
                    @Override
                    public void callBack(String str) {
                        Log.d("ccy", "Observable:1，emit thread = " + Thread.currentThread());
                        e.onNext("1");
                    }
                });
            }
        });
        Observable<String> o2 = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull final ObservableEmitter<String> e) throws Exception {
                Log.d("ccy", "Observable:2 , origin thread = " + Thread.currentThread());
                fakeFunSDK(5000, new OnCallback() {
                    @Override
                    public void callBack(String str) {
                        Log.d("ccy", "Observable:2，emit thread = " + Thread.currentThread());
                        e.onNext("2");
                    }
                });
            }
        });

        Observable.zip(o1, o2, new BiFunction<String, String, String>() {
            @Override
            public String apply(@NonNull String s, @NonNull String s2) throws Exception {
                return s + s2;
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.d("ccy", "onNext,s = " + s + ";thread = " + Thread.currentThread());
                    }
                });

    }


    public void fakeFunSDK(final long sleepTime, final OnCallback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("ccy", "子线程请求数据中，thread = " + Thread.currentThread());
                SystemClock.sleep(sleepTime);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        callback.callBack("我是数据");
                    }
                });
//                callback.callBack("asdasd");
            }
        }).start();
    }


    public interface OnCallback {
        void callBack(String str);
    }


    /**
     * map 线程测试
     * 结论：要看observeOn的位置，observeOn切换线程后，之后的操作符即在其线程运行，无关map操作符，这是理解问题
     */
    private void map() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
                Log.d("ccy", "emit thread = " + Thread.currentThread());
                e.onNext(1);
            }
        }).map(new Function<Integer, String>() {
            @Override
            public String apply(@NonNull Integer integer) throws Exception {
                Log.d("ccy", "map thread = " + Thread.currentThread());
                return integer + "c";
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.d("ccy", "accept thread = " + Thread.currentThread());
                    }
                });
    }

    private void map1() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
                Log.d("ccy", "1 emit thread = " + Thread.currentThread());
                e.onNext(1);
            }
        })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<Integer, String>() {
                    @Override
                    public String apply(@NonNull Integer integer) throws Exception {
                        Log.d("ccy", "1 map thread = " + Thread.currentThread());
                        return integer + "c";
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.d("ccy", "1 accept thread = " + Thread.currentThread());
                    }
                });
    }

    /**
     * doOnSubscribe/doOnNext/doOnError的线程测试，是怎么受subscribeOn影响的？
     */
    @SuppressLint("CheckResult")
    private void doOnSubscribeThreadDemo() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
                Log.d("ccy", "origin thread = " + Thread.currentThread());
                e.onNext("1");
//                e.onError(new RuntimeException("error"));
            }
        })
                .subscribeOn(Schedulers.io())  //改变了整个上游的线程
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        Log.d("ccy", "doOnSubscribe thread(before observeOn/2nd subscribeOn) = "
                                + Thread.currentThread());
                        //结论：computation线程(2nd subscribeOn)
                    }
                })
                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.d("ccy", "doOnNext thread (before observeOn)= " + Thread
                                .currentThread());
                        //结论：io线程
                    }
                })
                .map(new Function<String, String>() {
                    @Override
                    public String apply(String s) throws Exception {
                        return "2";
                    }
                })
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("ccy", "doOnError thread (before observeOn) = " + Thread
                                .currentThread());
                        //结论：io线程
                    }
                })
                .subscribeOn(Schedulers.computation()) //改变了doOnSubscribe的线程
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        Log.d("ccy", "doOnSubscribe thread (after observeOn/2nd subscribeOn)= " +
                                Thread.currentThread());
                        //结论：主线程（observeOn）
                    }
                })
                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.d("ccy", "doOnNext thread (after observeOn)= " + Thread.currentThread
                                ());
                        //结论：主线程（observeOn）
                    }
                })
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("ccy", "doOnError thread (after observeOn) = " + Thread
                                .currentThread());
                        //结论：主线程（observeOn）
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.d("ccy", "onNext accept thread = " + Thread.currentThread());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("ccy", "onError accept thread = " + Thread.currentThread());
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {

                    }
                }, new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        Log.d("ccy", "onSubscribe accept thread = " + Thread.currentThread());
                    }
                });
    }

    /**
     * doOnSubscribe/doOnNext/doOnError的线程测试2
     */
    private void doOnSubscribeThreadDemo2() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
                Log.d("ccy", "origin thread = " + Thread.currentThread());
                e.onNext("1");
                e.onNext("2");
            }
        })
                .flatMap(new Function<String, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(@NonNull String s) throws Exception {
                        Log.d("ccy", "flatMap thread = " + Thread.currentThread() + ";s = " + s);
//                        if (s.equals("1")) {
//                            return Observable.just(s);
//                        } else {
//                            return Observable.error(new RuntimeException("1234123"));
//                        }
//                        return Observable.just(s);
                        return Observable.error(new RuntimeException(s));
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread(), true)
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("ccy", "doOnError thread = " + Thread.currentThread());
                        //结论：主线程（observeOn）
                    }
                })
                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.d("ccy", "doOnNext thread = " + Thread.currentThread());
                        //结论：主线程（observeOn）
                    }
                })
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull String s) {
                        Log.d("ccy", "onNext thread = " + Thread.currentThread());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d("ccy", "onError thread = " + Thread.currentThread());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    private long lastTime;

    /**
     * onErrorResumeNext测试
     * <p>
     * 线程结论：onErrorResumeNext内使用subscribeOn线程 //20171117理解错误，应该跟observeOn有关
     */
    private void onErrorResumeNextDemo() {
        Observable
                .create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
                        if (System.currentTimeMillis() - lastTime > 5000) {  //5秒后断线
                            e.onError(new Throwable("数据超时失效了！"));
                        } else {
                            e.onNext("正常情况的数据");
                        }
                    }
                })
                .onErrorResumeNext(new Function<Throwable, ObservableSource<? extends String>>() {
                    @Override
                    public ObservableSource<? extends String> apply(@NonNull Throwable throwable)
                            throws Exception {
                        Log.d("ccy", "onErrorResumeNext thread = " + Thread.currentThread());
                        lastTime = System.currentTimeMillis();
                        return Observable.create(new ObservableOnSubscribe<String>() {
                            @Override
                            public void subscribe(@NonNull ObservableEmitter<String> e) throws
                                    Exception {
                                Log.d("ccy", "new Observable thread = " + Thread.currentThread());
                                e.onNext("重连后的数据");
                            }
                        });
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.d("ccy", "接受到的数据内容是：" + s);
                    }
                }, new Consumer<Throwable>() {  //该方法已被拦截，不会被调用
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("ccy", "报错：" + throwable.getMessage());
                    }
                });

    }

    private void subjectDemo() {

        final PublishSubject<String> subject = PublishSubject.<String>create();
        subject
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull String s) {
                        Log.d("ccy", "onNext, s= " + s + ";thread = " + Thread.currentThread());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        Log.d("ccy", "onComplete");
                    }
                });


        fakeFunSDK(2000, new OnCallback() {
            @Override
            public void callBack(String str) {
                subject.onNext(str);
//                subject.onComplete();
            }
        });

        fakeFunSDK(4000, new OnCallback() {
            @Override
            public void callBack(String str) {
                subject.onNext(str + "22222");
                subject.onComplete();
            }
        });
    }

    private void combineLatestDemo(Subject<String> s1, Subject<String> s2) {
        Observable.combineLatest(s1, s2, new BiFunction<String, String, Boolean>() {
            @Override
            public Boolean apply(@NonNull String s, @NonNull String s2) throws Exception {
                Log.d("ccy", "combineLatest thread = " + Thread.currentThread()); //结论：在主线程
                Log.d("ccy", "s1.length = " + s.length() + ";s2.length =" + s2.length());
                if (
                        (s.length() >= 2 && s.length() <= 4)
                                && (s2.length() >= 4 && s2.length() <= 8)
                        ) {
                    return true;
                }
                return false;
            }
        })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            tv1.setText("输入的内容正确！");
                            tv1.setBackgroundColor(0x4400ff00);
                        } else {
                            tv1.setText("输入的内容不合法！");
                            tv1.setBackgroundColor(0x44ff0000);
                        }
                    }
                });
    }

    //测试flowable水缸一开始就存还是可以经过操作符变化直到即将onNext时存入水缸
    //结论：发射的数据都能经过切换下游线程之前（observeOn）的操作符处理，然后再存入水缸
    private void flowableCacheDemo() {
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull FlowableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                Log.d("ccy", "emit 1");
                e.onNext(2);
                Log.d("ccy", "emit 2");
            }
        }, BackpressureStrategy.ERROR)
                .map(new Function<Integer, String>() {
                    @Override
                    public String apply(@NonNull Integer integer) throws Exception {
                        //结论：执行了
                        Log.d("ccy", "map invoked(before observeOn),thread = " + Thread
                                .currentThread().getName());
                        return "" + integer;
                    }
                })
                .subscribeOn(Schedulers.io())
                .map(new Function<String, String>() {
                    @Override
                    public String apply(@NonNull String s) throws Exception {
                        //结论：执行了
                        Log.d("ccy", "map invoked(after subscribeOn but before observeOn),thread " +
                                "= " + Thread.currentThread().getName());
                        return "" + s;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<String, String>() {
                    @Override
                    public String apply(@NonNull String s) throws Exception {
                        //结论：未执行
                        Log.d("ccy", "map invoked(after observeOn),thread = " + Thread
                                .currentThread().getName());
                        return "" + s;
                    }
                })
                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        //结论：未执行
                        Log.d("ccy", "doOnNext invoked(after observeOn),thread = " + Thread
                                .currentThread().getName());
                    }
                })
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        //不调用,测试发射的数据何时存入水缸
//                        s.request(2);
                    }

                    @Override
                    public void onNext(String s) {

                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void nullValueDemo() {
//        Bean b = new Bean();
//        Observable<List<Bean>> o1 = Observable.just(b)
//                .filter(new Predicate<Bean>() {
//                    @Override
//                    public boolean test(Bean bean) throws Exception {
//                        return false;
//                    }
//                })
//                .toList()
//                .toObservable();
//        Bean c = new Bean();
//        Observable<Bean> o2 = Observable.just(c);
//
//        Observable.zip(o1, o2, new BiFunction<List<Bean>, Bean, Object>() {
//            @Override
//            public Object apply(List<Bean> bean, Bean bean2) throws Exception {
//                //结论 bean instanceof ArrayList
//                Log.d("ccy","bean size = " +bean.size() + ";" +(bean instanceof ArrayList) +";"
// + (bean instanceof LinkedList));
//                Log.d("ccy","o1 = " + (bean == null) + ";o2 = " + (bean2 == null));
//                return 1;
//            }
//        }).subscribe(new Observer<Object>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//                Log.d(TAG, "onSubscribe() called with: d = [" + d + "]");
//            }
//
//            @Override
//            public void onNext(Object o) {
//                Log.d(TAG, "onNext() called with: o = [" + o + "]");
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.d(TAG, "onError() called with: e = [" + e + "]");
//            }
//
//            @Override
//            public void onComplete() {
//                Log.d(TAG, "onComplete() called");
//            }
//        });

        Observable.create(new ObservableOnSubscribe<List<Bean>>() {
            @Override
            public void subscribe(ObservableEmitter<List<Bean>> e) throws Exception {
                List<Bean> ll = new ArrayList<>();
//                ll.add(new Bean());
                e.onNext(ll);
                List<Bean> l = new ArrayList<>();
                e.onNext(l);
                e.onComplete();
            }
        })
                .<Bean>flatMap(new Function<List<Bean>, ObservableSource<? extends Bean>>() {
                    @Override
                    public ObservableSource<? extends Bean> apply(List<Bean> beans) throws
                            Exception {
                        Log.d("ccy", "flat map = " + beans.size());
                        return Observable.fromIterable(beans);
                    }
                })
                .toList()
                .toObservable()
                .subscribe(new Observer<List<Bean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d("ccy", "onSubscribe");
                    }

                    @Override
                    public void onNext(List<Bean> beans) {
                        Log.d("ccy", "size = " + beans.size());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("ccy", "e = " + e.toString());
                    }

                    @Override
                    public void onComplete() {
                        Log.d("ccy", "com");
                    }
                });

    }

    private String str = "1";

    public void test() {
        Observable<Long> o1 = Observable.just(1l)
//                .delay(2, TimeUnit.SECONDS)
//                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        Log.d("ccy", "o1,doOnSubscribe");
                    }
                })
//                .subscribeOn(AndroidSchedulers.mainThread())
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(Long aLong) throws Exception {
                        Log.d("ccy", "o1,在observeOn之前，thread = " + Thread.currentThread().getName
                                ());
                        return aLong;
                    }
                })
//                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(Long aLong) throws Exception {
                        Log.d("ccy", "o1,在observeOn之后，thread = " + Thread.currentThread().getName
                                ());
                        return aLong;
                    }
                })
                .doFinally(new Action() {
                    @Override
                    public void run() {
                        Log.d("ccy", "o1,doFinally");
                    }
                })
                .doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Log.d("ccy", "o1,doOnNext,t = " + Thread.currentThread().getName());
                    }
                })
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("ccy", "o1,doOnError,t = " + Thread.currentThread().getName());
                    }
                })
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.d("ccy", "o1,doOnComplete,t = " + Thread.currentThread().getName());
                    }
                });

        Observable<Long> o2 = Observable.just(2l)
//                .delay(5, TimeUnit.SECONDS)
//                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        Log.d("ccy", "o2,doOnSubscribe");
                    }
                })
//                .subscribeOn(AndroidSchedulers.mainThread())
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(Long aLong) throws Exception {
                        Log.d("ccy", "o2,在observeOn之前，thread = " + Thread.currentThread().getName
                                ());
                        return aLong;
                    }
                })
//                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(Long aLong) throws Exception {
                        Log.d("ccy", "o2,在observeOn之后，thread = " + Thread.currentThread().getName
                                ());
                        return aLong;
                    }
                })
                .doFinally(new Action() {
                    @Override
                    public void run() {
                        Log.d("ccy", "o2,doFinally");
                    }
                })
                .doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Log.d("ccy", "o2,doOnNext,t = " + Thread.currentThread().getName());
                    }
                })
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("ccy", "o2,doOnError,t = " + Thread.currentThread().getName());
                    }
                })
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.d("ccy", "o2,doOnComplete,t = " + Thread.currentThread().getName());
                    }
                });

        Observable o3 = Observable.create(new ObservableOnSubscribe() {
            @Override
            public void subscribe(ObservableEmitter e) throws Exception {
                e.onError(new Throwable("asd"));
            }
        })
                .delay(5, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        Log.d("ccy", "o3,doOnSubscribe");
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer() {
                    @Override
                    public void accept(Object o) throws Exception {
                        Log.d("ccy", "o3,doOnNext");
                    }
                })
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("ccy", "o3,doOnError");
                    }
                });


        Observable.zip(o1, o2, new BiFunction<Long, Long, String>() {
            @Override
            public String apply(Long aLong, Long aLong2) throws Exception {
                Log.d("ccy", "zip apply,thread = " + Thread.currentThread().getName());
                return "haha";
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        Log.d("ccy", "zip doOnSubscribe,t = " + Thread.currentThread().getName());
                    }
                })
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.d("ccy", "zip doFinally,t = " + Thread.currentThread().getName());
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.d("ccy", "zip onNext,thread = " + Thread.currentThread().getName());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("ccy", "zip onError,thread = " + Thread.currentThread().getName());
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.d("ccy", "zip onComplete,thread = " + Thread.currentThread()
                                .getName());
                    }
                });
//
//        Observable.create(new ObservableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
//                e.onError(new Throwable("asd"));
////                e.onError(new Throwable("ddd"));
//            }
//        })
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<Integer>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(Integer integer) {
//                        Log.d("ccy", "i = " + integer);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.d("ccy", "error");
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });
    }

    private long time;

    private void interTest() {
        time = System.currentTimeMillis();
        Observable.interval(3, TimeUnit.SECONDS)
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(Long aLong) throws Exception {
                        Log.d("ccy", "invoke");
                        return aLong;
                    }
                })
                .flatMap(new Function<Long, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(Long aLong) throws Exception {
                        return Observable.create(new ObservableOnSubscribe<Object>() {
                            @Override
                            public void subscribe(ObservableEmitter<Object> e) throws Exception {
                                Thread.sleep(5000);
                                Log.d("ccy", "emit,t = " + Thread.currentThread().getName());
                                Log.d("ccy", "time = " + (System.currentTimeMillis() - time));
                                time = System.currentTimeMillis();
                                e.onNext("1");
                            }
                        });
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        Log.d("ccy", "onnext");
                    }
                });
    }


    private String tempStr = "ppp";
    private boolean isFirst = true;

    public Observable<String> ob(boolean isFirst) {
        if (isFirst) {
            return Observable.create(new ObservableOnSubscribe<String>() {
                @Override
                public void subscribe(ObservableEmitter<String> e) throws Exception {
//                    Log.d("ccy", "subscribe,thread = " + Thread.currentThread());
//                    Log.d("ccy", "start repeat,delta time = " + (System.currentTimeMillis() -
// times));
//                    times = System.currentTimeMillis();
//                    Thread.sleep(5000);
//                    Log.d("ccy", "emitter thread,delta time =  " + (System.currentTimeMillis() -
//                            times));
//                    times = System.currentTimeMillis();
                    e.onNext("a1");
                    e.onComplete();
                }
            });
        } else {
            return Observable.just("not first");
        }
    }


    private long times;

    public void test2() {
        times = System.currentTimeMillis();
        Observable.just(1)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        Log.d("ccy", "doOnSubscribe");
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .flatMap(new Function<Integer, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(Integer integer) throws Exception {
                        Log.d("ccy", "flatMap,change isFirst,T = " + Thread.currentThread()
                                .getName());
                        isFirst = !isFirst;
                        return ob(isFirst);
                    }
                })
                .flatMap(new Function<String, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(String s) throws Exception {
                        Log.d("ccy", "flatMap,thread = " + Thread.currentThread().getName());
                        tempStr = s;
                        return Observable.just(s);
                    }
                })
                .repeatWhen(new Function<Observable<Object>, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(Observable<Object> objectObservable)
                            throws Exception {

                        return objectObservable.flatMap(new Function<Object, ObservableSource<?>>
                                () {

                            @Override
                            public ObservableSource<?> apply(Object o) throws Exception {
                                Log.d("ccy", "repeatWhen,isFirst = " + isFirst);
                                return Observable.timer(2, TimeUnit.SECONDS);
                            }
                        });
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.d("ccy", "doFinally2,thread = " + Thread.currentThread().getName());
                    }
                })
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposables.add(d);
                        Log.d("ccy", "onSubscribe,t = " + Thread.currentThread().getName());
                    }

                    @Override
                    public void onNext(String s) {
                        Log.d("ccy", "onNext,s = " + s + ";t = " + Thread.currentThread().getName
                                ());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("ccy", "onError,t = " + Thread.currentThread().getName());
                    }

                    @Override
                    public void onComplete() {
                        Log.d("ccy", "onComplete,t = " + Thread.currentThread().getName());
                    }
                });
    }


    class MyTextWatcher implements TextWatcher {
        private Subject<String> subject;

        public MyTextWatcher(Subject<String> subject) {
            this.subject = subject;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            subject.onNext(s.toString());
        }
    }


    private void subjectErrorDemo() {

    }


    private void delayErrorTest() {
        Observable<String> o1 = Observable.create(new ObservableOnSubscribe<String>
                () {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext("1");
                e.onComplete();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnEach(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d("ccy", "o1,onSubscribe");
                    }

                    @Override
                    public void onNext(String s) {
                        Log.d("ccy", "o1,onNext");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("ccy", "o1,onError");
                    }

                    @Override
                    public void onComplete() {
                        Log.d("ccy", "o1,onComplete");
                    }
                });

        Observable<String> o2 = Observable.create(new ObservableOnSubscribe<String>
                () {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onError(new Throwable("aa"));
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnEach(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d("ccy", "o2,onSubscribe");
                    }

                    @Override
                    public void onNext(String s) {
                        Log.d("ccy", "o2,onNext");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("ccy", "o2,onError");
                    }

                    @Override
                    public void onComplete() {
                        Log.d("ccy", "o2,onComplete");
                    }
                });


        Observable.zip(o1, o2, new BiFunction<String, String, Object>() {
            @Override
            public Object apply(String s, String s2) throws Exception {
                Log.d("ccy", "zip apply");
                return new Object();
            }
        }, true)
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d("ccy", "zip,onSubscribe");
                    }

                    @Override
                    public void onNext(Object o) {
                        Log.d("ccy", "zip,onNext");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("ccy", "zip,onError");
                    }

                    @Override
                    public void onComplete() {
                        Log.d("ccy", "zip,onComplete");
                    }
                });
    }


    private boolean flag;

    private void retryAndRepeatTest() {
        Log.d("ccy", "retryAndRepeatTest start");
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                Thread.sleep(1000);
                if (flag) {
                    Log.d("ccy", "Emitter onComplete");
                    e.onNext(10);
                    e.onComplete();
                } else {
                    Log.d("ccy", "Emitter onError");
                    e.onError(new Throwable("err"));
                }
                flag = !flag;
            }
        })
                .map(new Function<Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer) throws Exception {
                        Log.d("ccy", "map,t = " + Thread.currentThread().getName());
                        return integer;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retryWhen(new Function<Observable<Throwable>, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(Observable<Throwable> throwableObservable)
                            throws Exception {
                        Log.d("ccy", "retryWhen,t = " + Thread.currentThread().getName());
                        return throwableObservable.flatMap(new Function<Throwable,
                                ObservableSource<?>>() {

                            @Override
                            public ObservableSource<?> apply(Throwable throwable) throws Exception {
                                Log.d("ccy", "throw msg = " + throwable.getMessage());
                                return Observable.timer(1, TimeUnit.SECONDS);
                            }
                        });
                    }
                })
                .repeatWhen(new Function<Observable<Object>, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(Observable<Object> objectObservable) throws
                            Exception {
                        Log.d("ccy", "repeatWhen,t = " + Thread.currentThread().getName());
                        return objectObservable.flatMap(new Function<Object, ObservableSource<?>>
                                () {


                            @Override
                            public ObservableSource<?> apply(Object o) throws Exception {
                                Log.d("ccy", "o is integer ? =" + (o instanceof Integer)
                                        + ";o = " + (Integer) o);
                                return Observable.timer(1, TimeUnit.SECONDS);
                            }
                        });
                    }
                })
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d("ccy", "onSubscribe");
                        disposables.add(d);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d("ccy", "onNext");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("ccy", "onError");

                    }

                    @Override
                    public void onComplete() {
                        Log.d("ccy", "onComplete");
                    }
                });
    }

    @SuppressLint("CheckResult")
    private void demo() {
        //创建一个上游 Observable：
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
            }
        })
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d("ccy", "我只关心onNext事件");
                    }
                });

        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                Log.d(TAG, "emit 1");
                emitter.onNext(1);
                Log.d(TAG, "emit 2");
                emitter.onNext(2);
                Log.d(TAG, "emit 3");
                emitter.onNext(3);
                Log.d(TAG, "emit complete");
                emitter.onComplete();
                Log.d(TAG, "emit 4");
                emitter.onNext(4);
            }
        }).subscribe(new Observer<Integer>() {
            private Disposable mDisposable;
            private int i;

            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "subscribe");
                mDisposable = d;
            }

            @Override
            public void onNext(Integer value) {
                Log.d(TAG, "onNext: " + value);
                i++;
                if (i == 2) {
                    Log.d(TAG, "dispose");
                    mDisposable.dispose();
                    Log.d(TAG, "isDisposed : " + mDisposable.isDisposed());
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "error");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "complete");
            }
        });


    }

    private int count;

    @SuppressLint("CheckResult")
    private void demo1() {

        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                Log.d("ccy", "emitter thread = " + Thread.currentThread().getName());
                e.onNext(0);
                e.onComplete();
            }
        })
                .flatMap(new Function<Integer, ObservableSource<Integer>>() {
                    @Override
                    public ObservableSource<Integer> apply(Integer integer) throws Exception {
                        Log.d("ccy", "flat map thread = " + Thread.currentThread().getName());
                        Thread.sleep(2000);
                        return Observable.just(1);
                    }
                })
                .subscribeOn(Schedulers.single())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d("ccy", "doOnNext 1");
                    }
                })
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.d("ccy", "doFinally1 1");
                    }
                })
                .filter(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        Log.d("ccy", "filter integer = " + integer);
                        return integer == 1;
                    }
                })
                .observeOn(Schedulers.io())
                .flatMap(new Function<Integer, ObservableSource<Integer>>() {
                    @Override
                    public ObservableSource<Integer> apply(Integer integer) throws Exception {
                        Log.d("ccy", "flatMap");
                        Thread.sleep(2000);
                        return Observable.just(2);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d("ccy", "doOnNext 2");
                    }
                })
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.d("ccy", "doFinally 2");
                    }
                })
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d("ccy", "onNext");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("ccy", "onError");
                    }

                    @Override
                    public void onComplete() {
                        Log.d("ccy", "onComplete");
                    }
                });

    }

    /**
     * timer操作符说它自己默认执行在computation线程，
     * 那么我在repeatWhen通过timer实现延迟重订阅时，它会改变我原本的线程流吗？
     * 结论：会，但调整根据subscribeOn、observeOn调用位置即可修改
     */
    private void demo2() {

        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                Log.d("ccy", "发射线程 = " + Thread.currentThread().getName());
                Thread.sleep(2000);
                e.onNext(1);
                e.onComplete();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .repeatWhen(new Function<Observable<Object>, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(Observable<Object> objectObservable) throws
                            Exception {
                        Log.d("ccy", "repeatWhen（外） 线程 = " + Thread.currentThread().getName());
                        return objectObservable.flatMap(new Function<Object, ObservableSource<?>>
                                () {

                            @Override
                            public ObservableSource<?> apply(Object o) throws Exception {
                                Log.d("ccy", "repeatWhen（内） 线程 = " + Thread.currentThread()
                                        .getName());
                                return Observable.timer(2, TimeUnit.SECONDS);
                            }
                        });
                    }
                })
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposables.add(d);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d("ccy", "onNext 线程 = " + Thread.currentThread().getName());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 测试fromIterable发出的每个onNext是在一个线程排队执行还是直接并行.
     * 结论：在同一个线程
     */
    private void demo3() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);

//        Observable.fromIterable(list)
//                .flatMap(new Function<Integer, ObservableSource<String>>() {
//                    @Override
//                    public ObservableSource<String> apply(final Integer s) throws Exception {
//                        Log.d("ccy", "外层flatMap = " + Thread.currentThread().getName());
//                        return Observable.create(new ObservableOnSubscribe<String>() {
//                            @Override
//                            public void subscribe(ObservableEmitter<String> e) throws Exception {
//                                Log.d("ccy", "内层flatMap = " + Thread.currentThread().getName());
//                                Thread.sleep(1000);
//                                e.onNext("this" + s);
//                                e.onComplete();
//                            }
//                        });
//                    }
//                })
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<String>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(String s) {
//                        Log.d("ccy", "onNext = " + s + ";" + Thread.currentThread().getName());
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });


        //测试全部过滤掉的话最终的list是null还是长度为0
        //结论：长度为0
        Observable.fromIterable(list)
                .filter(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        return false;
                    }
                })
                .toList()
                .toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Integer>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Integer> integers) {
                        Log.d("ccy", "" + (integers == null ? "null" : integers.size() + ""));
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    public class a {

    }


    @Override
    protected void onDestroy() {
        disposables.clear();
        super.onDestroy();
    }
}
