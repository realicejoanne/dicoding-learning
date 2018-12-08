package trianne.dicoding.myflexiblefragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Kita menggunakan ragmentManager yang merupakan antarmuka untuk mengorganisir obyek fragment
        //Di sini kita menggunakan kelas FragmentManager yang berasal dari Android Support Library v4,
        //agar bisa kompatibel ke versi Android sebelumnya (Backward Compability).
        FragmentManager mFragmentManager = getSupportFragmentManager();

        //FragmentTransaction merupakan api untuk melakukan operasi pada fragment seperti add(), replace(), commit() dsb.
        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
        HomeFragment mHomeFragment = new HomeFragment();
        Fragment fragment = mFragmentManager.findFragmentByTag(HomeFragment.class.getSimpleName());
        if (!(fragment instanceof HomeFragment)) {
            mFragmentTransaction.add(R.id.frame_container, mHomeFragment, HomeFragment.class.getSimpleName());
            Log.d("MyFlexibleFragment", "Fragment Name:" + HomeFragment.class.getSimpleName());
            //minta obyek mFragmentTransaction untuk melakukan pemasangan obyek secara asynchronous
            //untuk ditampilkan ke antar muka pengguna (user interface).
            mFragmentTransaction.commit();
        }
    }
}
