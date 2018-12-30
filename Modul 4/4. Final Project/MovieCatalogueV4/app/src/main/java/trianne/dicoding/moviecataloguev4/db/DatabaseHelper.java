package trianne.dicoding.moviecataloguev4.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static trianne.dicoding.moviecataloguev4.db.DatabaseContract.FavoriteColumns.DESCRIPTION;
import static trianne.dicoding.moviecataloguev4.db.DatabaseContract.FavoriteColumns.POSTER;
import static trianne.dicoding.moviecataloguev4.db.DatabaseContract.FavoriteColumns.RELEASE_DATE;
import static trianne.dicoding.moviecataloguev4.db.DatabaseContract.FavoriteColumns.TABLE_FAVORITE;
import static trianne.dicoding.moviecataloguev4.db.DatabaseContract.FavoriteColumns.TITLE;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "db_favorite";
    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE_FAVORITE =
            String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            TABLE_FAVORITE,
            _ID,
            TITLE,
            POSTER,
            RELEASE_DATE,
            DESCRIPTION
    );

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_FAVORITE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_FAVORITE);
        onCreate(db);
    }
}

