package trianne.dicoding.mynotesapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.LinkedList;

import trianne.dicoding.mynotesapp.adapter.NoteAdapter;
import trianne.dicoding.mynotesapp.db.NoteHelper;
import trianne.dicoding.mynotesapp.entity.Note;

import static trianne.dicoding.mynotesapp.FormAddUpdateActivity.REQUEST_UPDATE;
import static trianne.dicoding.mynotesapp.db.DatabaseContract.CONTENT_URI;

/*
SQLite ini dipakai oleh aplikasi-aplikasi native dengan penyimpanan data yang tidak bersifat kompleks
seperti Google Chrome dan Firefox contohnya untuk menyimpan bookmark website dan juga aplikasi mobile
seperti aplikasi contact bawaan sebagai penyimpanan data lokal yang mendukung proses sinkronisasi ke server.

SQLite hanya mendukung beberapa tipe data seperti text untuk penyimpanan data dalam bentuk string,
int untuk menyimpan data dalam bentuk bilang bulat, dan real untuk penyimpan data dalam bentuk bilangan
pecahan/bilangan presisi. Jadi apabila ingin menyimpan data yang tidak didukung oleh SQLite maka diharuskan
dilakukan proses konversi tipe data yang sesuai dengan tipe data yang didukung sebelum melakukan penyimpanan data.

Jika merencanakan data yang disimpan dalam jumlah besar, maka sebaiknya semua proses dilakukan secara asynchronous.
Kamu bisa menggunakan AsyncTask atau Loader untuk melakukan ini.

Langkah membuat database:
1. Mendefinisikan skema data yang akan diimplementasikan ke dalam database (DatabaseContract)
2. Buat DatabaseHelper untuk menjalankan fungsi-fungsi dalam Data Definition Language (DDL) pada sebuah database.
    Create table, delete table. Cek https://developer.android.com/reference/android/database/sqlite/SQLiteOpenHelper.html
3. Membuat fungsi untuk manipulasi data dalam tabel (DML).
    Create, update, delete from table.
4. Mengambil Data (READ) dengan Method query() dan rawQuery().
5. Mengatur keamanan database dengan SQLcipher cek https://www.zetetic.net/sqlcipher/sqlcipher-for-android/
 */

/*
Tugas utama MainActivity:

    Menampilkan data dari database pada table note secara descending (menurun).
    Menerima nilai balik dari setiap aksi dan proses yang dilakukan di FormAddUpdateActivity.
 */

/*
Membuat content provider:
Pertama, yaitu ubah penggunaan model data Note menjadi cursor.
Kedua, ubah akses database dengan menggunakan ContentResolver.
Ketiga, hapus penggunaan komponen yang sudah tidak perlukan, misalnya NoteHelper
    karena fungsinya sudah digantikan oleh ContentResolver.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //inisiasi
    RecyclerView rvNotes;
    ProgressBar progressBar;
    FloatingActionButton fabAdd;

    //inisiasi list note, noteadapter, dan notehelper
    //private LinkedList<Note> list;
    private Cursor list;
    private NoteAdapter adapter;
    private NoteHelper noteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("Notes");

        rvNotes = (RecyclerView)findViewById(R.id.rv_notes);
        rvNotes.setLayoutManager(new LinearLayoutManager(this));
        rvNotes.setHasFixedSize(true);

        progressBar = (ProgressBar)findViewById(R.id.progressbar);
        fabAdd = (FloatingActionButton)findViewById(R.id.fab_add);
        fabAdd.setOnClickListener(this);

        adapter = new NoteAdapter(this);
        adapter.setListNotes(list);
        rvNotes.setAdapter(adapter);

        new LoadNoteAsync().execute();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.fab_add){
            Intent intent = new Intent(MainActivity.this, FormAddUpdateActivity.class);
            startActivityForResult(intent, FormAddUpdateActivity.REQUEST_ADD);
        }
    }

    private class LoadNoteAsync extends AsyncTask<Void, Void, Cursor>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            //Perhatikan penggunaan dari ContentResolver yaitu query(TABLE_NAME,null,null,null,null).
            //Fungsi query() adalah sama dengan query select di dalam database.
            return getContentResolver().query(CONTENT_URI,null,null,null,null);
        }

        @Override
        protected void onPostExecute(Cursor notes) {
            super.onPostExecute(notes);
            progressBar.setVisibility(View.GONE);

            list = notes;
            adapter.setListNotes(list);
            adapter.notifyDataSetChanged();

            if (list.getCount() == 0){
                showSnackbarMessage("Tidak ada data saat ini");
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == FormAddUpdateActivity.REQUEST_ADD){
            if (resultCode == FormAddUpdateActivity.RESULT_ADD){
                new LoadNoteAsync().execute();
                showSnackbarMessage("Satu item berhasil ditambahkan");
            }
        }
        else if (requestCode == REQUEST_UPDATE) {

            if (resultCode == FormAddUpdateActivity.RESULT_UPDATE) {
                new LoadNoteAsync().execute();
                showSnackbarMessage("Satu item berhasil diubah");
            }

            else if (resultCode == FormAddUpdateActivity.RESULT_DELETE) {
                new LoadNoteAsync().execute();
                showSnackbarMessage("Satu item berhasil dihapus");
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void showSnackbarMessage(String message){
        Snackbar.make(rvNotes, message, Snackbar.LENGTH_SHORT).show();
    }
}