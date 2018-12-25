package trianne.dicoding.moviecataloguev2.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

import trianne.dicoding.moviecataloguev2.DetailActivity;
import trianne.dicoding.moviecataloguev2.R;

public class SearchFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<MovieItems>>{

    ListView listView;
    EditText editTitle;
    ImageView imgPoster;
    Button btnSearch;
    MovieAdapter adapter;

    private View view;

    static final String EXTRAS_MOVIE = "EXTRAS_MOVIE";

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_search, container, false);

        adapter = new MovieAdapter(getActivity());
        adapter.notifyDataSetChanged();

        listView = (ListView)view.findViewById(R.id.lv_movies);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    final int position, long id) {

                MovieItems item = (MovieItems)parent.getItemAtPosition(position);

                Intent intent = new Intent(getActivity(), DetailActivity.class);

                intent.putExtra(DetailActivity.EXTRA_TITLE, item.getMov_title());
                intent.putExtra(DetailActivity.EXTRA_OVERVIEW, item.getMov_description());
                intent.putExtra(DetailActivity.EXTRA_RELEASE_DATE, item.getMov_date());
                intent.putExtra(DetailActivity.EXTRA_POSTER_JPG, item.getMov_image());

                startActivity(intent);
            }
        });

        editTitle   = (EditText)view.findViewById(R.id.edit_title);
        imgPoster   = (ImageView)view.findViewById(R.id.imgMoviePoster);

        btnSearch     = (Button)view.findViewById(R.id.btn_search);
        btnSearch.setOnClickListener(movieListener);

        String movieTitle = editTitle.getText().toString();

        Bundle bundle = new Bundle();
        bundle.putString(EXTRAS_MOVIE, movieTitle);

        getLoaderManager().initLoader(0,bundle,SearchFragment.this);

        return view;
    }

    @Override
    public Loader<ArrayList<MovieItems>> onCreateLoader(int i, Bundle bundle) {
        String movieTitle = "";
        if (bundle != null){
            movieTitle = bundle.getString(EXTRAS_MOVIE);
        }

        return new MovieAsyncTaskLoader(getActivity(), movieTitle);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<MovieItems>> loader, ArrayList<MovieItems> movieItems) {
        adapter.setData(movieItems);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<MovieItems>> loader) {
        adapter.setData(null);
    }

    View.OnClickListener movieListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String movieTitle = editTitle.getText().toString();
            if(TextUtils.isEmpty(movieTitle)){
                return;
            }

            Bundle bundle = new Bundle();
            bundle.putString(EXTRAS_MOVIE, movieTitle);
            getLoaderManager().restartLoader(0, bundle, SearchFragment.this);
        }
    };

}
