package trianne.dicoding.mywidget;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.widget.RemoteViews;

public class UpdatingWidgetService extends JobService {

    //Mirip dengan kode pada updateAppWidget yang terdapat pada BilanganAcakWidget.
    //Yang membedakan adalah bagaimana kita mendapatkan AppWidgetManager.
    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        //Dengan menggunakan getInstance(this) kita bisa mendapatkan obyek manager dari semua widget pada aplikasi kita.
        AppWidgetManager manager = AppWidgetManager.getInstance(this);

        RemoteViews view = new RemoteViews(getPackageName(), R.layout.bilangan_acak_widget);
        ComponentName theWidget = new ComponentName(this, BilanganAcakWidget.class);

        String lastUpdate = "Random: "+ NumberGenerator.Generate(100);

        view.setTextViewText(R.id.appwidget_text, lastUpdate);
        manager.updateAppWidget(theWidget, view);

        jobFinished(jobParameters, false);

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }
}
