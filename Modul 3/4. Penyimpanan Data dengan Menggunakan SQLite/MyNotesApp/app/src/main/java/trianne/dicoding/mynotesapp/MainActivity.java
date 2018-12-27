package trianne.dicoding.mynotesapp;

import android.content.Intent;
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

/*
Tugas utama MainActivity:

    Menampilkan data dari database pada table note secara descending (menurun).
    Menerima nilai balik dari setiap aksi dan proses yang dilakukan di FormAddUpdateActivity.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //inisiasi
    RecyclerView rvNotes;
    ProgressBar progressBar;
    FloatingActionButton fabAdd;

    //inisiasi list note, noteadapter, dan notehelper
    private LinkedList<Note> list;
    private NoteAdapter adapter;
    private NoteHelper noteHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //judul actionbar
        getSupportActionBar().setTitle("Notes");

        //memanggil recyclerview
        rvNotes = (RecyclerView)findViewById(R.id.rv_notes);
        rvNotes.setLayoutManager(new LinearLayoutManager(this));
        rvNotes.setHasFixedSize(true);

        //memanggil progress bar
        progressBar = (ProgressBar)findViewById(R.id.progressbar);

        //memanggil floating button
        fabAdd = (FloatingActionButton)findViewById(R.id.fab_add);
        fabAdd.setOnClickListener(this);

        //memanggil notehelper
        noteHelper = new NoteHelper(this);
        noteHelper.open();

        //memanggil linkedlist
        list = new LinkedList<>();

        //memanggil noteadapter
        adapter = new NoteAdapter(this);
        adapter.setListNotes(list);
        rvNotes.setAdapter(adapter);

        //proses asynchronous
        new LoadNoteAsync().execute();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    //floating button mengarah ke intent formaddupdateactivity
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.fab_add){
            Intent intent = new Intent(MainActivity.this, FormAddUpdateActivity.class);
            startActivityForResult(intent, FormAddUpdateActivity.REQUEST_ADD);
        }
    }

    private class LoadNoteAsync extends AsyncTask<Void, Void, ArrayList<Note>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);

            if (list.size() > 0){
                list.clear();
            }
        }

        @Override
        protected ArrayList<Note> doInBackground(Void... voids) {
            return noteHelper.query();
        }

        @Override
        protected void onPostExecute(ArrayList<Note> notes) {
            super.onPostExecute(notes);
            progressBar.setVisibility(View.GONE);

            list.addAll(notes);
            adapter.setListNotes(list);
            adapter.notifyDataSetChanged();

            if (list.size() == 0){
                showSnackbarMessage("Tidak ada data saat ini");
            }
        }
    }

    //crud
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FormAddUpdateActivity.REQUEST_ADD){
            if (resultCode == FormAddUpdateActivity.RESULT_ADD){
                new LoadNoteAsync().execute();
                showSnackbarMessage("Satu item berhasil ditambahkan");
                // rvNotes.getLayoutManager().smoothScrollToPosition(rvNotes, new RecyclerView.State(), 0);
            }
        }
        else if (requestCode == REQUEST_UPDATE) {
            if (resultCode == FormAddUpdateActivity.RESULT_UPDATE) {
                new LoadNoteAsync().execute();
                showSnackbarMessage("Satu item berhasil diubah");
                // int position = data.getIntExtra(FormAddUpdateActivity.EXTRA_POSITION, 0);
                // rvNotes.getLayoutManager().smoothScrollToPosition(rvNotes, new RecyclerView.State(), position);
            }
            else if (resultCode == FormAddUpdateActivity.RESULT_DELETE) {
                int position = data.getIntExtra(FormAddUpdateActivity.EXTRA_POSITION, 0);
                list.remove(position);
                adapter.setListNotes(list);
                adapter.notifyDataSetChanged();
                showSnackbarMessage("Satu item berhasil dihapus");
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (noteHelper != null){
            noteHelper.close();
        }
    }

    //tampilkan snackbar
    private void showSnackbarMessage(String message){
        Snackbar.make(rvNotes, message, Snackbar.LENGTH_SHORT).show();
    }
}