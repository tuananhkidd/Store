package com.kidd.store.view;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.ProgressBar;

import com.kidd.store.MainActivity;
import com.kidd.store.R;
import com.kidd.store.common.Constants;
import com.kidd.store.common.Utils;
import com.kidd.store.view.account.login.LoginActivity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SplashActivity extends AppCompatActivity {

    ProgressBar progressBar;
    int current = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        progressBar = findViewById(R.id.progress);

        new SplashTimer().execute();
    }

    private class SplashTimer extends AsyncTask<Void, Integer, Void> {
        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0]);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            long millisPerProgress = 2500 / 100;
            int progress = 0;
            try {
                while (progress <= 100) {
                    progress++;
                    publishProgress(progress);
                    Thread.sleep(millisPerProgress);
                }
            } catch (InterruptedException ignored) {

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            // if (!Constants.LOGIN_TRUE.equals(Utils.getSharePreferenceValues(SplashActivity.this, Constants.STATUS_LOGIN))) {
//            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            //   } else {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            //  }
            finish();
        }
    }
}
