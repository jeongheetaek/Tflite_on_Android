package yellow7918.ajou.ac.janggi;
//회원가입 모드
//회원가입관련 버튼 회원가입폼 정리 필요 --아직 안함

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TimeUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import javax.annotation.concurrent.ThreadSafe;

import static android.media.AudioManager.ERROR;

public class LoginActivity extends AppCompatActivity {
    private EditText et_id;
    private EditText et_pw;
    private Button btn_login;
    private Button btn_sign_up;
    private Button naver_sign_in;

    private String email;

    private ProgressBar progressBar;

    private FirebaseAuth firebaseAuth;

    TextToSpeech tts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != ERROR) {
                    // 언어를 선택한다.
                    tts.setLanguage(Locale.KOREAN);
                }
            }
        });
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

        progressBar = findViewById(R.id.progressBar);

        et_id = findViewById(R.id.email_edit);
        et_pw = findViewById(R.id.password_edit);

        if (getIntent().getStringExtra("email") != null) {
            email = getIntent().getStringExtra("email");
            et_id.setText(email);
            et_pw.requestFocus();
        }


        btn_login = findViewById(R.id.button_sign_in);
        btn_login.setOnClickListener(v -> {

            String password = et_pw.getText().toString();
            String email = et_id.getText().toString();
            if(et_id.getText().toString().equals("")){
                Toast.makeText(LoginActivity.this,"아이디를 입력해주세요.", Toast.LENGTH_SHORT).show();
                tts.speak("아이디를 입력해주세요.", TextToSpeech.QUEUE_FLUSH, null);
                et_id.requestFocus();
                return;
            }
            else if(et_pw.getText().toString().equals("")){
                Toast.makeText(LoginActivity.this,"비밀번호를 입력해주세요. ", Toast.LENGTH_SHORT).show();
                tts.speak("비밀번호를 입력해주세요.", TextToSpeech.QUEUE_FLUSH, null);
                et_pw.requestFocus();
                return;
            }
            else {
                progressBar.setVisibility(View.VISIBLE);
                firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    startActivity(new Intent(getBaseContext(), MainActivity.class));
                                    progressBar.setVisibility(View.GONE);
                                    finish();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(LoginActivity.this, "회원 정보가 틀렸습니다.", Toast.LENGTH_SHORT).show();
                                    tts.speak("회원 정보가 틀렸습니다. 다시 입력해주세요.", TextToSpeech.QUEUE_FLUSH, null);
                                    progressBar.setVisibility(View.GONE);
                                }
                            }
                        });
            }
        });

        btn_sign_up = findViewById(R.id.button_sign_up);
        btn_sign_up.setOnClickListener(v -> {
            startActivity(new Intent(this, SignUpActivity.class));
        });

        naver_sign_in = findViewById(R.id.naver_sign_up);
        naver_sign_in.setOnClickListener(v -> {
            startActivity(new Intent(this, Naver_login.class));
        });

    }

}
