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

import com.bumptech.glide.Glide;

import java.nio.channels.SelectableChannel;
import java.util.ArrayList;
import java.util.Locale;

import static android.media.AudioManager.ERROR;


public class SelectActivity extends AppCompatActivity{
    TextToSpeech tts;
    public Intent i;
    private static int count_t=0;
    SpeechRecognizer mRecognizer;
    private View mLayout;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    String[] REQUIRED_PERMISSIONS  = {Manifest.permission.RECORD_AUDIO};



    //int writeExternalStoragePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    private static ProgressBar progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.select_main3);
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
        setContentView(R.layout.select_main3);
        ImageView h=(ImageView)findViewById((R.id.help));
        Glide.with(this).load(R.drawable.m11).into(h);
        h.setContentDescription("앱 사용 도움말");
        h.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                tts.speak("아이리스 앱은 인공지능 기술이 사용된 보조공학기구입니다. 사물과 문서를 인식할 수 있고 장애인에게 필요한 정보를 한번에 확인할 수 있도록 되어있습니다. 모든 기능은 " +
                        "음성인식으로 실행이 가능합니다. 먼저, 사물인식은 사물을 카메라에 비추면 음성으로 사물에 대한 정보를 전달합니다." +
                        "또한 문서를 앨범에서 선택하거나 사진을 찍어 숫자와 글자를 음성으로 전달받고, 문서 내용을 주변사람들과 공유할 수 있도록 합니다. " +
                        "사용 전 Talkback 기능을 활성화해주시고, 음성 속도는 빠름으로 설정해주시기 바랍니다.", TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        ImageView b=(ImageView)findViewById((R.id.detectbtn));
        b.setContentDescription("사물인식");
        Glide.with(this).load(R.drawable.rr1).into(b);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                mRecognizer.stopListening();
                mRecognizer.destroy();
                tts.speak("인식 기능을 실행하겠습니다.", TextToSpeech.QUEUE_FLUSH, null);
                Intent intent = new Intent(
                        getApplicationContext(), SelectFunction.class);
                startActivity(intent);
            }
        });
        ImageView c=(ImageView)findViewById((R.id.infobtn));
        //Button c = (Button)findViewById(R.id.infobtn);
        c.setContentDescription("장애인 정보 확인");
        Glide.with(this).load(R.drawable.m15).into(c);
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                mRecognizer.stopListening();
                mRecognizer.destroy();
                tts.speak("장애인 관련 필요정보를 확인하겠습니다.", TextToSpeech.QUEUE_FLUSH, null);
                Intent intent = new Intent(
                        getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
        ImageView d=(ImageView)findViewById((R.id.numbtn));

        Glide.with(this).load(R.drawable.m14).into(d);
        d.setContentDescription("문서인식");
        d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                mRecognizer.stopListening();
                mRecognizer.destroy();
                tts.speak("문서 인식 기능을 실행하겠습니다.", TextToSpeech.QUEUE_FLUSH, null);
                Intent intent = new Intent(
                        getApplicationContext(), SelectFunction2.class);
                startActivity(intent);
            }
        });
        ImageView k=(ImageView)findViewById((R.id.sitbtn));
        k.setContentDescription("상황인식");
        Glide.with(this).load(R.drawable.m16).into(k);
        k.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                mRecognizer.stopListening();
                mRecognizer.destroy();
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
        Glide.with(this).load(R.drawable.m12).into(e);

        e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cameraPermission == PackageManager.PERMISSION_GRANTED) {
                    if(count_t==0) {
                        {tts.speak("음성인식을 실행하겠습니다. 사물인식은 사물, 문서인식은 문서, 장애인정보 확인은 정보라고 말씀해주세요.", TextToSpeech.QUEUE_FLUSH, null);}
                        progressbar.setVisibility(View.VISIBLE);
                        try {
                            Thread.sleep(6000);
                            //mRecognizer.startListening(i);
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }
                        //progressbar.setVisibility(View.GONE);
                        mRecognizer.startListening(i);
                        count_t++;
                    }
                    else {
                        tts.speak("음성인식을 실행하겠습니다.", TextToSpeech.QUEUE_FLUSH, null);
                        progressbar.setVisibility(View.VISIBLE);
                        try {
                            Thread.sleep(1300);
                            //mRecognizer.startListening(i);
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }
                        //progressbar.setVisibility(View.GONE);
                        mRecognizer.startListening(i);
                    }
                }
                else
                {
                    ActivityCompat.requestPermissions( SelectActivity.this, REQUIRED_PERMISSIONS, PERMISSIONS_REQUEST_CODE);
                    if(count_t==0) {
                        {tts.speak("음성인식을 실행하겠습니다. 사물인식은 사물, 문서인식은 문서, 장애인정보 확인은 정보라고 말씀해주세요.", TextToSpeech.QUEUE_FLUSH, null);}
                        progressbar.setVisibility(View.VISIBLE);
                        try {
                            Thread.sleep(6000);
                            //mRecognizer.startListening(i);
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }
                        //progressbar.setVisibility(View.GONE);
                        mRecognizer.startListening(i);
                        count_t++;
                    }
                    else {
                        tts.speak("음성인식을 실행하겠습니다.", TextToSpeech.QUEUE_FLUSH, null);
                        progressbar.setVisibility(View.VISIBLE);
                        try {
                            Thread.sleep(1300);
                            //mRecognizer.startListening(i);
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }
                        //progressbar.setVisibility(View.GONE);
                        mRecognizer.startListening(i);
                    }
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
                tts.speak("사물인식 기능을 실행하겠습니다.", TextToSpeech.QUEUE_FLUSH, null);
                Intent intent = new Intent(
                        getApplicationContext(), SelectFunction.class);
                startActivity(intent);
                mRecognizer.stopListening();
                mRecognizer.destroy();
            }
            else if(mResult.contains("정보")){
                tts.speak("장애인관련 필요정보를 확인하겠습니다.", TextToSpeech.QUEUE_FLUSH, null);
                Intent intent = new Intent(
                        getApplicationContext(), LoginActivity.class);
                startActivity(intent);

                mRecognizer.stopListening();
                mRecognizer.destroy();
            }
            else if(mResult.contains("문서")){
                tts.speak("문서인식 기능을 확인하겠습니다.", TextToSpeech.QUEUE_FLUSH, null);
                Intent intent = new Intent(
                        getApplicationContext(), SelectFunction2.class);
                startActivity(intent);
                mRecognizer.stopListening();
                mRecognizer.destroy();
            }
            else if(mResult.contains("상황")){
                tts.speak("상황인식 기능은 잠겨있습니다.", TextToSpeech.QUEUE_FLUSH, null);
                Intent intent = new Intent(
                        getApplicationContext(), SelectActivity.class);
                startActivity(intent);
                mRecognizer.stopListening();
                mRecognizer.destroy();
            }
            else if(mResult.contains("취소")){
                tts.speak("음성인식 기능을 종료합니다.", TextToSpeech.QUEUE_FLUSH, null);
                mRecognizer.stopListening();
                mRecognizer.destroy();
            }
            else {
                tts.speak("다시 한번 말씀해주세요.", TextToSpeech.QUEUE_FLUSH, null);
                try {
                    Thread.sleep(1300);
                    mRecognizer.startListening(i);
                } catch (InterruptedException e1) {
                    mRecognizer.stopListening();
                }
                //progressbar.setVisibility(View.GONE);


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
                try {
                    Thread.sleep(1300);mRecognizer.startListening(i);
                    //mRecognizer.startListening(i);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                    mRecognizer.stopListening();
                    //mRecognizer = null;
                }
                //progressbar.setVisibility(View.GONE);

            }

            else if(error == mRecognizer.ERROR_SERVER){
                Toast.makeText(getApplicationContext(),"서버에러 다시 한 번 말씀해주세요.",Toast.LENGTH_SHORT).show(); //서버 에러
                {tts.speak("다시 한 번 말씀해주세요.", TextToSpeech.QUEUE_FLUSH, null);}
                try {
                    Thread.sleep(1300);mRecognizer.startListening(i);
                    //mRecognizer.startListening(i);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                    mRecognizer.stopListening();
                    //mRecognizer = null;
                }
                //progressbar.setVisibility(View.GONE);

            }

            else if(error == mRecognizer.ERROR_CLIENT){
                Toast.makeText(getApplicationContext(),"클라 다시 한 번 말씀해주세요.",Toast.LENGTH_SHORT).show(); //클라이언트 에러
                tts.speak("다시 한 번 말씀해주세요.", TextToSpeech.QUEUE_FLUSH, null);
                try {
                    Thread.sleep(1300);mRecognizer.startListening(i);
                    //mRecognizer.startListening(i);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                    mRecognizer.stopListening();
                    //mRecognizer = null;
                }
                //progressbar.setVisibility(View.GONE);

            }
            else if(error == mRecognizer.ERROR_SPEECH_TIMEOUT){
                Toast.makeText(getApplicationContext(),"다시 한 번 말씀해주세요.",Toast.LENGTH_SHORT).show();
                tts.speak("다시 한 번 말씀해주세요.", TextToSpeech.QUEUE_FLUSH, null);
                try {
                    Thread.sleep(1300);
                    mRecognizer.startListening(i);
                    //mRecognizer.startListening(i);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                    mRecognizer.stopListening();
                    //mRecognizer = null;
                }
                //progressbar.setVisibility(View.GONE);

            }
            else if(error == mRecognizer.ERROR_NO_MATCH){
                Toast.makeText(getApplicationContext(),"다시 한 번 말씀해주세요.",Toast.LENGTH_SHORT).show();
                tts.speak("다시 한 번 말씀해주세요.", TextToSpeech.QUEUE_FLUSH, null);
                try {
                    Thread.sleep(1300);mRecognizer.startListening(i);
                    //mRecognizer.startListening(i);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                    mRecognizer.stopListening();
                    //mRecognizer = null;
                }
                //progressbar.setVisibility(View.GONE);


            }
            else if(error == mRecognizer.ERROR_RECOGNIZER_BUSY){
                Toast.makeText(getApplicationContext(),"다시 한 번 말씀해주세요.",Toast.LENGTH_SHORT).show(); // 인스턴스가 바쁨
                tts.speak("다시 한 번 말씀해주세요.", TextToSpeech.QUEUE_FLUSH, null);
                try {
                    Thread.sleep(1300);mRecognizer.startListening(i);
                    //mRecognizer.startListening(i);
                } catch (InterruptedException e1) {
                    mRecognizer.stopListening();
                    //mRecognizer = null;
                    e1.printStackTrace();

                }
                //progressbar.setVisibility(View.GONE);


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
    @Override
    public void onDestroy(){
        super.onDestroy();
        if(tts!=null) {
            tts.stop();
            tts.shutdown();
            tts = null;
        }
        if(mRecognizer!=null) {
            mRecognizer.stopListening();
            //recognizer = null;
            mRecognizer.destroy();
        }
        //SoundManager.playSound(1,1);
    }
}
