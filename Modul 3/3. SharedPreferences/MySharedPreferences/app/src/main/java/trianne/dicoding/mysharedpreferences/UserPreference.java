package trianne.dicoding.mysharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;

public class UserPreference {
    private String KEY_NAME = "name";
    private String KEY_EMAIL = "email";
    private String KEY_PHONE_NUMBER = "phone_number";
    private String KEY_AGE = "age";
    private String KEY_LOVE_MU = "love_mu";
    private SharedPreferences preferences;

    //Ketika Anda membuat obyek dari kelas UserPreference pada Activity berikutnya,
    //maka obyek sharedpreferences akan diciptakan dan hanya diciptakan sekali.
    //Jika sudah ada, obyek yang sudah ada yang akan dikembalikan.
    UserPreference(Context context) {
        String PREFS_NAME = "UserPref";
        preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    //Metode setName untuk menyimpan nilai string.
    //Untuk menyimpan data kita harus mengakses obyek editor yang dimiliki oleh preferences.
    //Metode getName adalah contoh untuk mengambil data string dari preferences.
    //Di sini kita set nilai null menjadi nilai default dari KEY_NAME.
    //Nilai default adalah nilai yang yang kita dapatkan ketika string dengan KEY_NAME belum ada nilainya.
    public void setName(String name) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_NAME, name);
        //data akan disimpan ke dalam preferences
        //apply dijalankan secara asynchronous, sedangkan commit secara synchronous
        editor.apply();
    }
    public String getName() {
        return preferences.getString(KEY_NAME, null);
    }

    void setEmail(String email) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_EMAIL, email);
        editor.apply();
    }
    String getEmail() {
        return preferences.getString(KEY_EMAIL, null);
    }

    void setPhoneNumber(String phoneNumber) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_PHONE_NUMBER, phoneNumber);
        editor.apply();
    }
    String getPhoneNumber() {
        return preferences.getString(KEY_PHONE_NUMBER, null);
    }

    void setAge(int age) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(KEY_AGE, age);
        editor.apply();
    }
    int getAge() {
        return preferences.getInt(KEY_AGE, 0);
    }

    void setLoveMU(boolean status) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(KEY_LOVE_MU, status);
        editor.apply();
    }
    boolean isLoveMU() {
        return preferences.getBoolean(KEY_LOVE_MU, false);
    }
}
