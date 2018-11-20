package com.hanium.chj.remotepj;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.widget.Toast;

public class AlarmSoundService extends Service {
    public AlarmSoundService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.alarmsound);
        Toast.makeText(AlarmSoundService.this,"알람이 울립니다.",Toast.LENGTH_SHORT).show();
        mediaPlayer.start();

        return START_NOT_STICKY;
    }

}
