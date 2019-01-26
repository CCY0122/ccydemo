package com.example.ccydemo;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.ccydemo.BadgeAndSlideMenu.BadgeAndSlidMenu;
import com.example.ccydemo.DragAndSwipe.MainActivity;
import com.example.ccydemo.EventBusDemo.EventBusAct1;
import com.example.ccydemo.PhotoViewDemo.PhotoViewDemo;
import com.example.ccydemo.RecordDemo.AudioRecordActivity;
import com.example.ccydemo.RecordDemo.MediaRecoderActivity;
import com.example.ccydemo.RetrofitDemo.RetrofitActivity;
import com.example.ccydemo.RxjavaDemo.RxActivity;
import com.example.ccydemo.TestDemo.TestAct;
import com.example.ccydemo.ViewDragHelper.Act;
import com.example.ccydemo.ZxingDemo.ZxingByOpen;
import com.example.ccydemo.brvah.BRVAHActivity;
import com.example.ccydemo.collapsingtoolbar.CollapsingToolBarActivity;
import com.example.ccydemo.douyin.DouYinActivity;
import com.example.ccydemo.dragAndDropDemo.DragAndDropAct;
import com.example.ccydemo.gifDemo.GifAct;
import com.example.ccydemo.multiWndForXM.MultiWndAct;
import com.example.ccydemo.multitypeDemo.MultitypeAct;
import com.example.ccydemo.nestedscroll.NestedScrollActivity;
import com.example.ccydemo.okhttpDemo.OkhttpActivity;
import com.example.ccydemo.rectLoadingView.RectLoadingAct;
import com.example.ccydemo.recyclerviewDiffutilDemo.DiffUtilAct;
import com.example.ccydemo.rollerRadioGroup.RollerRadioAct;
import com.example.ccydemo.selectitem.SelectItemActivity;
import com.example.ccydemo.sliderDemo.SliderAct1;
import com.example.ccydemo.videoViewDemo.VideoViewAct;
import com.example.ccydemo.wavedemo.WaveActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by XMuser on 2017-02-03.
 */

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {
    private Button b1;
    private Button b2;
    private Button b3;
    private Button b4;
    private Button b5;
    private Button b6;

    @BindView(R.id.b8)
    Button b8;
    @BindView(R.id.b9)
    Button b9;
    @BindView(R.id.b10)
    Button b10;
    @BindView(R.id.b11)
    Button b11;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity);
        ButterKnife.bind(this);
        getSupportActionBar().hide();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View
                    .SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            decorView.setSystemUiVisibility(option);

            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        b1 = (Button) findViewById(R.id.b1);
        b2 = (Button) findViewById(R.id.b2);
        b3 = (Button) findViewById(R.id.b3);
        b4 = (Button) findViewById(R.id.b4);
        b5 = (Button) findViewById(R.id.b5);
        b6 = (Button) findViewById(R.id.b6);

        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        b4.setOnClickListener(this);
        b5.setOnClickListener(this);
        b6.setOnClickListener(this);

        c(R.id.b7);


//        Log.d("ccy","onCreate : " + getIntent().getAction());
    }

    @Override
    protected void onResume() {
        super.onResume();
//        Log.d("ccy","onResume : " + getIntent().getAction());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.b1:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.b2:
                startActivity(new Intent(this, PhotoViewDemo.class));
                break;
            case R.id.b3:
                startActivity(new Intent(this, MediaRecoderActivity.class));
                break;
            case R.id.b4:
                s(AudioRecordActivity.class);
                break;
            case R.id.b5:
                s(ZxingByOpen.class);
                break;
            case R.id.b6:
                Intent i = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                startActivity(i);
                break;
            case R.id.b7:
                s(BadgeAndSlidMenu.class);
        }
    }

    public void s(Class clz) {
        startActivity(new Intent(this, clz));
    }

    public void c(int resId) {
        Button b = (Button) findViewById(resId);
        b.setOnClickListener(this);
    }


    @OnClick(R.id.b8)
    void b8(Button v) {
        if (v == b8) {
            Toast.makeText(this, "true;", Toast.LENGTH_LONG).show();
        }
        s(Act.class);
    }

    @OnClick(R.id.b9)
    void b9() {
        s(com.example.ccydemo.RecyclerViewHeader.Act.class);
    }

    @OnClick(R.id.b10)
    void b10(View v) {
        s(RetrofitActivity.class);
    }

    @OnClick(R.id.b11)
    void b11(View v) {
        s(EventBusAct1.class);
    }

    @OnClick(R.id.b12)
    void b12() {
        s(RxActivity.class);
    }

    @OnClick(R.id.b13)
    void b13() {
        s(OkhttpActivity.class);
    }

    @OnClick(R.id.b14)
    void b14() {
        s(VideoViewAct.class);
    }

    @OnClick(R.id.b15)
    void b15() {
        s(GifAct.class);
    }

    @OnClick(R.id.b16)
    void b16() {
        s(DiffUtilAct.class);
    }

    @OnClick(R.id.b17)
    void b17() {
        s(DragAndDropAct.class);
    }

    @OnClick(R.id.b18)
    void b18() {
        s(MultitypeAct.class);
    }

    @OnClick(R.id.b19)
    void b19() {
        s(RectLoadingAct.class);
    }

    @OnClick(R.id.b20)
    void b20() {
        s(RollerRadioAct.class);
    }

    @OnClick(R.id.b21)
    void b21() {
        s(SliderAct1.class);
    }

    @OnClick(R.id.b22)
    void b22() {
        s(MultiWndAct.class);
    }

    @OnClick(R.id.b23)
    void b23() {
        s(WeatherLineAct.class);
    }

    @OnClick(R.id.b24)
    void b24() {
        s(TestAct.class);
    }

    @OnClick(R.id.b25)
    void b25() {
        s(WaveActivity.class);
    }

    @OnClick(R.id.b26)
    void b26() {
        s(NestedScrollActivity.class);
    }

    @OnClick(R.id.b27)
    void b27() {
        s(BRVAHActivity.class);
    }

    @OnClick(R.id.b28)
    void b28() {
        s(DouYinActivity.class);
    }

    @OnClick(R.id.b29)
    void b29() {
        s(CollapsingToolBarActivity.class);
    }

    @OnClick(R.id.b30)
    void b30() {
        s(SelectItemActivity.class);

    }

    @OnClick(R.id.b31)
    void b31() {
//        s(OkDownloadAct.class);


//        Notification build = new NotificationCompat.Builder(this)
//                .setTicker("ticker")
//                .setContentTitle("content title")
//                .setContentText("contcnt text")
//                .setSmallIcon(R.drawable.uu)
//                .setContentIntent(PendingIntent.getActivity(this, 0, new Intent(), PendingIntent
//                        .FLAG_ONE_SHOT))
//                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.cloudy))
//                .build();
//        NotificationManager notificationManager = (NotificationManager) getSystemService
//                (NOTIFICATION_SERVICE);
//        build.flags = Notification.FLAG_NO_CLEAR;
//        notificationManager.notify(1, build);
//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        intent.setData(Uri.parse("h3cmagic://app.memory?fromWho=lalala&tag=ccy"));
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        if (getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY) != null) {
//            startActivity(intent);
//        } else {
//            Toast.makeText(this, "找不到", Toast.LENGTH_LONG).show();
//        }
        //this is test brance
        // this is main

        //this is test 2222
        //test test test
    }

}

