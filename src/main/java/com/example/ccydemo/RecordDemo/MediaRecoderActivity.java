package com.example.ccydemo.RecordDemo;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ccydemo.R;

import java.io.File;
import java.io.IOException;

/**
 * Created by XMuser on 2017-02-03.
 * 用MediaRecorder录音，用MediaPlayer播放。
 * 写完后发现没有在子线程录音，但测试好像并不会阻塞线程。
 */

public class MediaRecoderActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {

    private Button permissionBtn;
    private Button record;
    private Button play;
    private Button pause;
    private Button stop;
    private Button restart;
    private TextView volume;
    private TextView length;

    private MediaRecorder mediaRecorder;
    private MediaPlayer mediaPlayer;
    private String filePath;
    private String[] permissions = {android.Manifest.permission.RECORD_AUDIO};

    private Boolean recorderInitFlag = false; //录音初始化完毕标志


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.media_recorder_act);

        permissionBtn = (Button) findViewById(R.id.recoder_permission);
        record = (Button) findViewById(R.id.recoder_start_record);
        play = (Button) findViewById(R.id.recoder_play);
        restart = (Button) findViewById(R.id.recoder_restart);
        pause = (Button) findViewById(R.id.recoder_pause);
        stop = (Button) findViewById(R.id.recoder_stop);
        volume = (TextView) findViewById(R.id.recoder_volume);
        length = (TextView) findViewById(R.id.recoder_length);

        permissionBtn.setOnClickListener(this);
        record.setOnTouchListener(this);
        play.setOnClickListener(this);
        pause.setOnClickListener(this);
        stop.setOnClickListener(this);
        restart.setOnClickListener(this);

        filePath = getPath(File.separator+"audioDemo.3gp");  //amr、3gp、mp3、wav
        Log.d("b3","filePath="+filePath);
    }
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0 && mediaRecorder!=null) {
                int v = mediaRecorder.getMaxAmplitude();
                volume.setText(v+"");
            }
        }
    };

    private String getPath(String s) {
        return getCacheDir().getPath() + s;
    }


    private void onStart(Boolean flag) {
        if (flag) {
            startRecord();
        } else {
            stopRecord();
        }
    }

    private void stopRecord() {
        if (mediaRecorder != null) {
            if(recorderInitFlag) {
//                mediaRecorder.stop();   //不能再start后马上调用stop，会抛异常，考虑增加录音最短录音时间判断
                mediaRecorder.release();
                mediaRecorder = null;
                Log.d("b3", "stopRecord(recorder has been initialized)");
            }else {
                mediaRecorder = null;
                Log.d("b3", "stopRecord(recorder not initialized)");
            }
        }
        recorderInitFlag = false;
    }

    private void startRecord() {
        long time1 = System.currentTimeMillis();
        recorderInitFlag = false;
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setOutputFile(filePath);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mediaRecorder.prepare();
            mediaRecorder.start();
        } catch (IOException e) {
            Log.d("b3", "record prepare failed");
        }
        recorderInitFlag = true;

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (recorderInitFlag){
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
//                    Log.d("b3",""+mediaRecorder.getMaxAmplitude());
                    mHandler.sendEmptyMessage(0);
                }
            }
        }).start();
        long duration = System.currentTimeMillis() - time1;
        Log.d("b3", "start record,init duration = "+duration+"maxAmplitude="+mediaRecorder.getMaxAmplitude());

    }

    private void onPlay(Boolean flag) {
        if (flag) {
            startPlay();
        } else {
            stopPlay();
        }
    }

    private void pause(Boolean flag) {
        if (mediaPlayer != null) {
            if (flag && mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
            } else {
                mediaPlayer.start();
            }
        }
    }

    private void stopPlay() {
        if(mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
            Log.d("b3", "stop play");
        }
    }

    private void startPlay() {
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(filePath);
            mediaPlayer.prepare();
        } catch (IOException e) {
            Log.d("b3", "player prepare failed");
        }
        length.setText(mediaPlayer.getDuration()+"");
        mediaPlayer.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.recoder_permission:
                AskPermission(permissions);
                break;
            case R.id.recoder_play:
                onPlay(true);
                break;
            case R.id.recoder_pause:
                pause(true);
                break;
            case R.id.recoder_restart:
                pause(false);
                break;
            case R.id.recoder_stop:
                onPlay(false);
                break;
        }
    }

    private void AskPermission(String[] permissions) {
        if (ContextCompat.checkSelfPermission(this, permissions[0]) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, permissions, 1);
        }else {
            Toast.makeText(this,"permission already successed",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults); //需不需要？
        if(requestCode == 1){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this,"permission successed",Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(this,"permission denied",Toast.LENGTH_LONG).show();
            }
        }

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (v.getId() == R.id.recoder_start_record) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    onStart(true);
                    Log.d("b3","dowm");
                    break;
                case MotionEvent.ACTION_UP:
                    onStart(false);
                    Log.d("b3","up");
                    break;
            }
        }
        return false;
    }
}
