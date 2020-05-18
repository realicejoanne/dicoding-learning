package trianne.dicoding.mysharedpreferences;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class FormUserActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edtName, edtEmail, edtPhone, edtAge;
    RadioGroup rgLoveMu;
    RadioButton rbYes, rbNo;
    Button btnSave;

    public static String EXTRA_TYPE_FORM = "extra_type_form";
    public static int REQUEST_CODE = 100;
    public static int TYPE_ADD = 1;
    public static int TYPE_EDIT = 2;

    int formType;

    final String FIELD_REQUIRED = "Field cannot be empty";
    final String FIELD_DIGIT_ONLY = "Field is in number only";
    final String FIELD_ISNOT_VALID = "E-mail is not valid";

    private UserPreference mUserPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_user);

        edtName = (EditText) findViewById(R.id.edt_name);
        edtAge = (EditText) findViewById(R.id.edt_age);
        edtEmail = (EditText) findViewById(R.id.edt_email);
        edtPhone = (EditText) findViewById(R.id.edt_phone);
        rgLoveMu = (RadioGroup) findViewById(R.id.rg_love_mu);
        rbYes = (RadioButton) findViewById(R.id.rb_yes);
        rbNo = (RadioButton) findViewById(R.id.rb_no);
        btnSave = (Button) findViewById(R.id.btn_save);
        btnSave.setOnClickListener(this);

        formType = getIntent().getIntExtra(EXTRA_TYPE_FORM, 0);
        mUserPreference = new UserPreference(this);

        String actionBarTitle = null;
        String btnTitle = null;

        if (formType == 1) {
            actionBarTitle = "Add New User";
            btnTitle = "Save";
        }
        else {
            actionBarTitle = "Update";
            btnTitle = "Update";
            showPreferenceInForm();
        }

        getSupportActionBar().setTitle(actionBarTitle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btnSave.setText(btnTitle);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void showPreferenceInForm() {
        edtName.setText(mUserPreference.getName());
        edtEmail.setText(mUserPreference.getEmail());
        edtAge.setText(String.valueOf(mUserPreference.getAge()));
        edtPhone.setText(mUserPreference.getPhoneNumber());

        if (mUserPreference.isLoveMU()) {
            rbYes.setChecked(true);
        }
        else {
            rbNo.setChecked(false);
        }
    }

    //validasi ketika button diklik
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_save) {
            String name = edtName.getText().toString().trim();
            String email = edtEmail.getText().toString().trim();
            String age = edtAge.getText().toString().trim();
            String phoneNo = edtPhone.getText().toString().trim();

            boolean isLoveMU = rgLoveMu.getCheckedRadioButtonId() == R.id.rb_yes;
            boolean isEmpty = false;

            if (TextUtils.isEmpty(name)) {
                isEmpty = true;
                edtName.setError(FIELD_REQUIRED);
            }

            if (TextUtils.isEmpty(email)) {
                isEmpty = true;
                edtEmail.setError(FIELD_REQUIRED);
            }
            else if (!isValidEmail(email)) {
                isEmpty = true;
                edtEmail.setError(FIELD_ISNOT_VALID);
            }

            if (TextUtils.isEmpty(age)) {
                isEmpty = true;
                edtAge.setError(FIELD_REQUIRED);
            }

            if (TextUtils.isEmpty(phoneNo)) {
                isEmpty = true;
                edtPhone.setError(FIELD_REQUIRED);
            }
            else if (!TextUtils.isDigitsOnly(phoneNo)) {
                isEmpty = true;
                edtPhone.setError(FIELD_DIGIT_ONLY);
            }

            if (!isEmpty) {
                mUserPreference.setName(name);
                mUserPreference.setAge(Integer.parseInt(age));
                mUserPreference.setEmail(email);
                mUserPreference.setPhoneNumber(phoneNo);
                mUserPreference.setLoveMU(isLoveMU);
                Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    //Khusus untuk validasi email, gunakan regular expression untuk memeriksa apakah nilai email sudah valid atau belum.
    private boolean isValidEmail(CharSequence email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}