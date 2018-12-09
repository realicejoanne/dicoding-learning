package trianne.dicoding.mygcmnetworkmanager;

import android.content.Context;

import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.PeriodicTask;
import com.google.android.gms.gcm.Task;

public class SchedulerTask {
    private GcmNetworkManager mGcmNetworkManager;

    public SchedulerTask(Context context){
        mGcmNetworkManager = GcmNetworkManager.getInstance(context);
    }

    /*
    setService()
        Metode ini menentukan GcmTaskService yang akan dijalankan ketika kriteria dipenuhi
    setPeriod()
        Metode ini menentukan interval task yang akan dijalankan dalam satuan detik.
    setFlex()
        Metode ini menentukan range waktu untuk ekseskusi task yang akan dijalankan.
        Misal kita atur period = 30 dan flex = 10 maka task akan dijalankan di range antara 20 sampai 30.
    setTag()
        Metode ini untuk memberikan tag dari task yang akan dijalankan.
    setPersisted()
        Metode ini akan membuat semua task yang akan dijalankan tetap dipertahankan
        ketika terjadi proses reboot peranti.
     */
    public void createPeriodicTask() {
        Task periodicTask = new PeriodicTask.Builder()
                .setService(SchedulerService.class)
                .setPeriod(60)
                .setFlex(10)
                .setTag(SchedulerService.TAG_TASK_WEATHER_LOG)
                .setPersisted(true)
                .build();
        mGcmNetworkManager.schedule(periodicTask);
    }
    public void cancelPeriodicTask(){
        if (mGcmNetworkManager != null){
            mGcmNetworkManager.cancelTask(SchedulerService.TAG_TASK_WEATHER_LOG, SchedulerService.class);
        }
    }
}
