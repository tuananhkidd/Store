package com.kidd.store.common;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.kidd.store.MainActivity;
import com.kidd.store.models.response.HeaderProfile;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by TuanAnhKid on 3/30/2018.
 */

public class Utils {
    public static void setSharePreferenceValues(Context context, String key, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("bookstore", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getSharePreferenceValues(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("bookstore", Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, null);
    }

    public static void saveHeaderProfile(Context context, HeaderProfile headerProfile) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();

        if (headerProfile == null) {
            editor.putString(Constants.PRE_AVATAR_URL, null);
            editor.putString(Constants.PRE_FULL_NAME, null);
            editor.putString(Constants.PRE_EMAIL, null);
        } else {
            editor.putString(Constants.PRE_AVATAR_URL, headerProfile.getAvatarUrl());
            editor.putString(Constants.PRE_FULL_NAME, headerProfile.getFullName());
            editor.putString(Constants.PRE_EMAIL, headerProfile.getEmail());
        }
        editor.apply();
    }

    public static HeaderProfile getHeaderProfile(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        HeaderProfile headerProfile;
        headerProfile = new HeaderProfile();
        headerProfile.setAvatarUrl(preferences.getString(Constants.PRE_AVATAR_URL, null));
        headerProfile.setFullName(preferences.getString(Constants.PRE_FULL_NAME, null));
        headerProfile.setEmail(preferences.getString(Constants.PRE_EMAIL, null));
        return headerProfile;
    }
    public static void dialogShowDate(Activity activity, String title, DatePickerDialog.OnDateSetListener dateChangedListener) {
//        Calendar now = Calendar.getInstance();
//        DatePickerDialog dpd = DatePickerDialog.Builder(
//                dateChangedListener,
//                now.get(Calendar.YEAR),
//                now.get(Calendar.MONTH),
//                now.get(Calendar.DAY_OF_MONTH)
//        );
//        dpd.showYearPickerFirst(true);
//        dpd.dismissOnPause(true);
//        dpd.vibrate(true);
//        dpd.setTitle(title);
//        dpd.setAccentColor(Color.parseColor("#4CAF50"));
//        dpd.show(activity.getFragmentManager(), "Datepickerdialog");
    }

    public static String getDateFromMilliseconds(long milliSeconds) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

    public static boolean checkNetwork(Context context) {
        boolean available = false;
        ConnectivityManager cn = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cn.getActiveNetworkInfo();
        if (info != null && info.isAvailable() && info.isConnected()) {
            available = true;
        }
        return available;
    }

    public static boolean isEmailValid(String target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public static void rateApp(Context context) {
        Uri uri = Uri.parse("market://details?id=" + context.getPackageName());
//        Uri uri = Uri.parse("market://details?id=" + "code.android.ngocthai.place" + "&hl=vi");
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            context.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            context.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + context.getPackageName())));
        }
    }

}
