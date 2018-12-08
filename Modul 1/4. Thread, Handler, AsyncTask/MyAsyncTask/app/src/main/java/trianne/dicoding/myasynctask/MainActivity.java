package trianne.dicoding.myasynctask;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.lang.ref.WeakReference;

/* Teori
Activity, fragment, service, ui toolkit, broadcast receiver dijalankan di main thread atau ui thread

Ada dua aturan yang harus kita perhatikan agar tercipta pengalaman pengguna yang baik ketika menerapkan
proses komputasi intensif yang memakan waktu.

    - Jangan memblok ui thread atau main thread. Ini berarti kita harus menciptakan worker threads atau async task.
        Ini akan menjaga aplikasi tetap responsif.
    - Jangan melakukan pemanggilan komponen ui widget (seperti textview, button, imageview dsb) didalam worker thread
    atau thread yang sedang berjalan secara asynchronous.
        Ini karena Android UI Toolkit merupakan komponen yang hanya berjalan pada ui thread.Thread adalah sekumpulan perintah (instruksi) yang dapat dilaksanakan (dieksekusi) secara beriringan dengan thread lainnya. Hal ini dicapai dengan menggunakan mekanisme time slice (ketika satu CPU melakukan perpindahan antara satu thread ke thread lainnya) atau multiprocess (ketika thread tersebut dijalankan oleh CPU yang berbeda dalam satu sistem).

Thread adalah sekumpulan perintah (instruksi) yang dapat dilaksanakan (dieksekusi) secara beriringan dengan thread
lainnya. Hal ini dicapai dengan menggunakan mekanisme time slice (ketika satu CPU melakukan perpindahan antara satu
thread ke thread lainnya) atau multiprocess (ketika thread tersebut dijalankan oleh CPU yang berbeda dalam satu sistem).
 */

//Sumpah ga ngerti2 bngt inimah cuk, baca lagi aja teorinya.

public class MainActivity extends AppCompatActivity implements MyAsyncCallback {

    TextView tvStatus;
    TextView tvDesc;

    final static String INPUT_STRING = "Ini Demo AsyncTask!";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvStatus = findViewById(R.id.tv_status);
        tvDesc = findViewById(R.id.tv_desc);

        DemoAsync demoAsync = new DemoAsync(this);
        demoAsync.execute(INPUT_STRING);
    }


    @Override
    public void onPreExecute() {
        tvStatus.setText(R.string.status_pre);
        tvDesc.setText(INPUT_STRING);
    }

    @Override
    public void onPostExecute(String result) {
        tvStatus.setText(R.string.status_post);
        if (result != null) {
            tvDesc.setText(result);
        }
    }

    /**
     * 3 parameter generic <String, Void, String>
     * 1. Params, parameter input yang bisa dikirimkan
     * 2. Progress, digunakan untuk publish informasi sudah sampai mana proses background berjalan
     * 3. Result, object yang dikirimkan ke onPostExecute / hasil dari proses doInBackground
     */
    private static class DemoAsync extends AsyncTask<String, Void, String> {

        static final String LOG_ASYNC = "DemoAsync";

        // Penggunaan weakreference disarankan untuk menghindari memory leaks
        WeakReference<MyAsyncCallback> myListener;

        DemoAsync(MyAsyncCallback myListener) {
            this.myListener = new WeakReference<>(myListener);
        }

        //onPreExecute digunakan untuk persiapan asynctask
        //berjalan di Main Thread, bisa akses ke view karena masih di dalam Main Thread
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d(LOG_ASYNC, "Status: onPreExecute");

            MyAsyncCallback myListener = this.myListener.get();
            if (myListener != null) {
                myListener.onPreExecute();
            }
        }

        //doInBackground digunakan untuk menjalankan proses secara async
        //berjalan di background thread, tidak bisa akses ke view karena sudah beda thread
        //proses Asynchronus dijalankan pada method ini
        @Override
        protected String doInBackground(String... params) {
            Log.d(LOG_ASYNC, "Status: doInBackground");

            String output = null;
            try {
                //params[0] adalah 'Halo Ini Demo AsyncTask'
                String input = params[0];

                // Input stringnya ditambahkan dengan string ' Selamat Belajar!!"
                output = input + " Selamat Belajar!";

                //Sleep thread digunakan untuk simulasi bahwa ada proses yang sedang berjalan selama 5 detik
                //5000 miliseconds = 5 detik
                Thread.sleep(2000);

            }
            catch (Exception e) {
                Log.d(LOG_ASYNC, e.getMessage());
            }
            return output;
        }

        //onPostExecute dijalankan ketika proses doInBackground telah selesai
        //berjalan di Main Thread
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.d(LOG_ASYNC, "Status: onPostExecute");

            MyAsyncCallback myListener = this.myListener.get();
            if (myListener != null) {
                myListener.onPostExecute(result);
            }
        }
    }
}
