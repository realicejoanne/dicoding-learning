package trianne.dicoding.mytestingapp;

import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button btnSetValue;
    private TextView tvText;
    private ArrayList<String> names;
    private DelayAsync delayAsync;
    private ImageView imgPreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvText = (TextView)findViewById(R.id.tv_text);
        btnSetValue = (Button)findViewById(R.id.btn_set_nilai);
        //Error NullPointerException dibangkitkan karena kita mencoba menjalankan setOnclickListener
        //pada obyek btnSetValue yang belum diinisiasi (masih bernilai null).
        btnSetValue.setOnClickListener(this);

        names = new ArrayList<>();
        names.add("Patricia");
        names.add("Trianne");
        names.add("Joanne");

        //bagian bug Out Of Memory Exception diskip soalnya males gradle2 ah
        //cek aja https://github.com/bumptech/glide
        imgPreview.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.cat));
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_set_nilai){
            String name = "";

            //Kode di bawah ini akan membangkitkan IndexOutOfBoundsException.
            // for (int i = 0; i <= names.size(); i++){
            for (int i = 0; i < names.size(); i++){
                name += names.get(i)+"\n";
            }
            tvText.setText(name);

            //Permasalahannya terjadi ketika baris kode Thread.sleep() dijalankan.
            //Pada baris tersebut, dibuatlah sebuah thread yang sleep selama 3000000 ms (atau 3000 detik).
            //Thread ini berjalan di main thread. Karena waktu sleep ini melebihi 5 detik,
            //maka sistem Android menganggap aplikasi Not Responding.
            /* try {
                Thread.sleep(3000000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } */

            delayAsync = new DelayAsync();
            delayAsync.execute();
        }
    }

    class DelayAsync extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Thread.sleep(3000000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.d("DelayAsync", "Done");
        }
        @Override
        protected void onCancelled() {
            super.onCancelled();
            Log.d("DelayAsync", "Cancelled");
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (delayAsync != null){
            if (delayAsync.getStatus().equals(AsyncTask.Status.RUNNING)){
                delayAsync.cancel(true);
            }
        }
    }
}