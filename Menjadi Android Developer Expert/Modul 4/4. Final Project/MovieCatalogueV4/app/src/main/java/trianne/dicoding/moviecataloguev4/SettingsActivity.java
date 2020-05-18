package trianne.dicoding.moviecataloguev4;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import trianne.dicoding.moviecataloguev4.db.DatabaseContract;
import trianne.dicoding.moviecataloguev4.reminder.ReminderPreferences;
import trianne.dicoding.moviecataloguev4.reminder.ReminderDailyReceiver;
import trianne.dicoding.moviecataloguev4.reminder.ReminderReleaseReceiver;

public class SettingsActivity extends AppCompatActivity {
    //Butterknife
    @BindView(R.id.dailyReminder)
    Switch dailyReminder;
    @BindView(R.id.releaseReminder)
    Switch releaseReminder;

    //inisiasi, final stringnya di databasecontract
    public ReminderDailyReceiver reminderDailyReceiver;
    public ReminderReleaseReceiver reminderReleaseReceiver;
    public ReminderPreferences reminderPreference;
    public SharedPreferences spReleaseReminder, spDailyReminder;
    public SharedPreferences.Editor eReleaseReminder, eDailyReminder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //atur butterknifenya
        ButterKnife.bind(this);
        reminderDailyReceiver = new ReminderDailyReceiver();
        reminderReleaseReceiver = new ReminderReleaseReceiver();
        reminderPreference = new ReminderPreferences(this);
        setPreference();

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true); //???
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Settings");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    private void  setPreference(){
        spReleaseReminder = getSharedPreferences(DatabaseContract.KEY_UPCOMING_REMINDER, MODE_PRIVATE);
        spDailyReminder = getSharedPreferences(DatabaseContract.KEY_DAILY_REMINDER, MODE_PRIVATE);
        boolean checkSwUpcomingReminder = spReleaseReminder.getBoolean(DatabaseContract.KEY_FIELD_UPCOMING_REMINDER, false);
        releaseReminder.setChecked(checkSwUpcomingReminder);
        boolean checkSwDailyReminder = spDailyReminder.getBoolean(DatabaseContract.KEY_FIELD_DAILY_REMINDER, false);
        dailyReminder.setChecked(checkSwDailyReminder);
    }

    //Butterknife untuk check uncheck daily reminder
    @OnCheckedChanged(R.id.dailyReminder)
    public  void  setDailyRemind(boolean isChecked){
        eDailyReminder = spDailyReminder.edit();
        if (isChecked) {
            eDailyReminder.putBoolean(DatabaseContract.KEY_FIELD_DAILY_REMINDER, true);
            eDailyReminder.apply();
            dailyReminderOn();
        }
        else {
            eDailyReminder.putBoolean(DatabaseContract.KEY_FIELD_DAILY_REMINDER, false);
            eDailyReminder.apply();
            dailyReminderOff();
        }
    }

    private void dailyReminderOn() {
        String time = "07:00"; //waktu jam 7 pagi
        String message = "Daily Movies Today";
        reminderPreference.setReminderDailyTime(time);
        reminderPreference.setReminderDailyMessage(message);
        reminderDailyReceiver.setReminder(SettingsActivity.this, DatabaseContract.TYPE_REMINDER_RECEIVE, time, message);
    }

    private void dailyReminderOff() {
        reminderDailyReceiver.cancelReminder(SettingsActivity.this);
    }

    //Butterknife untuk check uncheck release reminder
    @OnCheckedChanged(R.id.releaseReminder)
    public  void setReleaseRemind(boolean isChecked){
        eReleaseReminder = spReleaseReminder.edit();
        if (isChecked) {
            eReleaseReminder.putBoolean(DatabaseContract.KEY_FIELD_UPCOMING_REMINDER, true);
            eReleaseReminder.apply();
            releaseReminderOn();
        }
        else {
            eReleaseReminder.putBoolean(DatabaseContract.KEY_FIELD_UPCOMING_REMINDER, false);
            eReleaseReminder.apply();
            releaseReminderOff();
        }
    }

    private void releaseReminderOn() {
        String time = "08:00"; //waktu jam 8 pagi
        String message = "Release Movies Today";
        reminderPreference.setReminderReleaseTime(time);
        reminderPreference.setReminderReleaseMessage(message);
        reminderReleaseReceiver.setReminder(SettingsActivity.this, DatabaseContract.TYPE_REMINDER_PREF, time, message);
    }

    private void releaseReminderOff() {
        reminderReleaseReceiver.cancelReminder(SettingsActivity.this);
    }

    //settings utk localization
    @OnClick({R.id.tvLang, R.id.imgLang})
    public void onViewClicked(View view) {
        Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
        startActivity(mIntent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home : {
                finish();
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }
}

