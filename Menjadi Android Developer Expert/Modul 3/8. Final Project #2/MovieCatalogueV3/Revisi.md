## Catatan Revisi dari Reviewer

Hindari penggunaan Hardcoded String saat menyusun layout aplikasi. Anda bisa memindahkannya ke dalam file strings.xml

    <TextView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Overview"

Sebaiknya menggunakan https agar bisa didukung oleh android versi terbaru.

    load("http://image.tmdb.org/t/p/original/" + image)
