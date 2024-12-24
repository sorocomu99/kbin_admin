package com.kb.inno.admin.Service;

import com.kb.inno.admin.DAO.FaqCategoryDAO;
import com.kb.inno.admin.DTO.FaqCategoryDTO;
import com.kb.inno.admin.DTO.FaqDTO;
import com.kb.inno.admin.DTO.SearchDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FaqCategoryService {

    // DAO 연결
    private final FaqCategoryDAO faqCategoryDAO;

    // FAQ 카테고리 리스트 조회
    public void selectList(int menuId, Model model, int page) {

        // 페이지의 전체 글 갯수
        int allCount = faqCategoryDAO.selectPageCount();

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

        // Search DTO에 담기
        SearchDTO search = new SearchDTO();

        // 끝 페이지
        int end = page * pageLetter;
        search.setEnd(end);

        // 시작 페이지
        int start = end + 1 - pageLetter;
        search.setStart(start);

        // 리스트 조회
        // 2. 코드를 가지고 가서 for문으로 조회
        List<FaqCategoryDTO> selectList = faqCategoryDAO.selectList(search);

        model.addAttribute("repeat", repeat);
        model.addAttribute("currentPage", page);
        model.addAttribute("selectList", selectList);
        model.addAttribute("menuId", menuId);
    }

    // FAQ 카테고리 등록
    public int insert(FaqCategoryDTO faqCategoryDTO, int loginId) {
        // 로그인 한 아이디 대입
        faqCategoryDTO.setFrst_rgtr(loginId);
        faqCategoryDTO.setLast_mdfr(loginId);

        return faqCategoryDAO.insert(faqCategoryDTO);
    }

    // FAQ 카테고리 상세 조회
    public FaqCategoryDTO select(int ctgry_sn) {
        return faqCategoryDAO.select(ctgry_sn);
    }

    // FAQ 카테고리 수정
    public int update(FaqCategoryDTO faqCategoryDTO, int loginId) {
        faqCategoryDTO.setLast_mdfr(loginId);

        return faqCategoryDAO.update(faqCategoryDTO);
    }

    // FAQ 카테고리 삭제
    public void delete(int ctgry_sn) {
        // 부모 요소 먼저 삭제
        int result = faqCategoryDAO.delete(ctgry_sn);
        if(result > 0) {
            faqCategoryDAO.deleteChild(ctgry_sn);
        }
    }

    public void preview(Model model, FaqCategoryDTO faqCategory) {
        int category_sn = faqCategory.getCtgry_sn();
        faqCategory.setCategory_sn(faqCategory.getCtgry_sn());
        //List<FaqCategoryDTO> selectList = faqCategoryDAO.selectListAll(category_sn);
        List<FaqCategoryDTO> selectList = faqCategoryDAO.selectListAll(faqCategory);
        model.addAttribute("selectList", selectList);
        model.addAttribute("faqCategory", faqCategory);
    }
}
