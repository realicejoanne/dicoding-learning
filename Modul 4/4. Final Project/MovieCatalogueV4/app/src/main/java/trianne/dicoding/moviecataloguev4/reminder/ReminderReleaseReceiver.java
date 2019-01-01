package trianne.dicoding.moviecataloguev4.reminder;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import trianne.dicoding.moviecataloguev4.DetailActivity;
import trianne.dicoding.moviecataloguev4.R;
import trianne.dicoding.moviecataloguev4.api.APIService;
import trianne.dicoding.moviecataloguev4.api.RetrofitClient;
import trianne.dicoding.moviecataloguev4.db.DatabaseContract;
import trianne.dicoding.moviecataloguev4.entity.Movies;
import trianne.dicoding.moviecataloguev4.entity.Results;

import static trianne.dicoding.moviecataloguev4.api.Server.BASE_URL_API;
import static trianne.dicoding.moviecataloguev4.db.DatabaseContract.API_KEY;

public class ReminderReleaseReceiver extends BroadcastReceiver {
    private final int NOTIF_ID_RELEASE = 21;
    public List<Movies> listMovie = new ArrayList<>();

    public ReminderReleaseReceiver() {

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        getUpcomingMovie(context);
    }

    public void setReminder(Context context, String type, String time, String message){
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, ReminderReleaseReceiver.class);
        intent.putExtra("messageRelease",message);
        intent.putExtra("typeRelease",type);

        String timeArray[] = time.split(":");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]));
        calendar.set(Calendar.SECOND,0);

        int requestCode = NOTIF_ID_RELEASE;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,requestCode,intent,0);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

        Toast.makeText(context,R.string.release_save_reminder, Toast.LENGTH_SHORT).show();
    }

    public void cancelReminder(Context context){
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context,ReminderReleaseReceiver.class);
        int requestCode = NOTIF_ID_RELEASE;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,requestCode,intent,0);
        alarmManager.cancel(pendingIntent);

        Toast.makeText(context,R.string.release_cancel_reminder, Toast.LENGTH_SHORT).show();
    }

    private void getUpcomingMovie(final Context context){
        APIService service = RetrofitClient.getClient(BASE_URL_API).create(APIService.class);
        Call<Results> call = service.getUpcomingMovie(API_KEY, DatabaseContract.LANG);

        call.enqueue(new Callback<Results>() {
            @Override
            public void onResponse(Call<Results> call, Response<Results> response) {
                listMovie = response.body().getMovies();

                List<Movies> items = response.body().getMovies();
                int index = new Random().nextInt(items.size());

                Movies item = items.get(index);

                int notifId = 200;

                String title = items.get(index).getTitle();
                String message = items.get(index).getOverview();
                showNotification(context, title, message, notifId, item);
            }

            @Override
            public void onFailure(Call<Results> call, Throwable t) {
                Log.d("getUpcomingMovie", "onFailure: " + t.toString());
            }
        });
    }

    private void showNotification(Context context, String title, String message, int notifId, Movies item) {
        NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Intent i = new Intent(context, DetailActivity.class);
        i.putExtra("title", item.getTitle());
        i.putExtra("release_date", item.getReleaseDate());
        i.putExtra("overview", item.getOverview());
        i.putExtra("poster_path", item.getPosterPath());
        PendingIntent pendingIntent = PendingIntent.getActivity(context, notifId, i, PendingIntent.FLAG_UPDATE_CURRENT);

        //properties notif
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, message)
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setContentTitle(title)
                .setContentText(message)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(alarmSound);

        notificationManagerCompat.notify(notifId, builder.build());
    }
}
