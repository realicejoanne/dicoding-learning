package trianne.dicoding.moviecataloguev4;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import trianne.dicoding.moviecataloguev4.adapter.MovieAdapter;
import trianne.dicoding.moviecataloguev4.api.APIService;
import trianne.dicoding.moviecataloguev4.api.Server;
import trianne.dicoding.moviecataloguev4.db.DatabaseContract;
import trianne.dicoding.moviecataloguev4.entity.Movies;
import trianne.dicoding.moviecataloguev4.entity.Results;

public class NowplayingActivity extends AppCompatActivity {

    RecyclerView rvMovies;
    private MovieAdapter adapter;
    List<Movies> listMovies = new ArrayList<>();
    ProgressDialog loading;
    APIService apiService;

    //private final String api_key = "130d9a6505bc83ef1d861c00ffc4bcc8";
    //private final String language = "en-US";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nowplaying);

        setVariable();
        if(savedInstanceState!=null){
            ArrayList<Movies> list;
            list = savedInstanceState.getParcelableArrayList("list_movie");
            adapter = new MovieAdapter(getApplicationContext(), list);
            rvMovies.setAdapter(adapter);
        }
        else {
            refresh();
        }

        //membuat back button toolbar
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("list_movie", new ArrayList<>(listMovies));
    }

    private void setVariable() {
        rvMovies = findViewById(R.id.rv_movies);
        apiService = Server.getAPIService();
        adapter = new MovieAdapter(getApplicationContext(), listMovies);
        rvMovies.setHasFixedSize(true);
        rvMovies.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvMovies.setAdapter(adapter);
    }

    private void refresh(){
        loading = ProgressDialog.show(this, null, "Please wait...", true, false);
        apiService.getNowPlayingMovie(DatabaseContract.API_KEY, DatabaseContract.LANG).enqueue(new Callback<Results>() {
            @Override
            public void onResponse(Call<Results> call, Response<Results> response) {
                if (response.isSuccessful()){
                    loading.dismiss();
                    listMovies = response.body().getMovies();
                    rvMovies.setAdapter(new MovieAdapter(getApplicationContext(), listMovies));
                    adapter.notifyDataSetChanged();
                }
                else {
                    loading.dismiss();
                    Toast.makeText(getApplicationContext(), "Failed to fetch data!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Results> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(getApplicationContext(), "Failed to connect internet!", Toast.LENGTH_SHORT).show();
            }
        });
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