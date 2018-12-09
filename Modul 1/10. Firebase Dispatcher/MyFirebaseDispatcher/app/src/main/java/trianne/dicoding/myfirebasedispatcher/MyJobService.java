package trianne.dicoding.myfirebasedispatcher;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

public class MyJobService extends JobService {

    public static final String TAG = MyJobService.class.getSimpleName();
    final String APP_ID = "Masukkan API key anda...";
    public static String EXTRAS_CITY = "extras_city";

    @Override
    public boolean onStartJob(JobParameters job) {
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        return false;
    }
}