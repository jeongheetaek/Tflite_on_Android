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
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Locale;

import static android.media.AudioManager.ERROR;


public class SelectActivity extends AppCompatActivity{
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
        setContentView(R.layout.select_main);
        ImageView b=(ImageView)findViewById((R.id.detectbtn));
        //Button b = (Button)findViewById(R.id.detectbtn);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                tts.speak("인식 기능을 실행하겠습니다.", TextToSpeech.QUEUE_FLUSH, null);
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
                tts.speak("장애인 관련 필요정보를 확인하겠습니다.", TextToSpeech.QUEUE_FLUSH, null);
                Intent intent = new Intent(
                        getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
