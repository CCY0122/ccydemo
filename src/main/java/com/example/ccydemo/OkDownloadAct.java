package com.example.ccydemo;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.liulishuo.okdownload.DownloadListener;
import com.liulishuo.okdownload.DownloadTask;
import com.liulishuo.okdownload.OkDownload;
import com.liulishuo.okdownload.StatusUtil;
import com.liulishuo.okdownload.core.breakpoint.BreakpointInfo;
import com.liulishuo.okdownload.core.cause.EndCause;
import com.liulishuo.okdownload.core.cause.ResumeFailedCause;
import com.liulishuo.okdownload.core.connection.DownloadOkHttp3Connection;
import com.liulishuo.okdownload.core.connection.DownloadUrlConnection;
import com.liulishuo.okdownload.core.listener.DownloadListener1;
import com.liulishuo.okdownload.core.listener.assist.Listener1Assist;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by ccy(17022) on 2018/12/21 下午2:07
 */
public class OkDownloadAct extends BaseActivity {

    TextView tvProgress;
    //        String url = "https://magic.h3c
    // .com/smarthomeback/api/app/plugin/key?platform=1&pluginCode=uuNetEase";
    String url = "https://b2.market.mi-img.com/download/AppStore/079d55859b124d3248fd219fa9945651547426ad2";
    DownloadTask task;
    String TAG = "ccy";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.okdownload_act);
        tvProgress = (TextView) findViewById(R.id.tv3);

//        DownloadOkHttp3Connection.Factory factory = new DownloadOkHttp3Connection.Factory();
//        factory.builder()
//                .readTimeout(100, TimeUnit.SECONDS)
//                .writeTimeout(100, TimeUnit.SECONDS)
//                .connectTimeout(100, TimeUnit.SECONDS);
//        OkDownload.Builder okDownloadBuilder = new OkDownload.Builder(this)
//                .connectionFactory(factory);
//        OkDownload.setSingletonInstance(okDownloadBuilder.build());

        task = new DownloadTask.Builder(url, Environment
                .getExternalStorageDirectory() + "/okDownloadTest/", "testapk.apk")
                .setPassIfAlreadyCompleted(false)
                .build();

//        Observable.create(new ObservableOnSubscribe<Object>() {
//            @Override
//            public void subscribe(ObservableEmitter<Object> e) throws Exception {
//                OkHttpClient client = new OkHttpClient.Builder().build();
//                Request request = new Request.Builder()
//                        .url(url)
//                        .build();
//                Response response = client.newCall(request).execute();
//                if (response != null && response.isSuccessful()) {
//                    long contentLength = response.body().contentLength();
//                    Log.d("ccy", contentLength + "length");
//                    response.close();
//                }
//            }
//        })
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe();
    }

    public void startDownload(View v) {


//        Observable.create(new ObservableOnSubscribe<Float>() {
//            @Override
//            public void subscribe(final ObservableEmitter<Float> e) throws Exception {
//                task.execute(new DownloadListener1() {
//                    @Override
//                    public void taskStart(@NonNull DownloadTask task, @NonNull Listener1Assist
//                            .Listener1Model model) {
//                        Log.i(TAG, "taskStart: ");
//                    }
//
//                    @Override
//                    public void retry(@NonNull DownloadTask task, @NonNull ResumeFailedCause
//                            cause) {
//                        Log.i(TAG, "retry: ");
//                    }
//
//                    @Override
//                    public void connected(@NonNull DownloadTask task, int blockCount, long
//                            currentOffset,
//                                          long totalLength) {
//                        Log.d(TAG, "connected() , blockCount = [" +
//                                blockCount + "], currentOffset = [" + currentOffset + "], " +
//                                "totalLength = " +
//                                "[" + totalLength + "]");
//                    }
//
//                    @Override
//                    public void progress(@NonNull DownloadTask task, long currentOffset, long
//                            totalLength) {
//                        Log.d(TAG, "progress() , currentOffset = [" +
//                                currentOffset + "], totalLength = [" + totalLength + "]" + ";
// pro " +
//                                "= " +
//                                (currentOffset / (totalLength * 1.0f)));
//                        e.onNext((currentOffset / (totalLength * 1.0f)));
//                    }
//
//                    @Override
//                    public void taskEnd(@NonNull DownloadTask task, @NonNull EndCause cause,
//                                        @Nullable
//                            Exception realCause, @NonNull Listener1Assist.Listener1Model model) {
//                        Log.i(TAG, "taskEnd: ");
//                        e.onComplete();
//                    }
//                });
//            }
//        })
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<Float>() {
//                    @Override
//                    public void accept(Float aFloat) throws Exception {
//                        Log.d("ccy", "pro = " + aFloat);
//                    }
//                });

//        task.enqueue(new DownloadListener() {
//            @Override
//            public void taskStart(@NonNull DownloadTask task) {
//                Log.i(TAG, "taskStart: ");
//            }
//
//            @Override
//            public void connectTrialStart(@NonNull DownloadTask task, @NonNull Map<String,
//                    List<String>> requestHeaderFields) {
//                Log.i(TAG, "connectTrialStart: ");
//            }
//
//            @Override
//            public void connectTrialEnd(@NonNull DownloadTask task, int responseCode, @NonNull
//                    Map<String, List<String>> responseHeaderFields) {
//                Log.i(TAG, "connectTrialEnd: ");
//            }
//
//            @Override
//            public void downloadFromBeginning(@NonNull DownloadTask task, @NonNull BreakpointInfo
//                    info, @NonNull ResumeFailedCause cause) {
//                total = info.getTotalLength();
//                Log.i(TAG, "downloadFromBeginning: total = " + info.getTotalLength() + ";offset =" +
//                        " " + info.getTotalOffset());
//            }
//
//            @Override
//            public void downloadFromBreakpoint(@NonNull DownloadTask task, @NonNull
//                    BreakpointInfo info) {
//                total = info.getTotalLength();
//                Log.i(TAG, "downloadFromBreakpoint: total = " + info.getTotalLength() + "; offset" +
//                        " " +
//                        "= " + info.getTotalOffset());
//            }
//
//            @Override
//            public void connectStart(@NonNull DownloadTask task, int blockIndex, @NonNull
//                    Map<String, List<String>> requestHeaderFields) {
//                Log.i(TAG, "connectStart: ");
//            }
//
//            @Override
//            public void connectEnd(@NonNull DownloadTask task, int blockIndex, int responseCode,
//                                   @NonNull Map<String, List<String>> responseHeaderFields) {
//                Log.i(TAG, "connectEnd: ");
//            }
//
//            @Override
//            public void fetchStart(@NonNull DownloadTask task, int blockIndex, long
//                    contentLength) {
//                Log.i(TAG, "fetchStart: total = " + contentLength);
//            }
//
//            @Override
//            public void fetchProgress(@NonNull DownloadTask task, int blockIndex, long
//                    increaseBytes) {
//                Log.i(TAG, "fetchProgress: blockIndex = " + blockIndex + ";increaseBytes = " +
//                        increaseBytes);
//                Log.d(TAG, "PRO = " + (increaseBytes / (total * 1.0f)));
//            }
//
//            @Override
//            public void fetchEnd(@NonNull DownloadTask task, int blockIndex, long contentLength) {
//                Log.i(TAG, "fetchEnd: ");
//            }
//
//            @Override
//            public void taskEnd(@NonNull DownloadTask task, @NonNull EndCause cause, @Nullable
//                    Exception realCause) {
//                Log.i(TAG, "taskEnd: cause = " + cause.name() + ";real cause = " + (realCause !=
//                        null ? realCause.getMessage() : " is null"));
//            }
//        });
        task.enqueue(new DownloadListener1() {
            @Override
            public void taskStart(@NonNull DownloadTask task, @NonNull Listener1Assist
                    .Listener1Model model) {

            }

            @Override
            public void retry(@NonNull DownloadTask task, @NonNull ResumeFailedCause cause) {

            }

            @Override
            public void connected(@NonNull DownloadTask task, int blockCount, long currentOffset,
                                  long totalLength) {

                Log.d(TAG, "connected() called with: task = [" + task + "], blockCount = [" +
                        blockCount + "], currentOffset = [" + currentOffset + "], totalLength = " +
                        "[" + totalLength + "]");
            }

            @Override
            public void progress(@NonNull DownloadTask task, long currentOffset, long totalLength) {
                float frac = currentOffset / (totalLength * 1.0f);
                int pr = (int) (frac * 100);
                Log.d("ccy","pro = " + (pr));
            }

            @Override
            public void taskEnd(@NonNull DownloadTask task, @NonNull EndCause cause, @Nullable Exception realCause, @NonNull Listener1Assist.Listener1Model model) {

                Log.d("ccy","end,cause = " + cause.name() + ";realCause = " +(realCause == null ? "null" : realCause.getMessage()));
            }
        });

    }

    private long total = 1;

    public void stopDownload(View v) {
        task.cancel();
    }

    public void isComplete(View v){
        Log.d("ccy","" + StatusUtil.isCompleted(task));
        Log.d("ccy","state = " + StatusUtil.getStatus(task).name());
    }
}
