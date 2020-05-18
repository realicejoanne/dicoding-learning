package trianne.dicoding.moviecataloguev4.reminder;

import android.content.Context;
import android.content.SharedPreferences;

import trianne.dicoding.moviecataloguev4.db.DatabaseContract;

public class ReminderPreferences {
    //gunakan sharedpreferences
    public SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;

    public ReminderPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences(DatabaseContract.PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setReminderReleaseTime(String time){
        editor.putString(DatabaseContract.KEY_REMINDER_DAILY,time);
        editor.commit();
    }

    public void setReminderReleaseMessage (String message){
        editor.putString(DatabaseContract.KEY_REMINDER_MESSAGE_RELEASE,message);
    }

    public void setReminderDailyTime(String time){
        editor.putString(DatabaseContract.KEY_REMINDER_DAILY,time);
        editor.commit();
    }

    public void setReminderDailyMessage(String message){
        editor.putString(DatabaseContract.KEY_REMINDER_MESSAGE_DAILY,message);
    }
}
