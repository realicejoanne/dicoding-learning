package trianne.dicoding.smslistenerapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

public class SmsReceiver extends BroadcastReceiver {
    final String TAG = SmsReceiver.class.getSimpleName();
    final SmsManager sms = SmsManager.getDefault();

    public SmsReceiver() {
    }

    //Di metode onReceive(), receiver akan memproses metadata dari SMS yang masuk.
    //Lalu ReceiverActivity akan dijalankan dengan membawa data melalui sebuah intent showSmsIntent.
    @Override
    public void onReceive(Context context, Intent intent) {
        final Bundle bundle = intent.getExtras();
        try {
            if (bundle != null) {
                final Object[] pdusObj = (Object[]) bundle.get("pdus");
                if (pdusObj != null) {
                    for (Object aPdusObj : pdusObj) {
                        SmsMessage currentMessage = getIncomingMessage(aPdusObj, bundle);
                        String senderNum = currentMessage.getDisplayOriginatingAddress();
                        String message = currentMessage.getDisplayMessageBody();
                        Log.d(TAG, "senderNum: " + senderNum + "; message: " + message);
                        Intent showSmsIntent = new Intent(context, SmsReceiverActivity.class);
                        showSmsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        showSmsIntent.putExtra(SmsReceiverActivity.EXTRA_SMS_NO, senderNum);
                        showSmsIntent.putExtra(SmsReceiverActivity.EXTRA_SMS_MESSAGE, message);
                        context.startActivity(showSmsIntent);
                    }
                }
                else {
                    Log.d(TAG, "onReceive: SMS is null");
                }
            }
        }
        catch (Exception e) {
            Log.d(TAG, "Exception smsReceiver" + e);
        }
    }

    /*
    Kita memanfaatkan fSmsManager dan SmsMesssage untuk melakukan pemrosesan SMS.
    Untuk memperoleh obyek dari kelas SmsMessage, yaitu obyek currentMessage,
    kita menggunakan metode getIncomingMessage().
    Metode ini akan mengembalikan currentMessage berdasarkan OS yang dijalankan.
    Hal ini perlu dilakukan karena metode SmsMessage.createFromPdu((object);
    sudah deprecated di OS Marshmallow ke atas.
     */
    private SmsMessage getIncomingMessage(Object aObject, Bundle bundle) {
        SmsMessage currentSMS;
        if (Build.VERSION.SDK_INT >= 23) {
            String format = bundle.getString("format");
            currentSMS = SmsMessage.createFromPdu((byte[]) aObject, format);
        } else {
            currentSMS = SmsMessage.createFromPdu((byte[]) aObject);
        }
        return currentSMS;
    }
}
