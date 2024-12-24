package com.kb.inno.admin.DAO;

import com.kb.inno.admin.DTO.FaqDTO;
import com.kb.inno.admin.DTO.FileDTO;
import com.kb.inno.admin.DTO.NoticeDTO;
import com.kb.inno.admin.DTO.SearchDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface NoticeDAO {
    // 공지사항 리스트 갯수 조회
    int selectPageCount(SearchDTO search);
    // 공지사항 리스트 조회
    List<NoticeDTO> selectList(SearchDTO search);
    // 공지사항 파일 저장
    int insertFile(FileDTO fileDTO);
    // 공지사항 등록
    int insert(NoticeDTO noticeDTO);
    // 공지사항 상세 조회
    NoticeDTO select(int ntc_sn);
    // 공지사항 파일 삭제
    void deleteFile(int file_sn);
    // 공지사항 수정
    int update(NoticeDTO noticeDTO);
    // 공지사항 삭제
    int delete(int ntcSn);
}
