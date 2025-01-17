package com.kb.inno.admin.DAO;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.kb.inno.admin.DTO.SearchDTO;
import com.kb.inno.admin.DTO.StartupDTO;

@Mapper
@Repository
public interface StartupDAO {
    // 공지사항 리스트 갯수 조회
    int selectPageCount(SearchDTO search);
    // KB 스타터스 리스트 조회
    List<StartupDTO> selectList(SearchDTO search);
    /**
    // KB 스타터스 조회
    List<StartersDTO> select();
    // KB 스타터스 저장
    int insert(StartersDTO startersDTO);
    // KB 스타터스 수정
    int update(StartersDTO startersDTO);
    */
}