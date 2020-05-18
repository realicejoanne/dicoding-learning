package trianne.dicoding.kamus;

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
import java.util.Timer;
import java.util.TimerTask;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.daimajia.numberprogressbar.OnProgressBarListener;

public class LoadingActivity extends AppCompatActivity implements OnProgressBarListener {
    private Timer timer;

    private NumberProgressBar bnp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        new LoadData().execute();

        bnp = (NumberProgressBar)findViewById(R.id.number_progress_bar);
        bnp.setOnProgressBarListener(this);
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        bnp.incrementProgressBy(1);
                    }
                });
            }
        }, 500, 100);
    }

    @Override
    public void onProgressChange(int current, int max) {
        if(current == max) {
            bnp.setProgress(0);
        }
    }

    private class LoadData extends AsyncTask<Void, Integer, Void> {
        final String TAG = LoadData.class.getSimpleName();
        DictionaryHelper dictionaryHelper;
        DictionaryPreferences appPreference;

        double progress;
        double maxprogress = 100;

        @Override
        protected void onPreExecute() {
            dictionaryHelper = new DictionaryHelper(LoadingActivity.this);
            appPreference   = new DictionaryPreferences(LoadingActivity.this);
        }

        @Override
        protected Void doInBackground(Void... params) {
            Boolean firstRun = appPreference.getFirstRun();
            if (firstRun) {
                ArrayList<DictionaryModel> dictionaryModelsEngToInd = preLoadRaw("Eng");
                ArrayList<DictionaryModel> dictionaryModelsIndToEng = preLoadRaw("Ind");
                dictionaryHelper.open();
                Double progressMaxInsert = 100.0;
                int total_size = dictionaryModelsEngToInd.size() + dictionaryModelsIndToEng.size();
                Double progressDiff = (progressMaxInsert - progress) / total_size;

                //Eng
                dictionaryHelper.beginTransaction();
                try {
                    for (DictionaryModel model : dictionaryModelsEngToInd) {
                        dictionaryHelper.insertTransaction(model, "Eng");
                    }
                    dictionaryHelper.setTransactionSuccess();
                }
                catch (Exception e) {
                    Log.e(TAG, "doInBackground: Exception");
                }
                dictionaryHelper.endTransaction();
                progress += progressDiff;
                publishProgress((int) progress);

                //Ind
                dictionaryHelper.beginTransaction();
                try {
                    for (DictionaryModel model : dictionaryModelsIndToEng) {
                        dictionaryHelper.insertTransaction(model, "Ind");
                    }
                    dictionaryHelper.setTransactionSuccess();
                }
                catch (Exception e) {
                    Log.e(TAG, "doInBackground: Exception");
                }
                dictionaryHelper.endTransaction();
                progress += progressDiff;
                publishProgress((int) progress);

                dictionaryHelper.close();
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
        protected void onPostExecute(Void result) {
            Intent i = new Intent(LoadingActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        }
    }

    public ArrayList<DictionaryModel> preLoadRaw(String selection) {
        int raw_data;
        if(selection == "Eng"){
            raw_data = R.raw.english_indonesia;
        }
        else {
            raw_data = R.raw.indonesia_english;
        }
        ArrayList<DictionaryModel> dictionaryModels = new ArrayList<>();
        String line = null;
        BufferedReader reader;
        try {
            Resources res = getResources();
            InputStream raw_dict = res.openRawResource(raw_data);

            reader = new BufferedReader(new InputStreamReader(raw_dict));
            int count = 0;
            do {
                line = reader.readLine();
                String[] splitstr = line.split("\t");

                DictionaryModel dictionaryModel;

                dictionaryModel = new DictionaryModel(splitstr[0], splitstr[1]);
                dictionaryModels.add(dictionaryModel);
                count++;
            }
            while (line != null);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return dictionaryModels;
    }
}
