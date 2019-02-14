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
import android.widget.ImageView;
import android.widget.TextView;

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
import static yellow7918.ajou.ac.janggi.Classifier.Recognition.title;

public class Classifi_MainActivity4 extends AppCompatActivity {
    TextToSpeech tts;
    EditText inputtext;
    Button button;
    private MediaPlayer mp;


    private static final String MODEL_PATH = "final_app.tflite";
    private static final String LABEL_PATH = "labels.txt";
    //private static final String MODEL_PATH = "optimized_graph.tflite";
    //private static final String LABEL_PATH = "retrained_labels.txt";

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

                /*if(text.contains("brightbalaws")) {
                    text = "밝은 블라우스";
                }else if(text.contains("brighthalf")) {
                    text = "깔끔한 색의 반팔티";
                }else if(text.contains("brightpadding")) {
                    text = "밝은색 계열의 패딩";
                }else if(text.contains("brightpants")) {
                    text = "가벼운 느낌의 바지";
                }else if(text.contains("brightshirts")) {
                    text = "밝은 색의 셔츠";
                }else if(text.contains("darkbalaws")) {
                    text = "어두운 색의 블라우스";
                }else if(text.contains("darkhalf")) {
                    text = "무거운 느낌의 반팔티";
                }else if(text.contains("darkpadding")) {
                    text = "어두운 색의 패딩";
                }else if(text.contains("darkpants")) {
                    text = "어두운 색의 바지";
                }else if(text.contains("darkshirts")) {
                    text = "정갈한 느낌의 셔츠";
                }else if(text.contains("darkskirts")) {
                    text = "어두운 색의 치마";
                }else if(text.contains("brightskirts")) {
                    text = "밝은 색의 치마";
                }
                *//////////////////////////////////////////////////
                if(text.contains("masisneunmilk")) {
                    text = "밝은 블라우스";
                }
                else if(text.contains("mikis")){
                    text = "밝은 블라우스";
                }
                else if(text.contains("mongshell")){
                    text = "밝은 블라우스";
                }
                else if(text.contains("ansungtangmyun")){
                    text = "밝은 블라우스";
                }
                else if(text.contains("cola")){
                    text = "밝은 블라우스";
                }
                else if(text.contains("chocosonge")){
                    text = "밝은 블라우스";
                }
                else if(text.contains("chocoemong")){
                    text = "밝은 블라우스";
                }
                else if(text.contains("chilseong")){
                    text = "밝은 색의 치마";
                }
                else if(text.contains("chicchoc")){
                    text = "밝은 색의 치마";
                }
                else if(text.contains("buldarkbog")){
                    text = "밝은 색의 치마";
                }
                else if(text.contains("bananamilk")){
                    text = "어두운 색의 치마";
                }else if(text.contains("trevi")){
                    text = "어두운 색의 치마";
                }
                else if(text.contains("zzawang")){
                    text = "어두운 색의 치마";
                }
                else if(text.contains("shinramen")){
                    text = "정갈한 느낌의 셔츠";
                }
                else if(text.contains("sprite")){
                    text = "정갈한 느낌의 셔츠";
                }
                else if(text.contains("toreta")){
                    text = "어두운 색의 바지";
                }
                else if(text.contains("welchis")){
                    text = "어두운 색의 바지";
                }
                else if(text.contains("seoulmilk")){
                    text = "무거운 느낌의 반팔티";
                }
                else if(text.contains("pocarisweat")){
                    text = "무거운 느낌의 반팔티";
                }
                else if(text.contains("pocachip")){
                    text = "어두운 색의 블라우스";
                }
                else if(text.contains("lottebaebaero")){
                    text = "가벼운 느낌의 바지";
                }
                else if(text.contains("jorypong")){
                    text = "밝은색 계열의 패딩";
                }
                else if(text.contains("hushmilk")){
                    text = "밝은색 계열의 패딩";
                }
                else if(text.contains("homrunball")){
                    text = "깔끔한 색의 반팔티";
                }
                else if(text.contains("galbae")){
                    text = "깔끔한 색의 반팔티";
                }
                else if(text.contains("demisoda")){
                    text = "깔끔한 색의 반팔티";
                }
                else if(text.contains("dejawa")){
                    text = "깔끔한 색의 반팔티";
                }

                tts.speak(text+"로 인식했습니다.", TextToSpeech.QUEUE_FLUSH, null);

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

