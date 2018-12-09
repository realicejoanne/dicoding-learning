package trianne.dicoding.myfirebasedispatcher;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

public class MyJobService extends JobService {

    public static final String TAG = MyJobService.class.getSimpleName();
    final String APP_ID = "b117077825a710062e0ee3c794d04cf3";
    public static String EXTRAS_CITY = "Jakarta";

    @Override
    public boolean onStartJob(JobParameters job) {
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        return false;
    }
}