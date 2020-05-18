package trianne.dicoding.myflexiblefragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


//Belajar Fragment sederhana
public class HomeFragment extends Fragment implements View.OnClickListener {


    public HomeFragment() {
        // Required empty public constructor
    }


    //Terdapat metode onCreateView() di mana layout interface didefinisikan dan ditransformasi
    //dari layout berupa file xml kedalam obyek view dengan menggunakan metode inflate()
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Inflater.inflate() merupakan obyek dari LayoutInflater yang berfungsi untuk
        // mengubah layout xml ke dalam bentuk obyek viewgroup atau widget melalui pemanggilan metode inflate()
        return inflater.inflate(R.layout.fragment_home, container, false);

        //Boolean attachToRoot: Apakah kita akan menempelkan layout kita ke dalam root dari parent layout yang ada.
        //Jika ya, maka akan ditempelkan kedalam parent layout yang ada.
        //Jika tidak, maka hanya akan menghasilkan nilai balik dalam bentu obyek view saja.
        //Kita memilih false pada attachToRoot karena pada kode ini kita ingin menambahkan event onClick pada button-nya.
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button btnCategory = view.findViewById(R.id.btn_category);
        btnCategory.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //FragmentManager untuk mengorganize atau mengkoordinasi semua obyek fragment yang terdapat di dalam sebuah activity
        //dan menempelkan CategoryFragment ke dalam activity dengan menggunakan FragmentTransaction.
        //Namun kita perlu melakukan pengecekan terhadap getFragementManager,
        //karena sebuah fragment ketika membutuhkan suatu context harus mengambil dari activity.
        if (v.getId() == R.id.btn_category) {
            FragmentManager mFragmentManager = getFragmentManager();
            if (mFragmentManager != null) {
                CategoryFragment mCategoryFragment = new CategoryFragment();
                FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
                //kita menggunakan method replace() dan bukan add() ketika ingin menempelkan sebuah fragment baru.
                //method replace() akan mengganti obyek fragment yang sedang tampil saat ini dengan obyek fragment yang baru
                mFragmentTransaction.replace(R.id.frame_container, mCategoryFragment, CategoryFragment.class.getSimpleName());
                mFragmentTransaction.commit();
            }
        }
    }
}

//Catatan mFragmentTransaction.addToBackStack(null)
//Kita menggunakan addToBackStack(null) karena kita ingin obyek fragment yang saat ini kita ciptakan
//masuk ke dalam sebuah fragment stack yang nantinya ketika kita tekan tombol back dia akan pop-out keluar dari stack
//dan menampilkan obyek fragment sebelumnya, HomeFragment.