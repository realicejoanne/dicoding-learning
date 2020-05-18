package trianne.dicoding.myintentapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MoveWithObjectActivity extends AppCompatActivity {

    TextView tvObject;
    public static final String EXTRA_PERSON = "extra_person";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_with_object);

        tvObject = findViewById(R.id.tv_object_received);

        //Melalui getIntent().getParcelableExtra(Key) kita dapat mengambil nilai obyek Person yang sebelumnya telah dikirim
        Person person = getIntent().getParcelableExtra(EXTRA_PERSON);
        String text = "Name\t: " + person.getName() + "\nEmail\t: " + person.getEmail() + "\nAge\t: " + person.getAge() + "\nLocation\t: " + person.getCity();
        tvObject.setText(text);
    }
}

//Jika kita ingin mengirimkan kumpulan obyek parcelable ke activity lain
//kita bisa memanfaatkan arraylist dan metode putParcelableArrayListExtra
//contoh:
//ArrayList<Person> persons = new ArrayList<>();
//moveWithObjectIntent.putParcelableArrayListExtra(KEY,persons);
//ArrayList<Person> persons = getIntent().getParcelableArrayListExtra(KEY);