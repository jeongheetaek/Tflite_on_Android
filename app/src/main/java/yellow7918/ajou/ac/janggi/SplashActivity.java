package yellow7918.ajou.ac.janggi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.speech.tts.TextToSpeech;

import java.util.Locale;

import static android.media.AudioManager.ERROR;

public class SplashActivity extends Activity {
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Vibrator vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        try {
            vibrator.vibrate(2000);
            Thread.sleep(2000);

            //vibrator.vibrate(new long[]{100, 1000, 100, 500, 100 ,500, 100 ,1000},0);
//            Thread.sleep(2000); //대기초 설정

        } catch (InterruptedException e){
            e.printStackTrace();
        }
        startActivity(new Intent(this,SelectActivity.class));
//        startActivity(new Intent(this,MainActivity.class));
        finish();
        }
    }
