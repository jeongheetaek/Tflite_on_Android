package yellow7918.ajou.ac.janggi;

import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

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
import static yellow7918.ajou.ac.janggi.Classifier.Recognition.confidence;
import static yellow7918.ajou.ac.janggi.Classifier.Recognition.title;

public class Classifi_MainActivity6 extends AppCompatActivity {
    TextToSpeech tts;
    EditText inputtext;
    Button button;
    private MediaPlayer mp;
    private MediaPlayer mp1;
    private MediaPlayer mp2;
    private MediaPlayer mp3;
    private MediaPlayer mp4;
    private MediaPlayer mp5;
    private MediaPlayer mp6;
    private MediaPlayer mp_real;

    private static final String MODEL_PATH = "toy_all.tflite";
    private static final String LABEL_PATH = "toy.txt";

    private static final int INPUT_SIZE = 224;

    private Classifier classifier;

    private Executor executor = Executors.newSingleThreadExecutor();
    //private TextView textViewResult;
    private Button btnDetectObject, btnToggleCamera, speech;
    private ImageView imageViewResult;
    private CameraView cameraView;
    private int k=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mp = MediaPlayer.create(this, R.raw.tick);
        mp1 = MediaPlayer.create(this, R.raw.bororo);
        mp2 = MediaPlayer.create(this, R.raw.kong);
        mp3 = MediaPlayer.create(this, R.raw.pink);
        mp4 = MediaPlayer.create(this, R.raw.robot);
        mp5 = MediaPlayer.create(this, R.raw.shark);
        mp6 = MediaPlayer.create(this, R.raw.tayo);
        mp = MediaPlayer.create(this, R.raw.tayo);


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
                if(text.contains("bbororo")){
                    text = "뽀로로";
                    mp_real = mp1;
                }else if(text.contains("kong")){
                    text = "콩순이";
                    mp_real = mp2;
                }else if(text.contains("pinkpong")){
                    text = "핑크퐁";
                    mp_real = mp3;
                }else if(text.contains("robocar")){
                    text = "로보카폴리";
                    mp_real = mp4;
                }else if(text.contains("shark")){
                    text = "상어가족";
                    mp_real = mp5;
                }else if(text.contains("tayo")) {
                    text = "타요";
                    mp_real = mp6;
                }
                if(num > 0.6) {
                    //tts.speak(results+"로 인식했습니다.", TextToSpeech.QUEUE_FLUSH, null);
                    tts.speak(text+"로 인식했습니다.", TextToSpeech.QUEUE_FLUSH, null);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    mp_real.start();
                }
                else
                    tts.speak("확실하지않습니다. 다시 인식해주세요.", TextToSpeech.QUEUE_FLUSH, null);
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

