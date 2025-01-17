package com.kb.inno.admin.DAO;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.kb.inno.admin.DTO.ReceiptDTO;


@Mapper
@Repository
public interface ReceiptDAO {
    //설문조사 갯수 조회
    int selectPageCount();
    //설문조사 리스트 조회
    List<ReceiptDTO> selectList(ReceiptDTO receiptDTO);
    
    int receiptPageCount(String srvy_sn);
    
    List<ReceiptDTO> receiptList(ReceiptDTO receiptDTO);
}
