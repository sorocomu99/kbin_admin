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

public class CommonUtil {
    public static boolean isLocal(String profile){
        return StringUtils.hasText(profile) && ("local".equals(profile));
    }

    public static boolean isDev(String profile){
        return StringUtils.hasText(profile) && ("dev".equals(profile));
    }

    public static boolean isProd(String profile){
        return StringUtils.hasText(profile) && ("prod".equals(profile));
    }

    public static boolean isNotProd(String profile){
        return !isProd(profile);
    }
}