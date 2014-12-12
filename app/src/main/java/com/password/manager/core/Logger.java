package com.password.manager.core;

import android.content.Context;
import android.widget.Toast;

import com.password.manager.R;

/**
 * Created by Clemens on 12.12.2014.
 */
public class Logger {
    public static void show(String message, Context context){
        Toast.makeText(context, getResourceString(R.string.error, context) + " " + message, Toast.LENGTH_LONG).show();
    }

    public static void show(int id, Context context){
        Toast.makeText(context, getResourceString(R.string.error, context) + " " + getResourceString(id, context), Toast.LENGTH_LONG).show();
    }

    public static String getResourceString(int id, Context context){
        return context.getResources().getString(id);
    }
}
