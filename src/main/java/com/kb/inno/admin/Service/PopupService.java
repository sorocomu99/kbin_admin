/**
 * 파일명     : PopupService.java
 * 화면명     : 팝업 관리
 * 설명       : 팝업 조회 및 등록, 수정, 삭제 처리
 * 최초개발일 : 2024.10.24
 * 최초개발자 : 양윤지
 * ==========================================================
 *   수정일            수정자           설명
 * ==========================================================
 */
package com.kb.inno.admin.Service;

import com.kb.inno.admin.DAO.PopupDAO;
import com.kb.inno.admin.DTO.MenuDTO;
import com.kb.inno.admin.DTO.PopupDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PopupService {
    private final PopupDAO popupDAO;

    // 팝업 리스트 조회
    public void selectList(Model model, int page, int menuId) {
        // 페이지의 전체 글 갯수
        int allCount = popupDAO.selectPageCount();

        // 한 페이지당 글 갯수
        int pageLetter = 10;

        // 전체 글 갯수 / 한 페이지에 나올 글 갯수
        int repeat = allCount / pageLetter;

        // 나머지가 0이 아니면
        if(allCount % pageLetter != 0){
            // 더하기
            repeat += 1;
        }

        // 끝 페이지
        int end = page * pageLetter;

        // 시작 페이지
        int start = end + 1 - pageLetter;

        // 리스트 조회
        //List<PopupDTO> selectList = popupDAO.selectList(start, end);
        HashMap map = new HashMap();
        map.put("start", start);
        map.put("end", end);
        List<PopupDTO> selectList = popupDAO.selectList(map);

        model.addAttribute("repeat", repeat);
        model.addAttribute("currentPage", page);
        model.addAttribute("selectList", selectList);
        model.addAttribute("menuId", menuId);
    }
    
    // 팝업 등록
    public int insert(PopupDTO popupDTO, int loginId) {
        // 로그인 한 아이디 세팅
        popupDTO.setFrst_rgtr(loginId);
        popupDTO.setLast_mdfr(loginId);

        // 날짜에서 - 빼기
        String bngn_ymd = popupDTO.getBgng_ymd();
        bngn_ymd = bngn_ymd.replace("-", "");
        popupDTO.setBgng_ymd(bngn_ymd);

        String end_ymd = popupDTO.getEnd_ymd();
        end_ymd = end_ymd.replace("-", "");
        popupDTO.setEnd_ymd(end_ymd);

        // 시간에서 : 빼기
        String bgng_hr = popupDTO.getBgng_hr();
        bgng_hr = bgng_hr.replace(":", "");
        popupDTO.setBgng_hr(bgng_hr);

        String end_hr = popupDTO.getEnd_hr();
        end_hr = end_hr.replace(":", "");
        popupDTO.setEnd_hr(end_hr);

        return popupDAO.insert(popupDTO);
    }

    // 팝업 상세 조회
    public PopupDTO select(int popupId) {
        PopupDTO popupDTO = popupDAO.select(popupId);

        if(popupDTO == null) {
            return null;
        }

        return popupDTO;
    }

    // 팝업 수정
    public int update(PopupDTO popupDTO, int loginId) {
        // 로그인 한 아이디 세팅
        popupDTO.setLast_mdfr(loginId);

        // 날짜에서 - 빼기
        String bngn_ymd = popupDTO.getBgng_ymd();
        bngn_ymd = bngn_ymd.replace("-", "");
        popupDTO.setBgng_ymd(bngn_ymd);

        String end_ymd = popupDTO.getEnd_ymd();
        end_ymd = end_ymd.replace("-", "");
        popupDTO.setEnd_ymd(end_ymd);

        // 시간에서 : 빼기
        String bgng_hr = popupDTO.getBgng_hr();
        bgng_hr = bgng_hr.replace(":", "");
        popupDTO.setBgng_hr(bgng_hr);

        String end_hr = popupDTO.getEnd_hr();
        end_hr = end_hr.replace(":", "");
        popupDTO.setEnd_hr(end_hr);

        return popupDAO.update(popupDTO);
    }
    
    // 팝업 삭제
    public void delete(int popupId) {
        popupDAO.delete(popupId);
    }

    // 전체 조회(미리보기용)
    public List<PopupDTO> selectListAll(PopupDTO popupDTO) {
        int popup_sn = popupDTO.getPopup_sn();
        //return popupDAO.selectListAll(popup_sn);
        return popupDAO.selectListAll(popupDTO);
    }
}
