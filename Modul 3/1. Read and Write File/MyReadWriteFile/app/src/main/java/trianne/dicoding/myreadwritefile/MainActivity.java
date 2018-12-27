package trianne.dicoding.myreadwritefile;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

/*
Internal Storage (Penyimpanan Internal)

    Akan selalu ada karena merupakan built in memory.
    Berkas yang disimpan hanya dapat diakses oleh aplikasi Anda.
    Ketika aplikasi dicopot, maka sistem akan secara otomatis menghapus semua data.

External Storage (Penyimpanan Eksternal)

    Bisa berupa memori internal atau penyimpanan dalam bentuk piranti yang bisa dilepas (removable storage). Dan jika menggunakan removable storage maka tidak selalu ada.
    Data dapat diakses oleh pihak di luar aplikasi Anda.
    Ketika aplikasi dicopot, sistem tidak akan menghapus data kecuali jika Anda membuat berkas  pada directory dengan memanfaatkan fungsi getExternalFilesDir().

Internal paling baik digunakan untuk menyimpan data yang tidak bisa diakses oleh pengguna atau aplikasi lain.
Sementara itu, external digunakan untuk menyimpan data yang dapat diakses oleh aplikasi lain.

Menyimpan Data di Internal Storage

Ada 2 metode yang dapat digunakan dalam penyimpanan internal yaitu:
    getFilesDir(), merepresentasikan internal directory di dalam aplikasi.
    getCacheDir(), merepresentasikan internal directory untuk data temporary cache. Sistem akan secara otomatis menghapus data yang ada pada cache jika sistem mengalami kekurangan resource (sumber daya).

Menyimpan Data di External Storage

Ada 2 kategori hak akses dalam mengakses external storage:
    Public files, yaitu berkas yang bisa bebas diakses oleh aplikasi lain. Ketika aplikasi dicopot, maka berkas publik masih akan tetap ada dan bisa diakses oleh pengguna. Contohnya adalah berkas gambar hasil unduhan aplikasi Anda.
    Private files, yaitu berkas yang dapat diakses oleh aplikasi lain akan tetapi akan hilang jika aplikasi dicopot. Contohnya adalah berkas temporary resources dari hasil unduhan.

Permission
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
https://github.com/googlesamples/easypermissions
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //inisiasi
    Button btnNew;
    Button btnOpen;
    Button btnSave;
    EditText editContent;
    EditText editTitle;
    File path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnNew = (Button) findViewById(R.id.button_new);
        btnOpen = (Button) findViewById(R.id.button_open);
        btnSave = (Button) findViewById(R.id.button_save);
        editContent = (EditText) findViewById(R.id.edit_file);
        editTitle = (EditText) findViewById(R.id.edit_title);
        btnNew.setOnClickListener(this);
        btnOpen.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        path = getFilesDir();
    }

    //menambahkan fungsi utk button
    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.button_new:
                newFile();
                break;
            case R.id.button_open:
                openFile();
                break;
            case R.id.button_save:
                saveFile();
                break;
        }
    }

    //file baru
    public void newFile() {
        //mengosongkan title dan content
        editTitle.setText("");
        editContent.setText("");
        Toast.makeText(this, "Clearing file", Toast.LENGTH_SHORT).show();
    }

    //memanggil data
    private void loadData(String title) {
        //memanggil class FileHelper
        String text = FileHelper.readFromFile(this, title);
        editTitle.setText(title);
        editContent.setText(text);
        Toast.makeText(this, "Loading " + title + " data", Toast.LENGTH_SHORT).show();
    }

    //membuka file
    public void openFile() {
        showList();
    }

    private void showList() {
        //Obyek path yang diinisiasikan dengan path = getFilesDir();
        //akan secara otomatis memperoleh path dari internal storage aplikasi.
        //Dengan menggunakan path.list(), kita akan memperoleh semua nama berkas yang ada.
        //Tiap berkas yang ditemukan ditambahkan ke dalam obyek arrayList.
        final ArrayList<String> arrayList = new ArrayList<String>();
        for (String file : path.list()) {
            arrayList.add(file);
        }
        final CharSequence[] items = arrayList.toArray(new CharSequence[arrayList.size()]);

        //Dengan memanfaatkan AlertDialog, kita dapat membuat daftar pilihan berkas sederhana.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pilih file yang diinginkan");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                loadData(items[item].toString());
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    //menyimpan data
    public void saveFile() {
        //jika judul kosong
        if (editTitle.getText().toString().isEmpty()) {
            Toast.makeText(this, "Title harus diisi terlebih dahulu", Toast.LENGTH_SHORT).show();
        }
        //menyimpan data ke file
        else {
            String title = editTitle.getText().toString();
            String text = editContent.getText().toString();
            FileHelper.writeToFile(title, text, this);
            Toast.makeText(this, "Saving " + editTitle.getText().toString() + " file", Toast.LENGTH_SHORT).show();
        }
    }

}
