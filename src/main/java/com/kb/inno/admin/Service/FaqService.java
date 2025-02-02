/**
 * 파일명     : FaqService.java
 * 화면명     : FAQ 관리
 * 설명       : FAQ 조회 및 등록, 수정, 삭제 처리
 * 최초개발일 : 2024.11.06
 * 최초개발자 : 양윤지
 * ==========================================================
 *   수정일            수정자           설명
 * ==========================================================
 */
package com.kb.inno.admin.Service;

import com.kb.inno.admin.DAO.FaqDAO;
import com.kb.inno.admin.DTO.CategoryDTO;
import com.kb.inno.admin.DTO.FaqDTO;
import com.kb.inno.admin.DTO.SearchDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FaqService {

    // DAQ 연결
    private final FaqDAO faqDAO;

    // FAQ 리스트 조회
    public void selectList(int menuId, int ctgry, Model model, String type, String keyword, int page) {
        // Search DTO에 담기
        SearchDTO search = new SearchDTO();
        search.setType(type);
        search.setKeyword(keyword);
        search.setCtgry(ctgry);

        // 페이지의 전체 글 갯수
        int allCount = faqDAO.selectPageCount(search);
        
        // 한 페이지당 글 갯수
        int pageLetter = 10;

        // 전체 글 갯수 / 한 페이지에 나올 글 갯수
        int repeat = allCount / pageLetter;
        // 나머지가 0이 아니면
        if(allCount % pageLetter != 0){
            // 더하기
            repeat += 1;
        }
        
        // repeat이 0이면
        if(repeat == 0) {
            repeat = 1;
        }

        // 만약 가져온 페이지가 repeat 보다 크다면
        if(repeat < page) {
            page = repeat;
        }

        // 만약 가져온 페이지가 0이라면
        if(page < 1) {
            page = 1;
        }

        // 끝 페이지
        int end = page * pageLetter;
        search.setEnd(end);
        // 시작 페이지
        int start = end + 1 - pageLetter;

        search.setStart(start);

        // 리스트 조회
        List<FaqDTO> selectList = faqDAO.selectList(search);

        model.addAttribute("repeat", repeat);
        model.addAttribute("currentPage", page);
        model.addAttribute("selectList", selectList);
        model.addAttribute("menuId", menuId);
        model.addAttribute("ctgry", ctgry);
    }

    // FAQ 등록
    public int insert(FaqDTO faqDTO, int loginId) {
        // 로그인 한 아이디 대입
        faqDTO.setFrst_rgtr(loginId);
        faqDTO.setLast_mdfr(loginId);

        return faqDAO.insert(faqDTO);
    }

    // FAQ 상세 조회
    public FaqDTO select(int faq_sn) {
        return faqDAO.select(faq_sn);
    }
    
    // FAQ 수정
    public int update(FaqDTO faqDTO, int loginId) {
        // 로그인 한 아이디 대입
        faqDTO.setLast_mdfr(loginId);

        return faqDAO.update(faqDTO);
    }

    // FAQ 삭제
    public void delete(int faq_sn) {
        faqDAO.delete(faq_sn);
    }

    // 카테고리 조회
    public Map<String, Object> selectCategory(int ctgry) {
        return faqDAO.selectCategory(ctgry);
    }

    public void getPreview(FaqDTO faqDTO, Model model) {
        int ctgry = Integer.parseInt(faqDTO.getFaq_ctgry_sn());


        // 정렬 순서에 따라 페이징 처리 및 여러 기능 추가가 필요 함
        // 차치하고 입력 값 1건만 표출하고 있음
        // 아래 코드는 향후 KB측 요청시 기본 조회 + 페이지 필터 기능이 추가 돼야 함

        // Search DTO에 담기
//        SearchDTO search = new SearchDTO();
//        search.setType("");
//        search.setKeyword("");
//        search.setCtgry(ctgry);
//
//        // 페이지의 전체 글 갯수
//        int allCount = faqDAO.selectPreviewPageCount(search);
//
//        // 한 페이지당 글 갯수
//        int pageLetter = 10;
//
//        // 전체 글 갯수 / 한 페이지에 나올 글 갯수
//        int repeat = allCount / pageLetter;
//        // 나머지가 0이 아니면
//        if(allCount % pageLetter != 0){
//            // 더하기
//            repeat += 1;
//        }
//
//        // repeat이 0이면
//        if(repeat == 0) {
//            repeat = 1;
//        }
//
//        int page = 1;
//
//        // 만약 가져온 페이지가 repeat 보다 크다면
//        if(repeat < page) {
//            page = repeat;
//        }
//
//        // 만약 가져온 페이지가 0이라면
//        if(page < 1) {
//            page = 1;
//        }
//
//        // 끝 페이지
//        int end = page * pageLetter;
//        search.setEnd(end);
//        // 시작 페이지
//        int start = end + 1 - pageLetter;
//
//        search.setStart(start);
//
//        search.setFaq_sn(faqDTO.getFaq_sn());
//
//        // 리스트 조회
//        List<FaqDTO> selectList = faqDAO.selectPreviewList(search);

        List<CategoryDTO> categoryList = faqDAO.selectCategoryAll();

        faqDTO.setFaq_id("tab".concat(String.valueOf(faqDTO.getFaq_sn())));
        faqDTO.setFaq_sec("tab".concat(String.valueOf(faqDTO.getFaq_sn()).concat("-section")));

        for (CategoryDTO categoryDTO : categoryList) {
            if (categoryDTO.getCtgry_sn() == ctgry) {
                faqDTO.setCtgry_nm(categoryDTO.getCtgry_nm());
            }
        }
        List<FaqDTO> selectList = new ArrayList<>();
        selectList.add(faqDTO);

//        model.addAttribute("repeat", repeat);
//        model.addAttribute("currentPage", page);
        model.addAttribute("selectList", selectList);
        model.addAttribute("menuId", 503000);
        model.addAttribute("ctgry", ctgry);
        model.addAttribute("selectCategory", categoryList);
        model.addAttribute("faq_sn", "tab".concat(String.valueOf(faqDTO.getFaq_sn())));
    }
}
