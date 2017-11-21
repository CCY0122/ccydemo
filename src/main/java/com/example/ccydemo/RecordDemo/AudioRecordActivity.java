package com.example.ccydemo.RecordDemo;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ccydemo.R;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by XMuser on 2017-02-04.
 * 用AudioRecord录音，AudioTrack播放。 录的是原始音频，更底层
 */

public class AudioRecordActivity extends AppCompatActivity implements View.OnClickListener {

    private Button startRecord;
    private Button stopRecord;
    private Button play;
    private Button stop;
    private Button pause;
    private TextView volume;
    private TextView length;

    private AudioRecord audioRecord;
    private AudioTrack audioTrack;
    private File filePath;
    private Boolean isRecording;
    private Boolean isPlaying;
    private DataOutputStream dop;
    private DataInputStream dip;

    private final int audioSource = MediaRecorder.AudioSource.MIC;
    private final int sampleRateInHz = 44100;   //hz
    private final int channelConfig = AudioFormat.CHANNEL_IN_MONO;  //单声道
    private final int audioFormat = AudioFormat.ENCODING_PCM_16BIT;
    private int minBufferSize = AudioRecord.getMinBufferSize(sampleRateInHz, channelConfig, audioFormat);

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.audio_record_act);

        initView();
        filePath = new File(getCacheDir() + File.separator + "audioRecordDemo.pcm");

    }

    private void initView() {
        startRecord = (Button) findViewById(R.id.audio_start_r);
        stopRecord = (Button) findViewById(R.id.audio_stop_r);
        play = (Button) findViewById(R.id.audio_play);
        pause = (Button) findViewById(R.id.audio_pause_play);
        stop = (Button) findViewById(R.id.audio_stop_play);
        volume = (TextView) findViewById(R.id.audio_volum);
        length = (TextView) findViewById(R.id.audio_length);

        startRecord.setOnClickListener(this);
        stopRecord.setOnClickListener(this);
        play.setOnClickListener(this);
        stop.setOnClickListener(this);
        pause.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.audio_start_r:
                startRecord.setText("recording.....");
                startRecord.setClickable(false);
                new Thread(new RecordThread()).start();
                break;
            case R.id.audio_stop_r:
                startRecord.setText("start record");
                startRecord.setClickable(true);
                stopAudioRecord();
                break;
            case R.id.audio_play:
                Log.d("b4","play click");
                new Thread(new PlayThread()).start();
                break;
            case R.id.audio_pause_play:
                Log.d("access","1");
                break;
            case R.id.audio_stop_play:
                stopAudioTrack();
                break;
        }
    }

    private void stopAudioTrack() {
        if (audioTrack != null) {
            isPlaying = false;
            audioTrack.stop();
            audioTrack.release();
            audioTrack = null;
            Log.d("b4","stopAudioTrack");
        }
    }

    private void stopAudioRecord() {
        if (audioRecord != null) {
            isRecording = false;
            audioRecord.stop();
            audioRecord.release();
            audioRecord = null;
            Log.d("b4","stopAudioRecord");
        }
    }


    class RecordThread implements Runnable {
        @Override
        public void run() {
            isRecording = true;
            try {
                dop = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(filePath)));
                audioRecord = new AudioRecord(audioSource, sampleRateInHz, channelConfig, audioFormat, minBufferSize);
                short[] buff = new short[minBufferSize]; //大小待测试
                audioRecord.startRecording();
                Log.d("b4","audio strat record. minBufferSize = "+minBufferSize+";");
                while (isRecording) {
                    int a = audioRecord.read(buff , 0 , buff.length);
                    for (int i = 0; i < a; i++) {
                        dop.writeShort(buff[i]);
                    }
//                    Log.d("b4","a = "+a);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    dop.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class PlayThread implements Runnable {
        @Override
        public void run() {
            isPlaying = true;
            try {
                short[] buff = new short[minBufferSize*4]; //大小多少合适？
                dip = new DataInputStream(new BufferedInputStream(new FileInputStream(filePath)));
                Log.d("b4","file length = " + filePath.length());
                audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,sampleRateInHz,AudioFormat.CHANNEL_OUT_MONO,audioFormat,minBufferSize,AudioTrack.MODE_STREAM);
                audioTrack.play();
                Log.d("b4","audioTrack play");
                while (isPlaying && dip.available() >0){
                    int i =0;
                    while (i < buff.length && dip.available()>0) {
                        buff[i] = dip.readShort();
                        i++;
                    }
                    audioTrack.write(buff , 0 , i);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    dip.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
