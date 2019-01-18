package yellow7918.ajou.ac.janggi;
//회원가입 관련 내역 데이터 관련해서 서버로 데이터를 전송 할건지 아니면 앱 자체적으로 할건지 의논 필요

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class SignUpActivity extends AppCompatActivity {

    private EditText etEmail;
    private EditText etName;
    private EditText etPassword;
    private EditText etPasswordConfirm;
    private Button btnDone;
    private Button btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etPasswordConfirm = findViewById(R.id.etPasswordConfirm);
        btnDone = findViewById(R.id.btnDone);
        btnCancel = findViewById(R.id.btnCancel);
        etName = findViewById(R.id.etName);

        if (getIntent() != null) {
            etEmail.setText(getIntent().getStringExtra("email"));
        }

        // 비밀번호 일치 검사
        etPasswordConfirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String password = etPassword.getText().toString();
                String confirm = etPasswordConfirm.getText().toString();

                if (password.equals(confirm)) {
                    etPassword.setBackgroundColor(Color.GREEN);
                    etPasswordConfirm.setBackgroundColor(Color.GREEN);
                } else {
                    etPassword.setBackgroundColor(Color.RED);
                    etPasswordConfirm.setBackgroundColor(Color.RED);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 이메일 입력 확인
                if (etEmail.getText().toString().length() == 0) {
                    Toast.makeText(SignUpActivity.this, "Email을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    etEmail.requestFocus();
                    return;
                }

                // 비밀번호 입력 확인
                if (etPassword.getText().toString().length() == 0) {
                    Toast.makeText(SignUpActivity.this, "비밀번호를 입력해 주세요.", Toast.LENGTH_SHORT).show();
                    etPassword.requestFocus();
                    return;
                }

                // 비밀번호 확인 입력 확인
                if (etPasswordConfirm.getText().toString().length() == 0) {
                    Toast.makeText(SignUpActivity.this, "비밀번호를 입력해 주세요.", Toast.LENGTH_SHORT).show();
                    etPasswordConfirm.requestFocus();
                    return;
                }

                // 비밀번호 일치 확인
                if (!etPassword.getText().toString().equals(etPasswordConfirm.getText().toString())) {
                    Toast.makeText(SignUpActivity.this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                    etPassword.setText("");
                    etPasswordConfirm.setText("");
                    etPassword.requestFocus();
                    return;
                }

                if (etName.getText().toString().length() == 0) {
                    Toast.makeText(SignUpActivity.this, "이름을 입력해 주세요.", Toast.LENGTH_SHORT).show();
                    etPasswordConfirm.requestFocus();
                    return;
                }

                Intent intent = new Intent(getBaseContext(), SignUpActivity2.class);

                intent.putExtra("email", etEmail.getText().toString());
                intent.putExtra("name", etName.getText().toString());
                intent.putExtra("password", etPassword.getText().toString());
                startActivity(intent);

                finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
