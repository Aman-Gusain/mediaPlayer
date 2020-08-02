package com.example.aman_gusain.mediaplayer;

import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    Button play, pause, next, stop, previous;
    SeekBar sbar1, sbar2;
    ListView lview;
    MediaPlayer mp;
    String[] songs = {"s1", "s2", "s3"};
    int[] songid = {R.raw.s1, R.raw.s2, R.raw.s3};
    int c_song = 0;
    Handler h = new Handler();
    Runnable r = new Runnable() {
        @Override
        public void run() {
            sbar1.setProgress(mp.getCurrentPosition());
            h.postDelayed(r, 1000);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lview = (ListView) findViewById(R.id.lview);
        sbar1 = (SeekBar) findViewById((R.id.sbar1));
        sbar2 = (SeekBar) findViewById(R.id.sbar2);
        play = (Button) findViewById(R.id.play);
        pause = (Button) findViewById(R.id.pause);
        next = (Button) findViewById(R.id.next);
        stop = (Button) findViewById((R.id.stop));
        previous = (Button) findViewById(R.id.previous);

        getSupportActionBar().hide();

        play.setOnClickListener(this);
        pause.setOnClickListener(this);
        next.setOnClickListener(this);
        previous.setOnClickListener(this);
        stop.setOnClickListener(this);
        sbar1.setOnSeekBarChangeListener(this);
        sbar2.setOnSeekBarChangeListener(this);
        ArrayAdapter a = new ArrayAdapter(this, android.R.layout.simple_list_item_1, songs);
        lview.setAdapter(a);
        lview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                setSong(i);
            }
        });
    }

    public void setSong(int index) {
        if (mp != null) {
            mp.reset();
        }
        mp = MediaPlayer.create(this, songid[index]);
        sbar1.setMax(mp.getDuration());
        mp.start();
        h.post(r);
        c_song = index;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.play:
                if (mp != null)
                    mp.start();
                break;
            case R.id.pause:
                if (mp != null)
                    mp.pause();
                break;
            case R.id.stop:
                if (mp != null) {
                    mp.stop();
                    mp.reset();
                    mp = MediaPlayer.create(this, songid[c_song]);
                }
                break;
            case R.id.next:
                if (mp != null && c_song < songs.length - 1) {
                    setSong(++c_song);
                }
                break;
            case R.id.previous:
                if (mp != null && c_song > 0) {
                    setSong(--c_song);
                }
                break;

        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        switch (seekBar.getId()) {
            case R.id.sbar1:
                if (b)
                    mp.seekTo(i);
                break;
            case R.id.sbar2:
                mp.setVolume(i * 0.01f, i * 0.01f);
                break;

        }



    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}

