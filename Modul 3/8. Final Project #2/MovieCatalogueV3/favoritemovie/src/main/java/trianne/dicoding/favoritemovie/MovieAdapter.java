package trianne.dicoding.favoritemovie;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import static trianne.dicoding.favoritemovie.DatabaseContract.FavoriteColumns.DESCRIPTION;
import static trianne.dicoding.favoritemovie.DatabaseContract.FavoriteColumns.POSTER;
import static trianne.dicoding.favoritemovie.DatabaseContract.FavoriteColumns.RELEASE_DATE;
import static trianne.dicoding.favoritemovie.DatabaseContract.FavoriteColumns.TITLE;
import static trianne.dicoding.favoritemovie.DatabaseContract.getColumnString;

public class MovieAdapter extends CursorAdapter {
    public MovieAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.items_movie, parent, false);
        return view;
    }

    @Override
    public Cursor getCursor() {
        return super.getCursor();
    }

    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {
        if (cursor != null){
            TextView textViewTitle, textViewOverview, textViewRelease;
            ImageView imgPoster;

            textViewTitle = view.findViewById(R.id.tv_item_title);
            textViewOverview = view.findViewById(R.id.tv_item_overview);
            textViewRelease = view.findViewById(R.id.tv_item_date);
            imgPoster = view.findViewById(R.id.imgPoster);
            CardView cv = view.findViewById(R.id.card_view);

            cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, DetailActivity.class);
                    i.putExtra("title", getColumnString(cursor,TITLE));
                    i.putExtra("release_date", getColumnString(cursor,RELEASE_DATE));
                    i.putExtra("overview", getColumnString(cursor,DESCRIPTION));
                    i.putExtra("poster_path", getColumnString(cursor, POSTER));

                    context.startActivity(i);
                }
            });

            textViewTitle.setText(getColumnString(cursor,TITLE));
            textViewRelease.setText(getColumnString(cursor,RELEASE_DATE));
            textViewOverview.setText(getColumnString(cursor,DESCRIPTION));

            Picasso.get().load("http://image.tmdb.org/t/p/w185/" + getColumnString(cursor, POSTER)).into(imgPoster);
        }
    }
}