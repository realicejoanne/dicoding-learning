package trianne.dicoding.myservice;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;

import java.lang.ref.WeakReference;

//Belajar Service sederhana
public class OriginService extends Service implements DummyAsyncCallback {
    public static final String ORIGIN_SERVICE = "OriginService";
    static final String TAG = OriginService.class.getSimpleName();

    public OriginService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        /* Log.d(ORIGIN_SERVICE, "OriginService dijalankan");
        return START_STICKY; */

        Log.d(TAG, "onStartCommand: ");
        DummyAsync dummyAsync = new DummyAsync(this);
        dummyAsync.execute();

        return START_STICKY;
        //START_STICKY menandakan bahwa bila service tersebut dimatikan oleh sistem Android
        //karena kekurangan memori, maka ia akan diciptakan kembali jika sudah ada memori yang
        //bisa digunakan. Metode onStartCommand() juga akan dijalankan kembali.
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    @Override
    public void preAsync() {
        Log.d(TAG, "preAsync: Mulai...");
    }

    @Override
    public void postAsync() {
        //Pada method onPostAsync() akan memanggil stopSelf() yang berarti akan memberhentikan
        //atau mematikan OriginService dari sistem Android.
        Log.d(TAG, "postAsync: Selesai...");
        stopSelf();
    }

    private static class DummyAsync extends AsyncTask<Void, Void, Void> {
        WeakReference<DummyAsyncCallback> callback;
        DummyAsync(DummyAsyncCallback callback) {
            this.callback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d(TAG, "onPreExecute: ");
            callback.get().preAsync();
        }

        @Override
        protected Void doInBackground(Void... params) {
            Log.d(TAG, "doInBackground: ");
            try {
                Thread.sleep(3000);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.d(TAG, "onPostExecute: ");
            callback.get().postAsync();
        }
    }
}

interface DummyAsyncCallback {
    void preAsync();
    void postAsync();
}