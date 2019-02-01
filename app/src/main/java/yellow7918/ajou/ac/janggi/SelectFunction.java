package yellow7918.ajou.ac.janggi;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
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

import java.util.Locale;

import static android.media.AudioManager.ERROR;

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
        super .onCreate(savedInstanceState);
        setContentView(R.layout.detection_four);


        ImageView a=(ImageView)findViewById((R.id.con_detect));
        //Intent intent = getIntent();
        //a.setImageResource(intent.getIntExtra()));
        //Button a = (Button)findViewById(R.id.con_detect);
        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                tts.speak("편의점 상품 인식 기능을 실행하겠습니다. 인식을 위해 화면전체를 터치해주세요.", TextToSpeech.QUEUE_FLUSH, null);
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
                tts.speak("일반 사물 인식 기능을 실행하겠습니다.인식을 위해 화면전체를 터치해주세요.", TextToSpeech.QUEUE_FLUSH, null);
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
                tts.speak("매장 인식 기능을 실행하겠습니다. 인식을 위해 화면전체를 터치해주세요.", TextToSpeech.QUEUE_FLUSH, null);
                Intent intent = new Intent(
                        getApplicationContext(), Classifi_MainActivity3.class);
                startActivity(intent);
            }
        });
        //Button d = (Button)findViewById(R.id.text);
        ImageView e=(ImageView)findViewById((R.id.situation));
        e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                tts.speak("상황 인식 기능은 잠겨있습니다.", TextToSpeech.QUEUE_FLUSH, null);
                Intent intent = new Intent(
                        getApplicationContext(), SelectFunction.class);
                startActivity(intent);
            }
        });
        //Button d = (Button)findViewById(R.id.text);
        ImageView f=(ImageView)findViewById((R.id.number));
        f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                tts.speak("숫자 인식 기능을 실행하겠습니다. 인식을 위해 문서를 선택해주세요.", TextToSpeech.QUEUE_FLUSH, null);
                Intent intent = new Intent(
                        getApplicationContext(), TabFragment.class);
                startActivity(intent);
            }
        });
        //Button d = (Button)findViewById(R.id.text);
        ImageView d=(ImageView)findViewById((R.id.text));
        d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                tts.speak("글자 인식 기능을 실행하겠습니다. 인식을 위해 문서를 선택해주세요.", TextToSpeech.QUEUE_FLUSH, null);
                Intent intent = new Intent(
                        getApplicationContext(), TabFragment2.class);
                startActivity(intent);
            }
        });

        ImageView k=(ImageView)findViewById((R.id.clothes));
        k.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                tts.speak("옷 찾기 기능을 실행하겠습니다.", TextToSpeech.QUEUE_FLUSH, null);
                Intent intent = new Intent(
                        getApplicationContext(), Classifi_MainActivity4.class);
                startActivity(intent);
            }
        });

        ImageView z=(ImageView)findViewById((R.id.comm));
        z.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                tts.speak("커뮤니티 기능은 잠겨있습니다.", TextToSpeech.QUEUE_FLUSH, null);
                Intent intent = new Intent(
                        getApplicationContext(), SelectFunction.class);
                startActivity(intent);
            }
        });
    }
}
