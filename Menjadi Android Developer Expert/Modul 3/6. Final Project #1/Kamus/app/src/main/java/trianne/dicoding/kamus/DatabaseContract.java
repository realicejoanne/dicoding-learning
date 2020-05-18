package trianne.dicoding.kamus;

import android.provider.BaseColumns;

public class DatabaseContract {
    public static String TABLE_IND_TO_ENG = "ind_eng";
    public static String TABLE_ENG_TO_IND = "eng_ind";

    public static final class KamusColumns implements BaseColumns {
        public static String FIELD_WORD = "word";
        public static String FIELD_MEANING = "meaning";
    }
}
