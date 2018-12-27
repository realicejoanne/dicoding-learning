package trianne.dicoding.mysound;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnSound;
    Button btnMedia;
    Button btnMediaStop;

    SoundPool sp;
    MediaPlayer mPlayer;

    int wav;
    boolean spLoaded = false;

    Intent it;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSound = (Button)findViewById(R.id.btn_soundpool);
        btnSound.setOnClickListener(this);

        btnMedia = (Button)findViewById(R.id.btn_mediaplayer);
        btnMediaStop = (Button)findViewById(R.id.btn_mediaplayer_stop);
        btnMedia.setOnClickListener(this);
        btnMediaStop.setOnClickListener(this);
        //mPlayer = MediaPlayer.create(this, R.raw.lauv_enemies);

        //Build.Version.SDK_INT > Lollipop, karena constructor versi lama telah deprecated.
        //Angka 10 menunjukkan jumlah maksimal streams yang bisa didukung oleh soundpool.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            sp = new SoundPool.Builder()
                    .setMaxStreams(10)
                    .build();
        }
        else {
            sp = new SoundPool(10, AudioManager.STREAM_MUSIC, 1);
        }

        //Soundpool hanya bisa memainkan berkas yang telah dimuat secara sempurna.
        //Maka untuk memastikan bahwa proses pemuatan telah selesai, Anda dapat menggunakan listener.
        sp.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            public void onLoadComplete(SoundPool soundPool, int sampleId,int status) {
                spLoaded = true;
            }
        });
        wav = sp.load(this, R.raw.clinking_glasses, 1);

        //Media player dengan service
        it = new Intent(this, MediaService.class);
        it.setAction(MediaService.ACTION_CREATE);
        it.setPackage(MediaService.ACTION_PACKAGE);
        startService(it);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_soundpool:
                //jika SoundPool sudah selesai diload (true) maka akan dimainkan
                //LeftVolume dan RightVolume merupakan parameter float dari besar kecilnya volume yang range-nya dimulai dari 0.0 - 1.0.
                //Priority merupakan urutan prioritas. Semakin besar nilai priority, semakin tinggi prioritas audio itu dijalankan.
                //Loop digunakan untuk mengulang audio ketika telah selesai dimainkan. Nilai -1 menunjukkan bahwa audio akan di ulang-ulang tanpa berhenti. Nilai 0 menunjukkan audio akan dimainkan sekali. Nilai 1 menunjukkan audio akan dimainkan sebanyak 2 kali.
                //Rate mempunyai range dari 0.5 - 2.0. Rate 1.0 akan memainkan audio secara normal, 0.5 akan memainkan audio dengan kecepatan setengah, dan 2.0 akan memainkan audio 2 kali lipat lebih cepat.
                if (spLoaded == true ){
                    sp.play(wav, 1, 1, 0, 0, 1);
                }
                break;
            case R.id.btn_mediaplayer:
                it.setAction(MediaService.ACTION_PLAY);
                it.setPackage(MediaService.ACTION_PACKAGE);
                startService(it);
                break;

            case R.id.btn_mediaplayer_stop:
                it.setAction(MediaService.ACTION_STOP);
                it.setPackage(MediaService.ACTION_PACKAGE);
                startService(it);
            /*case R.id.btn_mediaplayer:
                mPlayer.start();
                break;
            case R.id.btn_mediaplayer_stop:
                mPlayer.stop();
                break;*/
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(it);
    }
}