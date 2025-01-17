package com.kb.inno.admin.Service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.kb.inno.admin.DAO.ReceiptDAO;
import com.kb.inno.admin.DTO.ReceiptDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReceiptService {
    // DAO 연결
    private final ReceiptDAO receiptDAO;

    /**
     * 설문 조회
     * @param menuId
     * @param page
     * @param model
     */
    public void selectList(int menuId, int page, Model model) {
    	ReceiptDTO receiptDTO = new ReceiptDTO();

        int allCount = receiptDAO.selectPageCount();

        //한 페이지당 글 갯수
        int pageLetter = 10;

        //전체 글 갯수 / 한 페이지에 나올 글 갯수
        int repeat = allCount / pageLetter;
        //나머지가 0이 아니면
        if(allCount % pageLetter != 0){
            //더하기
            repeat += 1;
        }

        //repeat이 0이면
        if(repeat == 0) {
            repeat = 1;
        }

        //만약 가져온 페이지가 repeat 보다 크다면
        if(repeat < page) {
            page = repeat;
        }

        //만약 가져온 페이지가 0이라면
        if(page < 1) {
            page = 1;
        }

        // 끝 페이지
        int end = page * pageLetter;
        receiptDTO.setEnd(end);
        int start = end + 1 - pageLetter;
        receiptDTO.setStart(start);

        List<ReceiptDTO> selectList = receiptDAO.selectList(receiptDTO);
        model.addAttribute("repeat", repeat);
        model.addAttribute("currentPage", page);
        model.addAttribute("selectList", selectList);
        model.addAttribute("menuId", menuId);
    }

    
    public void receiptList(int menuId, int page, Model model, int srvy_sn) {
    	ReceiptDTO receiptDTO = new ReceiptDTO();

    	receiptDTO.setSrvy_sn(srvy_sn);
    	
        int allCount = receiptDAO.receiptPageCount(srvy_sn+"");

        //한 페이지당 글 갯수
        int pageLetter = 10;

        //전체 글 갯수 / 한 페이지에 나올 글 갯수
        int repeat = allCount / pageLetter;
        //나머지가 0이 아니면
        if(allCount % pageLetter != 0){
            //더하기
            repeat += 1;
        }

        //repeat이 0이면
        if(repeat == 0) {
            repeat = 1;
        }

        //만약 가져온 페이지가 repeat 보다 크다면
        if(repeat < page) {
            page = repeat;
        }

        //만약 가져온 페이지가 0이라면
        if(page < 1) {
            page = 1;
        }

        // 끝 페이지
        int end = page * pageLetter;
        receiptDTO.setEnd(end);
        int start = end + 1 - pageLetter;
        receiptDTO.setStart(start);

        List<ReceiptDTO> selectList = receiptDAO.receiptList(receiptDTO);
        model.addAttribute("repeat", repeat);
        model.addAttribute("currentPage", page);
        model.addAttribute("selectList", selectList);
        model.addAttribute("menuId", menuId);
    
    }
    
    
}
