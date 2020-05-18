package trianne.dicoding.kamus;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class SearchViewHolder extends RecyclerView.ViewHolder {

    TextView tvWord, tvMeaning;

    public SearchViewHolder(View itemView) {
        super(itemView);
        tvWord  = (TextView)itemView.findViewById(R.id.tvDetailWord);
        tvMeaning = (TextView)itemView.findViewById(R.id.tvDetailMeaning);
    }

    public void bind(final DictionaryModel dictionaryModel) {
        tvWord.setText(dictionaryModel.getWord());
        tvMeaning.setText(dictionaryModel.getMeaning());

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(itemView.getContext(), DetailActivity.class);
                intent.putExtra(DetailActivity.ITEM_WORD, dictionaryModel.getWord());
                intent.putExtra(DetailActivity.ITEM_MEANING, dictionaryModel.getMeaning());
                intent.putExtra(DetailActivity.ITEM_CATEGORY, dictionaryModel.getCategory());
                itemView.getContext().startActivity(intent);
            }
        });
    }
}