package trianne.dicoding.moviecataloguev4;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import trianne.dicoding.moviecataloguev4.db.DatabaseContract;

import static trianne.dicoding.moviecataloguev4.db.DatabaseContract.FavoriteColumns.CONTENT_URI;

public class DetailActivity extends AppCompatActivity {

    String img, title, date, desc;
    ImageView imgPoster;
    FloatingActionButton fvFav;
    TextView tvTitle, tvDate, tvDesc;
    CoordinatorLayout coordinatorLayout;

    private long id;

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
        setFavorite();

        Toolbar toolbar = findViewById(R.id.toolbar);
        //toolbar.setTitle("Detail Movie");
        //setSupportActionBar(toolbar);
        if(getSupportActionBar() != null)getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setMovie(){
        img = getIntent().getStringExtra("poster_path");
        title = getIntent().getStringExtra("title");
        date = getIntent().getStringExtra("release_date");
        desc = getIntent().getStringExtra("overview");

        Glide.with(getApplicationContext())
                .load("http://image.tmdb.org/t/p/w185"+img)
                .into(imgPoster);
        tvTitle.setText(title);
        tvDate.setText(date);
        tvDesc.setText(desc);

        fvFav.setImageResource(R.drawable.ic_favorite_border_black_24dp);
    }

    public boolean setFavorite(){
        Uri uri = Uri.parse(CONTENT_URI + "");
        boolean favorite = false;
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);

        String getTitle;
        if (cursor.moveToFirst()) {
            do {
                id = cursor.getLong(0);
                getTitle = cursor.getString(1);
                if (getTitle.equals(getIntent().getStringExtra("title"))){
                    fvFav.setImageResource(R.drawable.ic_favorite_black_24dp);
                    favorite = true;
                }
            }
            while (cursor.moveToNext());

        }
        return favorite;
    }

    public void favorite (View view) {
        if(setFavorite()){
            Uri uri = Uri.parse(CONTENT_URI+"/"+id);
            getContentResolver().delete(uri, null, null);
            fvFav.setImageResource(R.drawable.ic_favorite_border_black_24dp);
        }
        else {
            ContentValues values = new ContentValues();

            values.put(DatabaseContract.FavoriteColumns.POSTER, img);
            values.put(DatabaseContract.FavoriteColumns.TITLE, title);
            values.put(DatabaseContract.FavoriteColumns.RELEASE_DATE, date);
            values.put(DatabaseContract.FavoriteColumns.DESCRIPTION, desc);

            getContentResolver().insert(CONTENT_URI, values);
            setResult(101);

            fvFav.setImageResource(R.drawable.ic_favorite_black_24dp);
        }
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
