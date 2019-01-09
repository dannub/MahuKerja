package com.example.nurulmadihah.mahukerja.Save;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.nurulmadihah.mahukerja.object.Job;
import com.example.nurulmadihah.mahukerja.object.User;
import com.google.gson.Gson;

public class Save {
    public static void save(Context ctx, String name, String value, User user) {
        SharedPreferences s = ctx.getSharedPreferences("MahuKerja", Context.MODE_PRIVATE);
        SharedPreferences.Editor edt = s.edit();
        edt.putString(name, value);
        Gson gson = new Gson();
        String json = gson.toJson(user);
        edt.putString("Akun", json);
        edt.commit();
        edt.apply();
    }

    public static String readlogin(Context ctx, String name, String defaultvalue) {
        SharedPreferences s = ctx.getSharedPreferences("MahuKerja", Context.MODE_PRIVATE);
        return s.getString(name, defaultvalue);
    }

    public static User readakun(Context ctx, String name) {
        SharedPreferences s = ctx.getSharedPreferences("MahuKerja", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = s.getString(name, "");
        User user = gson.fromJson(json, User.class);
        return user;
    }

    public static Job readjob(Context ctx) {
        SharedPreferences s = ctx.getSharedPreferences("MahuKerja", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = s.getString("Job", "");
        Job job = gson.fromJson(json, Job.class);
        return job;
    }


}