## Catatan Revisi dari Reviewer

AsyncTaskLoader yang anda gunakan sudah deprecated, gunakan AsyncTaskLoader versi v4

    // pada MovieAsyncTaskLoader gunakan versi ini
    import android.support.v4.content.AsyncTaskLoader;

    // lalu pada MainActivity gunakan versi ini
    import android.support.v4.app.LoaderManager;
    import android.support.v4.content.Loader;

    // untuk memanggilnya gunakan getSupportLoaderManager()

Anda bisa mempersingkat proses casting view dengan menghapus redundant code. Contoh:

    listView = findViewById(R.id.lv_movies);

Atau tambahkan library ButterKnife agar casting view lebih simple dan mengurangi boilerplate code. [Cek Referensi.](http://www.vogella.com/tutorials/AndroidButterknife/article.html)

    @BindView(R.id.lv_movies)
    ListView listView;

    @BindView(R.id.edit_title)
    EditText editTitle;

Implementasikan Parcelable pada kelas POJO, agar proses pengiriman data melalui Intent lebih simple.

    MovieItems item = (MovieItems)parent.getItemAtPosition(position);
    Intent intent = new Intent(MainActivity.this, DetailActivity.class);
    intent.putExtra(DetailActivity.EXTRA_MOVIE, item);

Penggunaan API key dalam sebuah kelas bukanlah best practice, pindahkan ke dalam file Gradle demi keamanan. [Cek Referensi.](https://medium.com/code-better/hiding-api-keys-from-your-android-repository-b23f5598b906)

    private static final String API_KEY = "130d9a6505bc83ef1d861c00ffc4bcc8";

Manfaatkan attribute tools:text yang berfungsi sebagai placeholder text dalam pembuatan layout dinamis. Contoh:

    <TextView
        android:id="@+id/tvDate"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginLeft="@dimen/activity_horizontal_margin"
        tools:text="@string/date" /> // teks hanya muncul dalam layout preview
