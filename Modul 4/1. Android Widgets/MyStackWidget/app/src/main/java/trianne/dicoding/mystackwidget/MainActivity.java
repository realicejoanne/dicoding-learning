package trianne.dicoding.mystackwidget;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}

/*
Jenis Widget:
Information Widgets
    Widget ini biasanya digunakan untuk menampilkan informasi penting bagi pengguna dan secara otomatis
    akan memperbarui informasi jika terjadi perubahan dari waktu ke waktu. Misalnya widget cuaca atau jam.
    Jika pengguna menyentuh widget jenis ini, biasanya aplikasi yang berkaitan akan dibuka.
Collection Widgets
    Widget ini bisa menampilkan banyak elemen dari jenis yang sama, contohnya koleksi gambar, atau email,
    dan lain-lain.
Control Widgets
    Digunakan untuk menampilkan fungsi yang sering digunakan. Pengguna bisa mengaktifkan atau menon-aktifkan
    fungsi tersebut tanpa harus membuka aplikasinya. Misalnya pemutar musik, kontrol sistem, dan lain-lain.
Hybrid Widgets
    Widget ini bisa menampilkan semua dari widgets di atas. Misalnya widget musik yang menampilkan informasi
    musik yang sedang diputar, menampilkan koleksi musik dan dapat mengendalikan musik yang sedang dimainkan.

Panduan Widget:
Widget Content
    Konten dari sebuah widget  berguna untuk merayu pengguna.
    Jadi, pastikan informasi yang ada di dalam aplikasi lebih lengkap isinya dibandingkan informasi yang tampil
    pada widget.

Widget Navigation
    Widget dapat juga dimanfaatkan untuk navigasi ke dalam aplikasi. Misal dari widget dapat langsung menuju
    ke detail halaman dari aplikasi.

Widget Resizing
    Widget yang dapat diubah ukurannya dapat memberikan fleksibilitas kepada user untuk mengatur tampilan
    dari layar home.

Layout Considerations
    Karena perangkat Android memiliki ukuran yang baragam, maka widget harus menyesuaikan dengan beragam
    ukuran layar.

Widget Configuration
    Kadang kala widget butuh diatur terlebih dahulu sebelum dapat ditampilkan ke layar home, misalnya
    widget email butuh penggunanya untuk login terlebih dahulu. Usahakan konfigurasi yang ditampilkan
    tetap sederhana dan gunakan tampilan dialog dengan benar.
 */