package trianne.dicoding.stacknotif;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/*
Referensi:
https://material.io/guidelines/patterns/notifications.html
https://material.io/guidelines/patterns/notifications.html
 */

public class MainActivity extends AppCompatActivity {
    private static final CharSequence CHANNEL_NAME = "dicoding channel";
    private final static String GROUP_KEY_EMAILS = "group_key_emails";
    private final static int NOTIF_REQUEST_CODE = 200;

    private EditText edtSender;
    private EditText edtMessage;
    private Button btnKirim;

    //Variabel idNotif adalah indeks yang akan kita gunakan untuk mengakses list stackNotif.
    //Semua notifikasi yang kita kirimkan akan kita masukkan ke dalam variable stackNotif tersebut.
    private int idNotif = 0;
    private int maxNotif = 2;

    private List<NotificationItem> stackNotif = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtSender = findViewById(R.id.edtSender);
        edtMessage = findViewById(R.id.edtMessage);
        btnKirim = findViewById(R.id.btnSend);

        btnKirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sender = edtSender.getText().toString();
                String message = edtMessage.getText().toString();
                if (sender.isEmpty() || message.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Data harus diisi", Toast.LENGTH_SHORT).show();
                }
                else {
                    stackNotif.add(new NotificationItem(idNotif, sender, message));
                    sendNotif();
                    idNotif++; //Menaikkan nilai idNotif penting sebagai indeks untuk mengakses item dari stackNotif yang terakhir kali dimasukkan.
                    edtSender.setText("");
                    edtMessage.setText("");
                    InputMethodManager methodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    methodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                }
            }
        });
    }

    //Dengan menjalankan stackNotif.clear(); dan idNotif=0;
    //maka kita menghapus semua data pada list stackNotif dan mengembalikan indeks idNotif menjadi 0.
    //Alhasil, semuanya di-reset kembali ketika user menekan notifikasi yang ada.
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        stackNotif.clear();
        idNotif = 0;
    }

    private void sendNotif() {
        //Kita dapat langsung mendapatkan komponen manager dari notification
        //Dan ketika ingin melakukan update tinggal panggil mNotificationManager.notify()
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_notifications_white_48dp);

        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, NOTIF_REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder mBuilder;

        //Melakukan pengecekan jika idNotif lebih kecil dari Max Notif
        //apakah index idNotif sudah melewati nilai dari maxNotif
        String CHANNEL_ID = "channel_01";
        if (idNotif < maxNotif) {
            mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle("New Email from " + stackNotif.get(idNotif).getSender())
                    .setContentText(stackNotif.get(idNotif).getMessage())
                    .setSmallIcon(R.drawable.ic_mail_white_48dp)
                    .setLargeIcon(largeIcon)
                    .setGroup(GROUP_KEY_EMAILS)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);
        }
        else {
            NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle()
                    //addline mengindikasikan bahwa hanya 2 baris yang akan ditampilkan pada kelompok notifikasi
                    //Jika kita ingin menampilkan lebih dari 2 notifikasi, maka tinggal menambahkan metode addline lagi di bawahnya
                    .addLine("New Email from " + stackNotif.get(idNotif).getSender())
                    .addLine("New Email from " + stackNotif.get(idNotif - 1).getSender())
                    .setBigContentTitle(idNotif + " new emails")
                    .setSummaryText("mail@dicoding");
            mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle(idNotif + " new emails")
                    .setContentText("mail@dicoding.com")
                    .setSmallIcon(R.drawable.ic_mail_white_48dp)
                    .setGroup(GROUP_KEY_EMAILS)
                    .setGroupSummary(true)
                    .setContentIntent(pendingIntent)
                    .setStyle(inboxStyle)
                    .setAutoCancel(true);
        }

        //untuk Android Oreo ke atas
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT);

            mBuilder.setChannelId(CHANNEL_ID);

            if (mNotificationManager != null) {
                mNotificationManager.createNotificationChannel(channel);
            }
        }

        Notification notification = mBuilder.build();

        //untuk menampilkan notifikasi
        if (mNotificationManager != null) {
            mNotificationManager.notify(idNotif, notification);
        }
    }
}