package com.password.manager;


import android.util.Xml;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;

@Root(name="PasswordFile")
class Password {
    @Element(name="header")
    private String header;
    @Element(name="username")
    private String username;
    @Element(name="password")
    private String password;

    public Password(@Element(name="header")String header, @Element(name="username")String username, @Element(name="password")String password) {
        this.header = header;
        this.username = username;
        this.password = password;
    }

    public String serialize() throws Exception {
        String s = "";

        Serializer serializer = new Persister();
        StringWriter writer = new StringWriter();
        writer.append(s);
        serializer.write(this, writer);

        return writer.toString();
    }

    public static Password deserialize(String data) throws Exception {
        Serializer serializer = new Persister();



        return null;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
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
}
