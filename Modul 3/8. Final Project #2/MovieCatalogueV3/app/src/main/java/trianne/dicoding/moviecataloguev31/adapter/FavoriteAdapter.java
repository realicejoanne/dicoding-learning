package trianne.dicoding.moviecataloguev31.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import trianne.dicoding.moviecataloguev31.DetailActivity;
import trianne.dicoding.moviecataloguev31.R;
import trianne.dicoding.moviecataloguev31.entity.Favorite;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {
    private Context context;
    private Cursor listFavorite;

    public FavoriteAdapter(Context context){
        this.context = context;
    }

    public void setListFavorite(Cursor listFavorite) {
        this.listFavorite = listFavorite;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_movie, null, false);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        v.setLayoutParams(layoutParams);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Favorite favorite = getItem(position);

        Glide.with(context)
                .load("http://image.tmdb.org/t/p/w185"+favorite.getPoster())
                .into(holder.img);
        holder.title.setText(favorite.getTitle());
        holder.date.setText(favorite.getDate());
        holder.desc.setText(favorite.getDescription());

        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, DetailActivity.class);

                i.putExtra("title", favorite.getTitle());
                i.putExtra("release_date", favorite.getDate());
                i.putExtra("overview", favorite.getDescription());
                i.putExtra("poster_path", favorite.getPoster());

                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
        Log.e("DATE", ""+favorite.getDate());
    }

    @Override
    public int getItemCount() {
        if (listFavorite == null) return 0;
        return listFavorite.getCount();
    }

    private Favorite getItem(int position){
        if (!listFavorite.moveToPosition(position)) {
            throw new IllegalStateException("Position invalid");
        }
        return new Favorite(listFavorite);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView title, date, desc;
        CardView cv;

        ViewHolder(View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.imgPoster);
            title = itemView.findViewById(R.id.tv_item_title);
            date = itemView.findViewById(R.id.tv_item_date);
            desc = itemView.findViewById(R.id.tv_item_overview);
            cv = itemView.findViewById(R.id.card_view);
        }
    }
}
