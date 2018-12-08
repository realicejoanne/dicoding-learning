package trianne.dicoding.smslistenerapp;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

public class DownloadService extends IntentService {
    public static final String TAG = "DownloadService";

    public DownloadService() {
        super("DownloadService");
    }

    //Di sini kita akan menjalankan Intent Service yang akan melakukan proses mengunduh file secara Asynchronous
    //di background. Kelas DownloadService mengambil peran disini.
    //Pada kenyataannya, DownloadService ini hanya melakukan proses sleep() selama 5 detik dan
    //kemudian membroadcast sebuah IntentFilter dengan Action yang telah ditentukan, ACTION_DOWNLOAD_STATUS.
    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "Download Service dijalankan");
        if (intent != null) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Intent notifyFinishIntent = new Intent(MainActivity.ACTION_DOWNLOAD_STATUS);
            sendBroadcast(notifyFinishIntent);
        }
    }
}