package yellow7918.ajou.ac.janggi;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


public class SelectActivity extends AppCompatActivity{

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super .onCreate(savedInstanceState);
        setContentView(R.layout.select_main);
        ImageView b=(ImageView)findViewById((R.id.detectbtn));
        //Button b = (Button)findViewById(R.id.detectbtn);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                Intent intent = new Intent(
                        getApplicationContext(), SelectFunction.class);
                startActivity(intent);
            }
        });
        ImageView c=(ImageView)findViewById((R.id.infobtn));
        //Button c = (Button)findViewById(R.id.infobtn);
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                Intent intent = new Intent(
                        getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
