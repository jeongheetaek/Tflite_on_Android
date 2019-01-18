package yellow7918.ajou.ac.janggi;
//회원가입 관련 내역 데이터 관련해서 서버로 데이터를 전송 할건지 아니면 앱 자체적으로 할건지 의논 필요

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SignUpActivity2 extends AppCompatActivity {

    private Button btnDone;
    private Button btnCancel;

    private String email;
    private String name;
    private String password;
    private String key;

    private EditText keyword;

    private boolean[] tag;

    private boolean edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup2);

        CheckBox tag1 = findViewById(R.id.tag1);
        CheckBox tag2 = findViewById(R.id.tag2);
        CheckBox tag3 = findViewById(R.id.tag3);
        CheckBox tag4 = findViewById(R.id.tag4);
        CheckBox tag5 = findViewById(R.id.tag5);
        CheckBox tag6 = findViewById(R.id.tag6);
        CheckBox tag7 = findViewById(R.id.tag7);
        CheckBox tag8 = findViewById(R.id.tag8);
        CheckBox tag9 = findViewById(R.id.tag9);

        CheckBox[] checkBoxes = {tag1, tag2, tag3, tag4, tag5, tag6, tag7, tag8, tag9};

        keyword = findViewById(R.id.etKeyWord);

        if (getIntent() != null) {
            email = getIntent().getStringExtra("email");
            name = getIntent().getStringExtra("name");
            password = getIntent().getStringExtra("password");

            edit = getIntent().getBooleanExtra("edit", false);
            tag = getIntent().getBooleanArrayExtra("tag");
            key = getIntent().getStringExtra("keyword");
        }

        if(tag == null) {
            tag = new boolean[checkBoxes.length];
        }

        btnDone = findViewById(R.id.btnDone);
        btnCancel = findViewById(R.id.btnCancel);

        if (edit) {
            for (int i = 0; i < checkBoxes.length; i++) {
                checkBoxes[i].setChecked(tag[i]);
            }

            keyword.setText(key);

            btnDone.setText("완료");
            btnDone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                    Map<String, Object> map = new HashMap<>();
                    for (int i = 0; i < checkBoxes.length; i++)
                        checked(checkBoxes[i], i);

                    List<Boolean> tags = new ArrayList<>();

                    for (Boolean b : tag)
                        tags.add(b);

                    map.put("tag", tags);
                    map.put("keyword", keyword.getText().toString());

                    mDatabase.child("user").child(email.replace(".", "")).updateChildren(map, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                            finish();
                        }
                    });

                }
            });

            btnCancel.setText("취소");
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

        } else {

            btnDone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int i = 0; i < checkBoxes.length; i++)
                        checked(checkBoxes[i], i);
                    Intent intent = new Intent(getBaseContext(), SignUpActivity3.class);
                    intent.putExtra("name", name);
                    intent.putExtra("email", email);
                    intent.putExtra("password", password);

                    intent.putExtra("tag", tag);
                    intent.putExtra("keyword", keyword.getText().toString());
                    startActivity(intent);

                    finish();
                }
            });


            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getBaseContext(), SignUpActivity.class);

                    intent.putExtra("email", email);
                    intent.putExtra("name", name);
                    startActivity(intent);

                    finish();
                }
            });
        }
    }

    void checked(CheckBox checkBox, int index) {
        tag[index] = checkBox.isChecked();
    }
}
