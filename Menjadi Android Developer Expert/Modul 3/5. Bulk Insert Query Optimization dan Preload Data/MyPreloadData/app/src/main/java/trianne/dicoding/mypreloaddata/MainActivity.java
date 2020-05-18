package trianne.dicoding.mypreloaddata;

import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        new LoadData().execute();
    }

    private class LoadData extends AsyncTask<Void, Integer, Void> {
        final String TAG = LoadData.class.getSimpleName();
        MahasiswaHelper mahasiswaHelper;
        AppPreference appPreference;
        double progress;
        double maxprogress = 100;

        @Override
        protected void onPreExecute() {
            mahasiswaHelper = new MahasiswaHelper(MainActivity.this);
            appPreference = new AppPreference(MainActivity.this);
        }

        @Override
        protected Void doInBackground(Void... params) {
            //memeriksa nilai pada appPreference apakah data awal sudah tersimpan apa belum.
            Boolean firstRun = appPreference.getFirstRun();

            if (firstRun) {
                ArrayList<MahasiswaModel> mahasiswaModels = preLoadRaw();

                mahasiswaHelper.open();

                progress = 30;
                publishProgress((int) progress);
                Double progressMaxInsert = 80.0;
                Double progressDiff = (progressMaxInsert - progress) / mahasiswaModels.size();

                //Memanggil beginTransaction dimana kode ini akan memanggil kode di dalam helper
                mahasiswaHelper.beginTransaction();

                //Menggunakan looping, satu per satu data dimasukkan ke dalam database
                try {
                    for (MahasiswaModel model : mahasiswaModels) {
                        mahasiswaHelper.insertTransaction(model);
                        progress += progressDiff;
                        publishProgress((int) progress);
                    }
                    //Jika semua proses telah diset success maka akan dicommit ke database
                    mahasiswaHelper.setTransactionSuccess();
                }
                catch (Exception e) {
                    //Jika gagal maka do nothing
                    Log.e(TAG, "doInBackground: Exception");
                }

                //transaction selesai
                mahasiswaHelper.endTransaction();
                mahasiswaHelper.close();

                //Dengan memasang nilai false ketika aplikasi dibuka kembali,
                //proses transactional yang telah kita bahas tadi tidak akan dijalankan lagi
                appPreference.setFirstRun(false);
                publishProgress((int) maxprogress);
            }
            else {
                try {
                    synchronized (this) {
                        this.wait(2000);

                        publishProgress(50);

                        this.wait(2000);
                        publishProgress((int) maxprogress);
                    }
                }
                catch (Exception e) {
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Void result) {
            Intent i = new Intent(MainActivity.this, MahasiswaActivity.class);
            startActivity(i);
            finish();
        }
    }

    //mengambil data mentah dari data_mahasiswa
    public ArrayList<MahasiswaModel> preLoadRaw() {
        ArrayList<MahasiswaModel> mahasiswaModels = new ArrayList<>();
        String line = null;
        BufferedReader reader;
        try {
            Resources res = getResources();
            InputStream raw_dict = res.openRawResource(R.raw.data_mahasiswa);

            reader = new BufferedReader(new InputStreamReader(raw_dict));
            int count = 0;
            do {
                //membaca text tiap baris dan kemudian membaginya berdasarkan "\t" atau tab.
                //Bila kita melihat data mentahnya, maka nama dan nilai id dipisah menggunakan “\t” atau tab
                line = reader.readLine();
                String[] splitstr = line.split("\t");

                MahasiswaModel mahasiswaModel;

                mahasiswaModel = new MahasiswaModel(splitstr[0], splitstr[1]);
                mahasiswaModels.add(mahasiswaModel);
                count++;
            }
            while (line != null);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return mahasiswaModels;
    }
}
