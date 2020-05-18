package trianne.dicoding.learnespresso;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/*
    3 komponen utama Espresso:
    ViewMatchers (onView(ViewMatcher)), untuk menemukan elemen atau komponen antar muka yang diuji.
    ViewActions (perform(ViewAction)), untuk memberikan event untuk melakukan sebuah aksi pada komponen antar muka yang diuji.
    ViewAssertions(check(ViewAssertion)), untuk mengecek sebuah kondisi atau state dari komponen yang diuji.

    Referensi:
    https://developer.android.com/training/testing/ui-testing/espresso-testing.html
    https://developer.android.com/studio/test/espresso-test-recorder.html
    http://www.vogella.com/tutorials/AndroidTestingEspresso/article.html#espresso_runningespressotests
    https://guides.codepath.com/android/UI-Testing-with-Espresso
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    EditText edtText;
    Button btnChangeText, btnSwitchActivity;
    TextView tvPrintedText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Learn Espresso");
        }

        edtText = findViewById(R.id.edt_text);
        btnChangeText = findViewById(R.id.btn_change_text);
        btnSwitchActivity = findViewById(R.id.btn_swicth_activity);
        tvPrintedText = findViewById(R.id.tv_printed_text);
        btnChangeText.setOnClickListener(this);
        btnSwitchActivity.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_change_text:
                edtText.setText("Lalala");
                String input = edtText.getText().toString().trim();
                tvPrintedText.setText(input);
                break;

            case R.id.btn_swicth_activity:
                String text = edtText.getText().toString().trim();
                Intent intent = new Intent(this, SecondActivity.class);
                intent.putExtra(SecondActivity.EXTRA_INPUT, text);
                startActivity(intent);
                break;
        }
    }
}