package com.kb.inno.admin.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.kb.inno.admin.DAO.ReceiptDAO;
import com.kb.inno.admin.DTO.ReceiptConmDTO;
import com.kb.inno.admin.DTO.ReceiptDTO;
import com.kb.inno.admin.DTO.ReceiptListDTO;
import com.kb.inno.admin.DTO.SurveyDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReceiptService {
    // DAO 연결
    private final ReceiptDAO receiptDAO;

    /**
     * 설문정보 삭제 (하위 모든 정보 삭제)
     * @param surveyDTO
     */
    public int receiptDelete(ReceiptDTO receiptDTO) {
        //KB_SRVY_EXMN_INFO(설문조사정보), KB_SRBVY_QSTN_INFO(설문질문정보), KB_SRVY_ANS_INFO(설문답변정보), KB_SRVY_RSPNS_INFO(설문응답정보) 모두 삭제
        return receiptDAO.receiptDelete(receiptDTO);
    }
    
    /**
     * 지원서 임시 보관함 삭제 취소
     * @param surveyDTO
     */
    public int deleteCancel(ReceiptDTO receiptDTO) {
        return receiptDAO.deleteCancel(receiptDTO);
    }
    /**
     * 설문정보 삭제 (하위 모든 정보 삭제)
     * @param surveyDTO
     */
    public int tempDelete(ReceiptDTO receiptDTO) {
        return receiptDAO.tempDelete(receiptDTO);
    }

    public int updateAlert(ReceiptListDTO receiptListDTO) {
        return receiptDAO.updateAlert(receiptListDTO);
    }
    

    /**
     * 지원서 임시 보관함 조회
     * @param menuId
     * @param page
     * @param model
     */
    public void selecTemptList(int menuId, int page, Model model) {
    	ReceiptDTO receiptDTO = new ReceiptDTO();

        int allCount = receiptDAO.selectTrmpPageCount();

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

        List<ReceiptDTO> selectList = receiptDAO.selecTemptList(receiptDTO);
        model.addAttribute("repeat", repeat);
        model.addAttribute("currentPage", page);
        model.addAttribute("selectList", selectList);
        model.addAttribute("menuId", menuId);
    }
    
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
    
    public void receiptTenList(int menuId, String type, Model model, int srvy_sn) {
System.out.println("ReceiptService : receiptTenList--------------type["+type+"] srvy_sn["+srvy_sn+"]");
    	//List<ReceiptDTO> selectConmList = new List<ReceiptDTO>();
    	List<String> conm = receiptDAO.selectConm(srvy_sn+"");
    	System.out.println("-----------------------------------conm.size()["+conm.size()+"]");

    	List<ReceiptConmDTO> selectList;
    	ReceiptListDTO receiptListDTO = null;
    	List<ReceiptListDTO> list = new ArrayList<>(conm.size());
    	
		for(int i=0 ; i < conm.size() ; i++){
			System.out.println("----------------------------------- 1 for start[[["+conm.get(i)+"]]]]");
			selectList = receiptDAO.selectConmList(conm.get(i));
			receiptListDTO = new ReceiptListDTO();
			//receiptListDTO.setType(type);
			
			for(int n =0 ; n < selectList.size() ; n++) {
				System.out.println("------------------------------------------------------ 2 for start");
				System.out.println("["+n+"]  ["+ selectList.get(n)  +"]");
				System.out.println("------------------------------------------------------");
				System.out.println("======================================================");
				System.out.println(selectList.get(n).getSrvy_sn());
				System.out.println(selectList.get(n).getSrvy_ttl());
				System.out.println(selectList.get(n).getQstn_type());
				System.out.println(selectList.get(n).getSrvy_qstn());
				System.out.println(selectList.get(n).getSrvy_qstn_sn());
				System.out.println(selectList.get(n).getRspns_cn());
				System.out.println(selectList.get(n).getConm());
				System.out.println(selectList.get(n).getPrgrs_stts());
				System.out.println(selectList.get(n).getRownumber());
				System.out.println("======================================================");
				if(n == 0) {
					receiptListDTO.setSrvy_sn(selectList.get(n).getSrvy_sn());
					receiptListDTO.setConm(selectList.get(n).getConm());
					receiptListDTO.setPrgrs_stts(selectList.get(n).getPrgrs_stts());
					receiptListDTO.setRspns_cn1(selectList.get(n).getRspns_cn());
				} else if(n == 1) {
					receiptListDTO.setRspns_cn2(selectList.get(n).getRspns_cn());
				} else if(n == 2) {
					receiptListDTO.setRspns_cn3(selectList.get(n).getRspns_cn());
				} else if(n == 3) {
					receiptListDTO.setRspns_cn4(selectList.get(n).getRspns_cn());
				} else if(n == 4) {
					receiptListDTO.setRspns_cn5(selectList.get(n).getRspns_cn());
				} else if(n == 5) {
					receiptListDTO.setRspns_cn6(selectList.get(n).getRspns_cn());
				} else if(n == 6) {
					receiptListDTO.setRspns_cn7(selectList.get(n).getRspns_cn());
				} else if(n == 7) {
					receiptListDTO.setRspns_cn8(selectList.get(n).getRspns_cn());
				} else if(n == 8) {
					receiptListDTO.setRspns_cn9(selectList.get(n).getRspns_cn());
				} else if(n == 9) {
					receiptListDTO.setRspns_cn10(selectList.get(n).getRspns_cn());
				}
				System.out.println("------------------------------------------------------ 2 for end");
			}
			receiptListDTO.setAddRow(i);

			System.out.println("----------------------------------- receiptListDTO.getAddRow() ["+receiptListDTO.getAddRow()+"]");
			
			System.out.println("----------------------------------- ADD    type ["+type+"]receiptListDTO.getPrgrs_stts()["+receiptListDTO.getPrgrs_stts()+"]");
			if("all".equals(type)) {
				System.out.println("all true ["+type+"]");
				list.add(receiptListDTO);
			} else {
				System.out.println("all false ["+type+"]");
				if(type.equals(receiptListDTO.getPrgrs_stts())) {
					System.out.println("all false ["+type+"] receiptListDTO.getPrgrs_stts()["+receiptListDTO.getPrgrs_stts()+"]");
					list.add(receiptListDTO);	
				}
			}
			System.out.println("----------------------------------- 1 for start end");
		}
        //receiptDTO.setStart(start);
		System.out.println("-------------------------------------------list.size()["+list.size()+"]");
        
       // model.addAttribute("repeat", repeat);
		model.addAttribute("srvy_sn", srvy_sn);
        model.addAttribute("type", type);
        model.addAttribute("selectList", list);
        model.addAttribute("menuId", menuId);
    

    }
    
}
