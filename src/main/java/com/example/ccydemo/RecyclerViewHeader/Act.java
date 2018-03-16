package com.example.ccydemo.RecyclerViewHeader;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.ccydemo.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

/**
 * Created by XMuser on 2017-06-20.
 */

public class Act extends AppCompatActivity {

    private List<Bean> data;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view_header);
        final RecyclerView rv = (RecyclerView) findViewById(R.id.rv);
        LinearLayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rv.setLayoutManager(manager);
        GridLayoutManager manager1 = new GridLayoutManager(this,4);
        rv.setLayoutManager(manager1);
        initData();
//        Observable<List<Bean>> o = loadDatas("/storage/emulated/0/CSee/temp_images","jpg");
//        o.subscribe(new Consumer<List<Bean>>() {
//            @Override
//            public void accept(List<Bean> beans) throws Exception {
//                RecyclerAdapter adapter = new RecyclerAdapter(Act.this,beans);
//                rv.setAdapter(adapter);
//            }
//        });
        RecyclerAdapter adapter = new RecyclerAdapter(this,data);
        rv.setAdapter(adapter);
        Button b = (Button) findViewById(R.id.b);
    }

    private void initData() {
        data = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Bean b = new Bean();
            b.date = "20160101";
            b.resId = R.drawable.a;
            data.add(b);
        }
        for (int i = 0; i < 8; i++) {
            Bean b = new Bean();
            b.date = "20160202";
            b.resId = R.drawable.q;
            data.add(b);
        }
        for (int i = 0; i < 1; i++) {
            Bean b = new Bean();
            b.date = "20160303";
            b.resId = R.drawable.z;
            data.add(b);
        }
        for (int i = 0; i < 5; i++) {
            Bean b = new Bean();
            b.date = "20160404";
            b.resId = R.drawable.q;
            data.add(b);
        }
        for (int i = 0; i < 11; i++) {
            Bean b = new Bean();
            b.date = "20160505";
            b.resId = R.drawable.a;
            data.add(b);
        }
        for (int i = 0; i < 1; i++) {
            Bean b = new Bean();
            b.date = "20160606";
            b.resId = R.drawable.a;
            data.add(b);
        }
    }

    private Observable<List<Bean>> loadDatas(final String dir, final String... suffix) {
        File file = new File(dir);
        if (file == null || !file.exists()) {
            List<Bean> emptyData = new ArrayList<>();
            return Observable.just(emptyData);
        }
        return Observable.just(file)
                .filter(new Predicate<File>() {
                    @Override
                    public boolean test(File file) throws Exception {
                        File[] files = file.listFiles();
                        return file.isDirectory() && files != null && files.length > 0;
                    }
                })
                .flatMap(new Function<File, ObservableSource<File>>() {
                    @Override
                    public ObservableSource<File> apply(File file) throws Exception {
                        return Observable.fromArray(file.listFiles());
                    }
                })
                .filter(new Predicate<File>() {
                    @Override
                    public boolean test(File file) throws Exception {
                        if (file.isFile()) {
                            String path = file.getPath();
                            for (int i = 0; i < suffix.length; i++) {
                                if (path.endsWith(suffix[i])) {
                                    return true;
                                }
                            }
                        }
                        return false;
                    }
                })
                .map(new Function<File, Bean>() {
                    @Override
                    public Bean apply(File file) throws Exception {
                        Bean bean = new Bean();

                        bean.path = file.getPath();

                        return bean;
                    }
                })
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .toObservable();

    }
}
