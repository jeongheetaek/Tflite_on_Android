package yellow7918.ajou.ac.janggi;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompatSideChannelService;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.channels.SelectableChannel;
import java.util.ArrayList;
import java.util.Locale;

import static android.media.AudioManager.ERROR;


public class SelectActivity extends AppCompatActivity{
    TextToSpeech tts;
    public Intent i;
    SpeechRecognizer mRecognizer;
    private View mLayout;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    String[] REQUIRED_PERMISSIONS  = {Manifest.permission.RECORD_AUDIO};



    //int writeExternalStoragePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    private static ProgressBar progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.select_main2);
        progressbar = findViewById(R.id.progressBar3);
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != ERROR) {
                    // 언어를 선택한다.
                    tts.setLanguage(Locale.KOREAN);
                }
            }
        });

        tts.speak("음성인식 기능을 위해 화면을 터치해주세요.", TextToSpeech.QUEUE_FLUSH, null);

        super .onCreate(savedInstanceState);
        setContentView(R.layout.select_main2);
        ImageView b=(ImageView)findViewById((R.id.detectbtn));
        b.setContentDescription("사물인식");
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
        c.setContentDescription("장애인 정보 확인");
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                tts.speak("장애인 관련 필요정보를 확인하겠습니다.", TextToSpeech.QUEUE_FLUSH, null);
                Intent intent = new Intent(
                        getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
        ImageView d=(ImageView)findViewById((R.id.numbtn));
        //Button c = (Button)findViewById(R.id.infobtn);
        d.setContentDescription("문서인식");
        d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                tts.speak("문서 인식 기능을 실행하겠습니다.", TextToSpeech.QUEUE_FLUSH, null);
                Intent intent = new Intent(
                        getApplicationContext(), SelectFunction2.class);
                startActivity(intent);
            }
        });
        ImageView k=(ImageView)findViewById((R.id.sitbtn));
        k.setContentDescription("상황인식");
        //Button c = (Button)findViewById(R.id.infobtn);
        k.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                tts.speak("상황인식 기능은 잠겨있습니다.", TextToSpeech.QUEUE_FLUSH, null);
                Intent intent = new Intent(
                        getApplicationContext(), SelectActivity.class);
                startActivity(intent);
            }
        });

        //Intent
        i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());
        //음성 인식언어 설정   kr-KR 이니까 한국어
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR");

        //Recognizer 사용
        mRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        mRecognizer.setRecognitionListener(listener);
        int cameraPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);

        ImageView e=(ImageView)findViewById((R.id.infobttn));
        e.setContentDescription("음성인식");
        //Button c = (Button)findViewById(R.id.infobtn);
        e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cameraPermission == PackageManager.PERMISSION_GRANTED) {
                    tts.speak("음성인식을 실행하겠습니다. 사물인식은 사물, 문서인식은 문서, 장애인정보 확인은 정보라고 말씀해주세요.", TextToSpeech.QUEUE_FLUSH, null);
                    progressbar.setVisibility(View.VISIBLE);
                    try {
                        Thread.sleep(5500);
                        //mRecognizer.startListening(i);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                    //progressbar.setVisibility(View.GONE);
                    mRecognizer.startListening(i);
                }
                else
                {
                    ActivityCompat.requestPermissions( SelectActivity.this, REQUIRED_PERMISSIONS, PERMISSIONS_REQUEST_CODE);
                    tts.speak("음성인식을 실행하겠습니다. 사물인식은 사물, 문서인식은 문서, 장애인정보 확인은 정보라고 말씀해주세요.", TextToSpeech.QUEUE_FLUSH, null);
                    //progressbar.setVisibility(View.VISIBLE);
                    try {
                        Thread.sleep(5500);
                        //mRecognizer.startListening(i);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }

                    mRecognizer.startListening(i);
                    progressbar.setVisibility(View.GONE);
                }
            }
        });
    }
    private RecognitionListener listener = new RecognitionListener() {

        @Override
        public void onRmsChanged(float rmsdB) {
            // TODO Auto-generated method stub

        }

        //음성인식이 성공했을때 결과를 이용하는 함수
        //지금은 음성인식 결과가 HelloWorld!!부분에 들어가게 코딩됨
        @Override
        public void onResults(Bundle results) {
            //progressBar.setVisibility(View.VISIBLE);
            String key = "";
            key = SpeechRecognizer.RESULTS_RECOGNITION;
            ArrayList<String> mResult = results.getStringArrayList(key);
            String[] rs = new String[mResult.size()];
            mResult.toArray(rs);
            //TextView tv = (TextView) findViewById(R.id.textView);
            //tv.setText(""+rs[0]);

            if(mResult.contains("사물")){
                tts.speak("인식기능을 실행하겠습니다.", TextToSpeech.QUEUE_FLUSH, null);
                Intent intent = new Intent(
                        getApplicationContext(), SelectFunction.class);
                startActivity(intent);
            }
            else if(mResult.contains("정보")){
                tts.speak("장애인관련 필요정보를 확인하겠습니다.", TextToSpeech.QUEUE_FLUSH, null);
                Intent intent = new Intent(
                        getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
            else if(mResult.contains("문서")){
                tts.speak("문서인식 기능을 확인하겠습니다.", TextToSpeech.QUEUE_FLUSH, null);
                Intent intent = new Intent(
                        getApplicationContext(), SelectFunction2.class);
                startActivity(intent);
            }
            else if(mResult.contains("상황")){
                tts.speak("상황인식 기능은 잠겨있습니다.", TextToSpeech.QUEUE_FLUSH, null);
                Intent intent = new Intent(
                        getApplicationContext(), SelectActivity.class);
                startActivity(intent);
            }
            else if(mResult.contains("취소")){
                tts.speak("음성인식 기능을 종료합니다.", TextToSpeech.QUEUE_FLUSH, null);
            }
            else {
                tts.speak("다시 한번 말씀해주세요.", TextToSpeech.QUEUE_FLUSH, null);
                //tts.speak("음성인식을 실행하겠습니다.", TextToSpeech.QUEUE_FLUSH, null);
                progressbar.setVisibility(View.VISIBLE);
                try {
                    Thread.sleep(1000);
                    //mRecognizer.startListening(i);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                //progressbar.setVisibility(View.GONE);
                mRecognizer.startListening(i);

            }



        }

        @Override
        public void onReadyForSpeech(Bundle params) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onPartialResults(Bundle partialResults) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onEvent(int eventType, Bundle params) {
            // TODO Auto-generated method stub

        }

        //음성인식에 오류가 발생했을 때
        @Override
        public void onError(int error) {
            if(error == mRecognizer.ERROR_NETWORK_TIMEOUT){
                Toast.makeText(getApplicationContext(),"인터넷이 연결되지 않았습니다.",Toast.LENGTH_SHORT).show(); //네트워크 타임아웃 에러
                tts.speak("인터넷을 연결해주세요.", TextToSpeech.QUEUE_FLUSH, null);
            }
            else if(error == mRecognizer.ERROR_NETWORK){
                Toast.makeText(getApplicationContext(),"인터넷이 연결되지 않았습니다.",Toast.LENGTH_SHORT).show(); //네트워크 에러
                tts.speak("인터넷을 연결해주세요.", TextToSpeech.QUEUE_FLUSH, null);
            }

            else if(error == mRecognizer.ERROR_AUDIO){
                Toast.makeText(getApplicationContext(),"녹음에러 다시 한 번 말씀해주세요.",Toast.LENGTH_SHORT).show(); //녹음 에러
                tts.speak("다시 한 번 말씀해주세요.", TextToSpeech.QUEUE_FLUSH, null);
            }

            else if(error == mRecognizer.ERROR_SERVER){
                Toast.makeText(getApplicationContext(),"서버에러 다시 한 번 말씀해주세요.",Toast.LENGTH_SHORT).show(); //서버 에러
                tts.speak("다시 한 번 말씀해주세요.", TextToSpeech.QUEUE_FLUSH, null);
            }

            else if(error == mRecognizer.ERROR_CLIENT){
                Toast.makeText(getApplicationContext(),"클라 다시 한 번 말씀해주세요.",Toast.LENGTH_SHORT).show(); //클라이언트 에러
                tts.speak("다시 한 번 말씀해주세요.", TextToSpeech.QUEUE_FLUSH, null);
            }
            else if(error == mRecognizer.ERROR_SPEECH_TIMEOUT){
                Toast.makeText(getApplicationContext(),"다시 한 번 말씀해주세요.",Toast.LENGTH_SHORT).show();
                tts.speak("다시 한 번 말씀해주세요.", TextToSpeech.QUEUE_FLUSH, null);
            }
            else if(error == mRecognizer.ERROR_NO_MATCH){
                Toast.makeText(getApplicationContext(),"다시 한 번 말씀해주세요.",Toast.LENGTH_SHORT).show();
                tts.speak("다시 한 번 말씀해주세요.", TextToSpeech.QUEUE_FLUSH, null);

            }
            else if(error == mRecognizer.ERROR_RECOGNIZER_BUSY){
                Toast.makeText(getApplicationContext(),"다시 한 번 말씀해주세요.",Toast.LENGTH_SHORT).show(); // 인스턴스가 바쁨
                tts.speak("다시 한 번 말씀해주세요.", TextToSpeech.QUEUE_FLUSH, null);
            }





            // TODO Auto-generated method stub

        }

        @Override
        public void onEndOfSpeech() {
            // TODO Auto-generated method stub

        }

        @Override
        public void onBufferReceived(byte[] buffer) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onBeginningOfSpeech() {
            // TODO Auto-generated method stub

        }
    };
}
