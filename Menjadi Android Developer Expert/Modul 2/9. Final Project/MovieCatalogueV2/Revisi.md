## Catatan Revisi dari Reviewer

Mulailah untuk menerapkan konsep Material Design ketika mengembangkan project Aplikasi.

Update beberapa library yang digunakan ke versi yang terbaru.

    implementation 'com.android.volley:volley:1.1.0'

Hindari penggunaan Hardcoded String saat menyusun layout aplikasi. Anda bisa memindahkannya kedalam file strings.xml

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="@dimen/activity_vertical_margin"
        android:text="Overview"
        android:textStyle="bold"
        android:textSize="30sp"/>

Manfaatkan attribute tools:text yang berfungsi sebagai placeholder text, untuk membantu Anda dalam pembuatan layout dinamis. Contoh:

    <TextView
        android:id="@+id/tv_year_detail"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        tools:text="year"/>

Hindari penggunaan fungsi yang telah deprecated seperti yang berada pada kelas SearchFragment berikut:

    getLoaderManager().initLoader(0,bundle,SearchFragment.this);

Sekarang kita tidak perlu menuliskan type secara explicit ketika melakukan casting view.

    tvTitle = (TextView)findViewById(R.id.tvTitle);
    
    //type casting di atas bisa dihapus sehingga menjadi seperi berikut:
    tvTitle = findViewById(R.id.tvTitle);

Untuk mempermudah proses casting view bisa menggunakan bantuan library Butterknife. Contoh penggunaannya:

    @BindView(R.id.title) TextView title;
    @BindView(R.id.subtitle) TextView subtitle;
    @BindView(R.id.footer) TextView footer;

Library Butterknife bisa dipelajari pada tautan http://jakewharton.github.io/butterknife/

Untuk alasan keamaan kredensial, hindari menyematkan sebuah API KEY kedalam sebuah kelas seperti berikut:

    String apiKey = "b650046bf640e7bf7054093854b8d02a";

Sebaiknya dipindahkan kedalam berkas build.gradle seperti berikut

    android {
        defaultConfig {
            ...
            buildConfigField("String" , "TMDB_API_KEY" , '"b650046bf640e7bf7054093854b8d02a"')
        }
    }

Untuk mengaksesnya seperti berikut:

    String apiKey = BuildConfig.TMDB_API_KEY;

Biasakan untuk reformat code dan optimized import supaya code lebih rapi dan bersih dari unused import.

Hapus kode yang tidak digunakan atau sederhanakan code sesuai dengan convention. Anda bisa memanfaatkan fitur Analyze - Code Cleanup.

Hapuslah resource yang tidak pernah digunakan. Anda bisa menggunakan menu Refactor - Remove Unused Resource.

Gunakan fitur Reformat Code untuk merapihkan code termasuk indent dengan cara mengakses menu bar Code -> Reformat Code.
