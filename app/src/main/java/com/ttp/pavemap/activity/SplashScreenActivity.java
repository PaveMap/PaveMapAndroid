package com.ttp.pavemap.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;

import com.ttp.pavemap.R;

/**
 * Created by Thai on 10/5/2014.
 */
public class SplashScreenActivity extends FragmentActivity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        // printf hashkey for facebooksdk
//        try {
//            PackageInfo info = getPackageManager().getPackageInfo("com.ttp.pavemap", PackageManager.GET_SIGNATURES);
//            for (Signature signature : info.signatures) {
//                MessageDigest md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                Log.d("qwerty", Base64.encodeToString(md.digest(), Base64.DEFAULT));
//            }
//        } catch (PackageManager.NameNotFoundException e) {
//        } catch
//                (NoSuchAlgorithmException e) {
//        }
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
