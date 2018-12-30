package trianne.dicoding.moviecataloguev4;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.provider.Settings;
import android.provider.SettingsSlicesContract;
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
import trianne.dicoding.moviecataloguev4.reminder.ReminderReceiver;
import trianne.dicoding.moviecataloguev4.reminder.ReminderReleaseReceiver;

public class SettingsActivity extends AppCompatActivity {

    @BindView(R.id.dailyReminder)
    Switch dailyReminder;
    @BindView(R.id.releaseReminder)
    Switch releaseReminder;

    public ReminderReceiver reminderReceiverDaily;
    public ReminderReleaseReceiver reminderReceiverRelease;
    public ReminderPreferences reminderPreference;
    public SharedPreferences sReleaseReminder, sDailyReminder;
    public SharedPreferences.Editor editorReleaseReminder, editorDailyReminder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ButterKnife.bind(this);
        reminderReceiverDaily = new ReminderReceiver();
        reminderReceiverRelease = new ReminderReleaseReceiver();
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

    @OnCheckedChanged(R.id.dailyReminder)
    public  void  setDailyRemind(boolean isChecked){
        editorDailyReminder = sDailyReminder.edit();
        if (isChecked) {
            editorDailyReminder.putBoolean(DatabaseContract.KEY_FIELD_DAILY_REMINDER, true);
            editorDailyReminder.apply();
            dailyReminderOn();
        }
        else {
            editorDailyReminder.putBoolean(DatabaseContract.KEY_FIELD_DAILY_REMINDER, false);
            editorDailyReminder.apply();
            dailyReminderOff();
        }
    }

    @OnCheckedChanged(R.id.releaseReminder)
    public  void setReleaseRemind(boolean isChecked){
        editorReleaseReminder = sReleaseReminder.edit();
        if (isChecked) {
            editorReleaseReminder.putBoolean(DatabaseContract.KEY_FIELD_UPCOMING_REMINDER, true);
            editorReleaseReminder.apply();
            releaseReminderOn();
        }
        else {
            editorReleaseReminder.putBoolean(DatabaseContract.KEY_FIELD_UPCOMING_REMINDER, false);
            editorReleaseReminder.apply();
            releaseReminderOff();
        }
    }

    @OnClick({R.id.tvLang, R.id.imgLang})
    public void onViewClicked(View view) {
        Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
        startActivity(mIntent);
    }

    private void  setPreference(){
        sReleaseReminder = getSharedPreferences(DatabaseContract.KEY_HEADER_UPCOMING_REMINDER, MODE_PRIVATE);
        sDailyReminder = getSharedPreferences(DatabaseContract.KEY_HEADER_DAILY_REMINDER, MODE_PRIVATE);
        boolean checkSwUpcomingReminder = sReleaseReminder.getBoolean(DatabaseContract.KEY_FIELD_UPCOMING_REMINDER, false);
        releaseReminder.setChecked(checkSwUpcomingReminder);
        boolean checkSwDailyReminder = sDailyReminder.getBoolean(DatabaseContract.KEY_FIELD_DAILY_REMINDER, false);
        dailyReminder.setChecked(checkSwDailyReminder);
    }

    private void releaseReminderOn() {
        String time = "08:00"; //waktu jam 8 pagi
        String message = "Release Movies Today";
        reminderPreference.setReminderReleaseTime(time);
        reminderPreference.setReminderReleaseMessage(message);
        reminderReceiverRelease.setReminder(SettingsActivity.this, DatabaseContract.TYPE_REMINDER_PREF, time, message);
    }

    private void releaseReminderOff() {
        reminderReceiverRelease.cancelReminder(SettingsActivity.this);
    }

    private void dailyReminderOn() {
        String time = "07:00"; //waktu jam 7 pagi
        String message = "Daily Movies Today";
        reminderPreference.setReminderDailyTime(time);
        reminderPreference.setReminderDailyMessage(message);
        reminderReceiverDaily.setReminder(SettingsActivity.this, DatabaseContract.TYPE_REMINDER_RECEIVE, time, message);
    }

    private void dailyReminderOff() {
        reminderReceiverDaily.cancelReminder(SettingsActivity.this);
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

