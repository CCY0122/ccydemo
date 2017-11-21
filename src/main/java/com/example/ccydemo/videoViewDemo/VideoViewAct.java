package com.example.ccydemo.videoViewDemo;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.ccydemo.BaseActivity;
import com.example.ccydemo.R;

import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ccy on 2017-10-25.
 */

public class VideoViewAct extends BaseActivity {

    @BindView(R.id.video_view)
    VideoView videoView;
    @BindView(R.id.progress_bar)
    ProgressBar pb;
    @BindView(R.id.btn_play)
    ImageView btnPlay;
    @BindView(R.id.seek_bar)
    SeekBar seekBar;
    @BindView(R.id.video_control)
    RelativeLayout video_control;
    @BindView(R.id.current_pos)
    TextView currentPos;
    @BindView(R.id.total_pos)
    TextView totalPos;

    private boolean hasPrepared = false;

    private String uriStr = "http://test-video-123.oss-cn-beijing.aliyuncs.com/c142dd39f8222e1d_171025111447.m3u8";
    private String uriStr2 = "http://v.youku.com/v_show/id_XMzA5MDk5OTcxMg==.html?spm=a2hww.20027244.m_250036.5~5!2~5~5~5~5~A";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_view_act);
        ButterKnife.bind(this);

        pb.setVisibility(View.VISIBLE);

        videoView.post(new Runnable() {
            @Override
            public void run() {
                videoView.setVideoURI(Uri.parse(uriStr));
                videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        pb.setVisibility(View.GONE);
                        hasPrepared = true;
                        seekBar.setMax(mp.getDuration());
                        totalPos.setText("/" + mp.getDuration());

                        mp.start();

                        initMediaPlayerListener(mp);

                        startProgressUpdate();
                    }
                });
            }
        });

        initViewoViewListener();



        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                stopProgressUpdate();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                videoView.seekTo(seekBar.getProgress());
            }
        });

    }

    private void startProgressUpdate(){
        handler.sendEmptyMessage(1);
    }
    private void stopProgressUpdate(){
        handler.removeCallbacksAndMessages(null);
    }

    private Handler handler=  new Handler(){
        @Override
        public void dispatchMessage(Message msg) {
            switch (msg.what){
                case 1:
//                    Log.d("ccy","progress update");
                    seekBar.setProgress(videoView.getCurrentPosition());
                    currentPos.setText("" + videoView.getCurrentPosition());
                    handler.sendEmptyMessageDelayed(1,500);
                    break;
            }
            super.dispatchMessage(msg);
        }
    };


    private void initMediaPlayerListener(MediaPlayer mp){
        mp.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                switch (what){
                    case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                        pb.setVisibility(View.VISIBLE);
                        break;
                    case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                        pb.setVisibility(View.GONE);
                        break;
                }
                return false;
            }
        });

        mp.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() {
            @Override
            public void onSeekComplete(MediaPlayer mp) {
                startProgressUpdate();
                mp.start();
            }
        });


    }

    private void initViewoViewListener(){
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Toast.makeText(VideoViewAct.this,"播放结束",Toast.LENGTH_LONG).show();
            }
        });
        videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                pb.setVisibility(View.GONE);
                btnPlay.setVisibility(View.VISIBLE);
                return true;
            }
        });

        videoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(hasPrepared){
                    if(videoView.isPlaying()){
                        videoView.pause();
                        btnPlay.setVisibility(View.VISIBLE);
                        pb.setVisibility(View.GONE);
                    }else {
                        videoView.start();
                        btnPlay.setVisibility(View.GONE);
                    }
                }
            }
        });

//        videoView.setMediaController(new MediaController(this));
    }
}
