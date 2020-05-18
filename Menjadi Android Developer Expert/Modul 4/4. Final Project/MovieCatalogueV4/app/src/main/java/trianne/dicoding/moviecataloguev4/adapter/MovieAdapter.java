package trianne.dicoding.moviecataloguev4.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import trianne.dicoding.moviecataloguev4.DetailActivity;
import trianne.dicoding.moviecataloguev4.R;
import trianne.dicoding.moviecataloguev4.entity.Movies;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    public final static String EXTRA_MOVIE = "movie"; //important
    private Context context;
    private List<Movies> listMovies;

    public MovieAdapter(Context context, List<Movies> listMovies){
        this.context = context;
        this.listMovies = listMovies;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_movie, null, false);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        v.setLayoutParams(layoutParams);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Movies movies = listMovies.get(position);

        Glide.with(context)
                .load("http://image.tmdb.org/t/p/w185"+movies.getPosterPath())
                .into(holder.img);
        holder.title.setText(movies.getTitle());
        holder.date.setText(movies.getReleaseDate());
        holder.desc.setText(movies.getOverview());

        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, DetailActivity.class);

                i.putExtra("title", movies.getTitle());
                i.putExtra("release_date", movies.getReleaseDate());
                i.putExtra("overview", movies.getOverview());
                i.putExtra("poster_path", movies.getPosterPath());

                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listMovies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView img;
        private CardView cv;
        private TextView title, date, desc;

        public ViewHolder(View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.imgPoster);
            title = itemView.findViewById(R.id.tv_item_title);
            date = itemView.findViewById(R.id.tv_item_date);
            desc = itemView.findViewById(R.id.tv_item_overview);
            cv = itemView.findViewById(R.id.card_view);
        }
    }
}
