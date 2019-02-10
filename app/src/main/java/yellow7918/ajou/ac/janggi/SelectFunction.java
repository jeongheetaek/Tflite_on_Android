package yellow7918.ajou.ac.janggi;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
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
public class SelectFunction extends AppCompatActivity {
    TextToSpeech tts;
    public Intent i;
    SpeechRecognizer mRecognizer;

    private static ProgressBar progressBar;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != ERROR) {
                    // 언어를 선택한다.
                    tts.setLanguage(Locale.KOREAN);
                }
            }
        });
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detection_four);


        ImageView a = (ImageView) findViewById((R.id.con_detect));
        //Intent intent = getIntent();
        //a.setImageResource(intent.getIntExtra()));
        //Button a = (Button)findViewById(R.id.con_detect);
        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tts.speak("편의점 상품 인식 기능을 실행하겠습니다. 인식을 위해 화면전체를 터치해주세요.", TextToSpeech.QUEUE_FLUSH, null);
                Intent intent = new Intent(
                        getApplicationContext(), Classifi_MainActivity.class);
                startActivity(intent);
            }
        });

        //Button b = (Button)findViewById(R.id.house_detect);
        ImageView b = (ImageView) findViewById((R.id.house_detect));
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tts.speak("일반 사물 인식 기능을 실행하겠습니다.인식을 위해 화면전체를 터치해주세요.", TextToSpeech.QUEUE_FLUSH, null);
                Intent intent = new Intent(
                        getApplicationContext(), Classifi_MainActivity2.class);
                startActivity(intent);
            }
        });

        //Button c = (Button)findViewById(R.id.barcode);
        ImageView c = (ImageView) findViewById((R.id.barcode));
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tts.speak("매장 인식 기능을 실행하겠습니다. 인식을 위해 화면전체를 터치해주세요.", TextToSpeech.QUEUE_FLUSH, null);
                Intent intent = new Intent(
                        getApplicationContext(), Classifi_MainActivity3.class);
                startActivity(intent);
            }
        });
        //Button d = (Button)findViewById(R.id.text);
        ImageView e = (ImageView) findViewById((R.id.situation));
        e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tts.speak("상황 인식 기능은 잠겨있습니다.", TextToSpeech.QUEUE_FLUSH, null);
                Intent intent = new Intent(
                        getApplicationContext(), SelectFunction.class);
                startActivity(intent);
            }
        });
        //Button d = (Button)findViewById(R.id.text);
        ImageView f = (ImageView) findViewById((R.id.number));
        f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tts.speak("숫자 인식 기능을 실행하겠습니다. 인식을 위해 문서를 선택해주세요.", TextToSpeech.QUEUE_FLUSH, null);
                Intent intent = new Intent(
                        getApplicationContext(), TabFragment.class);
                startActivity(intent);
            }
        });
        //Button d = (Button)findViewById(R.id.text);
        ImageView d = (ImageView) findViewById((R.id.text));
        d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tts.speak("글자 인식 기능을 실행하겠습니다. 인식을 위해 문서를 선택해주세요.", TextToSpeech.QUEUE_FLUSH, null);
                Intent intent = new Intent(
                        getApplicationContext(), TabFragment2.class);
                startActivity(intent);
            }
        });

        ImageView k = (ImageView) findViewById((R.id.clothes));
        k.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tts.speak("옷 찾기 기능을 실행하겠습니다.", TextToSpeech.QUEUE_FLUSH, null);
                Intent intent = new Intent(
                        getApplicationContext(), Classifi_MainActivity4.class);
                startActivity(intent);
            }
        });

        i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());
        //음성 인식언어 설정   kr-KR 이니까 한국어
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR");

        mRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        mRecognizer.setRecognitionListener(listener);

        ImageView z = (ImageView) findViewById((R.id.comm));
        z.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tts.speak("음성인식을 실행하겠습니다.", TextToSpeech.QUEUE_FLUSH, null);
                try {
                    Thread.sleep(2300);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                //progressBar.setVisibility(View.VISIBLE);
                mRecognizer.startListening(i);
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

            if (mResult.contains("일반")) {
                tts.speak("일반 사물 인식 기능을 실행하겠습니다.", TextToSpeech.QUEUE_FLUSH, null);
                Intent intent = new Intent(
                        getApplicationContext(), Classifi_MainActivity.class);
                startActivity(intent);
            } else if (mResult.contains("편의점")) {
                tts.speak("편의점 상품 인식 기능을 실행하겠습니다.", TextToSpeech.QUEUE_FLUSH, null);
                Intent intent = new Intent(
                        getApplicationContext(), Classifi_MainActivity2.class);
                startActivity(intent);
            } else if (mResult.contains("매장")) {
                tts.speak("매장 인식 기능을 실행하겠습니다.", TextToSpeech.QUEUE_FLUSH, null);
                Intent intent = new Intent(
                        getApplicationContext(), Classifi_MainActivity3.class);
                startActivity(intent);
            }else if (mResult.contains("옷")) {
                tts.speak("옷 인식 기능을 실행하겠습니다.", TextToSpeech.QUEUE_FLUSH, null);
                Intent intent = new Intent(
                        getApplicationContext(), Classifi_MainActivity4.class);
                startActivity(intent);
            }else if (mResult.contains("숫자")) {
                tts.speak("숫자 인식 기능을 실행하겠습니다. 노란색 원을 터치해주세요.", TextToSpeech.QUEUE_FLUSH, null);
                Intent intent = new Intent(
                        getApplicationContext(), TabFragment.class);
                startActivity(intent);
            }else if (mResult.contains("글자")) {
                tts.speak("글자 인식 기능을 실행하겠습니다. 노란색 원을 터치해주세요.", TextToSpeech.QUEUE_FLUSH, null);
                Intent intent = new Intent(
                        getApplicationContext(), TabFragment2.class);
                startActivity(intent);
            }else if (mResult.contains("상황")) {
                tts.speak("상황 인식 기능은 잠겨있습니다.", TextToSpeech.QUEUE_FLUSH, null);
                /*Intent intent = new Intent(
                        getApplicationContext(), SelectFunction.class);
                startActivity(intent);
                */
            }else if (mResult.contains("나가기")) {
                tts.speak("음성인식 기능을 종료합니다.", TextToSpeech.QUEUE_FLUSH, null);
            } else {
                tts.speak("다시 한 번 정확하게 말씀해주세요.", TextToSpeech.QUEUE_FLUSH, null);
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
            if (error == mRecognizer.ERROR_NETWORK_TIMEOUT) {
                Toast.makeText(getApplicationContext(), "인터넷이 연결되지 않았습니다.", Toast.LENGTH_SHORT).show(); //네트워크 타임아웃 에러
                tts.speak("인터넷을 연결해주세요.", TextToSpeech.QUEUE_FLUSH, null);
            } else if (error == mRecognizer.ERROR_NETWORK) {
                Toast.makeText(getApplicationContext(), "인터넷이 연결되지 않았습니다.", Toast.LENGTH_SHORT).show(); //네트워크 에러
                tts.speak("인터넷을 연결해주세요.", TextToSpeech.QUEUE_FLUSH, null);
            } else if (error == mRecognizer.ERROR_AUDIO) {
                Toast.makeText(getApplicationContext(), "녹음에러 다시 한 번 말씀해주세요.", Toast.LENGTH_SHORT).show(); //녹음 에러
                tts.speak("다시 한 번 말씀해주세요.", TextToSpeech.QUEUE_FLUSH, null);
            } else if (error == mRecognizer.ERROR_SERVER) {
                Toast.makeText(getApplicationContext(), "서버에러 다시 한 번 말씀해주세요.", Toast.LENGTH_SHORT).show(); //서버 에러
                tts.speak("다시 한 번 말씀해주세요.", TextToSpeech.QUEUE_FLUSH, null);
            } else if (error == mRecognizer.ERROR_CLIENT) {
                Toast.makeText(getApplicationContext(), "클라 다시 한 번 말씀해주세요.", Toast.LENGTH_SHORT).show(); //클라이언트 에러
                tts.speak("다시 한 번 말씀해주세요.", TextToSpeech.QUEUE_FLUSH, null);
            } else if (error == mRecognizer.ERROR_SPEECH_TIMEOUT) {
                Toast.makeText(getApplicationContext(), "다시 한 번 말씀해주세요.", Toast.LENGTH_SHORT).show();
                tts.speak("다시 한 번 말씀해주세요.", TextToSpeech.QUEUE_FLUSH, null);
            } else if (error == mRecognizer.ERROR_NO_MATCH) {
                Toast.makeText(getApplicationContext(), "다시 한 번 말씀해주세요.", Toast.LENGTH_SHORT).show();
                tts.speak("다시 한 번 말씀해주세요.", TextToSpeech.QUEUE_FLUSH, null);

            } else if (error == mRecognizer.ERROR_RECOGNIZER_BUSY) {
                Toast.makeText(getApplicationContext(), "다시 한 번 말씀해주세요.", Toast.LENGTH_SHORT).show(); // 인스턴스가 바쁨
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
