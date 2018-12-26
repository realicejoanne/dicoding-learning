package trianne.dicoding.kamus;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class DictionaryPreferences {
    SharedPreferences preferences;
    Context context;

    public DictionaryPreferences(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        this.context = context;
    }

    public void setFirstRun(Boolean input){
        SharedPreferences.Editor editor = preferences.edit();
        String key = context.getResources().getString(R.string.dictionary_pref);
        editor.putBoolean(key,input);
        editor.commit();
    }

    public Boolean getFirstRun(){
        String key = context.getResources().getString(R.string.dictionary_pref);
        return preferences.getBoolean(key, true);
    }
}
