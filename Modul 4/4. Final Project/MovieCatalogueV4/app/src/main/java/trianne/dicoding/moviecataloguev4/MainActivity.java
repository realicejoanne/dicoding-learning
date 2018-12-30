package trianne.dicoding.moviecataloguev4;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import trianne.dicoding.moviecataloguev4.adapter.MovieAdapter;
import trianne.dicoding.moviecataloguev4.api.APIService;
import trianne.dicoding.moviecataloguev4.api.Server;
import trianne.dicoding.moviecataloguev4.db.DatabaseContract;
import trianne.dicoding.moviecataloguev4.entity.Movies;
import trianne.dicoding.moviecataloguev4.entity.Results;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    RecyclerView rvMovies;
    private MovieAdapter adapter;
    List<Movies> listMovies = new ArrayList<>();
    ProgressDialog loading;
    APIService apiService;

    //private final String api_key = "130d9a6505bc83ef1d861c00ffc4bcc8";
    //private final String language = "en-US";
    private final String page = "1";
    private final String sort_by = "popularity.desc";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

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
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("list_movie", new ArrayList<>(listMovies));
    }

    private void setVariable () {
        rvMovies = findViewById(R.id.rv_movies);
        apiService = Server.getAPIService();
        adapter = new MovieAdapter(getApplicationContext(), listMovies);
        rvMovies.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvMovies.setHasFixedSize(true);
        rvMovies.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.appbar_menu, menu);

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.search));
        searchView.setQueryHint(getResources().getString(R.string.search));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                searchMovie(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText)){
                    refresh();
                }
                return false;
            }
        });
        return true;
    }

    public void searchMovie(String keyword){
        apiService.getSearchMovie(DatabaseContract.API_KEY, DatabaseContract.LANG, keyword, page).enqueue(new Callback<Results>() {
            @Override
            public void onResponse(Call<Results> call, Response<Results> response) {
                if (response.isSuccessful()){
                    listMovies = response.body().getMovies();

                    rvMovies.setAdapter(new MovieAdapter(getApplicationContext(), listMovies));
                    adapter.notifyDataSetChanged();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Failed to fetch data!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Results> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Failed to connect internet!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void refresh(){
        loading = ProgressDialog.show(this, null, "Please wait...", true, false);

        apiService.getAllMovies(DatabaseContract.API_KEY, DatabaseContract.LANG, sort_by, page).enqueue(new Callback<Results>() {
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
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent most = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(most);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_np) {
            Intent now = new Intent(getApplicationContext(), NowplayingActivity.class);
            startActivity(now);
            Toast.makeText(getApplicationContext(), "Show Now Playing Movies", Toast.LENGTH_SHORT).show();
        }
        else if (id == R.id.nav_up) {
            Intent up = new Intent(getApplicationContext(), UpcomingActivity.class);
            startActivity(up);
            Toast.makeText(getApplicationContext(), "Show Upcoming Movies", Toast.LENGTH_SHORT).show();
        }
        else if (id == R.id.nav_fav) {
            Intent fav = new Intent(getApplicationContext(), FavoriteActivity.class);
            startActivity(fav);
            Toast.makeText(getApplicationContext(), "Show Favorite Movies", Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
