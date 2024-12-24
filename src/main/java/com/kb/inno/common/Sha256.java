/**
 * 파일명     : Sha256.java
 * 화면명     : 비밀번호 암호화
 * 설명       : 관리자 로그인 및 등록 수정시 비밀번호를 암호화 한다.
 * 최초개발일 : 2024.10.31
 * 최초개발자 : 이훈희
 * ==========================================================
 *   수정일            수정자           설명
 * ==========================================================
 *
 */
package com.kb.inno.common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Sha256 {
    /**
     * SHA-256으로 해싱하는 메소드
     *
     * @param text
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String encrypt(String text) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(text.getBytes());

        return bytesToHex(md.digest());
    }

    /**
     * 바이트를 헥스값으로 변환한다.
     *
     * @param bytes
     * @return
     */
    private static String bytesToHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(String.format("%02x", b));
        }

        return builder.toString();
    }
}
