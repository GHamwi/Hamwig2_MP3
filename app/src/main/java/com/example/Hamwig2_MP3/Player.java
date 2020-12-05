package com.example.Hamwig2_MP3;

/**************** Created by George B. Hamwi Homework 5 *******************/

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public class Player extends AppCompatActivity {
    SeekBar mSeekBar;
    TextView songTitle;
    ArrayList<File> allSongs;
    static MediaPlayer mMediaPlayer;
    int position;
    TextView curTime;
    TextView totTime;
    ImageView playIcon;
    ImageView prevIcon;
    ImageView nextIcon;
    Intent playerData;
    Bundle bundle;
    ImageView repeatIcon;
    ImageView suffleIcon;
    ImageView curListIcon;

    /* All Icons and text fields that appear on the page */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        mSeekBar = findViewById(R.id.mSeekBar);
        songTitle = findViewById(R.id.songTitle);
        curTime = findViewById(R.id.curTime);
        totTime = findViewById(R.id.totalTime);

        playIcon = findViewById(R.id.playIcon);
        prevIcon = findViewById(R.id.prevIcon);
        nextIcon = findViewById(R.id.nextIcon);

        repeatIcon = findViewById(R.id.repeatIcon);
        suffleIcon = findViewById(R.id.suffleIcon);
        curListIcon = findViewById(R.id.curListIcon);

        /* Starts to play the songs from the beginning of the list */
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
        }

        playerData = getIntent();
        bundle = playerData.getExtras();

        allSongs = (ArrayList) bundle.getParcelableArrayList("songs");
        position = bundle.getInt("position", 0);
        initPlayer(position);

        /* On click listener for the current list button  */
        curListIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent list = new Intent(getApplicationContext(),CurrentList.class);
                list.putExtra("songsList",allSongs);
                startActivity(list);

            }
        });

        /* On click listener for the play/pause button  */
        playIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play();
            }
        });

        /* On click listener for the previous song button  */
        prevIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (position <= 0) {
                    position = allSongs.size() - 1;
                } else {
                    position--;
                }

                initPlayer(position);
            }
        });

        /* On click listener for the next song button  */
        nextIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position < allSongs.size() - 1) {
                    position++;
                } else {
                    position = 0;

                }
                initPlayer(position);
            }
        });
    }

    /* Grabs the songs that are in mp3, files that are on the device  */
    private void initPlayer(final int position) {

        if (mMediaPlayer != null &&
            mMediaPlayer.isPlaying()) {
            mMediaPlayer.reset();
        }

        String sname = allSongs.get(position).getName().replace
                (".mp3", "").
                replace(".m4a", "").
                replace(".wav", "").
                replace(".m4b", "");

        songTitle.setText(sname);
        Uri songResourceUri = Uri.parse(allSongs.get(position).toString());

        /* creates and loads the MP3 player with song resources */
        mMediaPlayer = MediaPlayer.create(getApplicationContext(), songResourceUri);
        mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override

        /* Creates the time duration that the song is currently on, and the pause and play button */
            public void onPrepared(MediaPlayer mp) {
                String totalTime = createTimeLabel(mMediaPlayer.getDuration());
                totTime.setText(totalTime);
                mSeekBar.setMax(mMediaPlayer.getDuration());
                mMediaPlayer.start();
                playIcon.setImageResource(R.drawable.ic_pause_black_24dp);
            }
        });

        /* What the song does based on what the user has clicked to either go through the list or shuffle */
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                int curSongPoition = position;

                /* code to repeat songs until the */
                if (curSongPoition < allSongs.size() - 1) {
                    curSongPoition++;
                    initPlayer(curSongPoition);
                } else {
                    curSongPoition = 0;
                    initPlayer(curSongPoition);
                }
            }
        });

        /* Seek bar that you can move and scroll through the song that is currently playing */
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                if (fromUser) {
                    mMediaPlayer.seekTo(progress);
                    mSeekBar.setProgress(progress);
                }
            }

            /* When the user interacts with it from where the song is currently to where they move the seek bar too */
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        /* This is where the current songs name that is playing will appear */
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mMediaPlayer != null) {
                    try {

                     /* creates the new message to send to handler */
                        if (mMediaPlayer.isPlaying()) {
                            Message msg = new Message();
                            msg.what = mMediaPlayer.getCurrentPosition();
                            handler.sendMessage(msg);
                            Thread.sleep(1000);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            /* Handler is called to view the song name  */
            int current_position = msg.what;
            mSeekBar.setProgress(current_position);
            String cTime = createTimeLabel(current_position);
            curTime.setText(cTime);
        }
    };

    /* Play the song that is currently on the screen  */
    private void play() {

        if (mMediaPlayer != null &&
           !mMediaPlayer.isPlaying()) {
            mMediaPlayer.start();
            playIcon.setImageResource(R.drawable.ic_pause_black_24dp);
        } else {
            pause();
        }
    }

    /* Pause the song that is currently on the screen  */
    private void pause() {
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
            playIcon.setImageResource(R.drawable.ic_play_arrow_black_24dp);
        }
    }


    /* Time duration for the song that is currently playing */
    public String createTimeLabel(int duration) {
        String timeLabel = "";
        int min = duration / 1000 / 60;
        int sec = duration / 1000 % 60;

        timeLabel += min + ":";
        if (sec < 10) timeLabel += "0";
        timeLabel += sec;

        return timeLabel;

    }
}
