package com.password.manager.core.brut.force.prevention;


import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;

import com.password.manager.R;
import com.password.manager.core.Logger;
import com.password.manager.core.User;
import com.password.manager.core.handler.AESHandler;
import com.password.manager.core.handler.PasswordListHandler;
import com.password.manager.core.handler.PathHandler;

import java.io.File;
import java.util.HashMap;

/**
 * Created by Clemens on 18.01.2015.
 */
public class LockService extends Service {
    private Looper mServiceLooper;
    private ServiceHandler mServiceHandler;

    private HashMap<User, Integer> blockedUsers;

    public static LockService LOCKSERVICE;

    private final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            Bundle b = msg.getData();
            for (User user : blockedUsers.keySet()) {
                if (b.getInt(user.getUsername()) != 0) {
                    int i = b.getInt(user.getUsername());
                    long lockTime = LockTime.getTime(i) * 60 * 1000;
                    long endTime = System.currentTimeMillis() + lockTime;
                    while(System.currentTimeMillis() < endTime){
                        synchronized (this) {
                            try {
                                user.wait(endTime - System.currentTimeMillis());
                            } catch (Exception e) {
                            }
                        }
                    }

                    int count = blockedUsers.get(user) + 1;
                    blockedUsers.put(user, count);
                }
            }
        }
    }

    @Override
    public void onCreate() {
        LOCKSERVICE = this;

        HandlerThread thread = new HandlerThread("ServiceStartArguments", Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();

        mServiceLooper = thread.getLooper();
        mServiceHandler = new ServiceHandler(mServiceLooper);
        blockedUsers = new HashMap<>();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Message msg = mServiceHandler.obtainMessage();
        msg.arg1 = startId;
        mServiceHandler.sendMessage(msg);

        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        Logger.show("service done", this);
    }

    public boolean loginUser(String username, String password) throws Exception {

        if (username.length() == 0 && password.length() == 0)
            throw new Exception(Logger.getResourceString(R.string.error_no_user_input_and_password, getApplicationContext()));
        if (username.length() == 0)
            throw new Exception(Logger.getResourceString(R.string.error_no_user_input, getApplicationContext()));
        if (password.length() == 0)
            throw new Exception(Logger.getResourceString(R.string.error_no_password, getApplicationContext()));

        String path = PathHandler.PathToUsers + File.separator + username + ".xml";
        if (!new File(path).exists()) {
            throw new Exception(Logger.getResourceString(R.string.error_user_doesnt_exist, getApplicationContext()));
        }

        String user_file = PathHandler.readFile(path);
        User user = User.getInstance(user_file);
        String en_pas = AESHandler.encrypt(password, password).replace("\n", "");

        Integer i = blockedUsers.get(user);

        if (i != null) {
            if (LockTry.isBlock(i.intValue())) {
                Bundle b = new Bundle();
                b.putInt(user.getUsername(), blockedUsers.get(user));
                Message m = new Message();
                m.setData(b);
                if (!user.getPassword().equals(en_pas)) {
                    mServiceHandler.sendMessage(m);
                }

                throw new Exception(Logger.getResourceString(R.string.error_user_blocked, getApplicationContext()));
            }
        }

        if (!user.getPassword().equals(en_pas)) {
            if (blockedUsers.containsKey(user)) {
                int count = blockedUsers.get(user) + 1;
                blockedUsers.put(user, count);
            } else {
                blockedUsers.put(user.copy(), 1);
            }


            throw new Exception(Logger.getResourceString(R.string.error_wrong_password, getApplicationContext()));
        } else if (en_pas.length() == 0) {
            throw new Exception(Logger.getResourceString(R.string.error_no_password, getApplicationContext()));
        } else {
            user.setPassword(password.toString());

            String key_file = PathHandler.readFile(PathHandler.PathToKeys + File.separator + username + ".xml");
            if (key_file.isEmpty()) {
                PasswordListHandler passwordListHandler = PasswordListHandler.getInstance();
            } else {
                String de_key_file = AESHandler.decrypt(key_file, user.getPassword());
                PasswordListHandler passwordListHandler = PasswordListHandler.createPasswordListHandlerFromString(de_key_file);
            }

            blockedUsers.remove(user);

            return true;
        }
    }
}