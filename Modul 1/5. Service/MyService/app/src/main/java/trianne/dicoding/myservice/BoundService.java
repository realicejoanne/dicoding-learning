package trianne.dicoding.myservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;

//Belajar BoundService
public class BoundService extends Service {

    final String TAG = BoundService.class.getSimpleName();
    MyBinder mBinder = new MyBinder();
    final long startTime = System.currentTimeMillis();

    public BoundService() {
    }

    //Kelas yang dipanggil di metode onServiceConnected untuk memanggil kelas service untuk mengikat kelas service.
    //Pada kelas MyBinder yang diberi turunan kelas Binder ini mempunyai fungsi untuk melakukan
    //mekanisme pemanggilan prosedur jarak jauh.
    class MyBinder extends Binder {
        BoundService getService() {
            return BoundService.this;
        }
    }

    CountDownTimer mTimer = new CountDownTimer(100000, 1000) {
        @Override
        public void onTick(long l) {
            //berfungsi untuk melakukan hitungan mundur selama 100000 mili detik atau 100 detik.
            //Waktu tunggu untuk melihat proses terikatnya kelas BoundService ke MainActivity.
            long elapsedTime = System.currentTimeMillis() - startTime;
            Log.d(TAG, "onTick: " + elapsedTime);
        }
        @Override
        public void onFinish() {
        }
    };

    //Metode onCreate() berfungsi untuk memulai pembentukan kelas BoundService.
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");
    }

    //Service akan berjalan dan diikatkan atau ditempelkan dengan activity pemanggil.
    //Pada metode ini juga, mTimer akan mulai berjalan.
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: ");
        mTimer.start();
        return mBinder;
    }

    //Berfungsi untuk melepaskan service dari activity pemanggil.
    //Secara tidak langsung maka akan memanggil metode unBind yang ada di kelas BoundService.
    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind: ");
        mTimer.cancel();
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        Log.d(TAG, "onRebind: ");
    }

    //Berfungsi untuk melepaskan service dari aktivity pemanggil.
    //Kemudian setelah metode onUnBind dipanggil maka akan memanggil metode onDestroy.
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }
}
