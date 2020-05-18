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

    //important!
    public final static String PREF_NAME = "reminderPreferences";
    public static final String LANG = "en-US";
    public static final String API_KEY = "130d9a6505bc83ef1d861c00ffc4bcc8";

    //settings
    public static final String KEY_UPCOMING_REMINDER = "upcomingReminder";
    public static final String KEY_DAILY_REMINDER = "dailyReminder";
    public static final String KEY_FIELD_UPCOMING_REMINDER = "checkedUpcoming";
    public static final String KEY_FIELD_DAILY_REMINDER = "checkedDaily";
    public final static String KEY_REMINDER_DAILY = "DailyTime";
    public final static String KEY_REMINDER_MESSAGE_RELEASE = "reminderMessageRelease";
    public final static String KEY_REMINDER_MESSAGE_DAILY = "reminderMessageDaily";

    public static final String TYPE_REMINDER_PREF = "reminderAlarm";
    public static final String TYPE_REMINDER_RECEIVE = "reminderAlarmRelease";
}
