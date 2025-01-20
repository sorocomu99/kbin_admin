/**
 * 파일명     : SurveyService.java
 * 화면명     : 설문 관리
 * 설명       : 설문 조회 및 등록, 수정, 삭제 처리
 * 최초개발일  : 2025.01.06
 * 최초개발자  : 이훈희
 * ==========================================================
 *   수정일            수정자           설명
 * ==========================================================
 */
package com.kb.inno.admin.Service;

import com.kb.inno.admin.DAO.KbStartersSurvey;
import com.kb.inno.admin.DAO.SurveyDAO;
import com.kb.inno.admin.DTO.*;
import com.kb.inno.common.FileUploader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SurveyService {
    // DAO 연결
    private final SurveyDAO surveyDAO;

	private final KbStartersSurvey surveyRepository;

    /**
     * 설문 조회
     * @param menuId
     * @param page
     * @param model
     */
    public void selectList(int menuId, int page, Model model) {
        SurveyDTO surveyDTO = new SurveyDTO();

        int allCount = surveyDAO.selectPageCount();

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
        surveyDTO.setEnd(end);
        int start = end + 1 - pageLetter;
        surveyDTO.setStart(start);

        List<SurveyDTO> selectList = surveyDAO.selectList(surveyDTO);
        model.addAttribute("repeat", repeat);
        model.addAttribute("currentPage", page);
        model.addAttribute("selectList", selectList);
        model.addAttribute("menuId", menuId);
    }

    /**
     * 설문정보 등록
     * @param surveyDTO
     * @param loginId
     * @return
     */
    public HashMap<String, Object> exmnInsert(SurveyDTO surveyDTO, int loginId) {
        HashMap<String, Object> resultMap = new HashMap<>();

        surveyDTO.setFrst_rgtr(loginId);  //최초등록자 세팅
        surveyDTO.setLast_mdfr(loginId);  //최종수정자 세팅

        //파일을 첨부했는지 확인
        int file_yn = surveyDTO.getFile_yn();
        if (file_yn == 1) {
            //파일 저장
            MultipartFile file = surveyDTO.getSurvey_file();

            FileUploader fileUploader = new FileUploader();
            FileDTO fileSave = fileUploader.insertFile(file, loginId);

            int fileIns = surveyDAO.insertFile(fileSave);
            if(fileSave != null && fileIns == 1) {
                surveyDTO.setFile_sn(fileSave.getFile_sn());
                surveyDTO.setBane_file_sn(fileSave.getFile_sn());
            }
        }

        int srvySn = surveyDAO.selectMaxSn();
        surveyDTO.setSrvy_sn(srvySn);
        
        int exminIns = surveyDAO.exmnInsert(surveyDTO);

        if (exminIns == 1) {
            resultMap.put("errorCd", "00");
            resultMap.put("errorMsg", "설문정보가 정상적으로 등록되었습니다.");
        } else {
            resultMap.put("errorCd", "99");
            resultMap.put("errorMsg", "설문정보가 등록 중 오류가 발생되었습니다.");
        }

        return resultMap;
    }

    /**
     * 복사
     * @param surveyDTO
     * @param loginId
     * @return
     */
    public HashMap<String, Object> surverCopy(SurveyDTO surveyDTO, int loginId) {
        HashMap<String, Object> resultMap = new HashMap<>();

        surveyDTO.setFrst_rgtr(loginId);  //최초등록자 세팅
        surveyDTO.setLast_mdfr(loginId);  //최종수정자 세팅
// 화면에서 가져온 복사 srvy_sn
        //1. 카피할 대상을 가져온다
        SurveyDTO selectTarget = surveyDAO.selectTarget(surveyDTO);
        //파일 FILE_NM
        
        int srvySn = surveyDAO.selectMaxSn();

        SurveyDTO surveyCopy = new SurveyDTO();
        surveyCopy.setSrvy_sn(srvySn);
        surveyCopy.setSrvy_ttl(selectTarget.getSrvy_ttl());
        surveyCopy.setBane_file_sn(selectTarget.getBane_file_sn());
        surveyCopy.setFrst_rgtr(selectTarget.getFrst_rgtr());
        surveyCopy.setLast_mdfr(selectTarget.getLast_mdfr());

        int exminIns = surveyDAO.exmnInsert(surveyCopy);

        if (exminIns == 1) {
            resultMap.put("errorCd", "00");
            resultMap.put("errorMsg", "설문정보가 정상적으로 등록되었습니다.");
        } else {
            resultMap.put("errorCd", "99");
            resultMap.put("errorMsg", "설문정보가 등록 중 오류가 발생되었습니다.");
        }

        return resultMap;
    }
 
    public void selectGuide(SurveyDTO surveyDTO, Model model, int menuId) {
        surveyDTO.setSprt_expln_sn(surveyDTO.getSrvy_sn());
        SurveyDTO selectGuide = surveyDAO.selectGuide(surveyDTO);
        model.addAttribute("selectGuide", selectGuide);
        model.addAttribute("menuId", menuId);
        model.addAttribute("srvy_sn", surveyDTO.getSrvy_sn());
    }

    /**
     * 지원안내 정보 등록 및 수정
     * @param surveyDTO
     * @param loginId
     * @return
     */
    public HashMap<String, Object> qstnInsert(SurveyDTO surveyDTO, int loginId) {
    	HashMap<String, Object> resultMap = new HashMap<>();
    	
    	System.out.println("=======================================    start [[[" + surveyDTO + "]]]");

    	System.out.println("=======================================    sn [[[" + surveyDTO.getSrvy_sn1() + "]]]");

        System.out.println("=======================================    type1 [" + surveyDTO.getQstn_type1());
        System.out.println("=======================================    type2 [" + surveyDTO.getQstn_type2());
        System.out.println("=======================================    type3 [" + surveyDTO.getQstn_type3());
        System.out.println("=======================================    type4 [" + surveyDTO.getQstn_type4());
        System.out.println("=======================================    type5 [" + surveyDTO.getQstn_type5());
        System.out.println("=======================================    type6 [" + surveyDTO.getQstn_type6());
        System.out.println("=======================================    type7 [" + surveyDTO.getQstn_type7());
        System.out.println("=======================================    type8 [" + surveyDTO.getQstn_type8());
        System.out.println("=======================================    type9 [" + surveyDTO.getQstn_type9());
        System.out.println("=======================================    type10 [" + surveyDTO.getQstn_type10());
        
        surveyDTO.setFrst_rgtr(loginId);  //최초등록자 세팅
        surveyDTO.setLast_mdfr(loginId);  //최종수정자 세팅
        
        //문항 테이블  SRVY_QSTN_SN
        
        if(surveyDTO.getQstn_type1() != null) {
        	
            int srvy_qstn_sn = surveyDAO.selectQstnMaxSn();
            
            surveyDTO.setSrvy_qstn_sn(srvy_qstn_sn);        	
            
            surveyDTO.setQstn_type(surveyDTO.getQstn_type1());
            surveyDTO.setSrvy_qstn(surveyDTO.getSrvy_qstn1());
            surveyDTO.setSrvy_dtl_qstn(surveyDTO.getSrvy_dtl_qstn11());

            System.out.println("=======================================    15 [" + surveyDTO.getSrvy_qstn_sn());
            System.out.println("=======================================    16 [" + surveyDTO.getQstn_type()+ "]["+ surveyDTO.getQstn_type1()+"]");
            System.out.println("=======================================    16 [" + surveyDTO.getSrvy_qstn()+ "]["+ surveyDTO.getSrvy_qstn1()+"]");
            System.out.println("=======================================    16 [" + surveyDTO.getSrvy_dtl_qstn()+ "]["+ surveyDTO.getSrvy_dtl_qstn11()+"]");
            

            surveyDAO.qstnInsert(surveyDTO);
            
            System.out.println("=======================================  surveyDAO.qstnInsert - end");

          //  System.out.println("=======================================  ansInsert - start");

            HashMap<String, Integer> resulCnt = ansCnt(surveyDTO);
            System.out.println("======================================= 009 ["+resulCnt+"]["+resulCnt.size()+"]");

            if(resulCnt.get("ansCount0") == null) {
            	System.out.println("보기가 없을 경우 ======================================================================================");
            	if(resulCnt.get("ansCount1") != null) {
            		int ac1 = resulCnt.get("tCount1");
            		
            		System.out.println("===================================================================== ac1["+ ac1 +"]");
            		
            		for(int i=1 ; i <= ac1 ; i++) {
            			
    	        		SurveyDTO ansDto = new SurveyDTO();
    	            	System.out.println("=======================11["+i+"]");
    	            	
    	            	ansDto.setSrvy_qstn_sn(surveyDTO.getSrvy_qstn_sn());//SRVY_QSTN_SN            	
    	            	ansDto.setAftr_mvmn("0");
    	            	
    	            	ansDto.setFrst_rgtr(surveyDTO.getFrst_rgtr());
    	            	ansDto.setLast_mdfr(surveyDTO.getLast_mdfr());
    	            	
    	            	if(i == 1) {
    	            		System.out.println("=======================1["+i+"]");
    	            		ansDto.setSrvy_ans_sn(i);
    	            		ansDto.setAns_cn(surveyDTO.getAns_cn11());	
    	            	} else if(i == 2) {
    	            		System.out.println("=======================2["+i+"]");
    	            		ansDto.setSrvy_ans_sn(i);
    	            		ansDto.setAns_cn(surveyDTO.getAns_cn12());
    	            	} else if(i == 3) {
    	            		System.out.println("=======================3["+i+"]");
    	            		ansDto.setSrvy_ans_sn(i);
    	            		ansDto.setAns_cn(surveyDTO.getAns_cn13());
    	            	} else if(i == 4) {
    	            		System.out.println("=======================4["+i+"]");
    	            		ansDto.setSrvy_ans_sn(i);
    	            		ansDto.setAns_cn(surveyDTO.getAns_cn14());
    	            	} else if(i == 5) {
    	            		System.out.println("=======================5["+i+"]");
    	            		ansDto.setSrvy_ans_sn(i);
    	            		ansDto.setAns_cn(surveyDTO.getAns_cn15());
    	            	} else if(i == 6) {
    	            		System.out.println("=======================6["+i+"]");
    	            		ansDto.setSrvy_ans_sn(i);
    	            		ansDto.setAns_cn(surveyDTO.getAns_cn16());
    	            	} else if(i == 7) {
    	            		System.out.println("=======================7["+i+"]");
    	            		ansDto.setSrvy_ans_sn(i);
    	            		ansDto.setAns_cn(surveyDTO.getAns_cn17());
    	            	} else if(i == 8) {
    	            		System.out.println("=======================8["+i+"]");
    	            		ansDto.setSrvy_ans_sn(i);
    	            		ansDto.setAns_cn(surveyDTO.getAns_cn18());
    	            	} else if(i == 9) {
    	            		System.out.println("=======================9["+i+"]");
    	            		ansDto.setSrvy_ans_sn(i);
    	            		ansDto.setAns_cn(surveyDTO.getAns_cn19());
    	            	} else if(i == 10) {
    	            		System.out.println("=======================10["+i+"]");
    	            		ansDto.setSrvy_ans_sn(i);
    	            		ansDto.setAns_cn(surveyDTO.getAns_cn110());
    	            	}
    	            	
    	            	System.out.println("-------------------------------- ac1 ansDto ["+ansDto+"]]");
    	
    	            	//보기 테이블 KB_SRVY_ANS_INFO	
    	            	
    	            	int ansIns = surveyDAO.ansInsert(ansDto);	
    	            	System.out.println("-------------------------------- surveyDAO.ansInsert - end");
            		}
            	}
            }
        }
        if(surveyDTO.getQstn_type2() != null) {
        	
            int srvy_qstn_sn = surveyDAO.selectQstnMaxSn();
            
            surveyDTO.setSrvy_qstn_sn(srvy_qstn_sn);        	
            
            surveyDTO.setQstn_type(surveyDTO.getQstn_type2());
            surveyDTO.setSrvy_qstn(surveyDTO.getSrvy_qstn2());
            surveyDTO.setSrvy_dtl_qstn(surveyDTO.getSrvy_dtl_qstn21());

            System.out.println("=======================================    25 [" + surveyDTO.getSrvy_qstn_sn());
            System.out.println("=======================================    26 [" + surveyDTO.getQstn_type()+ "]["+ surveyDTO.getQstn_type2()+"]");
            System.out.println("=======================================    26 [" + surveyDTO.getSrvy_qstn()+ "]["+ surveyDTO.getSrvy_qstn2()+"]");
            System.out.println("=======================================    26 [" + surveyDTO.getSrvy_dtl_qstn()+ "]["+ surveyDTO.getSrvy_dtl_qstn21()+"]");
            
            
            surveyDAO.qstnInsert(surveyDTO);
            
            System.out.println("=======================================    28");
            
            HashMap<String, Integer> resulCnt = ansCnt(surveyDTO);
            System.out.println("======================================= 009 ["+resulCnt+"]["+resulCnt.size()+"]");
            int	ansIns = 0;
    		if(resulCnt.get("ansCount2") != null) {
        		int ac2 = resulCnt.get("tCount2");
        		
        		System.out.println("===================================================================== ac2["+ ac2 +"]");
        		
        		for(int i=1 ; i <= ac2 ; i++) {
        			
	        		SurveyDTO ansDto = new SurveyDTO();
	            	System.out.println("=======================22["+i+"]");
	            	
	            	ansDto.setSrvy_qstn_sn(surveyDTO.getSrvy_qstn_sn());//SRVY_QSTN_SN            	
	            	ansDto.setAftr_mvmn("0");
	            	
	            	ansDto.setFrst_rgtr(surveyDTO.getFrst_rgtr());
	            	ansDto.setLast_mdfr(surveyDTO.getLast_mdfr());
	            	
	            	if(i == 1) {
	            		System.out.println("=======================1["+i+"]["+surveyDTO.getAns_cn21()+"]");
	            		ansDto.setSrvy_ans_sn(i);
	            		ansDto.setAns_cn(surveyDTO.getAns_cn21());	
	            	} else if(i == 2) {
	            		System.out.println("=======================2["+i+"]");
	            		ansDto.setSrvy_ans_sn(i);
	            		ansDto.setAns_cn(surveyDTO.getAns_cn22());
	            	} else if(i == 3) {
	            		System.out.println("=======================3["+i+"]");
	            		ansDto.setSrvy_ans_sn(i);
	            		ansDto.setAns_cn(surveyDTO.getAns_cn23());
	            	} else if(i == 4) {
	            		System.out.println("=======================4["+i+"]");
	            		ansDto.setSrvy_ans_sn(i);
	            		ansDto.setAns_cn(surveyDTO.getAns_cn24());
	            	} else if(i == 5) {
	            		System.out.println("=======================5["+i+"]");
	            		ansDto.setSrvy_ans_sn(i);
	            		ansDto.setAns_cn(surveyDTO.getAns_cn25());
	            	} else if(i == 6) {
	            		System.out.println("=======================6["+i+"]");
	            		ansDto.setSrvy_ans_sn(i);
	            		ansDto.setAns_cn(surveyDTO.getAns_cn26());
	            	} else if(i == 7) {
	            		System.out.println("=======================7["+i+"]");
	            		ansDto.setSrvy_ans_sn(i);
	            		ansDto.setAns_cn(surveyDTO.getAns_cn27());
	            	} else if(i == 8) {
	            		System.out.println("=======================8["+i+"]");
	            		ansDto.setSrvy_ans_sn(i);
	            		ansDto.setAns_cn(surveyDTO.getAns_cn28());
	            	} else if(i == 9) {
	            		System.out.println("=======================9["+i+"]");
	            		ansDto.setSrvy_ans_sn(i);
	            		ansDto.setAns_cn(surveyDTO.getAns_cn29());
	            	} else if(i == 10) {
	            		System.out.println("=======================10["+i+"]");
	            		ansDto.setSrvy_ans_sn(i);
	            		ansDto.setAns_cn(surveyDTO.getAns_cn210());
	            	}
	            	
	            	System.out.println("-------------------------------- ac2 ansDto ["+ansDto+"]]");
	            	//보기 테이블 KB_SRVY_ANS_INFO	
	            	ansIns = surveyDAO.ansInsert(ansDto);	
        		}
    		}

            System.out.println("=======================================    29 end ["+ansIns+"]");
            
        }
        if(surveyDTO.getQstn_type3() != null) {
        	
            int srvy_qstn_sn = surveyDAO.selectQstnMaxSn();
            
            surveyDTO.setSrvy_qstn_sn(srvy_qstn_sn);        	
            
            System.out.println("=======================================    35 [" + surveyDTO.getSrvy_qstn_sn());
            System.out.println("=======================================    36 [" + surveyDTO.getRequired3());
            
            
            surveyDAO.qstnInsert(surveyDTO);
            
            System.out.println("=======================================    38 qstnInsert end");
            
            int ansIns = 0;
            HashMap<String, Integer> resulCnt = ansCnt(surveyDTO);
            System.out.println("======================================= 009 ["+resulCnt+"]["+resulCnt.size()+"]");
            
    		if(resulCnt.get("ansCount3") != null) {
        		int ac3 = resulCnt.get("tCount3");
        		
        		System.out.println("===================================================================== ac3["+ ac3 +"]");
        		
        		for(int i=1 ; i <= ac3 ; i++) {
        			
	        		SurveyDTO ansDto = new SurveyDTO();
	            	System.out.println("=======================33["+i+"]");
	            	
	            	ansDto.setSrvy_qstn_sn(surveyDTO.getSrvy_qstn_sn());//SRVY_QSTN_SN            	
	            	ansDto.setAftr_mvmn("0");
	            	
	            	ansDto.setFrst_rgtr(surveyDTO.getFrst_rgtr());
	            	ansDto.setLast_mdfr(surveyDTO.getLast_mdfr());
	            	
	            	if(i == 1) {
	            		System.out.println("=======================1["+i+"]");
	            		ansDto.setSrvy_ans_sn(i);
	            		ansDto.setAns_cn(surveyDTO.getAns_cn31());	
	            	} else if(i == 2) {
	            		System.out.println("=======================2["+i+"]");
	            		ansDto.setSrvy_ans_sn(i);
	            		ansDto.setAns_cn(surveyDTO.getAns_cn32());
	            	} else if(i == 3) {
	            		System.out.println("=======================3["+i+"]");
	            		ansDto.setSrvy_ans_sn(i);
	            		ansDto.setAns_cn(surveyDTO.getAns_cn33());
	            	} else if(i == 4) {
	            		System.out.println("=======================4["+i+"]");
	            		ansDto.setSrvy_ans_sn(i);
	            		ansDto.setAns_cn(surveyDTO.getAns_cn34());
	            	} else if(i == 5) {
	            		System.out.println("=======================5["+i+"]");
	            		ansDto.setSrvy_ans_sn(i);
	            		ansDto.setAns_cn(surveyDTO.getAns_cn35());
	            	} else if(i == 6) {
	            		System.out.println("=======================6["+i+"]");
	            		ansDto.setSrvy_ans_sn(i);
	            		ansDto.setAns_cn(surveyDTO.getAns_cn36());
	            	} else if(i == 7) {
	            		System.out.println("=======================7["+i+"]");
	            		ansDto.setSrvy_ans_sn(i);
	            		ansDto.setAns_cn(surveyDTO.getAns_cn37());
	            	} else if(i == 8) {
	            		System.out.println("=======================8["+i+"]");
	            		ansDto.setSrvy_ans_sn(i);
	            		ansDto.setAns_cn(surveyDTO.getAns_cn38());
	            	} else if(i == 9) {
	            		System.out.println("=======================9["+i+"]");
	            		ansDto.setSrvy_ans_sn(i);
	            		ansDto.setAns_cn(surveyDTO.getAns_cn39());
	            	} else if(i == 10) {
	            		System.out.println("=======================10["+i+"]");
	            		ansDto.setSrvy_ans_sn(i);
	            		ansDto.setAns_cn(surveyDTO.getAns_cn310());
	            	}
	            	
	            	System.out.println("-------------------------------- ac3 ansDto ["+ansDto+"]]");
	            	//보기 테이블 KB_SRVY_ANS_INFO	
	            	ansIns = surveyDAO.ansInsert(ansDto);	
        		}
    		}
    		System.out.println("  ansIns ["+ansIns+"]");
    		
        }
        if(surveyDTO.getQstn_type4() != null) {
        	
            int srvy_qstn_sn = surveyDAO.selectQstnMaxSn();
            
            surveyDTO.setSrvy_qstn_sn(srvy_qstn_sn);        	
            
            System.out.println("=======================================    45 [" + surveyDTO.getSrvy_qstn_sn());
            System.out.println("=======================================    46 [" + surveyDTO.getRequired4());
            
            
            surveyDAO.qstnInsert(surveyDTO);
            
            System.out.println("=======================================    48");
        	int ansIns = 0;
      	  //보기가 몇개인지확인 -- 질문타입이 객관식일때만 돌리자
        	HashMap<String, Integer> resulCnt = ansCnt(surveyDTO);
        	System.out.println("======================================= 009 ["+resulCnt+"]["+resulCnt.size()+"]");
	  		if(resulCnt.get("ansCount4") != null) {
	    		int ac4 = resulCnt.get("tCount4");
	    		
	    		System.out.println("===================================================================== ac4["+ ac4 +"]");
	    		
	    		for(int i=1 ; i <= ac4 ; i++) {
	    			
	        		SurveyDTO ansDto = new SurveyDTO();
	            	System.out.println("=======================44["+i+"]");
	            	
	            	ansDto.setSrvy_qstn_sn(surveyDTO.getSrvy_qstn_sn());//SRVY_QSTN_SN            	
	            	ansDto.setAftr_mvmn("0");
	            	
	            	ansDto.setFrst_rgtr(surveyDTO.getFrst_rgtr());
	            	ansDto.setLast_mdfr(surveyDTO.getLast_mdfr());
	            	
	            	if(i == 1) {
	            		System.out.println("=======================1["+i+"]");
	            		ansDto.setSrvy_ans_sn(i);
	            		ansDto.setAns_cn(surveyDTO.getAns_cn41());	
	            	} else if(i == 2) {
	            		System.out.println("=======================2["+i+"]");
	            		ansDto.setSrvy_ans_sn(i);
	            		ansDto.setAns_cn(surveyDTO.getAns_cn42());
	            	} else if(i == 3) {
	            		System.out.println("=======================3["+i+"]");
	            		ansDto.setSrvy_ans_sn(i);
	            		ansDto.setAns_cn(surveyDTO.getAns_cn43());
	            	} else if(i == 4) {
	            		System.out.println("=======================4["+i+"]");
	            		ansDto.setSrvy_ans_sn(i);
	            		ansDto.setAns_cn(surveyDTO.getAns_cn44());
	            	} else if(i == 5) {
	            		System.out.println("=======================5["+i+"]");
	            		ansDto.setSrvy_ans_sn(i);
	            		ansDto.setAns_cn(surveyDTO.getAns_cn45());
	            	} else if(i == 6) {
	            		System.out.println("=======================6["+i+"]");
	            		ansDto.setSrvy_ans_sn(i);
	            		ansDto.setAns_cn(surveyDTO.getAns_cn46());
	            	} else if(i == 7) {
	            		System.out.println("=======================7["+i+"]");
	            		ansDto.setSrvy_ans_sn(i);
	            		ansDto.setAns_cn(surveyDTO.getAns_cn47());
	            	} else if(i == 8) {
	            		System.out.println("=======================8["+i+"]");
	            		ansDto.setSrvy_ans_sn(i);
	            		ansDto.setAns_cn(surveyDTO.getAns_cn48());
	            	} else if(i == 9) {
	            		System.out.println("=======================9["+i+"]");
	            		ansDto.setSrvy_ans_sn(i);
	            		ansDto.setAns_cn(surveyDTO.getAns_cn49());
	            	} else if(i == 10) {
	            		System.out.println("=======================10["+i+"]");
	            		ansDto.setSrvy_ans_sn(i);
	            		ansDto.setAns_cn(surveyDTO.getAns_cn410());
	            	}
	            	
	            	System.out.println("-------------------------------- ac4 ansDto ["+ansDto+"]]");
	            	//보기 테이블 KB_SRVY_ANS_INFO	
	            	ansIns = surveyDAO.ansInsert(ansDto);
	            	System.out.println("-------------------------------- ac4 ansIns ["+ansIns+"]]");
	    		}
			}
            
        }
        if(surveyDTO.getQstn_type5() != null) {
        	
            int srvy_qstn_sn = surveyDAO.selectQstnMaxSn();
            
            surveyDTO.setSrvy_qstn_sn(srvy_qstn_sn);        	
            
            System.out.println("=======================================    55 [" + surveyDTO.getSrvy_qstn_sn());
            System.out.println("=======================================    56 [" + surveyDTO.getRequired5());
            
            
            surveyDAO.qstnInsert(surveyDTO);
            
            System.out.println("=======================================    58");
        	int ansIns = 0;
      	  	//보기가 몇개인지확인 -- 질문타입이 객관식일때만 돌리자
         	HashMap<String, Integer> resulCnt = ansCnt(surveyDTO);
          	System.out.println("======================================= 009 ["+resulCnt+"]["+resulCnt.size()+"]");
	  		if(resulCnt.get("ansCount5") != null) {
	    		int ac5 = resulCnt.get("tCount5");
	    		
	    		System.out.println("===================================================================== ac5["+ ac5 +"]");
	    		
	    		for(int i=1 ; i <= ac5 ; i++) {
	    			
	        		SurveyDTO ansDto = new SurveyDTO();
	            	System.out.println("=======================5["+i+"]");
	            	
	            	ansDto.setSrvy_qstn_sn(surveyDTO.getSrvy_qstn_sn());//SRVY_QSTN_SN            	
	            	ansDto.setAftr_mvmn("0");
	            	
	            	ansDto.setFrst_rgtr(surveyDTO.getFrst_rgtr());
	            	ansDto.setLast_mdfr(surveyDTO.getLast_mdfr());
	            	
	            	if(i == 1) {
	            		System.out.println("=======================1["+i+"]");
	            		ansDto.setSrvy_ans_sn(i);
	            		ansDto.setAns_cn(surveyDTO.getAns_cn51());	
	            	} else if(i == 2) {
	            		System.out.println("=======================2["+i+"]");
	            		ansDto.setSrvy_ans_sn(i);
	            		ansDto.setAns_cn(surveyDTO.getAns_cn52());
	            	} else if(i == 3) {
	            		System.out.println("=======================3["+i+"]");
	            		ansDto.setSrvy_ans_sn(i);
	            		ansDto.setAns_cn(surveyDTO.getAns_cn53());
	            	} else if(i == 4) {
	            		System.out.println("=======================4["+i+"]");
	            		ansDto.setSrvy_ans_sn(i);
	            		ansDto.setAns_cn(surveyDTO.getAns_cn54());
	            	} else if(i == 5) {
	            		System.out.println("=======================5["+i+"]");
	            		ansDto.setSrvy_ans_sn(i);
	            		ansDto.setAns_cn(surveyDTO.getAns_cn55());
	            	} else if(i == 6) {
	            		System.out.println("=======================6["+i+"]");
	            		ansDto.setSrvy_ans_sn(i);
	            		ansDto.setAns_cn(surveyDTO.getAns_cn56());
	            	} else if(i == 7) {
	            		System.out.println("=======================7["+i+"]");
	            		ansDto.setSrvy_ans_sn(i);
	            		ansDto.setAns_cn(surveyDTO.getAns_cn57());
	            	} else if(i == 8) {
	            		System.out.println("=======================8["+i+"]");
	            		ansDto.setSrvy_ans_sn(i);
	            		ansDto.setAns_cn(surveyDTO.getAns_cn58());
	            	} else if(i == 9) {
	            		System.out.println("=======================9["+i+"]");
	            		ansDto.setSrvy_ans_sn(i);
	            		ansDto.setAns_cn(surveyDTO.getAns_cn59());
	            	} else if(i == 10) {
	            		System.out.println("=======================10["+i+"]");
	            		ansDto.setSrvy_ans_sn(i);
	            		ansDto.setAns_cn(surveyDTO.getAns_cn510());
	            	}
	            	
	            	System.out.println("-------------------------------- ac5 ansDto ["+ansDto+"]]");
	            	//보기 테이블 KB_SRVY_ANS_INFO	
	            	ansIns = surveyDAO.ansInsert(ansDto);
	            	
	            	System.out.println("-------------------------------- ac5 ansIns ["+ansIns+"]]");
	    		}
			}
          
        }
        if(surveyDTO.getQstn_type6() != null) {
        	
            int srvy_qstn_sn = surveyDAO.selectQstnMaxSn();
            
            surveyDTO.setSrvy_qstn_sn(srvy_qstn_sn);        	
            
            System.out.println("=======================================    65 [" + surveyDTO.getSrvy_qstn_sn());
            System.out.println("=======================================    66 [" + surveyDTO.getRequired6());
            
            
            surveyDAO.qstnInsert(surveyDTO);
            
            System.out.println("=======================================    68");
        	int ansIns = 0;
        	//보기가 몇개인지확인 -- 질문타입이 객관식일때만 돌리자
        	HashMap<String, Integer> resulCnt = ansCnt(surveyDTO);
          	System.out.println("======================================= 009 ["+resulCnt+"]["+resulCnt.size()+"]");
	  		if(resulCnt.get("ansCount6") != null) {
	    		int ac6 = resulCnt.get("tCount6");
	    		
	    		System.out.println("===================================================================== ac6["+ ac6 +"]");
	    		
	    		for(int i=1 ; i <= ac6 ; i++) {
	    			
	        		SurveyDTO ansDto = new SurveyDTO();
	            	System.out.println("=======================66["+i+"]");
	            	
	            	ansDto.setSrvy_qstn_sn(surveyDTO.getSrvy_qstn_sn());//SRVY_QSTN_SN            	
	            	ansDto.setAftr_mvmn("0");
	            	
	            	ansDto.setFrst_rgtr(surveyDTO.getFrst_rgtr());
	            	ansDto.setLast_mdfr(surveyDTO.getLast_mdfr());
	            	
	            	if(i == 1) {
	            		System.out.println("=======================1["+i+"]");
	            		ansDto.setSrvy_ans_sn(i);
	            		ansDto.setAns_cn(surveyDTO.getAns_cn61());	
	            	} else if(i == 2) {
	            		System.out.println("=======================2["+i+"]");
	            		ansDto.setSrvy_ans_sn(i);
	            		ansDto.setAns_cn(surveyDTO.getAns_cn62());
	            	} else if(i == 3) {
	            		System.out.println("=======================3["+i+"]");
	            		ansDto.setSrvy_ans_sn(i);
	            		ansDto.setAns_cn(surveyDTO.getAns_cn63());
	            	} else if(i == 4) {
	            		System.out.println("=======================4["+i+"]");
	            		ansDto.setSrvy_ans_sn(i);
	            		ansDto.setAns_cn(surveyDTO.getAns_cn64());
	            	} else if(i == 5) {
	            		System.out.println("=======================5["+i+"]");
	            		ansDto.setSrvy_ans_sn(i);
	            		ansDto.setAns_cn(surveyDTO.getAns_cn65());
	            	} else if(i == 6) {
	            		System.out.println("=======================6["+i+"]");
	            		ansDto.setSrvy_ans_sn(i);
	            		ansDto.setAns_cn(surveyDTO.getAns_cn66());
	            	} else if(i == 7) {
	            		System.out.println("=======================7["+i+"]");
	            		ansDto.setSrvy_ans_sn(i);
	            		ansDto.setAns_cn(surveyDTO.getAns_cn67());
	            	} else if(i == 8) {
	            		System.out.println("=======================8["+i+"]");
	            		ansDto.setSrvy_ans_sn(i);
	            		ansDto.setAns_cn(surveyDTO.getAns_cn68());
	            	} else if(i == 9) {
	            		System.out.println("=======================9["+i+"]");
	            		ansDto.setSrvy_ans_sn(i);
	            		ansDto.setAns_cn(surveyDTO.getAns_cn69());
	            	} else if(i == 10) {
	            		System.out.println("=======================10["+i+"]");
	            		ansDto.setSrvy_ans_sn(i);
	            		ansDto.setAns_cn(surveyDTO.getAns_cn610());
	            	}
	            	
	            	System.out.println("-------------------------------- ac6 ansDto6 ["+ansDto+"]]");
	            	//보기 테이블 KB_SRVY_ANS_INFO	
	            	ansIns = surveyDAO.ansInsert(ansDto);
	            	System.out.println("-------------------------------- ac6 ansIns6 ["+ansIns+"]]");
	    		}
			}
            
        }
        if(surveyDTO.getQstn_type7() != null) {
        	
            int srvy_qstn_sn = surveyDAO.selectQstnMaxSn();
            
            surveyDTO.setSrvy_qstn_sn(srvy_qstn_sn);        	
            
            System.out.println("=======================================    75 [" + surveyDTO.getSrvy_qstn_sn());
            System.out.println("=======================================    76 [" + surveyDTO.getRequired7());
            
            
            surveyDAO.qstnInsert(surveyDTO);
            
            System.out.println("=======================================    78");
        	int ansIns = 0;
        	//보기가 몇개인지확인 -- 질문타입이 객관식일때만 돌리자
        	HashMap<String, Integer> resulCnt = ansCnt(surveyDTO);
          	System.out.println("======================================= 009 ["+resulCnt+"]["+resulCnt.size()+"]");
	  		if(resulCnt.get("ansCount7") != null) {
	    		int ac7 = resulCnt.get("tCount7");
	    		
	    		System.out.println("===================================================================== ac7["+ ac7 +"]");
	    		
	    		for(int i=1 ; i <= ac7 ; i++) {
	    			
	        		SurveyDTO ansDto = new SurveyDTO();
	            	System.out.println("=======================77["+i+"]");
	            	
	            	ansDto.setSrvy_qstn_sn(surveyDTO.getSrvy_qstn_sn());//SRVY_QSTN_SN            	
	            	ansDto.setAftr_mvmn("0");
	            	
	            	ansDto.setFrst_rgtr(surveyDTO.getFrst_rgtr());
	            	ansDto.setLast_mdfr(surveyDTO.getLast_mdfr());
	            	
	            	if(i == 1) {
	            		System.out.println("=======================1["+i+"]");
	            		ansDto.setSrvy_ans_sn(i);
	            		ansDto.setAns_cn(surveyDTO.getAns_cn71());	
	            	} else if(i == 2) {
	            		System.out.println("=======================2["+i+"]");
	            		ansDto.setSrvy_ans_sn(i);
	            		ansDto.setAns_cn(surveyDTO.getAns_cn72());
	            	} else if(i == 3) {
	            		System.out.println("=======================3["+i+"]");
	            		ansDto.setSrvy_ans_sn(i);
	            		ansDto.setAns_cn(surveyDTO.getAns_cn73());
	            	} else if(i == 4) {
	            		System.out.println("=======================4["+i+"]");
	            		ansDto.setSrvy_ans_sn(i);
	            		ansDto.setAns_cn(surveyDTO.getAns_cn74());
	            	} else if(i == 5) {
	            		System.out.println("=======================5["+i+"]");
	            		ansDto.setSrvy_ans_sn(i);
	            		ansDto.setAns_cn(surveyDTO.getAns_cn75());
	            	} else if(i == 6) {
	            		System.out.println("=======================6["+i+"]");
	            		ansDto.setSrvy_ans_sn(i);
	            		ansDto.setAns_cn(surveyDTO.getAns_cn76());
	            	} else if(i == 7) {
	            		System.out.println("=======================7["+i+"]");
	            		ansDto.setSrvy_ans_sn(i);
	            		ansDto.setAns_cn(surveyDTO.getAns_cn77());
	            	} else if(i == 8) {
	            		System.out.println("=======================8["+i+"]");
	            		ansDto.setSrvy_ans_sn(i);
	            		ansDto.setAns_cn(surveyDTO.getAns_cn78());
	            	} else if(i == 9) {
	            		System.out.println("=======================9["+i+"]");
	            		ansDto.setSrvy_ans_sn(i);
	            		ansDto.setAns_cn(surveyDTO.getAns_cn79());
	            	} else if(i == 10) {
	            		System.out.println("=======================10["+i+"]");
	            		ansDto.setSrvy_ans_sn(i);
	            		ansDto.setAns_cn(surveyDTO.getAns_cn710());
	            	}
	            	
	            	System.out.println("-------------------------------- ac7 ansDto ["+ansDto+"]]");
	            	//보기 테이블 KB_SRVY_ANS_INFO	
	            	ansIns = surveyDAO.ansInsert(ansDto);
	            	System.out.println("-------------------------------- ac7 ansIns ["+ansIns+"]]");
	    		}
			}
            
        }
        if(surveyDTO.getQstn_type8() != null) {
        	
            int srvy_qstn_sn = surveyDAO.selectQstnMaxSn();
            
            surveyDTO.setSrvy_qstn_sn(srvy_qstn_sn);        	
            
            System.out.println("=======================================    85 [" + surveyDTO.getSrvy_qstn_sn());
            System.out.println("=======================================    86 [" + surveyDTO.getRequired8());
            
            
            surveyDAO.qstnInsert(surveyDTO);
            
            System.out.println("=======================================    88");
        	int ansIns = 0;
      	  	//보기가 몇개인지확인 -- 질문타입이 객관식일때만 돌리자
        	HashMap<String, Integer> resulCnt = ansCnt(surveyDTO);
          	System.out.println("======================================= 009 ["+resulCnt+"]["+resulCnt.size()+"]");
	  		if(resulCnt.get("ansCount8") != null) {
	    		int ac8 = resulCnt.get("tCount8");
	    		
	    		System.out.println("===================================================================== ac8["+ ac8 +"]");
	    		
	    		for(int i=1 ; i <= ac8 ; i++) {
	    			
	        		SurveyDTO ansDto = new SurveyDTO();
	            	System.out.println("=======================88["+i+"]");
	            	
	            	ansDto.setSrvy_qstn_sn(surveyDTO.getSrvy_qstn_sn());//SRVY_QSTN_SN            	
	            	ansDto.setAftr_mvmn("0");
	            	
	            	ansDto.setFrst_rgtr(surveyDTO.getFrst_rgtr());
	            	ansDto.setLast_mdfr(surveyDTO.getLast_mdfr());
	            	
	            	if(i == 1) {
	            		System.out.println("=======================1["+i+"]");
	            		ansDto.setSrvy_ans_sn(i);
	            		ansDto.setAns_cn(surveyDTO.getAns_cn81());	
	            	} else if(i == 2) {
	            		System.out.println("=======================2["+i+"]");
	            		ansDto.setSrvy_ans_sn(i);
	            		ansDto.setAns_cn(surveyDTO.getAns_cn82());
	            	} else if(i == 3) {
	            		System.out.println("=======================3["+i+"]");
	            		ansDto.setSrvy_ans_sn(i);
	            		ansDto.setAns_cn(surveyDTO.getAns_cn83());
	            	} else if(i == 4) {
	            		System.out.println("=======================4["+i+"]");
	            		ansDto.setSrvy_ans_sn(i);
	            		ansDto.setAns_cn(surveyDTO.getAns_cn84());
	            	} else if(i == 5) {
	            		System.out.println("=======================5["+i+"]");
	            		ansDto.setSrvy_ans_sn(i);
	            		ansDto.setAns_cn(surveyDTO.getAns_cn85());
	            	} else if(i == 6) {
	            		System.out.println("=======================6["+i+"]");
	            		ansDto.setSrvy_ans_sn(i);
	            		ansDto.setAns_cn(surveyDTO.getAns_cn86());
	            	} else if(i == 7) {
	            		System.out.println("=======================7["+i+"]");
	            		ansDto.setSrvy_ans_sn(i);
	            		ansDto.setAns_cn(surveyDTO.getAns_cn87());
	            	} else if(i == 8) {
	            		System.out.println("=======================8["+i+"]");
	            		ansDto.setSrvy_ans_sn(i);
	            		ansDto.setAns_cn(surveyDTO.getAns_cn88());
	            	} else if(i == 9) {
	            		System.out.println("=======================9["+i+"]");
	            		ansDto.setSrvy_ans_sn(i);
	            		ansDto.setAns_cn(surveyDTO.getAns_cn89());
	            	} else if(i == 10) {
	            		System.out.println("=======================10["+i+"]");
	            		ansDto.setSrvy_ans_sn(i);
	            		ansDto.setAns_cn(surveyDTO.getAns_cn810());
	            	}
	            	
	            	System.out.println("-------------------------------- ac8 ansDto ["+ansDto+"]]");
	            	//보기 테이블 KB_SRVY_ANS_INFO	
	            	ansIns = surveyDAO.ansInsert(ansDto);
	            	System.out.println("-------------------------------- ac8 ansIns ["+ansIns+"]]");
	    		}
			}   
        }
        if(surveyDTO.getQstn_type9() != null) {
        	
            int srvy_qstn_sn = surveyDAO.selectQstnMaxSn();
            
            surveyDTO.setSrvy_qstn_sn(srvy_qstn_sn);        	
            
            System.out.println("=======================================    95 [" + surveyDTO.getSrvy_qstn_sn());
            System.out.println("=======================================    96 [" + surveyDTO.getRequired9());
            
            
            surveyDAO.qstnInsert(surveyDTO);
            
            System.out.println("=======================================    98");
        	int ansIns = 0;
      	  	//보기가 몇개인지확인 -- 질문타입이 객관식일때만 돌리자
        	HashMap<String, Integer> resulCnt = ansCnt(surveyDTO);
          	System.out.println("======================================= 009 ["+resulCnt+"]["+resulCnt.size()+"]");
	  		if(resulCnt.get("ansCount9") != null) {
	    		int ac9 = resulCnt.get("tCount9");
	    		
	    		System.out.println("===================================================================== ac9["+ ac9 +"]");
	    		
	    		for(int i=1 ; i <= ac9 ; i++) {
	    			
	        		SurveyDTO ansDto = new SurveyDTO();
	            	System.out.println("=======================99["+i+"]");
	            	
	            	ansDto.setSrvy_qstn_sn(surveyDTO.getSrvy_qstn_sn());//SRVY_QSTN_SN            	
	            	ansDto.setAftr_mvmn("0");
	            	
	            	ansDto.setFrst_rgtr(surveyDTO.getFrst_rgtr());
	            	ansDto.setLast_mdfr(surveyDTO.getLast_mdfr());
	            	
	            	if(i == 1) {
	            		System.out.println("=======================1["+i+"]");
	            		ansDto.setSrvy_ans_sn(i);
	            		ansDto.setAns_cn(surveyDTO.getAns_cn91());	
	            	} else if(i == 2) {
	            		System.out.println("=======================2["+i+"]");
	            		ansDto.setSrvy_ans_sn(i);
	            		ansDto.setAns_cn(surveyDTO.getAns_cn92());
	            	} else if(i == 3) {
	            		System.out.println("=======================3["+i+"]");
	            		ansDto.setSrvy_ans_sn(i);
	            		ansDto.setAns_cn(surveyDTO.getAns_cn93());
	            	} else if(i == 4) {
	            		System.out.println("=======================4["+i+"]");
	            		ansDto.setSrvy_ans_sn(i);
	            		ansDto.setAns_cn(surveyDTO.getAns_cn94());
	            	} else if(i == 5) {
	            		System.out.println("=======================5["+i+"]");
	            		ansDto.setSrvy_ans_sn(i);
	            		ansDto.setAns_cn(surveyDTO.getAns_cn95());
	            	} else if(i == 6) {
	            		System.out.println("=======================6["+i+"]");
	            		ansDto.setSrvy_ans_sn(i);
	            		ansDto.setAns_cn(surveyDTO.getAns_cn96());
	            	} else if(i == 7) {
	            		System.out.println("=======================7["+i+"]");
	            		ansDto.setSrvy_ans_sn(i);
	            		ansDto.setAns_cn(surveyDTO.getAns_cn97());
	            	} else if(i == 8) {
	            		System.out.println("=======================8["+i+"]");
	            		ansDto.setSrvy_ans_sn(i);
	            		ansDto.setAns_cn(surveyDTO.getAns_cn98());
	            	} else if(i == 9) {
	            		System.out.println("=======================9["+i+"]");
	            		ansDto.setSrvy_ans_sn(i);
	            		ansDto.setAns_cn(surveyDTO.getAns_cn99());
	            	} else if(i == 10) {
	            		System.out.println("=======================10["+i+"]");
	            		ansDto.setSrvy_ans_sn(i);
	            		ansDto.setAns_cn(surveyDTO.getAns_cn910());
	            	}
	            	
	            	System.out.println("-------------------------------- ac9 ansDto ["+ansDto+"]]");
	            	//보기 테이블 KB_SRVY_ANS_INFO	
	            	ansIns = surveyDAO.ansInsert(ansDto);
	            	System.out.println("-------------------------------- ac9 ansIns ["+ansIns+"]]");
	    		}
			}
            
        }
        if(surveyDTO.getQstn_type10() != null) {
        	
            int srvy_qstn_sn = surveyDAO.selectQstnMaxSn();
            
            surveyDTO.setSrvy_qstn_sn(srvy_qstn_sn);        	
            
            System.out.println("=======================================    105 [" + surveyDTO.getSrvy_qstn_sn());
            System.out.println("=======================================    106 [" + surveyDTO.getRequired10());
            
            
            surveyDAO.qstnInsert(surveyDTO);
            
            System.out.println("=======================================    108");
        	int ansIns = 0;
        	//보기가 몇개인지확인 -- 질문타입이 객관식일때만 돌리자
        	HashMap<String, Integer> resulCnt = ansCnt(surveyDTO);
          	System.out.println("======================================= 009 ["+resulCnt+"]["+resulCnt.size()+"]");
	      	if(resulCnt.get("ansCount10") != null) {
	    		int ac10 = resulCnt.get("tCount10");
	    		
	    		System.out.println("===================================================================== ac10["+ ac10 +"]");
	    		
	    		for(int i=1 ; i <= ac10 ; i++) {
	    			
	        		SurveyDTO ansDto = new SurveyDTO();
	            	System.out.println("=======================110["+i+"]");
	            	
	            	ansDto.setSrvy_qstn_sn(surveyDTO.getSrvy_qstn_sn());//SRVY_QSTN_SN            	
	            	ansDto.setAftr_mvmn("0");
	            	
	            	ansDto.setFrst_rgtr(surveyDTO.getFrst_rgtr());
	            	ansDto.setLast_mdfr(surveyDTO.getLast_mdfr());
	            	
	            	if(i == 1) {
	            		System.out.println("=======================1["+i+"]");
	            		ansDto.setSrvy_ans_sn(i);
	            		ansDto.setAns_cn(surveyDTO.getAns_cn101());	
	            	} else if(i == 2) {
	            		System.out.println("=======================2["+i+"]");
	            		ansDto.setSrvy_ans_sn(i);
	            		ansDto.setAns_cn(surveyDTO.getAns_cn102());
	            	} else if(i == 3) {
	            		System.out.println("=======================3["+i+"]");
	            		ansDto.setSrvy_ans_sn(i);
	            		ansDto.setAns_cn(surveyDTO.getAns_cn103());
	            	} else if(i == 4) {
	            		System.out.println("=======================4["+i+"]");
	            		ansDto.setSrvy_ans_sn(i);
	            		ansDto.setAns_cn(surveyDTO.getAns_cn104());
	            	} else if(i == 5) {
	            		System.out.println("=======================5["+i+"]");
	            		ansDto.setSrvy_ans_sn(i);
	            		ansDto.setAns_cn(surveyDTO.getAns_cn105());
	            	} else if(i == 6) {
	            		System.out.println("=======================6["+i+"]");
	            		ansDto.setSrvy_ans_sn(i);
	            		ansDto.setAns_cn(surveyDTO.getAns_cn106());
	            	} else if(i == 7) {
	            		System.out.println("=======================7["+i+"]");
	            		ansDto.setSrvy_ans_sn(i);
	            		ansDto.setAns_cn(surveyDTO.getAns_cn107());
	            	} else if(i == 8) {
	            		System.out.println("=======================8["+i+"]");
	            		ansDto.setSrvy_ans_sn(i);
	            		ansDto.setAns_cn(surveyDTO.getAns_cn108());
	            	} else if(i == 9) {
	            		System.out.println("=======================9["+i+"]");
	            		ansDto.setSrvy_ans_sn(i);
	            		ansDto.setAns_cn(surveyDTO.getAns_cn109());
	            	} else if(i == 10) {
	            		System.out.println("=======================10["+i+"]");
	            		ansDto.setSrvy_ans_sn(i);
	            		ansDto.setAns_cn(surveyDTO.getAns_cn1010());
	            	}
	            	
	            	System.out.println("-------------------------------- ac10 ansDto ["+ansDto+"]]");
	            	//보기 테이블 KB_SRVY_ANS_INFO	
	            	ansIns = surveyDAO.ansInsert(ansDto);	
	            	System.out.println("-------------------------------- ac10 ansIns ["+ansIns+"]]");
	    		}
	    	}
            
        }
        
        resultMap.put("errorCd", "00");
        resultMap.put("errorMsg", "정상적으로 등록되었습니다.");

        
        //파일을 첨부했는지 확인
		/**
		  int file_yn = surveyDTO.getFile_yn(); if (file_yn == 1) { //파일 저장
		  MultipartFile file = surveyDTO.getSurvey_file();
		  
		  FileUploader fileUploader = new FileUploader(); FileDTO fileSave =
		  fileUploader.insertFile(file, loginId);
		  
		  int fileIns = surveyDAO.insertFile(fileSave); if(fileSave != null && fileIns== 1) { surveyDTO.setFile_sn(fileSave.getFile_sn());
		  surveyDTO.setBane_file_sn(fileSave.getFile_sn()); } }
		
        
		 */

        
    	return resultMap;
    }
    

    
    // 지원서 설문 관리 > 문항관리 > 문항 > 보기가 몇개 있는지 체크 해서 카운터를 던져준다
    public HashMap<String, Integer> ansCnt(SurveyDTO surveyDTO) {
    	
        int cnT1 = 0;
        int cnT2 = 0;
        int cnT3 = 0;
        int cnT4 = 0;
        int cnT5 = 0;
        int cnT6 = 0;
        int cnT7 = 0;
        int cnT8 = 0;
        int cnT9 = 0;
        int cnT10 = 0;
        
        if(surveyDTO.getAns_cn11() != null) {
        	cnT1 += 1;
        }
        if(surveyDTO.getAns_cn12() != null) {
        	cnT1 += 1;
        }
		if(surveyDTO.getAns_cn13() != null) {
        	cnT1 += 1;
        }
		if(surveyDTO.getAns_cn14() != null) {
        	cnT1 += 1;
        }
		if(surveyDTO.getAns_cn15() != null) {
        	cnT1 += 1;
        }
		if(surveyDTO.getAns_cn16() != null) {
        	cnT1 += 1;
        }
		if(surveyDTO.getAns_cn17() != null) {
        	cnT1 += 1;
        }
		if(surveyDTO.getAns_cn18() != null) {
        	cnT1 += 1;
        }
		if(surveyDTO.getAns_cn19() != null) {
        	cnT1 += 1;
        }
		if(surveyDTO.getAns_cn110() != null){
        	cnT1 += 1;
        }
        if(surveyDTO.getAns_cn21() != null) {
        	cnT2 += 1;
        }
		if(surveyDTO.getAns_cn22() != null) {
        	cnT2 += 1;
        }
		if(surveyDTO.getAns_cn23() != null) {
        	cnT2 += 1;
        }
		if(surveyDTO.getAns_cn24() != null) {
        	cnT2 += 1;
        }
		if(surveyDTO.getAns_cn25() != null) {
        	cnT2 += 1;
        }
		if(surveyDTO.getAns_cn26() != null) {
        	cnT2 += 1;
        }
		if(surveyDTO.getAns_cn27() != null) {
        	cnT2 += 1;
        }
		if(surveyDTO.getAns_cn28() != null) {
        	cnT2 += 1;
        }
		if(surveyDTO.getAns_cn29() != null) {
        	cnT2 += 1;
        }
		if(surveyDTO.getAns_cn210() != null){
        	cnT2 += 1;
        }
        if(surveyDTO.getAns_cn31() != null) {
        	cnT3 += 1;
        }
		if(surveyDTO.getAns_cn32() != null) {
        	cnT3 += 1;
        }
		if(surveyDTO.getAns_cn33() != null) {
        	cnT3 += 1;
        }
		if(surveyDTO.getAns_cn34() != null) {
        	cnT3 += 1;
        }
		if(surveyDTO.getAns_cn35() != null) {
        	cnT3 += 1;
        }
		if(surveyDTO.getAns_cn36() != null) {
        	cnT3 += 1;
        }
		if(surveyDTO.getAns_cn37() != null) {
        	cnT3 += 1;
        }
		if(surveyDTO.getAns_cn38() != null) {
        	cnT3 += 1;
        }
		if(surveyDTO.getAns_cn39() != null) {
        	cnT3 += 1;
        }
		if(surveyDTO.getAns_cn310() != null){
        	cnT3 += 1;
        }
        if(surveyDTO.getAns_cn41() != null) {
        	cnT4 += 1;
        }
		if(surveyDTO.getAns_cn42() != null) {
        	cnT4 += 1;
        }
		if(surveyDTO.getAns_cn43() != null) {
        	cnT4 += 1;
        }
		if(surveyDTO.getAns_cn44() != null) {
        	cnT4 += 1;
        }
		if(surveyDTO.getAns_cn45() != null) {
        	cnT4 += 1;
        }
		if(surveyDTO.getAns_cn46() != null) {
        	cnT4 += 1;
        }
		if(surveyDTO.getAns_cn47() != null) {
        	cnT4 += 1;
        }
		if(surveyDTO.getAns_cn48() != null) {
        	cnT4 += 1;
        }
		if(surveyDTO.getAns_cn49() != null) {
        	cnT4 += 1;
        }
		if(surveyDTO.getAns_cn410() != null){
        	cnT4 += 1;
        }
        if(surveyDTO.getAns_cn51() != null) {
        	cnT5 += 1;
        }
		if(surveyDTO.getAns_cn52() != null) {
        	cnT5 += 1;
        }
		if(surveyDTO.getAns_cn53() != null) {
        	cnT5 += 1;
        }
		if(surveyDTO.getAns_cn54() != null) {
        	cnT5 += 1;
        }
		if(surveyDTO.getAns_cn55() != null) {
        	cnT5 += 1;
        }
		if(surveyDTO.getAns_cn56() != null) {
        	cnT5 += 1;
        }
		if(surveyDTO.getAns_cn57() != null) {
        	cnT5 += 1;
        }
		if(surveyDTO.getAns_cn58() != null) {
        	cnT5 += 1;
        }
		if(surveyDTO.getAns_cn59() != null) {
        	cnT5 += 1;
        }
		if(surveyDTO.getAns_cn510() != null){
        	cnT5 += 1;
        }
        if(surveyDTO.getAns_cn61() != null) {
        	cnT6 += 1;
        }
		if(surveyDTO.getAns_cn62() != null) {
        	cnT6 += 1;
        }
		if(surveyDTO.getAns_cn63() != null) {
        	cnT6 += 1;
        }
		if(surveyDTO.getAns_cn64() != null) {
        	cnT6 += 1;
        }
		if(surveyDTO.getAns_cn65() != null) {
        	cnT6 += 1;
        }
		if(surveyDTO.getAns_cn66() != null) {
        	cnT6 += 1;
        }
		if(surveyDTO.getAns_cn67() != null) {
        	cnT6 += 1;
        }
		if(surveyDTO.getAns_cn68() != null) {
        	cnT6 += 1;
        }
		if(surveyDTO.getAns_cn69() != null) {
        	cnT6 += 1;
        }
		if(surveyDTO.getAns_cn610() != null){
        	cnT6 += 1;
        }
        if(surveyDTO.getAns_cn71() != null) {
        	cnT7 += 1;
        }
		if(surveyDTO.getAns_cn72() != null) {
        	cnT7 += 1;
        }
		if(surveyDTO.getAns_cn73() != null) {
        	cnT7 += 1;
        }
		if(surveyDTO.getAns_cn74() != null) {
        	cnT7 += 1;
        }
		if(surveyDTO.getAns_cn75() != null) {
        	cnT7 += 1;
        }
		if(surveyDTO.getAns_cn76() != null) {
        	cnT7 += 1;
        }
		if(surveyDTO.getAns_cn77() != null) {
        	cnT7 += 1;
        }
		if(surveyDTO.getAns_cn78() != null) {
        	cnT7 += 1;
        }
		if(surveyDTO.getAns_cn79() != null) {
        	cnT7 += 1;
        }
		if(surveyDTO.getAns_cn710() != null){
        	cnT7 += 1;
        }
        if(surveyDTO.getAns_cn81() != null) {
        	cnT8 += 1;
        }
		if(surveyDTO.getAns_cn82() != null) {
        	cnT8 += 1;
        }
		if(surveyDTO.getAns_cn83() != null) {
        	cnT8 += 1;
        }
		if(surveyDTO.getAns_cn84() != null) {
        	cnT8 += 1;
        }
		if(surveyDTO.getAns_cn85() != null) {
        	cnT8 += 1;
        }
		if(surveyDTO.getAns_cn86() != null) {
        	cnT8 += 1;
        }
		if(surveyDTO.getAns_cn87() != null) {
        	cnT8 += 1;
        }
		if(surveyDTO.getAns_cn88() != null) {
        	cnT8 += 1;
        }
		if(surveyDTO.getAns_cn89() != null) {
        	cnT8 += 1;
        }
		if(surveyDTO.getAns_cn810() != null){
        	cnT8 += 1;
        }
        if(surveyDTO.getAns_cn91() != null) {
        	cnT9 += 1;
        }
		if(surveyDTO.getAns_cn92() != null) {
        	cnT9 += 1;
        }
		if(surveyDTO.getAns_cn93() != null) {
        	cnT9 += 1;
        }
		if(surveyDTO.getAns_cn94() != null) {
        	cnT9 += 1;
        }
		if(surveyDTO.getAns_cn95() != null) {
        	cnT9 += 1;
        }
		if(surveyDTO.getAns_cn96() != null) {
        	cnT9 += 1;
        }
		if(surveyDTO.getAns_cn97() != null) {
        	cnT9 += 1;
        }
		if(surveyDTO.getAns_cn98() != null) {
        	cnT9 += 1;
        }
		if(surveyDTO.getAns_cn99() != null) {
        	cnT9 += 1;
        }
		if(surveyDTO.getAns_cn910() != null){
        	cnT9 += 1;
        }
        if(surveyDTO.getAns_cn101() != null){
        	cnT10 += 1;
        } 
		if(surveyDTO.getAns_cn102() != null){
        	cnT10 += 1;
        } 
		if(surveyDTO.getAns_cn103() != null){
        	cnT10 += 1;
        } 
		if(surveyDTO.getAns_cn104() != null){
        	cnT10 += 1;
        } 
		if(surveyDTO.getAns_cn105() != null){
        	cnT10 += 1;
        } 
		if(surveyDTO.getAns_cn106() != null){
        	cnT10 += 1;
        } 
		if(surveyDTO.getAns_cn107() != null){
        	cnT10 += 1;
        } 
		if(surveyDTO.getAns_cn108() != null){
        	cnT10 += 1;
        } 
		if(surveyDTO.getAns_cn109() != null){
        	cnT10 += 1;
        } 
		if(surveyDTO.getAns_cn1010() != null){
        	cnT10 += 1;
        } 
        
		System.out.println("cnT1["+cnT1+"]cnT2["+cnT2+"]cnT3["+cnT3+"]cnT4["+cnT4+"]cnT5["+cnT5+"]cnT6["+cnT6+"]cnT7["+cnT7+"]cnT8["+cnT8+"]cnT9["+cnT9+"]cnT1["+cnT10+"]");
		
		HashMap<String, Integer> resulCnt = new HashMap<>();
		
		if(cnT1 == 0 && cnT2 == 0 && cnT3 == 0 && cnT4 == 0 && cnT5 == 0 && cnT6 == 0 && cnT7 == 0 && cnT8 == 0 && cnT9 == 0 && cnT10 == 0) {
        	resulCnt.put("ansCount0",0);
        	resulCnt.put("tCount10",0);
			
		} else {
			
	        if(cnT1 > 0) {
	        	int ansCount = 1;//몇번 문항 보기
	        	int tCount = cnT1;// 보기 갯수
	        	
	        	resulCnt.put("ansCount1",ansCount);
	        	resulCnt.put("tCount1",tCount);
	        }
	        if(cnT2 > 0) {
	        	int ansCount = 2;//몇번 문항 보기
	        	int tCount = cnT2;// 보기 갯수
	        	
	        	resulCnt.put("ansCount2",ansCount);
	        	resulCnt.put("tCount2",tCount);
	        }
	        if(cnT3 > 0) {
	        	int ansCount = 3;//몇번 문항 보기
	        	int tCount = cnT3;// 보기 갯수
	        	
	        	resulCnt.put("ansCount3",ansCount);
	        	resulCnt.put("tCount3",tCount);
	        }
	        if(cnT4 > 0) {
	        	int ansCount = 4;//몇번 문항 보기
	        	int tCount = cnT4;// 보기 갯수
	        	
	        	resulCnt.put("ansCount4",ansCount);
	        	resulCnt.put("tCount4",tCount);
	        }
	        if(cnT5 > 0) {
	        	int ansCount = 5;//몇번 문항 보기
	        	int tCount = cnT5;// 보기 갯수
	        	
	        	resulCnt.put("ansCount5",ansCount);
	        	resulCnt.put("tCount5",tCount);
	        }
	        if(cnT6 > 0) {
	        	int ansCount = 6;//몇번 문항 보기
	        	int tCount = cnT6;// 보기 갯수
	        	
	        	resulCnt.put("ansCount6",ansCount);
	        	resulCnt.put("tCount6",tCount);
	        }
	        if(cnT7 > 0) {
	        	int ansCount = 7;//몇번 문항 보기
	        	int tCount = cnT7;// 보기 갯수
	        	
	        	resulCnt.put("ansCount7",ansCount);
	        	resulCnt.put("tCount7",tCount);
	        }
	        if(cnT8 > 0) {
	        	int ansCount = 8;//몇번 문항 보기
	        	int tCount = cnT8;// 보기 갯수
	        	
	        	resulCnt.put("ansCount8",ansCount);
	        	resulCnt.put("tCount8",tCount);
	        }
	        if(cnT9 > 0) {
	        	int ansCount = 9;//몇번 문항 보기
	        	int tCount = cnT9;// 보기 갯수
	        	
	        	resulCnt.put("ansCount9",ansCount);
	        	resulCnt.put("tCount9",tCount);
	        }
	        if(cnT10 > 0) {
	        	int ansCount = 10;//몇번 문항 보기
	        	int tCount = cnT10;// 보기 갯수
	        	
	        	resulCnt.put("ansCount10",ansCount);
	        	resulCnt.put("tCount10",tCount);
	        }
		}
		
    	return resulCnt;
    } 
    
    
    
    
    
    
    
    /**
     * 지원안내 정보 등록 및 수정
     * @param surveyDTO
     * @param loginId
     * @return
     */
    public HashMap<String, Object> guideInsert(SurveyDTO surveyDTO, int loginId) {
        HashMap<String, Object> resultMap = new HashMap<>();

        surveyDTO.setFrst_rgtr(loginId);  //최초등록자 세팅
        surveyDTO.setLast_mdfr(loginId);  //최종수정자 세팅

        int guideIns = surveyDAO.guideInsert(surveyDTO);

        if (guideIns == 1) {
            resultMap.put("errorCd", "00");
            resultMap.put("errorMsg", "지원안내정보가 정상적으로 등록되었습니다.");
        } else {
            resultMap.put("errorCd", "99");
            resultMap.put("errorMsg", "지원안내정보 등록 중 오류가 발생되었습니다.");
        }

        return resultMap;
    }

    /**
     * 설문정보 삭제 (하위 모든 정보 삭제)
     * @param surveyDTO
     */
    public int exmnDelete(SurveyDTO surveyDTO) {
        //KB_SRVY_EXMN_INFO(설문조사정보), KB_SRBVY_QSTN_INFO(설문질문정보), KB_SRVY_ANS_INFO(설문답변정보), KB_SRVY_RSPNS_INFO(설문응답정보) 모두 삭제
        return surveyDAO.exmnDelete(surveyDTO);
    }

	public List<KbStartersSurveyDTO> getSurveyList(SearchDTO searchDTO) {
		return surveyRepository.getSurveyList(searchDTO);
	}

	public int getSurveyListCnt(SearchDTO searchDTO) {
		return surveyRepository.countSurvey(searchDTO);
	}

	public List<KbStartersQuestionDTO> getQuestionList(int surveyNo) {
		KbStartersQuestionDTO question = new KbStartersQuestionDTO();
		question.setSurvey_no(surveyNo);
		return surveyRepository.getQuestion(question);
	}

	public Map<String, Object> saveSurvey(KbStartersSurveyDTO survey, MultipartFile bannerFile, int loginId) {
		Map<String, Object> result = new HashMap<>();
		try {
			FileUploader fileUploader = new FileUploader();
			if(bannerFile != null && bannerFile.getSize() != 0){
				FileDTO fileSave = fileUploader.insertFile(bannerFile, loginId);
				survey.setBanner_file_path(fileSave.getFile_path());
				survey.setBanner_filename(fileSave.getFile_nm());
			}

			survey.setLast_mdfr(loginId);

			if (survey.getSurvey_no() == 0) {
				survey.setFrst_rgtr(loginId);
				int maxSurveyNo = surveyRepository.getMaxSurveyNo();
				survey.setSurvey_no(maxSurveyNo);
				surveyRepository.insertSurvey(survey);
			} else {
				surveyRepository.updateSurvey(survey);
			}

			result.put("result", "success");
		} catch (Exception e) {
			result.put("result", "fail");
			result.put("message", e.getMessage());
		}
		return result;
	}

	public Map<String, Object> deleteSurvey(int surveyNo) {
		Map<String, Object> result = new HashMap<>();
		try {
			KbStartersSurveyDTO survey = new KbStartersSurveyDTO();
			survey.setSurvey_no(surveyNo);
			surveyRepository.deleteSurvey(survey);
			result.put("result", "success");
		} catch (Exception e) {
			result.put("result", "fail");
			result.put("message", e.getMessage());
		}
		return result;
	}
}
