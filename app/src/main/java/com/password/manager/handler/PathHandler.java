package com.password.manager.handler;

import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Clemens on 27.11.2014.
 */
public class PathHandler {

    public static String Path = Environment.getExternalStorageDirectory() + File.separator + "Android" + File.separator + "data" + File.separator + "com.password.manager";
    public static String PathToUsers = Path + File.separator + "USERS";
    public static String PathToKeys = Path + File.separator + "KEYS";
    public static String PathToSettingsFile = Path + File.separator + "settings.xml";

    public static boolean fileExists(String path)
    {
        return new File(path).exists();
    }


    public static String readFile(String path) throws Exception {
        String file = "";
        File f = new File(path);

        if (!f.exists()) throw new IOException("No such file!");

        BufferedReader reader = new BufferedReader(new FileReader(f));

        String line = "";
        while ((line = reader.readLine()) != null) {
            file += line;
        }

        return file;
    }

    public static void writeFile(String file, String content) throws Exception {
        File f = new File(file);

        if (!f.exists()) {
            f.createNewFile();
        }

        OutputStream fo = new FileOutputStream(file);
        fo.write(content.getBytes());
        fo.close();
    }

    public static void createPMDirectory() {
        File f = new File(Path);
        if (!f.exists()) {
            f.mkdir();
        }
        f = new File(PathToKeys);
        if (!f.exists()) {
            f.mkdir();
        }
        f = new File(PathToUsers);
        if (!f.exists()) {
            f.mkdir();
        }
    }
}
