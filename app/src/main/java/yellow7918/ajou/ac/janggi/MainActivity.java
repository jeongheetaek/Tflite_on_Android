package yellow7918.ajou.ac.janggi;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fm;
    private BottomNavigationView bottomNavigation;
    boolean doubleBackToExitPressedOnce = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = new MainFragment();
            fm.beginTransaction().add(R.id.fragment_container, fragment).commit();
        }

        bottomNavigation = findViewById(R.id.bottomNavigation);

        bottomNavigation.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.action_home:
                    fm.beginTransaction().replace(R.id.fragment_container, new MainFragment()).commit();
                    return true;
                case R.id.action_search:
                    fm.beginTransaction().replace(R.id.fragment_container, new SearchFragment()).commit();
                    return true;
                case R.id.action_recommend:
                    fm.beginTransaction().replace(R.id.fragment_container, new RecommendFragment()).commit();
                    return true;
                case R.id.action_alarm:
                    fm.beginTransaction().replace(R.id.fragment_container, new AlarmFragment()).commit();
                    return true;
                case R.id.action_setting:
                    fm.beginTransaction().replace(R.id.fragment_container, new SettingFragment()).commit();
                    return true;
            }
            return false;
        });
        alarm();
    }


    private void alarm() {

        NotificationManager notificationManager= (NotificationManager)MainActivity.this.getSystemService(MainActivity.this.NOTIFICATION_SERVICE);
        Intent intent1 = new Intent(MainActivity.this.getApplicationContext(),MainActivity.class); //인텐트 생성.

        Notification.Builder builder = new Notification.Builder(getApplicationContext());
        intent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP| Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingNotificationIntent = PendingIntent.getActivity( MainActivity.this,0, intent1,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setSmallIcon(R.drawable.icon).setTicker("아이리스 알림").setWhen(System.currentTimeMillis())
                .setNumber(1).setContentTitle("아이리스 맞춤공고 알림").setContentText("새로운 맞춤공고가 도착했습니다")
                .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE).setContentIntent(pendingNotificationIntent).setAutoCancel(true).setOngoing(true);

        notificationManager.notify(1, builder.build()); // Notification send



        new AlertDialog.Builder(MainActivity.this)

                .setTitle("아이리스 알람")
                .setMessage("새로운 맞춤공고가 도착했습니다")
                .setNegativeButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                })
                .show();


    }


    @Override
    public void onBackPressed() {
        if (fm.getBackStackEntryCount() == 0) {

            if (doubleBackToExitPressedOnce) {
                finish();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "'뒤로' 버튼을 한번 더 누르면 정보 찾기가 종료됩니다.", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        } else {
            super.onBackPressed();
        }
    }

    public void changeFragment(Fragment fragment) {
        fm.beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
    }
}


