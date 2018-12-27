package trianne.dicoding.mysound;

import android.app.Service;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.io.IOException;

public class MediaService extends Service implements MediaPlayer.OnPreparedListener,MediaPlayer.OnErrorListener {
    public static final String ACTION_PACKAGE = "com.example.action";
    public static final String ACTION_PLAY = "com.example.action.PLAY";
    public static final String ACTION_STOP = "com.example.action.STOP";
    public static final String ACTION_CREATE = "com.example.action.CREATE";
    MediaPlayer mMediaPlayer = null;

    int serviceId = 777;
    @Nullable

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void init(){
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        AssetFileDescriptor afd = getApplicationContext().getResources().openRawResourceFd(R.raw.lauv_enemies);

        try {
            mMediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        mMediaPlayer.setOnPreparedListener(this);
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getAction();
        switch(action) {
            case ACTION_CREATE:
                init();
                break;
            case ACTION_PLAY:
                if (!mMediaPlayer.isPlaying()) {
                    mMediaPlayer.prepareAsync(); //prosesnya asynchronous
                }
                break;
            case ACTION_STOP:
                mMediaPlayer.stop();
                break;
            default:
                break;
        }
        return flags;
    }

    public void onPrepared(MediaPlayer player) {
        mMediaPlayer.start();
    }

    @Override
    public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
        return false;
    }

    @Override
    public void onDestroy() {
        if (mMediaPlayer != null) mMediaPlayer.release();
    }
}
