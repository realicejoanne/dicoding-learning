package trianne.dicoding.moviecataloguev2.homeadapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import trianne.dicoding.moviecataloguev2.DetailActivity;
import trianne.dicoding.moviecataloguev2.R;

public class NowplayingUpcomingAdapter extends RecyclerView.Adapter<NowplayingUpcomingAdapter.ViewHolder> {

    private List<MovieItems> movieLists;
    private Context context;

    public NowplayingUpcomingAdapter(List<MovieItems> movieLists, Context context) {
        this.movieLists = movieLists;
        this.context = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.items_movie_card, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final MovieItems movList = movieLists.get(position);
        holder.title.setText(movList.getMov_title());
        holder.overview.setText(movList.getMov_description());

        String release_date = movList.getMov_date();
        SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = date_format.parse(release_date);

            SimpleDateFormat new_date_format = new SimpleDateFormat("E, MMM dd, yyyy");
            String date_of_release = new_date_format.format(date);
            holder.date.setText(date_of_release);

        }
        catch (ParseException e) {
            e.printStackTrace();
        }

        Glide.with(context)
                .load("http://image.tmdb.org/t/p/w500/"+movList.getMov_image())
                .into(holder.poster);

        holder.btnFavorite.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                Toast.makeText(context, "Favorite: "+movList.getMov_title(), Toast.LENGTH_SHORT).show();
            }
        }));

        holder.btnShare.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                Toast.makeText(context, "Share: "+movList.getMov_title(), Toast.LENGTH_SHORT).show();
            }
        }));

        holder.cvDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MovieItems movieList = movieLists.get(position);
                Intent Intent = new Intent(context, DetailActivity.class);
                Intent.putExtra(DetailActivity.EXTRA_TITLE, movieList.getMov_title());
                Intent.putExtra(DetailActivity.EXTRA_OVERVIEW, movieList.getMov_description());
                Intent.putExtra(DetailActivity.EXTRA_POSTER_JPG, movieList.getMov_image());
                Intent.putExtra(DetailActivity.EXTRA_RELEASE_DATE, movieList.getMov_date());
                context.startActivity(Intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {
        public TextView title, overview, date;
        public ImageView poster;
        public Button btnFavorite, btnShare;
        public LinearLayout cvDetail; //cv = cardview

        public ViewHolder(View itemView) {
            super(itemView);

            title       = (TextView) itemView.findViewById(R.id.tv_item_name);
            poster      = (ImageView) itemView.findViewById(R.id.imgPoster);
            overview    = (TextView) itemView.findViewById(R.id.tv_item_overview);
            date        = (TextView) itemView.findViewById(R.id.tv_item_date);
            btnFavorite = (Button) itemView.findViewById(R.id.btn_set_favorite);
            btnShare    = (Button) itemView.findViewById(R.id.btn_set_share);
            cvDetail    = (LinearLayout) itemView.findViewById(R.id.cv_movie);
        }

    }
}
