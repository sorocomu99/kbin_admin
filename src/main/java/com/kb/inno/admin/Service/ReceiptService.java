package com.kb.inno.admin.Service;

import java.io.InputStream;
import java.util.*;

import com.kb.inno.admin.DAO.SendMailDAO;
import com.kb.inno.admin.DTO.*;
import com.kb.inno.common.StringUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.kb.inno.admin.DAO.ReceiptDAO;

import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ReceiptService {
    // DAO 연결
    private final ReceiptDAO receiptDAO;
    private final SendMailDAO sendMailDAO;

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

    public void insertSendMailInfo(SendMailDTO sendMailDTO, List<String> receivers) {
        try{
            sendMailDAO.saveMailInfo(sendMailDTO);
            for(int i = 0; i < receivers.size(); i++) {
                sendMailDTO.setSend_mail_hist_sn(i + 1);
                sendMailDTO.setMail_rcvr(receivers.get(i));
                sendMailDAO.saveHistory(sendMailDTO);
            }
        }catch (Exception ignored) {
        }
    }


    public boolean uploadXlsForApplyStatus(MultipartFile file, Integer surveyNo) {
        try {
            List<KbStartersApplyDTO> updateList = parseExcel(file, surveyNo);

            if(updateList != null && updateList.size() > 0) {
                for (KbStartersApplyDTO dto : updateList) {
                    receiptDAO.updateApplyStatus(dto);
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<KbStartersApplyDTO> parseExcel(MultipartFile file, Integer surveyNo) throws Exception {
        Workbook workbook = null;

        try {
            InputStream inputStream = file.getInputStream();
            if (file.getOriginalFilename().endsWith(".xlsx")) {
                workbook = new XSSFWorkbook(inputStream);
            } else if (file.getOriginalFilename().endsWith(".xls")) {
                workbook = new HSSFWorkbook(inputStream);
            } else {
                throw new IllegalArgumentException("지원하지 않는 파일 형식입니다.");
            }

            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();

            // 첫 행(제목) 제외
            if (rowIterator.hasNext()) {
                rowIterator.next();
            }

            List<KbStartersApplyDTO> updateList = new ArrayList<>();

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();

                // 1열과 4열 데이터 추출
                Cell firstCell = row.getCell(0); // 첫 번째 열 (지원 번호)
                Cell fourthCell = row.getCell(3); // 네 번째 열 (처리 상태)

                String applyNo = getCellValue(firstCell);
                String applyStatus = getCellValue(fourthCell);

                if(StringUtil.hasText(applyNo)
                        && StringUtil.hasText(applyStatus)){
                    try{
                        KbStartersApplyDTO dto = new KbStartersApplyDTO();
                        dto.setApply_no(Integer.parseInt(applyNo));
                        dto.setApply_status(applyStatus);
                        dto.setSurvey_no(surveyNo);
                        updateList.add(dto);
                    }catch (Exception ignored) {
                    }
                }
            }
            return updateList;
        } finally {
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (Exception e) {
                }
            }
        }
    }

    private String getCellValue(Cell cell) {
        if (cell == null) {
            return null;
        }

        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                return cell.getStringCellValue();
            case Cell.CELL_TYPE_NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    return String.valueOf(cell.getNumericCellValue());
                }
            case Cell.CELL_TYPE_BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case Cell.CELL_TYPE_FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }

    public List<KbStartersApplyDTO> getSurveyApplyStatusList(int surveyNo) {
        List<KbStartersApplyDTO> surveyApplyStatusList = receiptDAO.getSurveyApplyStatusList(surveyNo);

        if(surveyApplyStatusList == null || surveyApplyStatusList.size() == 0) {
            surveyApplyStatusList = createDefaultStatusList();
        }

        return surveyApplyStatusList;
    }

    private List<KbStartersApplyDTO> createDefaultStatusList() {
        List<KbStartersApplyDTO> defaultList = new ArrayList<KbStartersApplyDTO>();
        String[] defaultStatuses = {"접수", "1차", "2차", "3차", "4차", "5차", "심사", "합격", "불합격"};

        for (String status : defaultStatuses) {
            KbStartersApplyDTO dto = new KbStartersApplyDTO();
            dto.setApply_status(status);
            defaultList.add(dto);
        }

        return defaultList;
    }

    @Transactional
    public Map<String, Object> saveApplyStatusList(Map<String, Object> requestData, int loginId) {
        Map<String, Object> result = new HashMap<>();
        result.put("result", "fail");
        try {
            int surveyNo = (int) requestData.get("surveyNo");
            List<String> applyStatusList = (List<String>) requestData.get("applyStatus");

            if(applyStatusList != null && applyStatusList.size() > 0) {
                KbStartersApplyDTO apply = new KbStartersApplyDTO();
                apply.setSurvey_no(surveyNo);
                apply.setFrst_rgtr(loginId);
                apply.setLast_mdfr(loginId);

                receiptDAO.deleteApplyStatus(apply);

                int i = 0;

                for(String status : applyStatusList){
                    try{
                        if(StringUtil.hasText(status)) {
                            apply.setSeq(++i);
                            apply.setApply_status(status);
                            receiptDAO.saveApplyStatus(apply);
                        }
                    }catch (Exception ignored) {
                    }
                }
            }
            result.put("result", "success");
        } catch (Exception e) {
        }
        return result;
    }
}
