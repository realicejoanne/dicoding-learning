package trianne.dicoding.myintentapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnMoveActivity;
    Button btnMoveWithDataActivity;
    Button btnMoveWithObject;
    Button btnDialPhone;
    Button btnMoveForResult;
    TextView tvResult;

    private int REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //MoveActivity (sederhana)
        btnMoveActivity = findViewById(R.id.btn_move_activity);
        btnMoveActivity.setOnClickListener(this);

        //MoveWithDataActivity (explicit)
        btnMoveWithDataActivity = findViewById(R.id.btn_move_activity_data);
        btnMoveWithDataActivity.setOnClickListener(this);

        //MoveWithObjectActivity (explicit parcelable)
        btnMoveWithObject = findViewById(R.id.btn_move_activity_object);
        btnMoveWithObject.setOnClickListener(this);

        //implicit
        btnDialPhone = findViewById(R.id.btn_dial_number);
        btnDialPhone.setOnClickListener(this);

        //MoveForResultActivity (implicit w/ result)
        btnMoveForResult = findViewById(R.id.btn_move_for_result);
        btnMoveForResult.setOnClickListener(this);
        tvResult = findViewById(R.id.tv_result);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_move_activity:
                //Kita membuat Intent dengan memberikan kelas Activity asal (MainActivity.this)
                // dan kelas Activity tujuan (MoveActivity.class) pada konstruktor kelas Intent
                Intent moveIntent = new Intent(MainActivity.this, MoveActivity.class);
                //metode ini akan menjalankan activity baru tanpa membawa data
                startActivity(moveIntent);
                break;

            case R.id.btn_move_activity_data:
                //Kita memanfaatkan metode putExtra() untuk mengirimkan data bersamaan dengan obyek Intent
                Intent moveWithDataIntent = new Intent(MainActivity.this, MoveWithDataActivity.class);
                moveWithDataIntent.putExtra(MoveWithDataActivity.EXTRA_NAME, "Patricia Joanne");
                moveWithDataIntent.putExtra(MoveWithDataActivity.EXTRA_AGE, 19);
                startActivity(moveWithDataIntent);
                break;

            case R.id.btn_move_activity_object:
                //Di atas kita menciptakan sebuah obyek Person bernama person yang mana kelas tersebut adalah Parcelable
                // kita atur semua data sesuai dengan propertinya
                Person person = new Person();
                person.setName("Patricia Joanne");
                person.setAge(5);
                person.setEmail("trianne24@gmail.com");
                person.setCity("Bandung");
                Intent moveWithObjectIntent = new Intent(MainActivity.this, MoveWithObjectActivity.class);
                moveWithObjectIntent.putExtra(MoveWithObjectActivity.EXTRA_PERSON, person);
                startActivity(moveWithObjectIntent);
                break;

            case R.id.btn_dial_number:
                //Variabel ACTION_DIAL menentukan intent filter dari aplikasi-aplikasi yang bisa menangani action tersebut
                //Intent lebih lengkap: https://developer.android.com/reference/android/content/Intent
                //Uri adalah untaian karakter untuk mengidentifikasi nama, sumber, atau layanan di internet
                //Pada Uri.parse("tel:"+phoneNumber) kita melakukan parsing Uri dari bentuk teks string menjadi obyek uri menggunakan metode static parse(String)
                //“tel” adalah sebuah skema yang disepakati untuk sumber daya telepon dan phoneNumber adalah variabel string.
                // Skema lain dari Uri bisa dilihat di: https://developer.android.com/guide/components/intents-common.html
                String phoneNumber = "081234567890";
                Intent dialPhoneIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+phoneNumber));
                startActivity(dialPhoneIntent);
                break;

            case R.id.btn_move_for_result:
                Intent moveForResultIntent = new Intent(MainActivity.this, MoveForResultActivity.class);
                startActivityForResult(moveForResultIntent, REQUEST_CODE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) { //dilakukan perbandingan apakah requestCode sama dengan yang dikirimkan oleh MainActivity
            if (resultCode == MoveForResultActivity.RESULT_CODE) { //apakah nilai resultCode sama dengan resultCode yang dikirim oleh MoveForResultActivity
                int selectedValue = data.getIntExtra(MoveForResultActivity.EXTRA_SELECTED_VALUE, 0);
                tvResult.setText(String.format("Hasil: %s", selectedValue));
            }
        }
    }
}
