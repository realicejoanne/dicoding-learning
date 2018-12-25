package trianne.dicoding.moviecataloguev2;

import android.content.Intent;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import trianne.dicoding.moviecataloguev2.search.MovieAdapter;
import trianne.dicoding.moviecataloguev2.search.MovieAsyncTaskLoader;
import trianne.dicoding.moviecataloguev2.search.MovieItems;
import trianne.dicoding.moviecataloguev2.search.SearchFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        //getSupportActionBar().setElevation(0);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null){
            setFragment(new HomeFragment(), getResources().getString(R.string.app_name));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.appbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings){
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Bundle bundle = new Bundle();
        Fragment fragment = null;
        String title = "";

        if (id == R.id.nav_home) {
            title = getResources().getString(R.string.nav_home);
            fragment = new HomeFragment();
        }
        else if (id == R.id.nav_search) {
            title = getResources().getString(R.string.nav_search);
            fragment = new SearchFragment();
        }

        setFragment(fragment, title);
        return true;
    }

    public void setFragment(Fragment fragment, String title){
        if(fragment != null){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_main, fragment);
            ft.commit();
        }
        getSupportActionBar().setTitle(title);
    }
}
