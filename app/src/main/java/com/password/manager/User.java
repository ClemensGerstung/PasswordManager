package com.password.manager;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by Clemens on 03.09.2014.
 */

/// one of the old classes
@Root(name="user")
public class User {
    @Element(name="name")
    public static String username;
    @Element(name="password")
    public static String password;
    @Element(name="path")
    public static String path;
}
