package trianne.dicoding.myactionbar;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //untuk menampilkan custom item pada action bar, kita hanya menggunakan xml dan 1 baris kode.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        if (searchManager != null) {
            //Untuk mengambil komponen SearchView yang sebelumnya sudah di inflate
            SearchView searchView = (SearchView) (menu.findItem(R.id.search)).getActionView();
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

            //SetQueryHint() berguna untuk memberikan hint query search apa yang telah dimasukkan.
            //Hal ini akan memudahkan pengguna untuk memasukkan suatu kata.
            searchView.setQueryHint(getResources().getString(R.string.search_hint));

            //Pada setOnQueryTextListener() ada 2 metode yang kita terapkan,
            //yaitu onQueryTextSubmit() dan onQueryTextChange().
            //Metode onQueryTextSubmit() akan dipanggil saat Submit ditekan.
            //Sementara itu, onQueryTextChange() akan dipanggil setiap kali user memasukkan
            //atau mengubah query yang ada pada inputan searchView.
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    Toast.makeText(MainActivity.this, query, Toast.LENGTH_SHORT).show();
                    return true;
                }
                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.menu1:
                MenuFragment menuFragment = new MenuFragment();
                FragmentManager mFragmentManager = getSupportFragmentManager();
                FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.fragment_container,menuFragment);
                mFragmentTransaction.addToBackStack(null);
                mFragmentTransaction.commit();
                return true;

            case R.id.menu2:
                Intent i = new Intent(this,MenuActivity.class);
                startActivity(i);
                return true;

            default:
                return true;
        }
    }
}