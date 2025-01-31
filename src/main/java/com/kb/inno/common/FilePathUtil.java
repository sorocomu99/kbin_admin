/**
 * 파일명     : StringUtil.java
 * 화면명     : 문자열 공통함수
 * 설명       : 문자열에 관련된 공통함수
 * 최초개발일 : 2024.10.23
 * 최초개발자 : 이훈희
 * ==========================================================
 *   수정일            수정자           설명
 * ==========================================================
 *
 */
package com.kb.inno.common;

import org.springframework.util.StringUtils;

import java.nio.file.Path;
import java.nio.file.Paths;

public class FilePathUtil {
    private static Path getRootPath(String profile) {
        String rootPath = "";
        if(CommonUtil.isLocal(profile)) {
            rootPath = FilePathEnum.LOCAL_UPLOAD_ROOT.getPath();
        }else if(CommonUtil.isLocalDev(profile)) {
            rootPath = FilePathEnum.LOCAL_DEV_UPLOAD_ROOT.getPath();
        }else if(CommonUtil.isDev(profile)) {
            rootPath = FilePathEnum.DEV_UPLOAD_ROOT.getPath();
        }else if(CommonUtil.isProd(profile)) {
            rootPath = FilePathEnum.PROD_UPLOAD_ROOT.getPath();
        }
        return Paths.get(rootPath).toAbsolutePath().normalize();
    }

    private static String getFilePath(String profile) {
        if(CommonUtil.isLocal(profile)) {
            return FilePathEnum.LOCAL_UPLOAD_FILE.getPath();
        }else if(CommonUtil.isLocalDev(profile)) {
            return FilePathEnum.LOCAL_DEV_UPLOAD_FILE.getPath();
        }else if(CommonUtil.isDev(profile)) {
            return FilePathEnum.DEV_UPLOAD_FILE.getPath();
        }else if(CommonUtil.isProd(profile)) {
            return FilePathEnum.PROD_UPLOAD_FILE.getPath();
        }
        return "";
    }

    public static String getSavePath(String profile) {
        return getRootPath(profile).resolve(getFilePath(profile)).toString();
    }

    public static String getSummerNoteImagesPath() {
        if(CommonUtil.isProd(PropertiesValue.profilesActive)) {
            return FilePathEnum.PROD_SUMMER_NOTE_IMAGES.getPath();
        }else {
            return FilePathEnum.LOCAL_SUMMER_NOTE_IMAGES.getPath();
        }
    }
}