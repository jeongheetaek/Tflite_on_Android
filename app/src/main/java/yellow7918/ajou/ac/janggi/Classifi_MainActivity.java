package yellow7918.ajou.ac.janggi;

import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.speech.tts.TextToSpeech;
import android.widget.Toast;

import com.wonderkiln.camerakit.CameraKitError;
import com.wonderkiln.camerakit.CameraKitEvent;
import com.wonderkiln.camerakit.CameraKitEventListener;
import com.wonderkiln.camerakit.CameraKitImage;
import com.wonderkiln.camerakit.CameraKitVideo;
import com.wonderkiln.camerakit.CameraView;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static android.media.AudioManager.ERROR;
import static java.security.AccessController.getContext;

import static yellow7918.ajou.ac.janggi.Classifier.Recognition.confidence;
import static yellow7918.ajou.ac.janggi.Classifier.Recognition.title;
public class Classifi_MainActivity extends AppCompatActivity {

    TextToSpeech tts;
    EditText inputtext;
    Button button;
    private MediaPlayer mp;


    //private static final String MODEL_PATH = "mobilenet_quant_v1_224.tflite`";
    //private static final String MODEL_PATH = "final_app.tflite";
    //private static final String LABEL_PATH = "labels.txt";
    private static final String MODEL_PATH = "conv_all.tflite";
    private static final String LABEL_PATH = "conv_all.txt";
    //private static final String MODEL_PATH = "tflite_shop_final.tflite";
    //private static final String LABEL_PATH = "shop_labels2.txt";

    private static final int INPUT_SIZE = 224;

    private Classifier classifier;

    private Executor executor = Executors.newSingleThreadExecutor();
    //private TextView textViewResult;
    private Button btnDetectObject, btnToggleCamera, speech;
    private ImageView imageViewResult;
    private CameraView cameraView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mp = MediaPlayer.create(this, R.raw.tick);


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
        setContentView(R.layout.classifi_activity_main);
        cameraView = findViewById(R.id.cameraView);
        imageViewResult = findViewById(R.id.imageViewResult);
        //textViewResult = findViewById(R.id.textViewResult);
        //textViewResult.setMovementMethod(new ScrollingMovementMethod());

        btnToggleCamera = findViewById(R.id.btnToggleCamera);
        btnDetectObject = findViewById(R.id.btnDetectObject);
        //speech = findViewById(R.id.speech);

        cameraView.addCameraKitListener(new CameraKitEventListener() {
            @Override
            public void onEvent(CameraKitEvent cameraKitEvent) {

            }

            @Override
            public void onError(CameraKitError cameraKitError) {

            }

            @Override
            public void onImage(CameraKitImage cameraKitImage) {

                Bitmap bitmap = cameraKitImage.getBitmap();

                bitmap = Bitmap.createScaledBitmap(bitmap, INPUT_SIZE, INPUT_SIZE, false);

                imageViewResult.setImageBitmap(bitmap);

                final List<Classifier.Recognition> results = classifier.recognizeImage(bitmap);
                String text = title;
                Float num = confidence;
                Log.d("test", title);
                //SoundManager.cleanup();
                mp.pause();
                if(text.contains("zzawang")) {
                    text = "짜왕";
                }
                else if(text.contains("anseong")){
                    text = "안성탕면";
                }
                else if(text.contains("zzapagette")){
                    text = "짜파게티";
                }
                else if(text.contains("yulramen")){
                    text = "열라면";
                }
                else if(text.contains("yangparing")){
                    text = "양파링";
                }
                else if(text.contains("wellchis")){
                    text = "웰치스";
                }
                else if(text.contains("tumsaeramen")){
                    text = "틈새라면";
                }
                else if(text.contains("tuigimudong")){
                    text = "튀김우동";
                }
                else if(text.contains("tropicanaspakling")){
                    text = "트로피카나스파클링";
                }
                else if(text.contains("sprite")){
                    text = "스프라이트";
                }
                else if(text.contains("snackmyun")){
                    text = "스낵면";
                }
                else if(text.contains("bananakick")){
                    text = "바나나킥";
                }else if(text.contains("bbabbaro")){
                    text = "빼빼로";
                }
                else if(text.contains("buldarkboggeam")){
                    text = "불닭볶음면";
                }
                else if(text.contains("butterwapple")){
                    text = "버터와플";
                }
                else if(text.contains("chamggeramen")){
                    text = "참깨라면";
                }
                else if(text.contains("cheetos")){
                    text = "치토스";
                }
                else if(text.contains("chilseong")){
                    text = "칠성사이다";
                }
                else if(text.contains("chocopie")){
                    text = "초코파이";
                }
                else if(text.contains("cocacola")){
                    text = "코카콜라";
                }
                else if(text.contains("conchip")){
                    text = "콘칩";
                }
                else if(text.contains("crownsando")){
                    text = "크라운산도";
                }
                else if(text.contains("demisoda")){
                    text = "데미소다";
                }
                else if(text.contains("drpepper")){
                    text = "닥터페퍼";
                }
                else if(text.contains("fanta")){
                    text = "판타";
                }
                else if(text.contains("ggocalcorn")){
                    text = "꼬깔콘";
                }
                else if(text.contains("ggugawbaegi")){
                    text = "꽈배기";
                }
                else if(text.contains("goraebob")){
                    text = "고래밥";
                }
                else if(text.contains("homerunball")){
                    text = "홈런볼";
                }
                else if(text.contains("hotsitx")){
                    text = "핫식스";
                }
                else if(text.contains("jinjjanbbong")){
                    text = "진짬뽕";
                }
                else if(text.contains("jinramen")){
                    text = "진라면";
                }
                else if(text.contains("joriphong")){
                    text = "조리퐁";
                }
                else if(text.contains("letsbe")){
                    text = "레스비";
                }
                else if(text.contains("matdongsan")){
                    text = "맛동산";
                }
                else if(text.contains("milkis")){
                    text = "밀키스";
                }
                else if(text.contains("miyeokgoog")){
                    text = "미역국";
                }
                else if(text.contains("moopama")){
                    text = "무파마";
                }
                else if(text.contains("mounteendue")){
                    text = "마운틴듀";
                }
                else if(text.contains("ohgamja")){
                    text = "오감자";
                }
                else if(text.contains("ohyes")){
                    text = "오예스";
                }
                else if(text.contains("ojinguddangkong")){
                    text = "오징어땅콩";
                }
                else if(text.contains("ojingujjambbong")){
                    text = "오징어짬뽕";
                }
                else if(text.contains("paldo")){
                    text = "팔도비빔면";
                }
                else if(text.contains("pepsi")){
                    text = "펩시콜라";
                }
                else if(text.contains("pocachip")){
                    text = "포카칩";
                }
                else if(text.contains("pocarisweat")){
                    text = "포카리스웨이트";
                }
                else if(text.contains("postic")){
                    text = "포스틱";
                }
                else if(text.contains("powerade")){
                    text = "파워에이드";
                }
                else if(text.contains("pringles")){
                    text = "프링글스";
                }
                else if(text.contains("saewooggang")){
                    text = "새우깡";
                }
                else if(text.contains("samyang")){
                    text = "삼양라면";
                }
                else if(text.contains("shinrame")){
                    text = "신라면";
                }
                else if(text.contains("sickhye")){
                    text = "식혜";
                }
                if(num > 0.6) {
                    //tts.speak(results+"로 인식했습니다.", TextToSpeech.QUEUE_FLUSH, null);
                    tts.speak(text+"로 인식했습니다.", TextToSpeech.QUEUE_FLUSH, null);
                }
                else
                    tts.speak("확실하지않습니다.  다시 인식해주세요.", TextToSpeech.QUEUE_FLUSH, null);
                /*
                speech.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String text = title;
                        Log.d("test", title);
                        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
                        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);

                    }
                });
                */
                //textViewResult.setText(results.toString());
            }

            @Override
            public void onVideo(CameraKitVideo cameraKitVideo) {

            }
        });

        btnToggleCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraView.toggleFacing();
            }
        });

        btnDetectObject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.setLooping(true);
                mp.start();
                //SoundManager.playSound(1,1);
                cameraView.captureImage();
            }
        });

        initTensorFlowAndLoadModel();
    }

    @Override
    protected void onResume() {
        super.onResume();
        cameraView.start();
    }

    @Override
    protected void onPause() {
        cameraView.stop();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mp.release();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                classifier.close();
            }
        });
    }

    private void initTensorFlowAndLoadModel() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    classifier = TensorFlowImageClassifier2.create(
                            getAssets(),
                            MODEL_PATH,
                            LABEL_PATH,
                            INPUT_SIZE);
                    makeButtonVisible();
                } catch (final Exception e) {
                    throw new RuntimeException("Error initializing TensorFlow!", e);
                }
            }
        });
    }

    private void makeButtonVisible() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                btnDetectObject.setVisibility(View.VISIBLE);
            }
        });
    }
}
