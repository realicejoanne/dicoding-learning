package trianne.dicoding.myjobscheduler;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnStart, btnCancel;
    private int jobId = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStart = (Button)findViewById(R.id.btn_start);
        btnCancel = (Button)findViewById(R.id.btn_cancel);
        btnStart.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_start:
                startJob();
                break;
            case R.id.btn_cancel:
                cancelJob();
                break;
        }
    }

    private void startJob(){
        ComponentName mServiceComponent = new ComponentName(this, GetCurrentWeatherJobService.class);
        JobInfo.Builder builder = new JobInfo.Builder(jobId, mServiceComponent);

        //Ketika bernilai NETWORK_TYPE_ANY berarti tidak ada ketentuan dia harus terhubung ke network jenis tertentu.
        //Bila kita ingin memasang ketentuan bahwa job hanya akan berjalan ketika perangkat terhubung ke network Wi-fi,
        //maka kita perlu memberikan nilai NETWORK_TYPE_UNMETERED.
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);

        //Apakah job akan dijalankan ketika perangkat dalam keadaan sedang digunakan atau tidak.
        //Secara default, parameter ini bernilai false. Bila kita ingin job dijalankan ketika
        //perangkat dalam kondisi tidak digunakan, maka kita beri nilai true.
        builder.setRequiresDeviceIdle(false);

        //Apakah job akan dijalankan ketika batere sedang diisi atau tidak.
        //Nilai true akan mengindikasikan bawah job hanya berjalan ketika batere sedang diisi.
        //Kondisi ini dapat digunakan bila job yang dijalankan akan memakan waktu yang lama,
        //sehingga membutuhkan batere yang tidak kosong.
        builder.setRequiresCharging(false);

        //Set berapa interval waktu kapan job akan dijalankan.
        //Ini bisa kita gunakan untuk menjalankan job yang sifatnya repeat atau berulang.
        //Nilai parameter yang kita masukkan adalah dalam milisecond, dan 1000 ms adalah 1 detik.
        builder.setPeriodic(18000);

        JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(builder.build());
        Toast.makeText(this, "Job Service started", Toast.LENGTH_SHORT).show();
    }
    private void cancelJob(){
        JobScheduler tm = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        tm.cancel(jobId);
        Toast.makeText(this, "Job Service canceled", Toast.LENGTH_SHORT).show();
        finish();
    }
}


//Detail cek https://developer.android.com/reference/android/app/job/JobInfo.Builder.html