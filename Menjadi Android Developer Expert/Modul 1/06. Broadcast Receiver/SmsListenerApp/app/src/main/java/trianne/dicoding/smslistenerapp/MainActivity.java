package trianne.dicoding.smslistenerapp;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/* Logika
    Melakukan klik ke button permission → untuk check permissionSMS → ada sms masuk →
    menerima dengan broadcast receiver → menampilkan kelayar android

    Melakukan klik ke button download → menjalankan background service → proses selesai →
    menampilakn dalam bentuk toast atau pesan singkat

    Poin penting:
    Registrasikan sebuah obyek BroadcastReceiver pada komponen aplikasi seperti Activity dan
        Fragment dan tentukan Action/Event apa yang ingin didengar/direspon.
    Lakukan proses terkait pada metode onReceiver() ketika event atau action yang dipantau
        dibroadcast oleh komponen lain.
    Jangan lupa untuk mencopot pemasangan obyek receiver dari komponen sebelum komponen tersebut
        dihancurkan atau dimatikan.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnCheckPermission;
    Button btnDownload;
    public static final String ACTION_DOWNLOAD_STATUS = "download_status";
    private BroadcastReceiver downloadReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCheckPermission = findViewById(R.id.btn_permission);
        btnCheckPermission.setOnClickListener(this);

        btnDownload = findViewById(R.id.btn_download);
        btnDownload.setOnClickListener(this);

        downloadReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d(DownloadService.TAG, "Download Selesai");
                Toast.makeText(context, "Download Selesai", Toast.LENGTH_SHORT).show();
            }
        };
        IntentFilter downloadIntentFilter = new IntentFilter(ACTION_DOWNLOAD_STATUS);
        registerReceiver(downloadReceiver, downloadIntentFilter);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_permission) {
            PermissionManager.check(this, Manifest.permission.RECEIVE_SMS, SMS_REQUEST_CODE);
        }
        else if (v.getId() == R.id.btn_download) {

        }
    }

    final int SMS_REQUEST_CODE = 101;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == SMS_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Sms receiver permission diterima", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Sms receiver permission ditolak", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
