/**
 * 파일명     : StartersService.java
 * 화면명     : KB 스타터스 관리
 * 설명       : KB 스타터스 조회 및 등록, 수정, 삭제 처리
 * 최초개발일 : 2024.10.31
 * 최초개발자 : 양윤지
 * ==========================================================
 *   수정일            수정자           설명
 * ==========================================================
 */
package com.kb.inno.admin.Service;

import com.kb.inno.admin.DAO.StartersDAO;
import com.kb.inno.admin.DTO.StartersDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
@RequiredArgsConstructor
public class StartersService {

    // 서비스 연결
    private final StartersDAO startersDAO;

    // KB 스타터스 조회
    public StartersDTO select() {
        return startersDAO.select();
    }

    // KB 스타터스 등록
    public int insert(StartersDTO startersDTO, int loginId) {
        // 로그인 한 아이디 대입
        startersDTO.setFrst_rgtr(loginId);
        startersDTO.setLast_mdfr(loginId);

        return startersDAO.insert(startersDTO);
    }

    // KB 스타터스 수정
    public int update(StartersDTO startersDTO, int loginId) {
        // 로그인 한 아이디 대입
        startersDTO.setLast_mdfr(loginId);

        return startersDAO.update(startersDTO);
    }

    public void getPreview(StartersDTO startersDTO, Model model) {

    }
}
