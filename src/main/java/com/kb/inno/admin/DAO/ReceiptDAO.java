package com.kb.inno.admin.DAO;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.kb.inno.admin.DTO.ReceiptDTO;
import com.kb.inno.admin.DTO.SurveyDTO;


@Mapper
@Repository
public interface ReceiptDAO {
	
    //지원서 임시 보관함 영구 삭제
    int receiptDelete(ReceiptDTO receiptDTO);
    //지원서 임시 보관함 삭제 취소
    int deleteCancel(ReceiptDTO receiptDTO);
	
    // 지원서 임시 보관 카운터
    int selectTrmpPageCount();
	// 지원서 임시 보관 리스트
    List<ReceiptDTO> selecTemptList(ReceiptDTO receiptDTO);
    
	
    //설문조사 삭제
    int tempDelete(ReceiptDTO receiptDTO);
    //설문조사 갯수 조회
    int selectPageCount();
    //설문조사 리스트 조회
    List<ReceiptDTO> selectList(ReceiptDTO receiptDTO);
    
    int receiptPageCount(String srvy_sn);
    
    List<ReceiptDTO> receiptList(ReceiptDTO receiptDTO);
}
