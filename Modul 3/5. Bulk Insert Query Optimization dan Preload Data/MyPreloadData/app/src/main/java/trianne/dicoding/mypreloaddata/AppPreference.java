package trianne.dicoding.mypreloaddata;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class AppPreference {

    SharedPreferences prefs;
    Context context;

    public AppPreference(Context context) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        this.context = context;
    }

    //setFirstRun digunakan untuk mengganti nilai dengan kata kunci string dari app_first_run.
    //Metode ini akan dipanggil setelah proses insert ke dalam database berhasil.
    //getFirstRun digunakan untuk mengambil nilai dengan kata kunci string dari app_first_run.
    //Kedua metode ini digunakan sebagai parameter untuk memeriksa apakah data mahasiswa sudah dimasukkan ke database.
    public void setFirstRun(Boolean input){
        SharedPreferences.Editor editor = prefs.edit();
        String key = context.getResources().getString(R.string.app_first_run);
        editor.putBoolean(key,input);
        editor.commit();
    }

    public Boolean getFirstRun(){
        String key = context.getResources().getString(R.string.app_first_run);
        return prefs.getBoolean(key, true);
    }
}
