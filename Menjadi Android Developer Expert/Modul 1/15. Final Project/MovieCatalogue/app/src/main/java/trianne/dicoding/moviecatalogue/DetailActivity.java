package trianne.dicoding.moviecatalogue;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DetailActivity extends AppCompatActivity {

    public static String EXTRA_TITLE        = "extra_title";
    public static String EXTRA_RELEASE_DATE = "extra_release_date";
    public static String EXTRA_OVERVIEW     = "extra_overview";
    public static String EXTRA_POSTER_JPG   = "extra_poster_jpg";

    private TextView tvTitle, tvOverview, tvDate;
    private ImageView imgPoster;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //layout activity_detail
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvTitle = (TextView)findViewById(R.id.tvTitle);
        tvOverview = (TextView)findViewById(R.id.tvDesc);
        tvDate = (TextView)findViewById(R.id.tvDate);
        imgPoster = (ImageView)findViewById(R.id.imgMoviePoster);

        String title = getIntent().getStringExtra(EXTRA_TITLE);
        String release_date = getIntent().getStringExtra(EXTRA_RELEASE_DATE);
        String overview = getIntent().getStringExtra(EXTRA_OVERVIEW);
        String poster_jpg = getIntent().getStringExtra(EXTRA_POSTER_JPG);
        SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");

        tvTitle.setText(title);
        tvOverview.setText(overview);
        //picasso set img
        Picasso.get().load("http://image.tmdb.org/t/p/w500/" + poster_jpg).into(imgPoster);

        //set date
        try {
            Date date = date_format.parse(release_date);

            SimpleDateFormat new_date_format = new SimpleDateFormat("EEEE, MMM dd, yyyy");
            String date_of_release = new_date_format.format(date);
            tvDate.setText(date_of_release);

        }

        catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
