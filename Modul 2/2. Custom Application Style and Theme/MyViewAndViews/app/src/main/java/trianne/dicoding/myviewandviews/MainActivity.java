package trianne.dicoding.myviewandviews;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/*
    Bacaan referensi:
    https://developer.android.com/design/index.html
    https://material.google.com/
    https://www.youtube.com/watch?v=x5-ntYM_2UY
*/

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //INI PENTING!! SERING DITANYAIN!! CARA GANTI JUDUL APPBAR GINI YAK
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Google Pixel");
        }
    }
}
