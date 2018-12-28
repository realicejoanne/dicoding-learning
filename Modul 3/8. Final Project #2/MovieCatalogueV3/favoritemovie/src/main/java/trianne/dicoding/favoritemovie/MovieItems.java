package trianne.dicoding.favoritemovie;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import static android.provider.BaseColumns._ID;
import static trianne.dicoding.favoritemovie.DatabaseContract.FavoriteColumns.DESCRIPTION;
import static trianne.dicoding.favoritemovie.DatabaseContract.FavoriteColumns.TITLE;
import static trianne.dicoding.favoritemovie.DatabaseContract.FavoriteColumns.POSTER;
import static trianne.dicoding.favoritemovie.DatabaseContract.FavoriteColumns.RELEASE_DATE;
import static trianne.dicoding.favoritemovie.DatabaseContract.getColumnInt;
import static trianne.dicoding.favoritemovie.DatabaseContract.getColumnString;

public class MovieItems implements Parcelable {
    private int id;
    private String title;
    private String date;
    private String description;
    private String poster;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeString(this.date);
        dest.writeString(this.poster);
    }

    public MovieItems(Cursor cursor){
        this.id = getColumnInt(cursor, _ID);
        this.title = getColumnString(cursor, TITLE);
        this.description = getColumnString(cursor, DESCRIPTION);
        this.date = getColumnString(cursor, RELEASE_DATE);
        this.poster = getColumnString(cursor, POSTER);
    }

    private MovieItems(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.description = in.readString();
        this.date = in.readString();
        this.poster = in.readString();
    }

    public static final Parcelable.Creator<MovieItems> CREATOR = new Parcelable.Creator<MovieItems>() {
        @Override
        public MovieItems createFromParcel(Parcel in) {
            return new MovieItems(in);
        }

        @Override
        public MovieItems[] newArray(int size) {
            return new MovieItems[size];
        }
    };
}
