package kr.ac.jbnu.se.JBNU_Expedition;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private final int SPLASH_DISPLAY_TIME = 3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        delaySplashScreen(SPLASH_DISPLAY_TIME);
    }

    // 지정한 시간(초) 만큼 Splash 화면을 보여주고 다음 화면(SignInActivity)으로 넘어감
    private void delaySplashScreen(int sec) {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
                startActivity(intent);
                finish();
            }
        }, 1000 * sec);
    }

}
