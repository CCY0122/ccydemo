package com.example.ccydemo.RxjavaDemo;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.view.LayoutInflaterCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ccydemo.BaseActivity;
import com.example.ccydemo.R;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.w3c.dom.Text;

import java.util.ArrayList;

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
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * Created by ccy on 2017-09-22.
 */

public class RxActivity extends BaseActivity {

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


//        doOnSubscribeThreadDemo();

//        doOnSubscribeThreadDemo2();

//        flowableCacheDemo();

//        map();
//        map1();
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
    public void openPopupWindow(){
        View v = LayoutInflater.from(this).inflate(R.layout.popupwindow,null);
        final PopupWindow pop = new PopupWindow(v, 500, 300);
        View content = v.findViewById(R.id.content);
        TextView t = (TextView) v.findViewById(R.id.pop_t1);
        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RxActivity.this,"asdasd",Toast.LENGTH_LONG).show();
                if(pop != null){
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
        v.measure(View.MeasureSpec.UNSPECIFIED,View.MeasureSpec.UNSPECIFIED);
        pop.showAtLocation(popBtn, Gravity.NO_GRAVITY,location1[0],location1[1] - v.getMeasuredHeight());
    }


    /**
     * 状态位测试，无关RXJAVA
     * @param v
     */
    @OnClick({R.id.state_1,R.id.state_2})
    public void stateTest(View v){
        switch (v.getId()){
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
                    public ObservableSource<String> apply(@NonNull final Integer integer) throws Exception {
                        return Observable.create(new ObservableOnSubscribe<String>() {
                            @Override
                            public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
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
                                .subscribeOn(Schedulers.newThread())
                                ;
                    }
                })
//                .subscribeOn(Schedulers.newThread())
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
                        Log.d("ccy", "onComplete,time = " + (System.currentTimeMillis() - lastTime));
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

    private void map1(){
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
                Log.d("ccy","1 emit thread = " + Thread.currentThread());
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
                        Log.d("ccy", "doOnSubscribe thread(before observeOn/2nd subscribeOn) = " + Thread.currentThread());
                        //结论：computation线程(2nd subscribeOn)
                    }
                })
                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.d("ccy", "doOnNext thread (before observeOn)= " + Thread.currentThread());
                        //结论：io线程
                    }
                })
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("ccy", "doOnError thread (before observeOn) = " + Thread.currentThread());
                        //结论：io线程
                    }
                })
                .subscribeOn(Schedulers.computation()) //改变了doOnSubscribe的线程
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        Log.d("ccy", "doOnSubscribe thread (after observeOn/2nd subscribeOn)= " + Thread.currentThread());
                        //结论：主线程（observeOn）
                    }
                })
                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.d("ccy", "doOnNext thread (after observeOn)= " + Thread.currentThread());
                        //结论：主线程（observeOn）
                    }
                })
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("ccy", "doOnError thread (after observeOn) = " + Thread.currentThread());
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
                });
    }

    /**
     * doOnSubscribe/doOnNext/doOnError的线程测试2
     */
    private void doOnSubscribeThreadDemo2(){
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
                        Log.d("ccy", "flatMap thread = " + Thread.currentThread()+";s = " + s);
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
                .observeOn(AndroidSchedulers.mainThread(),true)
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
                    public ObservableSource<? extends String> apply(@NonNull Throwable throwable) throws Exception {
                        Log.d("ccy", "onErrorResumeNext thread = " + Thread.currentThread());
                        lastTime = System.currentTimeMillis();
                        return Observable.create(new ObservableOnSubscribe<String>() {
                            @Override
                            public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
                                Log.d("ccy", "new Observable thread = " + Thread.currentThread());
                                e.onNext("重连后的数据");
                            }
                        });
                    }
                })
                .subscribeOn(Schedulers.newThread())
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
                Log.d("ccy","s1.length = " + s.length() + ";s2.length =" + s2.length());
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
    private void flowableCacheDemo(){
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull FlowableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                Log.d("ccy","emit 1");
                e.onNext(2);
                Log.d("ccy","emit 2");
            }
        }, BackpressureStrategy.ERROR)
                .map(new Function<Integer, String>() {
                    @Override
                    public String apply(@NonNull Integer integer) throws Exception {
                        //结论：执行了
                        Log.d("ccy","map invoked(before observeOn),thread = " + Thread.currentThread().getName());
                        return ""+integer;
                    }
                })
                .subscribeOn(Schedulers.io())
                .map(new Function<String, String>() {
                    @Override
                    public String apply(@NonNull String s) throws Exception {
                        //结论：执行了
                        Log.d("ccy","map invoked(after subscribeOn but before observeOn),thread = " + Thread.currentThread().getName());
                        return ""+s;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<String, String>() {
                    @Override
                    public String apply(@NonNull String s) throws Exception {
                        //结论：未执行
                        Log.d("ccy","map invoked(after observeOn),thread = " + Thread.currentThread().getName());
                        return ""+s;
                    }
                })
                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        //结论：未执行
                        Log.d("ccy","doOnNext invoked(after observeOn),thread = " + Thread.currentThread().getName());
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


}
