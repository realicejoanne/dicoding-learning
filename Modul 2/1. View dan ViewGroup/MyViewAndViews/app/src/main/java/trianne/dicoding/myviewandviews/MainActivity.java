package trianne.dicoding.myviewandviews;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/*
    Bacaan referensi:
    https://developer.android.com/guide/practices/screens_support.html
    http://developer.android.com/training/multiscreen/screendensities.html
    http://dpi.lv/
    https://pixplicity.com/dp-px-converter/
    https://www.youtube.com/watch?v=zhszwkcay2A
    https://jampasir.wordpress.com/2015/07/15/android-unit-px-pixel-dpdip-density-independent-pixel-dan-sp-scale-independent-pixels/
    https://material.uplabs.com/
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
