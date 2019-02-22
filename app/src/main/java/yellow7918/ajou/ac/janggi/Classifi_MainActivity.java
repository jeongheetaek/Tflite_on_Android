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
                Log.d("test", title);
                //SoundManager.cleanup();
                mp.pause();
                if(text.contains("masisneunmilk")) {
                    text = "마시는우유";
                }
                else if(text.contains("mikis")){
                    text = "밀키스";
                }
                else if(text.contains("mongshell")){
                    text = "몽쉘";
                }
                else if(text.contains("ansungtangmyun")){
                    text = "안성탕면";
                }
                else if(text.contains("cola")){
                    text = "코카콜라";
                }
                else if(text.contains("chocosonge")){
                    text = "초코송이";
                }
                else if(text.contains("chocoemong")){
                    text = "초코에몽";
                }
                else if(text.contains("chilseong")){
                    text = "칠성사이다";
                }
                else if(text.contains("chicchoc")){
                    text = "칙촉";
                }
                else if(text.contains("buldarkbog")){
                    text = "불닭볶음면";
                }
                else if(text.contains("bananamilk")){
                    text = "바나나우유";
                }else if(text.contains("trevi")){
                    text = "트레비";
                }
                else if(text.contains("zzawang")){
                    text = "짜왕";
                }
                else if(text.contains("shinramen")){
                    text = "신라면";
                }
                else if(text.contains("sprite")){
                    text = "스프라이트";
                }
                else if(text.contains("toreta")){
                    text = "토레타";
                }
                else if(text.contains("welchis")){
                    text = "웰치스";
                }
                else if(text.contains("seoulmilk")){
                    text = "서울우유";
                }
                else if(text.contains("pocarisweat")){
                    text = "포카리스웨트";
                }
                else if(text.contains("pocachip")){
                    text = "포카칩";
                }
                else if(text.contains("lottebaebaero")){
                    text = "롯데빼빼로";
                }
                else if(text.contains("jorypong")){
                    text = "조리퐁";
                }
                else if(text.contains("hushmilk")){
                    text = "허쉬우유";
                }
                else if(text.contains("homrunball")){
                    text = "홈러본";
                }
                else if(text.contains("galbae")){
                    text = "갈아만든배";
                }
                else if(text.contains("demisoda")){
                    text = "데미소다";
                }
                else if(text.contains("dejawa")){
                    text = "데자와";
                }
                tts.speak(text+"로 인식했습니다.", TextToSpeech.QUEUE_FLUSH, null);
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
