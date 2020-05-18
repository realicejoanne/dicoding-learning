package trianne.dicoding.dicodingnotesapp.db;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

//Copy paste dari app utama
public class DatabaseContract {
    public static String TABLE_NOTE = "note";

    //id tidak perlu didefinisikan disini karena kolom _ID sudah ada secara default di dalam kelas BaseColumns
    //sebenarnya persyaratan ketika kita ingin menggunakan ListView dengan Cursor
    //untuk menggunakan ListView dengan Cursor kita harus set kolom id dengan nama _ID
    public static final class NoteColumns implements BaseColumns {
        //Note title
        public static String TITLE = "title";
        //Note description
        public static String DESCRIPTION = "description";
        //Note date
        public static String DATE = "date";
    }

    //Variabel AUTHORITY merupakan base authority yang akan kita gunakan untuk mengidentifikasi bahwa
    //provider NoteProvider milik MyNotesApp lah yang akan diakses.
    public static final String AUTHORITY = "trianne.dicoding.mynotesapp";

    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(TABLE_NOTE)
            .build();

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString( cursor.getColumnIndex(columnName) );
    }

    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt( cursor.getColumnIndex(columnName) );
    }

    public static long getColumnLong(Cursor cursor, String columnName) {
        return cursor.getLong( cursor.getColumnIndex(columnName) );
    }
}
