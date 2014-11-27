package com.password.manager.classes;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by Clemens on 03.09.2014.
 */

/// one of the old classes
@Root(name="user")
public class User {
    @Element(name="name")
    public String username;
    @Element(name="password")
    public String password;
    @Element(name="path")
    public String path;

    public User(@Element(name="name")String username,
                @Element(name="password")String password,
                @Element(name="path")String path) {
        this.username = username;
        this.password = password;
        this.path = path;
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
}
