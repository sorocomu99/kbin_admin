/**
 * 파일명     : AdminLoginService.java
 * 화면명     : 로그인
 * 설명       : 관리자 화면 접속시 로그인 및 세션 생성
 *              로그아웃 세션 삭제
 * 최초개발일 : 2024.10.31
 * 최초개발자 : 이훈희
 * ==========================================================
 *   수정일            수정자           설명
 * ==========================================================
 *
 */
package com.kb.inno.admin.Service;

import com.kb.inno.admin.DAO.AdminLoginDAO;
import com.kb.inno.admin.DTO.AdminLoginDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminLoginService {
    @Autowired
    AdminLoginDAO adminLoginDAO;

    /**
     * 입력한 아이디를 가지고 정보가 존재하는지 확인한다.
     * param mngrId
     * @return AdminLoginDTO
     */
    public AdminLoginDTO adminSelectOne(String mngrId) {
        AdminLoginDTO adminLoginDTO = adminLoginDAO.adminSelectOne(mngrId);

        if (adminLoginDTO == null) {
            return null;
        }

        return adminLoginDTO;
    }
}
