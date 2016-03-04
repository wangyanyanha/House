package com.fangdichan.house.atys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.fangdichan.house.R;

/**
 * Created by Smarft on 2015/8/20.
 */
public class SplashActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                Intent intent = new Intent(SplashActivity.this, LoginActivity2.class);
                startActivity(intent);
                finish();
//				overridePendingTransition(R.anim.fade, R.anim.hold);
            }
        }, 2000);
    }
}
