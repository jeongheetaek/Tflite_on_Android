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
import android.widget.Toast;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

//import javax.annotation.Nullable;

/*
public class SelectFunction extends Fragment {
    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstancestate){
        View v = inflater.inflate(R.layout.detection_four, container, false);

        ImageView detect = v.findViewById(R.id.con_detect);
        MainActivity mainActivity = (MainActivity)getActivity();

        detect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.changeFragment(new );
            }
        });

        return v;
    }

}
*/
public class SelectFunction extends AppCompatActivity{
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super .onCreate(savedInstanceState);
        setContentView(R.layout.detection_four);


        ImageView a=(ImageView)findViewById((R.id.con_detect));
        //Intent intent = getIntent();
        //a.setImageResource(intent.getIntExtra()));
        //Button a = (Button)findViewById(R.id.con_detect);
        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                Intent intent = new Intent(
                        getApplicationContext(), Classifi_MainActivity.class);
                startActivity(intent);
            }
        });

        //Button b = (Button)findViewById(R.id.house_detect);
        ImageView b=(ImageView)findViewById((R.id.house_detect));
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                Intent intent = new Intent(
                        getApplicationContext(), Classifi_MainActivity2.class);
                startActivity(intent);
            }
        });

        //Button c = (Button)findViewById(R.id.barcode);
        ImageView c=(ImageView)findViewById((R.id.barcode));
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                Intent intent = new Intent(
                        getApplicationContext(), Classifi_MainActivity.class);
                startActivity(intent);
            }
        });

        //Button d = (Button)findViewById(R.id.text);
        ImageView d=(ImageView)findViewById((R.id.text));
        d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                Intent intent = new Intent(
                        getApplicationContext(), Ocract.class);
                startActivity(intent);
            }
        });
    }
}
