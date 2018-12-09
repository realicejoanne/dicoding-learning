package trianne.dicoding.myalarmmanager;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView tvOneTimeDate, tvOneTimeTime, tvRepeatingTime;
    private EditText edtOneTimeMessage, edtRepeatingMessage;
    private Button btnOneTimeDate, btnOneTimeTime, btnOneTime,
            btnRepeatingTime, btnRepeating, btnCancelRepeatingAlarm;
    private Calendar calOneTimeDate, calOneTimeTime , calRepeatTimeTime;
    private AlarmReceiver alarmReceiver;
    private AlarmPreference alarmPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //One Time Alarm
        btnOneTimeDate = (Button)findViewById(R.id.btn_one_time_alarm_date);
        btnOneTimeTime = (Button)findViewById(R.id.btn_one_time_alarm_time);
        tvOneTimeDate = (TextView)findViewById(R.id.tv_one_time_alarm_date);
        tvOneTimeTime = (TextView)findViewById(R.id.tv_one_time_alarm_time);
        edtOneTimeMessage = (EditText)findViewById(R.id.edt_one_time_alarm_message);
        btnOneTime = (Button)findViewById(R.id.btn_set_one_time_alarm);

        //Repeating Time Alarm
        btnRepeatingTime = (Button)findViewById(R.id.btn_repeating_time_alarm_time);
        tvRepeatingTime = (TextView)findViewById(R.id.tv_repeating_alarm_time);
        edtRepeatingMessage = (EditText)findViewById(R.id.edt_repeating_alarm_message);
        btnRepeating = (Button)findViewById(R.id.btn_repeating_time_alarm);

        //Cancel Repeating Alarm
        btnCancelRepeatingAlarm = (Button)findViewById(R.id.btn_cancel_repeating_alarm);

        //Listener
        btnOneTimeDate.setOnClickListener(this);
        btnOneTimeTime.setOnClickListener(this);
        btnOneTime.setOnClickListener(this);

        btnRepeatingTime.setOnClickListener(this);
        btnRepeating.setOnClickListener(this);

        btnCancelRepeatingAlarm.setOnClickListener(this);

        //Calendar
        calOneTimeDate = Calendar.getInstance();
        calOneTimeTime = Calendar.getInstance();
        calRepeatTimeTime = Calendar.getInstance();

        //Alarm Manager
        alarmPreference = new AlarmPreference(this);
        alarmReceiver = new AlarmReceiver();

        if (!TextUtils.isEmpty(alarmPreference.getOneTimeDate())){
            setOneTimeText();
        }

        if (!TextUtils.isEmpty(alarmPreference.getRepeatingTime())){
            setRepeatingText();
        }

    }
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_one_time_alarm_date){
            final Calendar currentDate = Calendar.getInstance();
            new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                //Fungsi onDateSet akan dipanggil ketika kita memilih tanggal yang kita inginkan.
                //Kemudian kita pasang variable calOneTimeData dengan tanggal yang kita pilih.
                //Variable tersebut dapat digunakan untuk mengisi TextView tanggal alarm.
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    calOneTimeDate.set(year, monthOfYear, dayOfMonth);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    tvOneTimeDate.setText(dateFormat.format(calOneTimeDate.getTime()));
                }
            }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE)).show();
        }

        else if (v.getId() == R.id.btn_one_time_alarm_time){
            final Calendar currentDate = Calendar.getInstance();
            new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    calOneTimeTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    calOneTimeTime.set(Calendar.MINUTE, minute);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
                    tvOneTimeTime.setText(dateFormat.format(calOneTimeTime.getTime()));
                    //Log.v(TAG, "The choosen one " + date.getTime());
                }
            }, currentDate.get(Calendar.HOUR_OF_DAY), currentDate.get(Calendar.MINUTE), true).show();
        }

        else if (v.getId() == R.id.btn_set_one_time_alarm){
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String oneTimeDate = dateFormat.format(calOneTimeDate.getTime());
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
            String oneTimeTime = timeFormat.format(calOneTimeTime.getTime());
            String oneTimeMessage = edtOneTimeMessage.getText().toString();
            //Data disimpan
            alarmPreference.setOneTimeDate(oneTimeDate);
            alarmPreference.setOneTimeMessage(oneTimeMessage);
            alarmPreference.setOneTimeTime(oneTimeTime);

            alarmReceiver.setOneTimeAlarm(this, AlarmReceiver.TYPE_ONE_TIME,
                    alarmPreference.getOneTimeDate(),
                    alarmPreference.getOneTimeTime(),
                    alarmPreference.getOneTimeMessage());
        }

        else if (v.getId() == R.id.btn_repeating_time_alarm_time) {
            final Calendar currentDate = Calendar.getInstance();
            new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    calRepeatTimeTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    calRepeatTimeTime.set(Calendar.MINUTE, minute);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
                    tvRepeatingTime.setText(dateFormat.format(calRepeatTimeTime.getTime()));
                    //Log.v(TAG, "The choosen one " + date.getTime());
                }
            }, currentDate.get(Calendar.HOUR_OF_DAY), currentDate.get(Calendar.MINUTE), true).show();
        }

        else if (v.getId() == R.id.btn_repeating_time_alarm){
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
            String repeatingTimeTime = timeFormat.format(calRepeatTimeTime.getTime());
            String repeatingTimeMessage = edtRepeatingMessage.getText().toString();

            alarmPreference.setRepeatingTime(repeatingTimeTime);
            alarmPreference.setRepeatingMessage(repeatingTimeMessage);
            setRepeatingText();

            alarmReceiver.setRepeatingAlarm(this, AlarmReceiver.TYPE_REPEATING,
                    alarmPreference.getRepeatingTime(), alarmPreference.getRepeatingMessage());
        }

        else if (v.getId() == R.id.btn_cancel_repeating_alarm){
            alarmReceiver.cancelAlarm(this, AlarmReceiver.TYPE_REPEATING);
        }
    }

    //Ketika pengguna membuka kembali aplikasi, nilai-nilai di atas akan ditampilkan ke dalam obyek TextView.
    private void setOneTimeText() {
        tvOneTimeTime.setText(alarmPreference.getOneTimeTime());
        tvOneTimeDate.setText(alarmPreference.getOneTimeDate());
        edtOneTimeMessage.setText(alarmPreference.getOneTimeMessage());
    }

    private void setRepeatingText() {
        tvRepeatingTime.setText(alarmPreference.getRepeatingTime());
        edtRepeatingMessage.setText(alarmPreference.getRepeatingMessage());
    }
}