package trianne.dicoding.moviecataloguev4.db;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public final class DatabaseContract {
    public static final String AUTHORITY = "trianne.dicoding.moviecataloguev4";
    public static final String SCHEME = "content";

    private DatabaseContract(){

    }

    public static final class FavoriteColumns implements BaseColumns {
        public static String TABLE_FAVORITE = "favorite";

        public static String TITLE = "name";
        public static String RELEASE_DATE = "date";
        public static String DESCRIPTION = "description";
        public static String POSTER = "poster";

        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_FAVORITE)
                .build();
    }

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString( cursor.getColumnIndex(columnName) );
    }

    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt( cursor.getColumnIndex(columnName) );
    }
}
