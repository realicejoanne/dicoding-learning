package trianne.dicoding.myservice;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

//Belajar Intent Service

/*
Kita menjalankan IntentService dengan sebuah obyek Intent dari MainActivity dengan membawa data,
dalam konteks ini adalah nilai integer yang menentukan berapa lama background proses dijalankan.
MyIntentService dijalankan kemudian melakukan pemrosesan obyek Intent yang dikirimkan untuk
menjalankan background.
Seperti sifatnya, IntentService tidak perlu mematikan dirinya sendiri, secara otomatis ketika
proses yang dilakukan selesai, maka IntentService berhenti dengan sendirinya.
 */
public class MyIntentService extends IntentService {

    public static String EXTRA_DURATION = "extra_duration";
    public static final String TAG = MyIntentService.class.getSimpleName();

    public MyIntentService() {
        super("MyIntentService");
    }

    //Kode diatas akan dijalankan pada thread terpisah secara asynchronous.
    // Jadi kita tidak membutuhkan lagi obyek AsyncTask seperti pada service sebelumnya.
    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            int duration = intent.getIntExtra(EXTRA_DURATION, 0);
            try {
                Thread.sleep(duration);
                Log.d(TAG, "onHandleIntent: Selesai...");
            }
            catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy()");
    }
}