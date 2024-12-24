package com.kb.inno.admin.DAO;

import com.kb.inno.admin.DTO.FaqCategoryDTO;
import com.kb.inno.admin.DTO.FaqDTO;
import com.kb.inno.admin.DTO.SearchDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Mapper
@Repository
public interface FaqCategoryDAO {
    // FAQ 카테고리 리스트 갯수 조회
    int selectPageCount();
    // FAQ 카테고리 리스트 조회
    List<FaqCategoryDTO> selectList(SearchDTO search);
    // FAQ 카테고리 등록
    int insert(FaqCategoryDTO faqCategoryDTO);
    // FAQ 카테고리 상세 조회
    FaqCategoryDTO select(int ctgry_sn);
    // FAQ 카테고리 수정
    int update(FaqCategoryDTO faqCategoryDTO);
    // FAQ 카테고리 삭제
    int delete(int ctgry_sn);
    // FAQ 자식 전부 삭제
    void deleteChild(int ctgry_sn);
    // FAQ 카테고리 모두 조회
    //List<FaqCategoryDTO> selectListAll(int category_sn);
    List<FaqCategoryDTO> selectListAll(FaqCategoryDTO faqCategoryDTO);
}
