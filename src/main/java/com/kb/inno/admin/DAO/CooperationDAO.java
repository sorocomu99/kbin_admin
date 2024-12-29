package com.kb.inno.admin.DAO;

import com.kb.inno.admin.DTO.CooperationDTO;
import com.kb.inno.admin.DTO.FileDTO;
import com.kb.inno.admin.DTO.VisualDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CooperationDAO {
    // 협력 기관 리스트 조회
    List<CooperationDTO> selectList();
    // 협력 기관 파일 저장
    int insertFile(FileDTO fileDTO);
    // 협력 기관 등록
    int insert(CooperationDTO cooperationDTO);
    // 협력 기관 상세 조회
    CooperationDTO select(int coope_sn);
    // 협력 기관 파일 삭제
    void deleteFile(int file_sn);
    // 협력 기관 수정
    int update(CooperationDTO cooperationDTO);
    int sortUpdate(CooperationDTO cooperationDTO);
    // 협력 기관 삭제
    void delete(int coope_sn);
    // 협력 기관 리스트 조회 (미리보기용)
    //List<VisualDTO> selectListAll(int coope_sn);
    List<VisualDTO> selectListAll(CooperationDTO cooperationDTO);
}
