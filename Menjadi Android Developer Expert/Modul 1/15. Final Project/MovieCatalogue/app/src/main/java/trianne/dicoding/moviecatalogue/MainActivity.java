package trianne.dicoding.moviecatalogue;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements android.app.LoaderManager.LoaderCallbacks<ArrayList<MovieItems>> {

    ListView listView;
    EditText editTitle;
    ImageView imgPoster;
    Button btnSearch;
    MovieAdapter adapter;

    static final String EXTRAS_MOVIE = "EXTRAS_MOVIE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //layout activity_main
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //pakai class MovieAdapter
        adapter = new MovieAdapter(this);
        adapter.notifyDataSetChanged();

        //listView ambil data dr adapter, movie diklik masuk ke intent detail
        listView = (ListView)findViewById(R.id.lv_movies);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    final int position, long id) {
                MovieItems item = (MovieItems)parent.getItemAtPosition(position);

                Intent intent = new Intent(MainActivity.this, DetailActivity.class);

                intent.putExtra(DetailActivity.EXTRA_TITLE, item.getMov_title());
                intent.putExtra(DetailActivity.EXTRA_RELEASE_DATE, item.getMov_date());
                intent.putExtra(DetailActivity.EXTRA_OVERVIEW, item.getMov_description());
                intent.putExtra(DetailActivity.EXTRA_POSTER_JPG, item.getMov_image());

                startActivity(intent);
            }
        });

        editTitle   = (EditText)findViewById(R.id.edit_title);
        imgPoster   = (ImageView)findViewById(R.id.imgMoviePoster);

        btnSearch   = (Button)findViewById(R.id.btn_search);
        btnSearch.setOnClickListener(movieListener);

        String movieTitle = editTitle.getText().toString();

        Bundle bundle = new Bundle();
        bundle.putString(EXTRAS_MOVIE, movieTitle);

        getLoaderManager().initLoader(0, bundle, this);
    }

    @Override
    public android.content.Loader<ArrayList<MovieItems>> onCreateLoader(int i, Bundle bundle) {
        String movieTitle = "";
        if (bundle != null){
            movieTitle = bundle.getString(EXTRAS_MOVIE);
        }

        return new MovieAsyncTaskLoader(this,movieTitle);
    }

    @Override
    public void onLoadFinished(android.content.Loader<ArrayList<MovieItems>> loader,
                               ArrayList<MovieItems> movieItems) {
        adapter.setData(movieItems);
    }

    @Override
    public void onLoaderReset(android.content.Loader<ArrayList<MovieItems>> loader) {
        adapter.setData(null);
    }

    //Button search diklik
    View.OnClickListener movieListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String movieTitle = editTitle.getText().toString();
            if(TextUtils.isEmpty(movieTitle)){
                return;
            }

            Bundle bundle = new Bundle();
            bundle.putString(EXTRAS_MOVIE, movieTitle);
            getLoaderManager().restartLoader(0, bundle, MainActivity.this);
        }
    };
}