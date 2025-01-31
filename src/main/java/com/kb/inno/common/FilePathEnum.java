package com.kb.inno.common;

import java.io.File;

public enum FilePathEnum {
    LOCAL_UPLOAD_ROOT("D:" + File.separator + "fsfile"),
    LOCAL_UPLOAD_FILE("dev_kbinnovation" + File.separator),

    LOCAL_DEV_UPLOAD_ROOT("D:" + File.separator + "fsfile"),
    LOCAL_DEV_UPLOAD_FILE("dev_kbinnovation" + File.separator),

    DEV_UPLOAD_ROOT(File.separator + "fsfile"),
    DEV_UPLOAD_FILE("dev_kbinnovation" + File.separator),

    PROD_UPLOAD_ROOT(File.separator + "fsfile"),
    PROD_UPLOAD_FILE("kbinnovation" + File.separator),

    LOCAL_SUMMER_NOTE_IMAGES(File.separator + "kbinnovationhub_devadm" + File.separator + "summernoteimages" + File.separator),

    PROD_SUMMER_NOTE_IMAGES(File.separator + "kbinnovationhub_adm" + File.separator + "summernoteimages" + File.separator),

    ;

    private final String path;

    FilePathEnum(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}



















