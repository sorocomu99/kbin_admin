package com.kb.inno.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PropertiesValue {

    public static String profilesActive;

    public static String staticPath;

    @Value("${spring.profiles.active}")
    public void setProfilesActive(String value) {
        profilesActive = value;
    }

    @Value("${staticpath}")
    public void setStaticPath(String value) {
        staticPath = value;
    }
}