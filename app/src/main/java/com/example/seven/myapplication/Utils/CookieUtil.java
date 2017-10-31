package com.example.seven.myapplication.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.net.CookieManager;
import java.net.CookiePolicy;

import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;

/**
 * Created by daichen on 2017/10/30.
 */

public class CookieUtil {
    public static final String ISLOGINED = "islogined";
    public static final String COOKIE = "cookie";

    public static void saveCookiePreference(Context context, String value) {
        SharedPreferences preference = context.getSharedPreferences(ISLOGINED,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preference.edit();
        editor.putString(COOKIE, value);
        editor.apply();

    }


    public static void setCookies(OkHttpClient.Builder builder) {
        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        builder.cookieJar(new JavaNetCookieJar(cookieManager));
    }
}
