/**
 * 파일명     : SurveyDAO.java
 * 화면명     : 설문 관리
 * 설명       : 설문 조회 및 등록, 수정, 삭제 처리
 * 최초개발일  : 2025.01.06
 * 최초개발자  : 이훈희
 * ==========================================================
 *   수정일            수정자           설명
 * ==========================================================
 */
package com.kb.inno.admin.DAO;

import com.kb.inno.admin.DTO.SendMailDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SendMailDAO {
    //보낸 메일함 갯수 조회
    int selectPageCount();
    //보낸 메일함 조회
    List<SendMailDTO> selectListMail(SendMailDTO sendMailDTO);
    //메일 상세 내용 조회
    SendMailDTO selectDetailMail(SendMailDTO sendMailDTO);
    SendMailDTO selectMailSendName(SendMailDTO sendMailDTO);
    int deleteOne(SendMailDTO sendMailDTO);
    int deleteDetail(SendMailDTO sendMailDTO);
    //보낸 메일함 보낸사람 조회
    List<SendMailDTO> selectDetailMailList(SendMailDTO sendMailDTO);
}
