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
public class Classifi_MainActivity3 extends AppCompatActivity {

    TextToSpeech tts;
    EditText inputtext;
    Button button;
    private MediaPlayer mp;


    //private static final String MODEL_PATH = "mobilenet_quant_v1_224.tflite`";
    //private static final String MODEL_PATH = "tflite_shop_final.tflite";
    private static final String MODEL_PATH = "shop.tflite";
    //private static final String LABEL_PATH = "shop_labels_final.txt";
    private static final String LABEL_PATH = "shop.txt";

    private static final int INPUT_SIZE = 224;

    private Classifier classifier;

    private Executor executor = Executors.newSingleThreadExecutor();
    // private TextView textViewResult;
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
                if(text.contains("burger")) {
                    text = "버거킹";
                }else if(text.contains("cu")) {
                    text = "씨유 편의점";
                }else if(text.contains("ediya")) {
                    text = "이디야";
                }else if(text.contains("gs")) {
                    text = "지에스 편의점";
                }else if(text.contains("lotte")) {
                    text = "롯데리아";
                }else if(text.contains("seven")) {
                    text = "세븐일레븐";
                }else if(text.contains("star")) {
                    text = "스타벅스";
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
                //.setText(results.toString());
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
