package com.password.manager.core;

import com.password.manager.core.handler.AESHandler;
import com.password.manager.core.handler.PathHandler;
import com.password.manager.core.handler.SerializerHandler;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.File;

/**
 * Created by Clemens on 03.09.2014.
 */

/// one of the old classes
/// but modified a lot...
@Root(name = "user")
public class User {
    private static User user;
    @Element(name = "name")
    public String username;
    @Element(name = "password")
    public String password;
    @Element(name = "path")
    public String path;

    private int lockTime;

    public User(@Element(name = "name") String username,
                @Element(name = "password") String password,
                @Element(name = "path") String path) {
        this.username = username;
        this.password = password;
        this.path = path;
        lockTime = 0;
    }

    public static User getInstance(String user_file) throws Exception {
        if (user == null && user_file != null) {
            user = SerializerHandler.deserialize(user_file, User.class);
        }

        return user;
    }

    public static boolean isLoggedIn() {
        return user != null;
    }

    public static void logout() {
        if (!isLoggedIn()) return;
        user.password = null;
        user.username = null;
        user.path = null;
        user = null;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getLockTime() {
        return lockTime;
    }

    public void setLockTime(int lockTime) {
        this.lockTime = lockTime;
    }

    public boolean isLocked() {
        return lockTime > 0;
    }

    public void save() throws Exception {
        User user = new User(this.username, this.password, this.path);
        user.setPassword(AESHandler.encrypt(user.getPassword(), user.getPassword()).replace("\n", ""));
        String ser = SerializerHandler.serialize(user);
        PathHandler.writeFile(PathHandler.PathToUsers + File.separator + user.username + ".xml", ser);
    }
}
