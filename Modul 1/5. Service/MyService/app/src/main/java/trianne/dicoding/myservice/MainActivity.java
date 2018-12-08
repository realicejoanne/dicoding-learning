package trianne.dicoding.myservice;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnStartService;
    Button btnStartIntentService;
    Button btnStartBoundService;
    Button btnStopBoundService;
    boolean mServiceBound = false;
    BoundService mBoundService;

    //Merupakan listener untuk menerima callback dari ServiceConnetion.
    //Kalau dilihat ada dua callback, yakni ketika mulai terhubung dengan kelas service
    //dan juga ketika kelas service sudah terputus.
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            mServiceBound = false;
        }
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            BoundService.MyBinder myBinder = (BoundService.MyBinder) service;
            mBoundService = myBinder.getService();
            mServiceBound = true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStartService = findViewById(R.id.btn_start_service);
        btnStartService.setOnClickListener(this);
        btnStartIntentService = findViewById(R.id.btn_start_intent_service);
        btnStartIntentService.setOnClickListener(this);
        btnStartBoundService = findViewById(R.id.btn_start_bound_service);
        btnStartBoundService.setOnClickListener(this);
        btnStopBoundService = findViewById(R.id.btn_stop_bound_service);
        btnStopBoundService.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //startService() karena kita menginginkan sebuah service yang berjalan.
            //Setelah dijalankan, metode onStartCommand() pada OriginService akan dijalankan.
            case R.id.btn_start_service:
                Intent mStartServiceIntent = new Intent(MainActivity.this, OriginService.class);
                startService(mStartServiceIntent);
                break;
            //Kita menjalankan IntentService.
            //Kita dapat menentukan berapa lama service ini akan berjalan.
            case R.id.btn_start_intent_service:
                Intent mStartIntentService = new Intent(MainActivity.this, MyIntentService.class);
                mStartIntentService.putExtra(MyIntentService.EXTRA_DURATION, 5000);
                startService(mStartIntentService);
                break;
            //Kita menggunakan bindService untuk memulai mengikat kelas BoundService
            //ke kelas MainActivity. Sedangkan mBoundServiceIntent adalah sebuah intent eksplisit
            //untuk menjalankan komponen dari dalam sebuah aplikasi.
            //mServiceConnection adalah sebuah ServiceConnection berfungsi sebagai callback
            //dari kelas BoundService.
            //BIND_AUTO_CREATE yang membuat sebuah layanan jika layanan tersebut belum aktif.
            case R.id.btn_start_bound_service:
                Intent mBoundServiceIntent = new Intent(MainActivity.this, BoundService.class);
                bindService(mBoundServiceIntent, mServiceConnection, BIND_AUTO_CREATE);
                break;
            case R.id.btn_stop_bound_service:
                unbindService(mServiceConnection);
                break;
        }
    }

    //Kode onDestroy() akan memanggil unBindService atau melakukan pelepasan service dari Activity.
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mServiceBound) {
            unbindService(mServiceConnection);
        }
    }
}
