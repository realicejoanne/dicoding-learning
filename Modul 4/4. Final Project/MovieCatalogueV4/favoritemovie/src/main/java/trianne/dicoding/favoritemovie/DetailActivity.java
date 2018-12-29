package trianne.dicoding.favoritemovie;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {
    String img, title, date, desc;
    ImageView imgPoster;
    FloatingActionButton fvFav;
    TextView tvTitle, tvDate, tvDesc;
    CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        imgPoster = findViewById(R.id.imgMoviePoster);
        tvTitle = findViewById(R.id.tvTitle);
        tvDate = findViewById(R.id.tvDate);
        tvDesc = findViewById(R.id.tvDesc);
        fvFav = findViewById(R.id.love);
        coordinatorLayout = findViewById(R.id.activity_detail);

        setMovie();
    }

    private void setMovie(){
        img = getIntent().getStringExtra("poster_path");
        title = getIntent().getStringExtra("title");
        date = getIntent().getStringExtra("release_date");
        desc = getIntent().getStringExtra("overview");

        Picasso.get().load("http://image.tmdb.org/t/p/w185/" + img).into(imgPoster);
        tvTitle.setText(title);
        tvDate.setText(date);
        tvDesc.setText(desc);

        fvFav.setImageResource(R.drawable.ic_favorite_black_24dp);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home: {
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