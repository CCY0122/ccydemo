package com.example.ccydemo.okhttpDemo;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.example.ccydemo.BaseActivity;
import com.example.ccydemo.R;

import java.io.File;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by ccy on 2017-10-24.
 */

public class OkhttpActivity extends BaseActivity {

    @BindView(R.id.okhttp_b1)
    Button b1;
    @BindView(R.id.okhttp_t1)
    TextView t1;
    @BindView(R.id.okhttp_web)
    WebView webView;

    OkHttpClient client;
    private String url = "https://api.douban.com/v2/movie/search?q=张艺谋&tag=喜剧";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.okhttp_act);
        ButterKnife.bind(this);


        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(5, TimeUnit.SECONDS).readTimeout(5, TimeUnit.SECONDS).writeTimeout(5, TimeUnit.SECONDS);
        builder.retryOnConnectionFailure(true);
        File cacheFile = getCacheDir();
        print("缓存地址 = " + cacheFile.getAbsolutePath());
        print("缓存地址存在？ " + cacheFile.exists());
        File f = new File(getCacheDir().getAbsolutePath()+File.separator+"asd.txt");
        if(!f.exists()){
            f.mkdir();
        }
        File cacheFile2 = getExternalCacheDir();
        print("外部缓存地址 = " + cacheFile2.getAbsolutePath());
        print("外部缓存地址存在？ " + cacheFile2.exists());
        File f2 = new File(getExternalCacheDir().getAbsolutePath()+File.separator+"asdasd.txt");
        if(!f2.exists()){
            f2.mkdir();
        }
        final Cache cache = new Cache(cacheFile2, 1024 * 1024 * 5);  //5M
        builder.cache(cache);
        builder.addNetworkInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Response response = chain.proceed(chain.request());
                return response.newBuilder()
                        .removeHeader("pragma")
                        .addHeader("Cache-Control", "max-stale=10")
                        .addHeader("Cache-Control", "max-age=10")
                        .build();
            }
        });
        client = builder.build();

    }

    @OnClick(R.id.okhttp_b1)
    public void b1() {
        //测试10秒缓存
        Request request = new Request.Builder()
                .cacheControl(new CacheControl.Builder().maxStale(10, TimeUnit.SECONDS).build())
                .url(url)
                .build();


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                print("请求失败：" + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    print("请求成功");
                } else if (response.cacheResponse() != null) {
                    print("获取缓存");
                } else {
                    print("请求失败：" + response.code());
                }
            }
        });
    }

    private void print(final String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                t1.append(text + "\n");
            }
        });
    }

    //copy from GeekNews
    private OkHttpClient provideClient(OkHttpClient.Builder builder) {
//        if (BuildConfig.DEBUG) {
//            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
//            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
//            builder.addInterceptor(loggingInterceptor);
//        }
        File cacheFile = getCacheDir();
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);
        Interceptor cacheInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!isNetworkConnected()) {
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }
                Response response = chain.proceed(request);
                if (isNetworkConnected()) {
                    int maxAge = 0;
                    // 有网络时, 不缓存, 最大保存时长为0
                    response.newBuilder()
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .removeHeader("Pragma")
                            .build();
                } else {
                    // 无网络时，设置超时为4周
                    int maxStale = 60 * 60 * 24 * 28;
                    response.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + 10)
                            .removeHeader("Pragma")
                            .build();
                }
                return response;
            }
        };
        builder.addNetworkInterceptor(cacheInterceptor);
        builder.addInterceptor(cacheInterceptor);
        builder.cache(cache);
        //设置超时
        builder.connectTimeout(10, TimeUnit.SECONDS);
        builder.readTimeout(20, TimeUnit.SECONDS);
        builder.writeTimeout(20, TimeUnit.SECONDS);
        //错误重连
        builder.retryOnConnectionFailure(true);
        return builder.build();
    }

    public  boolean isNetworkConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null;
    }
}
