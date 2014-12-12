package com.password.manager.core;

import com.password.manager.handler.SerializerHandler;
import com.password.manager.handler.PathHandler;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by Clemens on 03.12.2014.
 */

@Root(name = "settings")
public class Settings {
    private static Settings settings;
    @Element(name = "saveLogin")
    private boolean saveLogin;
    @Element(name = "rememberedUserName")
    private String rememberedUserName;

    public boolean isSaveLogin() {
        return saveLogin;
    }

    public void setSaveLogin(boolean saveLogin) {
        this.saveLogin = saveLogin;
    }

    public String getRememberedUserName() {
        return rememberedUserName;
    }

    public void setRememberedUserName(String rememberedUserName) {
        this.rememberedUserName = rememberedUserName;
    }

    public Settings(@Element(name = "rememberedUserName") String rememberedUserName,
                    @Element(name = "saveLogin") boolean saveLogin) {
        this.rememberedUserName = rememberedUserName;
        this.saveLogin = saveLogin;
    }

    public Settings() {
        this.rememberedUserName = "";
        this.saveLogin = false;
    }

    public static Settings getInstance() throws Exception {
        if (settings == null) {
            if(PathHandler.fileExists(PathHandler.PathToSettingsFile)){
                String settingsFile = PathHandler.readFile(PathHandler.PathToSettingsFile);

                try {
                    settings = SerializerHandler.deserialize(settingsFile, Settings.class);
                } catch (Exception e) {
                    settings = new Settings();
                }

            }
            else {
                settings = new Settings();
                String ser = SerializerHandler.serialize(settings);
                PathHandler.writeFile(PathHandler.PathToSettingsFile, ser);
            }
        }

        return settings;
    }

    public void save() throws Exception {
        String ser = SerializerHandler.serialize(settings);
        PathHandler.writeFile(PathHandler.PathToSettingsFile, ser);
    }
}
