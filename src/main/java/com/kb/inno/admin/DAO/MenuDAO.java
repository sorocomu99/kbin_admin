/**
 * 파일명     : MenuDAO.java
 * 화면명     : 메뉴 관리
 * 설명       : 메뉴 조회 및 등록, 수정, 삭제 처리
 * 최초개발일 : 2024.11.04
 * 최초개발자 : 양윤지
 * ==========================================================
 *   수정일            수정자           설명
 * ==========================================================
 */
package com.kb.inno.admin.DAO;

import com.kb.inno.admin.DTO.MenuDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MenuDAO {
    // 메뉴 리스트 조회
    List<MenuDTO> selectList();
    // 메뉴 상세 조회
    MenuDTO select(int menu_sn);
    // 메뉴 수정
    int update(MenuDTO menuDTO);
    // 메뉴 리스트 조회(공통 메뉴)
    List<MenuDTO> selectListConfig();
}
