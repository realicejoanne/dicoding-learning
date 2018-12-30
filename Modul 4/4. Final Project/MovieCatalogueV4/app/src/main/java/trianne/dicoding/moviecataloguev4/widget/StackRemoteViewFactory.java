package trianne.dicoding.moviecataloguev4.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Binder;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import trianne.dicoding.moviecataloguev4.R;
import trianne.dicoding.moviecataloguev4.adapter.MovieAdapter;
import trianne.dicoding.moviecataloguev4.db.DatabaseContract;
import trianne.dicoding.moviecataloguev4.entity.Favorite;

import static trianne.dicoding.moviecataloguev4.db.DatabaseContract.FavoriteColumns.CONTENT_URI;

public class StackRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory {

    private ArrayList<Favorite> mWidgetItems = new ArrayList<>();
    private Context mContext;

    StackRemoteViewFactory(Context context, Intent intent) {
        mContext = context;
        int mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        mWidgetItems.clear();
        final long identityToken = Binder.clearCallingIdentity();
        Cursor cursor = mContext.getContentResolver().query(CONTENT_URI, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Favorite favorite = new Favorite(cursor);
                mWidgetItems.add(favorite);
                cursor.moveToNext();
            }
            while (!cursor.isAfterLast());
        }

        if (cursor != null) {
            cursor.close();
        }
        Binder.restoreCallingIdentity(identityToken);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return mWidgetItems.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        Favorite currentMovieFavorite;
        Bundle extras = new Bundle();
        Bitmap bmp = null;
        String title = null;
        try {
            currentMovieFavorite = mWidgetItems.get(position);
            bmp = Glide.with(mContext)
                    .asBitmap()
                    .load(DatabaseContract.LINK_IMAGE + currentMovieFavorite.getPoster())
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();
                    //.error(new ColorDrawable(mContext.getResources().getColor(R.color.colorPrimaryDark)))

            title = currentMovieFavorite.getTitle();
            extras.putString(MovieAdapter.EXTRA_MOVIE,currentMovieFavorite.getTitle());

        }
        catch (InterruptedException | ExecutionException | IndexOutOfBoundsException e) {
            Log.d("Widget Error", "error");
        }

        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.items_widget);
        rv.setImageViewBitmap(R.id.imgWidget, bmp);
        rv.setTextViewText(R.id.tvWidget, title);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);

        rv.setOnClickFillInIntent(R.id.imgWidget, fillInIntent);
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
