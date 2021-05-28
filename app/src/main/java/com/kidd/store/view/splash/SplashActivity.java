package com.kidd.store.view.splash;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ProgressBar;

import com.kidd.store.MainActivity;
import com.kidd.store.R;
import com.kidd.store.presenter.splash.GetAppVersionPresenter;
import com.kidd.store.presenter.splash.GetAppVersionPresenterImpl;

public class SplashActivity extends AppCompatActivity implements SplashView {

    ProgressBar progressBar;
    private boolean isRequestSuccess = false;
    private AsyncTask<Void, Integer, Void> splashTimer;
    private GetAppVersionPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        presenter = new GetAppVersionPresenterImpl(this, this);
        progressBar = findViewById(R.id.progress);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        }, 2000);
    }

    @Override
    public void startProgress() {
//        splashTimer = new SplashTimer().execute();



    }

    @Override
    public boolean isInProgress() {
        return splashTimer != null && !splashTimer.isCancelled();
    }

    @Override
    public void completeLoading() {
        isRequestSuccess = true;
    }

    @Override
    public void onServerMaintance() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.server_maintain)
                .setMessage(R.string.server_maintain_message)
                .setCancelable(false)
                .setPositiveButton(R.string.exit, (dialogInterface, i) -> {
                    finish();
                })
                .show();
    }

    @Override
    protected void onStart() {
        super.onStart();
//        presenter.startApp();
    }

    @Override
    public void onServerError() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.error_happened)
                .setMessage(R.string.error_message)
                .setCancelable(false)
                .setPositiveButton(R.string.retry, (dialogInterface, i) -> {
                    presenter.startApp();
                })
                .show();
    }

    @Override
    public void onNetworkError() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.no_internet_connection)
                .setMessage(R.string.please_make_sure_the_intenet_connection_is_enable)
                .setCancelable(false)
                .setPositiveButton(R.string.retry, (dialogInterface, i) -> {
                    presenter.startApp();
                }).show();
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
//            try {
//                while (progress <= 80) {
//                    progress++;
//                    publishProgress(progress);
//                    Thread.sleep(millisPerProgress);
//                }
//                while (!isRequestSuccess);
//                while (progress <= 100) {
//                    progress++;
//                    publishProgress(progress);
//                    Thread.sleep(millisPerProgress);
//                }
//            } catch (InterruptedException ignored) {
//
//            }
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
