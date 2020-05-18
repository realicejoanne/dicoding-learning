package trianne.dicoding.kamus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    public static final String ITEM_WORD = "word";
    public static final String ITEM_MEANING = "meaning";
    public static final String ITEM_CATEGORY = "category";

    TextView tvWord, tvMeaning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //actionbar berdasarkan nama katanya
        getSupportActionBar().setTitle(getIntent().getStringExtra(ITEM_WORD));
        getSupportActionBar().setSubtitle(getIntent().getStringExtra(ITEM_CATEGORY));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //tampilkan
        tvWord = (TextView)findViewById(R.id.tvDetailWord);
        tvMeaning = (TextView)findViewById(R.id.tvDetailMeaning);
        tvWord.setText(getIntent().getStringExtra(ITEM_WORD));
        tvMeaning.setText(getIntent().getStringExtra(ITEM_MEANING));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
