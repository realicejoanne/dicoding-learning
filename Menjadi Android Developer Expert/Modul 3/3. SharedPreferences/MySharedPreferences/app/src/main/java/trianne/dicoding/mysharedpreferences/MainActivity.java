package trianne.dicoding.mysharedpreferences;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvName, tvAge, tvPhoneNo, tvEmail, tvIsLoveMU;
    Button btnSave;

    private UserPreference mUserPreference;
    private boolean isPreferenceEmpty = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvName = (TextView) findViewById(R.id.tv_name);
        tvAge = (TextView) findViewById(R.id.tv_age);
        tvPhoneNo = (TextView) findViewById(R.id.tv_phone);
        tvEmail = (TextView) findViewById(R.id.tv_email);
        tvIsLoveMU = (TextView) findViewById(R.id.tv_is_love_mu);
        btnSave = (Button) findViewById(R.id.btn_save);
        btnSave.setOnClickListener(this);

        mUserPreference = new UserPreference(this);
        getSupportActionBar().setTitle("My User Preference");
        showExistingPreference();
    }

    private void showExistingPreference() {
        //tampilkan data yg sudah dimasukkan
        if (!TextUtils.isEmpty(mUserPreference.getName())) {
            tvName.setText(mUserPreference.getName());
            tvAge.setText(String.valueOf(mUserPreference.getAge()));
            tvIsLoveMU.setText(mUserPreference.isLoveMU() ? "Yes" : "No");
            tvEmail.setText(mUserPreference.getEmail());
            tvPhoneNo.setText(mUserPreference.getPhoneNumber());
            btnSave.setText("Edit");
        }
        //jika kosong
        else {
            final String TEXT_EMPTY = "NULL";
            tvName.setText(TEXT_EMPTY);
            tvAge.setText(TEXT_EMPTY);
            tvIsLoveMU.setText(TEXT_EMPTY);
            tvEmail.setText(TEXT_EMPTY);
            tvPhoneNo.setText(TEXT_EMPTY);
            btnSave.setText("Save");
            isPreferenceEmpty = true;
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_save) {
            Intent intent = new Intent(MainActivity.this, FormUserActivity.class);
            if (isPreferenceEmpty) {
                intent.putExtra(FormUserActivity.EXTRA_TYPE_FORM, FormUserActivity.TYPE_ADD);
            }
            else {
                intent.putExtra(FormUserActivity.EXTRA_TYPE_FORM, FormUserActivity.TYPE_EDIT);
            }
            startActivityForResult(intent, FormUserActivity.REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FormUserActivity.REQUEST_CODE) {
            showExistingPreference();
        }
    }
}
