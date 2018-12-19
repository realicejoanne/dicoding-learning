package trianne.dicoding.mymultilanguageapp;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

/*
    Referensi: http://blog.danlew.net/2014/09/08/what-should-i-localize/
    Library recommended: https://github.com/square/phrase
*/

public class MainActivity extends AppCompatActivity {

    TextView tvHello, tvPlural, tvXliff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvHello = findViewById(R.id.tv_hello);
        tvPlural = findViewById(R.id.tv_plural);
        tvXliff = findViewById(R.id.tv_xliff);

        int pokeCount = 3;
        String hello = String.format(getResources().getString(R.string.hello_world), "Patricia Joanne", pokeCount, "Joseph Pieter");
        tvHello.setText(hello);

        //Pada penerapannya, teks yang ditampilkan akan sesuai dengan jumlah yang diberikan.
        int songCount = 5;
        String pluralText = getResources().getQuantityString(R.plurals.numberOfSongsAvailable, songCount, songCount);
        tvPlural.setText(pluralText);
        tvXliff.setText(getResources().getString(R.string.app_homeurl));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_change_settings){
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
        }
        return super.onOptionsItemSelected(item);
    }
}
