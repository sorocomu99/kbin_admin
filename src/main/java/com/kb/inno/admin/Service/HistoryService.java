/**
 * 파일명     : HistoryService.java
 * 화면명     : 연혁 관리
 * 설명       : 연혁 조회 및 등록, 수정, 삭제 처리
 * 최초개발일  : 2024.12.31
 * 최초개발자  : 이훈희
 * ==========================================================
 *   수정일            수정자           설명
 * ==========================================================
 */
package com.kb.inno.admin.Service;

import com.kb.inno.admin.DAO.HistoryDAO;
import com.kb.inno.admin.DTO.HistoryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.*;

@Service
@RequiredArgsConstructor
public class HistoryService {

    // DAO 연결
    private final HistoryDAO historyDAO;
    
    /**
     * @return
     * 연혁 리스트 조회
     */
    //public List<HistoryDTO> selectList(int menuId, int page) {
    public void selectList(int menuId, int page, Model model) {
        //List<HistoryDTO> selectList = historyDAO.selectList();
        HistoryDTO historyDTO = new HistoryDTO();
        // 페이지의 전체 글 갯수
        int allCount = historyDAO.selectPageCount();

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
        historyDTO.setEnd(end);
        int start = end + 1 - pageLetter;



        List<HistoryDTO> selectList = historyDAO.selectListOne(historyDTO);
        List<HistoryDTO> previewList = historyDAO.selectList();
        model.addAttribute("repeat", repeat);
        model.addAttribute("currentPage", page);
        model.addAttribute("selectList", selectList);
        model.addAttribute("previewList", previewList);
        model.addAttribute("menuId", menuId);
        //return selectList;
    }

    /**
     * 연혁 등록
     * @param historyDTO
     * @param map
     * @param loginId
     * @return
     */
    public HashMap<String, Object> insert(HistoryDTO historyDTO, HashMap<String, Object> map, int loginId) throws Exception {
        HashMap<String, Object> resultMap = new HashMap<>();

        historyDTO.setFrst_rgtr(loginId);  //최초등록자 세팅
        historyDTO.setLast_mdfr(loginId);  //최종수정자 세팅

        //해당년도의 데이터 존재 여부 확인
        int totalCnt = historyDAO.selectCount(historyDTO);
        if (totalCnt > 0) {
            resultMap.put("errorCd", "99");
            resultMap.put("errorMsg", "입력하신 해당년도의 연혁이 존재합니다.");
            return resultMap;
        }

        //동적으로 생성된 값이 넘어오므로 항목 1 부터 10까지 값이 있는 경우 해당 DTO에 값을 담아 for문으로 insert 한다.
        try {
            int mainSn = 1;
            //항목 1 저장
            if (map.get("main_input1") != null && !map.get("main_input1").equals("")) {
                historyDTO.setHstry_lclsf_sn(mainSn);  //연혁 대분류 일련번호
                historyDTO.setHstry_lclsf_ttl(map.get("main_input1").toString());  //연혁 대분류 제목
                historyDTO.setHstry_lclsf_sort_no(mainSn);  //연혁 대분류 정렬번호

                if (map.get("subInputList1") != null && !map.get("subInputList1").equals("")) {
                    int subSn1 = 1;
                    List<String> subInput1 = new ArrayList<>();
                    subInput1 = (List) map.get("subInputList1");
                    for (int i = 0; i < subInput1.size(); i++) {
                        historyDTO.setHstry_sclsf_sn(subSn1);  //연혁 소분류 일련번호
                        historyDTO.setHstry_sclsf_ttl(subInput1.get(i));  //연혁 소분류 제목
                        historyDTO.setHstry_sclsf_sort_no(subSn1);  //연혁 소분류 정렬번호
                        historyDTO.setHstry_sclsf_up_lclsf_sn(mainSn);  //연혁 소분류 상위 대분류 일련번호

                        int ins = historyDAO.insert(historyDTO);
                        if (ins != 1) {
                            historyDAO.delete(historyDTO);
                            resultMap.put("errorCd", "90");
                            resultMap.put("errorMsg", "등록 중 오류가 발생했습니다.");
                            return resultMap;
                        }
                        subSn1 = subSn1 + 1;
                    }
                }
            }

            //항목 2 저장
            if (map.get("main_input2") != null && !map.get("main_input2").equals("")) {
                mainSn = mainSn + 1;
                historyDTO.setHstry_lclsf_sn(mainSn);  //연혁 대분류 일련번호
                historyDTO.setHstry_lclsf_ttl(map.get("main_input2").toString());  //연혁 대분류 제목
                historyDTO.setHstry_lclsf_sort_no(mainSn);  //연혁 대분류 정렬번호

                if (map.get("subInputList2") != null && !map.get("subInputList2").equals("")) {
                    int subSn2 = 1;
                    List<String> subInput2 = new ArrayList<>();
                    subInput2 = (List) map.get("subInputList2");
                    for (int i = 0; i < subInput2.size(); i++) {
                        historyDTO.setHstry_sclsf_sn(subSn2);  //연혁 소분류 일련번호
                        historyDTO.setHstry_sclsf_ttl(subInput2.get(i));  //연혁 소분류 제목
                        historyDTO.setHstry_sclsf_sort_no(subSn2);  //연혁 소분류 정렬번호
                        historyDTO.setHstry_sclsf_up_lclsf_sn(mainSn);  //연혁 소분류 상위 대분류 일련번호

                        int ins = historyDAO.insert(historyDTO);
                        if (ins != 1) {
                            historyDAO.delete(historyDTO);
                            resultMap.put("errorCd", "90");
                            resultMap.put("errorMsg", "등록 중 오류가 발생했습니다.");
                            return resultMap;
                        }
                        subSn2 = subSn2 + 1;
                    }
                }
            }

            //항목 3 저장
            if (map.get("main_input3") != null && !map.get("main_input3").equals("")) {
                mainSn = mainSn + 1;
                historyDTO.setHstry_lclsf_sn(mainSn);  //연혁 대분류 일련번호
                historyDTO.setHstry_lclsf_ttl(map.get("main_input3").toString());  //연혁 대분류 제목
                historyDTO.setHstry_lclsf_sort_no(mainSn);  //연혁 대분류 정렬번호

                if (map.get("subInputList3") != null && !map.get("subInputList3").equals("")) {
                    int subSn3 = 1;
                    List<String> subInput3 = new ArrayList<>();
                    subInput3 = (List) map.get("subInputList3");
                    for (int i = 0; i < subInput3.size(); i++) {
                        historyDTO.setHstry_sclsf_sn(subSn3);  //연혁 소분류 일련번호
                        historyDTO.setHstry_sclsf_ttl(subInput3.get(i));  //연혁 소분류 제목
                        historyDTO.setHstry_sclsf_sort_no(subSn3);  //연혁 소분류 정렬번호
                        historyDTO.setHstry_sclsf_up_lclsf_sn(mainSn);  //연혁 소분류 상위 대분류 일련번호

                        int ins = historyDAO.insert(historyDTO);
                        if (ins != 1) {
                            historyDAO.delete(historyDTO);
                            resultMap.put("errorCd", "90");
                            resultMap.put("errorMsg", "등록 중 오류가 발생했습니다.");
                            return resultMap;
                        }
                        subSn3 = subSn3 + 1;
                    }
                }
            }

            //항목 4 저장
            if (map.get("main_input4") != null && !map.get("main_input4").equals("")) {
                mainSn = mainSn + 1;
                historyDTO.setHstry_lclsf_sn(mainSn);  //연혁 대분류 일련번호
                historyDTO.setHstry_lclsf_ttl(map.get("main_input4").toString());  //연혁 대분류 제목
                historyDTO.setHstry_lclsf_sort_no(mainSn);  //연혁 대분류 정렬번호

                if (map.get("subInputList4") != null && !map.get("subInputList4").equals("")) {
                    int subSn4 = 1;
                    List<String> subInput4 = new ArrayList<>();
                    subInput4 = (List) map.get("subInputList4");
                    for (int i = 0; i < subInput4.size(); i++) {
                        historyDTO.setHstry_sclsf_sn(subSn4);  //연혁 소분류 일련번호
                        historyDTO.setHstry_sclsf_ttl(subInput4.get(i));  //연혁 소분류 제목
                        historyDTO.setHstry_sclsf_sort_no(subSn4);  //연혁 소분류 정렬번호
                        historyDTO.setHstry_sclsf_up_lclsf_sn(mainSn);  //연혁 소분류 상위 대분류 일련번호

                        int ins = historyDAO.insert(historyDTO);
                        if (ins != 1) {
                            historyDAO.delete(historyDTO);
                            resultMap.put("errorCd", "90");
                            resultMap.put("errorMsg", "등록 중 오류가 발생했습니다.");
                            return resultMap;
                        }
                        subSn4 = subSn4 + 1;
                    }
                }
            }

            //항목 5 저장
            if (map.get("main_input5") != null && !map.get("main_input5").equals("")) {
                mainSn = mainSn + 1;
                historyDTO.setHstry_lclsf_sn(mainSn);  //연혁 대분류 일련번호
                historyDTO.setHstry_lclsf_ttl(map.get("main_input5").toString());  //연혁 대분류 제목
                historyDTO.setHstry_lclsf_sort_no(mainSn);  //연혁 대분류 정렬번호

                if (map.get("subInputList5") != null && !map.get("subInputList5").equals("")) {
                    int subSn5 = 1;
                    List<String> subInput5 = new ArrayList<>();
                    subInput5 = (List) map.get("subInputList5");
                    for (int i = 0; i < subInput5.size(); i++) {
                        historyDTO.setHstry_sclsf_sn(subSn5);  //연혁 소분류 일련번호
                        historyDTO.setHstry_sclsf_ttl(subInput5.get(i));  //연혁 소분류 제목
                        historyDTO.setHstry_sclsf_sort_no(subSn5);  //연혁 소분류 정렬번호
                        historyDTO.setHstry_sclsf_up_lclsf_sn(mainSn);  //연혁 소분류 상위 대분류 일련번호

                        int ins = historyDAO.insert(historyDTO);
                        if (ins != 1) {
                            historyDAO.delete(historyDTO);
                            resultMap.put("errorCd", "90");
                            resultMap.put("errorMsg", "등록 중 오류가 발생했습니다.");
                            return resultMap;
                        }
                        subSn5 = subSn5 + 1;
                    }
                }
            }

            //항목 6 저장
            if (map.get("main_input6") != null && !map.get("main_input6").equals("")) {
                mainSn = mainSn + 1;
                historyDTO.setHstry_lclsf_sn(mainSn);  //연혁 대분류 일련번호
                historyDTO.setHstry_lclsf_ttl(map.get("main_input6").toString());  //연혁 대분류 제목
                historyDTO.setHstry_lclsf_sort_no(mainSn);  //연혁 대분류 정렬번호

                if (map.get("subInputList6") != null && !map.get("subInputList6").equals("")) {
                    int subSn6 = 1;
                    List<String> subInput6 = new ArrayList<>();
                    subInput6 = (List) map.get("subInputList6");
                    for (int i = 0; i < subInput6.size(); i++) {
                        historyDTO.setHstry_sclsf_sn(subSn6);  //연혁 소분류 일련번호
                        historyDTO.setHstry_sclsf_ttl(subInput6.get(i));  //연혁 소분류 제목
                        historyDTO.setHstry_sclsf_sort_no(subSn6);  //연혁 소분류 정렬번호
                        historyDTO.setHstry_sclsf_up_lclsf_sn(mainSn);  //연혁 소분류 상위 대분류 일련번호

                        int ins = historyDAO.insert(historyDTO);
                        if (ins != 1) {
                            historyDAO.delete(historyDTO);
                            resultMap.put("errorCd", "90");
                            resultMap.put("errorMsg", "등록 중 오류가 발생했습니다.");
                            return resultMap;
                        }
                        subSn6 = subSn6 + 1;
                    }
                }
            }

            //항목 7 저장
            if (map.get("main_input7") != null && !map.get("main_input7").equals("")) {
                mainSn = mainSn + 1;
                historyDTO.setHstry_lclsf_sn(mainSn);  //연혁 대분류 일련번호
                historyDTO.setHstry_lclsf_ttl(map.get("main_input7").toString());  //연혁 대분류 제목
                historyDTO.setHstry_lclsf_sort_no(mainSn);  //연혁 대분류 정렬번호

                if (map.get("subInputList7") != null && !map.get("subInputList7").equals("")) {
                    int subSn7 = 1;
                    List<String> subInput7 = new ArrayList<>();
                    subInput7 = (List) map.get("subInputList7");
                    for (int i = 0; i < subInput7.size(); i++) {
                        historyDTO.setHstry_sclsf_sn(subSn7);  //연혁 소분류 일련번호
                        historyDTO.setHstry_sclsf_ttl(subInput7.get(i));  //연혁 소분류 제목
                        historyDTO.setHstry_sclsf_sort_no(subSn7);  //연혁 소분류 정렬번호
                        historyDTO.setHstry_sclsf_up_lclsf_sn(mainSn);  //연혁 소분류 상위 대분류 일련번호

                        int ins = historyDAO.insert(historyDTO);
                        if (ins != 1) {
                            historyDAO.delete(historyDTO);
                            resultMap.put("errorCd", "90");
                            resultMap.put("errorMsg", "등록 중 오류가 발생했습니다.");
                            return resultMap;
                        }
                        subSn7 = subSn7 + 1;
                    }
                }
            }

            //항목 8 저장
            if (map.get("main_input8") != null && !map.get("main_input8").equals("")) {
                mainSn = mainSn + 1;
                historyDTO.setHstry_lclsf_sn(mainSn);  //연혁 대분류 일련번호
                historyDTO.setHstry_lclsf_ttl(map.get("main_input8").toString());  //연혁 대분류 제목
                historyDTO.setHstry_lclsf_sort_no(mainSn);  //연혁 대분류 정렬번호

                if (map.get("subInputList8") != null && !map.get("subInputList8").equals("")) {
                    int subSn8 = 1;
                    List<String> subInput8 = new ArrayList<>();
                    subInput8 = (List) map.get("subInputList8");
                    for (int i = 0; i < subInput8.size(); i++) {
                        historyDTO.setHstry_sclsf_sn(subSn8);  //연혁 소분류 일련번호
                        historyDTO.setHstry_sclsf_ttl(subInput8.get(i));  //연혁 소분류 제목
                        historyDTO.setHstry_sclsf_sort_no(subSn8);  //연혁 소분류 정렬번호
                        historyDTO.setHstry_sclsf_up_lclsf_sn(mainSn);  //연혁 소분류 상위 대분류 일련번호

                        int ins = historyDAO.insert(historyDTO);
                        if (ins != 1) {
                            historyDAO.delete(historyDTO);
                            resultMap.put("errorCd", "90");
                            resultMap.put("errorMsg", "등록 중 오류가 발생했습니다.");
                            return resultMap;
                        }
                        subSn8 = subSn8 + 1;
                    }
                }
            }

            //항목 9 저장
            if (map.get("main_input9") != null && !map.get("main_input9").equals("")) {
                mainSn = mainSn + 1;
                historyDTO.setHstry_lclsf_sn(mainSn);  //연혁 대분류 일련번호
                historyDTO.setHstry_lclsf_ttl(map.get("main_input9").toString());  //연혁 대분류 제목
                historyDTO.setHstry_lclsf_sort_no(mainSn);  //연혁 대분류 정렬번호

                if (map.get("subInputList9") != null && !map.get("subInputList9").equals("")) {
                    int subSn9 = 1;
                    List<String> subInput9 = new ArrayList<>();
                    subInput9 = (List) map.get("subInputList9");
                    for (int i = 0; i < subInput9.size(); i++) {
                        historyDTO.setHstry_sclsf_sn(subSn9);  //연혁 소분류 일련번호
                        historyDTO.setHstry_sclsf_ttl(subInput9.get(i));  //연혁 소분류 제목
                        historyDTO.setHstry_sclsf_sort_no(subSn9);  //연혁 소분류 정렬번호
                        historyDTO.setHstry_sclsf_up_lclsf_sn(mainSn);  //연혁 소분류 상위 대분류 일련번호

                        int ins = historyDAO.insert(historyDTO);
                        if (ins != 1) {
                            historyDAO.delete(historyDTO);
                            resultMap.put("errorCd", "90");
                            resultMap.put("errorMsg", "등록 중 오류가 발생했습니다.");
                            return resultMap;
                        }
                        subSn9 = subSn9 + 1;
                    }
                }
            }

            //항목 10 저장
            if (map.get("main_input10") != null && !map.get("main_input10").equals("")) {
                mainSn = mainSn + 1;
                historyDTO.setHstry_lclsf_sn(mainSn);  //연혁 대분류 일련번호
                historyDTO.setHstry_lclsf_ttl(map.get("main_input10").toString());  //연혁 대분류 제목
                historyDTO.setHstry_lclsf_sort_no(mainSn);  //연혁 대분류 정렬번호

                if (map.get("subInputList10") != null && !map.get("subInputList10").equals("")) {
                    int subSn10 = 1;
                    List<String> subInput10 = new ArrayList<>();
                    subInput10 = (List) map.get("subInputList10");
                    for (int i = 0; i < subInput10.size(); i++) {
                        historyDTO.setHstry_sclsf_sn(subSn10);  //연혁 소분류 일련번호
                        historyDTO.setHstry_sclsf_ttl(subInput10.get(i));  //연혁 소분류 제목
                        historyDTO.setHstry_sclsf_sort_no(subSn10);  //연혁 소분류 정렬번호
                        historyDTO.setHstry_sclsf_up_lclsf_sn(mainSn);  //연혁 소분류 상위 대분류 일련번호

                        int ins = historyDAO.insert(historyDTO);
                        if (ins != 1) {
                            historyDAO.delete(historyDTO);
                            resultMap.put("errorCd", "90");
                            resultMap.put("errorMsg", "등록 중 오류가 발생했습니다.");
                            return resultMap;
                        }
                        subSn10 = subSn10 + 1;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            historyDAO.delete(historyDTO);
            resultMap.put("errorCd", "90");
            resultMap.put("errorMsg", "등록 중 오류가 발생했습니다.");
            return resultMap;
        }

        resultMap.put("errorCd", "00");
        resultMap.put("errorMsg", "연혁이 정상적으로 등록되었습니다.");

        return resultMap;
    }

    /**
     * 연혁 수정 페이지 이동
     * @param hstry_yr
     * @return
     */
    //public List<HistoryDTO> selectDetail(String hstry_yr) {
    public void selectDetail(String hstry_yr, Model model) {
        HistoryDTO historyDTO = new HistoryDTO();
        historyDTO.setHstry_yr(hstry_yr);
        historyDTO.setHstry_lclsf_sn(1);
        List<HistoryDTO> selectDetail = historyDAO.selectDetail(historyDTO);
        model.addAttribute("selectDetail", selectDetail);

        historyDTO.setHstry_lclsf_sn(2);
        List<HistoryDTO> selectDetail2 = historyDAO.selectDetail(historyDTO);
        model.addAttribute("selectDetail2", selectDetail2);

        historyDTO.setHstry_lclsf_sn(3);
        List<HistoryDTO> selectDetail3 = historyDAO.selectDetail(historyDTO);
        model.addAttribute("selectDetail3", selectDetail3);

        historyDTO.setHstry_lclsf_sn(4);
        List<HistoryDTO> selectDetail4 = historyDAO.selectDetail(historyDTO);
        model.addAttribute("selectDetail4", selectDetail4);

        historyDTO.setHstry_lclsf_sn(5);
        List<HistoryDTO> selectDetail5 = historyDAO.selectDetail(historyDTO);
        model.addAttribute("selectDetail5", selectDetail5);

        historyDTO.setHstry_lclsf_sn(6);
        List<HistoryDTO> selectDetail6 = historyDAO.selectDetail(historyDTO);
        model.addAttribute("selectDetail6", selectDetail6);

        historyDTO.setHstry_lclsf_sn(7);
        List<HistoryDTO> selectDetail7 = historyDAO.selectDetail(historyDTO);
        model.addAttribute("selectDetail7", selectDetail7);

        historyDTO.setHstry_lclsf_sn(8);
        List<HistoryDTO> selectDetail8 = historyDAO.selectDetail(historyDTO);
        model.addAttribute("selectDetail8", selectDetail8);

        historyDTO.setHstry_lclsf_sn(9);
        List<HistoryDTO> selectDetail9 = historyDAO.selectDetail(historyDTO);
        model.addAttribute("selectDetail9", selectDetail9);

        historyDTO.setHstry_lclsf_sn(10);
        List<HistoryDTO> selectDetail10 = historyDAO.selectDetail(historyDTO);
        model.addAttribute("selectDetail10", selectDetail10);

        int maxLcl = historyDAO.selectMaxLcl(historyDTO);
        model.addAttribute("maxLcl", maxLcl+1);
        //return selectDetail;
    }

    /**
     * 연혁 업데이트
     * @param historyDTO
     * @param map
     * @param loginId
     * @return
     */
    public HashMap<String, Object> update(HistoryDTO historyDTO, HashMap<String, Object> map, int loginId) {
        HashMap<String, Object> resultMap = new HashMap<>();

        historyDTO.setFrst_rgtr(loginId);  //최초등록자 세팅
        historyDTO.setLast_mdfr(loginId);  //최종수정자 세팅

        //동적으로 생성된 값이 넘어오므로 항목 1 부터 10까지 값이 있는 경우 해당 DTO에 값을 담아 for문으로 insert 한다.
        //저장전 기존 데이터를 삭제 후 처리한다.
        historyDAO.delete(historyDTO);
        int mainSn = 1;
        //항목 1 저장
        if (map.get("main_input1") != null && !map.get("main_input1").equals("")) {
            historyDTO.setHstry_lclsf_sn(mainSn);  //연혁 대분류 일련번호
            historyDTO.setHstry_lclsf_ttl(map.get("main_input1").toString());  //연혁 대분류 제목
            historyDTO.setHstry_lclsf_sort_no(mainSn);  //연혁 대분류 정렬번호

            if (map.get("subInputList1") != null && !map.get("subInputList1").equals("")) {
                int subSn1 = 1;
                List<String> subInput1 = new ArrayList<>();
                subInput1 = (List) map.get("subInputList1");
                for (int i = 0; i < subInput1.size(); i++) {
                    historyDTO.setHstry_sclsf_sn(subSn1);  //연혁 소분류 일련번호
                    historyDTO.setHstry_sclsf_ttl(subInput1.get(i));  //연혁 소분류 제목
                    historyDTO.setHstry_sclsf_sort_no(subSn1);  //연혁 소분류 정렬번호
                    historyDTO.setHstry_sclsf_up_lclsf_sn(mainSn);  //연혁 소분류 상위 대분류 일련번호

                    int ins = historyDAO.insert(historyDTO);
                    if (ins != 1) {
                        historyDAO.delete(historyDTO);
                        resultMap.put("errorCd", "90");
                        resultMap.put("errorMsg", "수정 중 오류가 발생했습니다.");
                        return resultMap;
                    }
                    subSn1 = subSn1 + 1;
                }
            }
        }

        //항목 2 저장
        if (map.get("main_input2") != null && !map.get("main_input2").equals("")) {
            mainSn = mainSn + 1;
            historyDTO.setHstry_lclsf_sn(mainSn);  //연혁 대분류 일련번호
            historyDTO.setHstry_lclsf_ttl(map.get("main_input2").toString());  //연혁 대분류 제목
            historyDTO.setHstry_lclsf_sort_no(mainSn);  //연혁 대분류 정렬번호

            if (map.get("subInputList2") != null && !map.get("subInputList2").equals("")) {
                int subSn2 = 1;
                List<String> subInput2 = new ArrayList<>();
                subInput2 = (List) map.get("subInputList2");
                for (int i = 0; i < subInput2.size(); i++) {
                    historyDTO.setHstry_sclsf_sn(subSn2);  //연혁 소분류 일련번호
                    historyDTO.setHstry_sclsf_ttl(subInput2.get(i));  //연혁 소분류 제목
                    historyDTO.setHstry_sclsf_sort_no(subSn2);  //연혁 소분류 정렬번호
                    historyDTO.setHstry_sclsf_up_lclsf_sn(mainSn);  //연혁 소분류 상위 대분류 일련번호

                    int ins = historyDAO.insert(historyDTO);
                    if (ins != 1) {
                        historyDAO.delete(historyDTO);
                        resultMap.put("errorCd", "90");
                        resultMap.put("errorMsg", "수정 중 오류가 발생했습니다.");
                        return resultMap;
                    }
                    subSn2 = subSn2 + 1;
                }
            }
        }

        //항목 3 저장
        if (map.get("main_input3") != null && !map.get("main_input3").equals("")) {
            mainSn = mainSn + 1;
            historyDTO.setHstry_lclsf_sn(mainSn);  //연혁 대분류 일련번호
            historyDTO.setHstry_lclsf_ttl(map.get("main_input3").toString());  //연혁 대분류 제목
            historyDTO.setHstry_lclsf_sort_no(mainSn);  //연혁 대분류 정렬번호

            if (map.get("subInputList3") != null && !map.get("subInputList3").equals("")) {
                int subSn3 = 1;
                List<String> subInput3 = new ArrayList<>();
                subInput3 = (List) map.get("subInputList3");
                for (int i = 0; i < subInput3.size(); i++) {
                    historyDTO.setHstry_sclsf_sn(subSn3);  //연혁 소분류 일련번호
                    historyDTO.setHstry_sclsf_ttl(subInput3.get(i));  //연혁 소분류 제목
                    historyDTO.setHstry_sclsf_sort_no(subSn3);  //연혁 소분류 정렬번호
                    historyDTO.setHstry_sclsf_up_lclsf_sn(mainSn);  //연혁 소분류 상위 대분류 일련번호

                    int ins = historyDAO.insert(historyDTO);
                    if (ins != 1) {
                        historyDAO.delete(historyDTO);
                        resultMap.put("errorCd", "90");
                        resultMap.put("errorMsg", "수정 중 오류가 발생했습니다.");
                        return resultMap;
                    }
                    subSn3 = subSn3 + 1;
                }
            }
        }

        //항목 4 저장
        if (map.get("main_input4") != null && !map.get("main_input4").equals("")) {
            mainSn = mainSn + 1;
            historyDTO.setHstry_lclsf_sn(mainSn);  //연혁 대분류 일련번호
            historyDTO.setHstry_lclsf_ttl(map.get("main_input4").toString());  //연혁 대분류 제목
            historyDTO.setHstry_lclsf_sort_no(mainSn);  //연혁 대분류 정렬번호

            if (map.get("subInputList4") != null && !map.get("subInputList4").equals("")) {
                int subSn4 = 1;
                List<String> subInput4 = new ArrayList<>();
                subInput4 = (List) map.get("subInputList4");
                for (int i = 0; i < subInput4.size(); i++) {
                    historyDTO.setHstry_sclsf_sn(subSn4);  //연혁 소분류 일련번호
                    historyDTO.setHstry_sclsf_ttl(subInput4.get(i));  //연혁 소분류 제목
                    historyDTO.setHstry_sclsf_sort_no(subSn4);  //연혁 소분류 정렬번호
                    historyDTO.setHstry_sclsf_up_lclsf_sn(mainSn);  //연혁 소분류 상위 대분류 일련번호

                    int ins = historyDAO.insert(historyDTO);
                    if (ins != 1) {
                        historyDAO.delete(historyDTO);
                        resultMap.put("errorCd", "90");
                        resultMap.put("errorMsg", "수정 중 오류가 발생했습니다.");
                        return resultMap;
                    }
                    subSn4 = subSn4 + 1;
                }
            }
        }

        //항목 5 저장
        if (map.get("main_input5") != null && !map.get("main_input5").equals("")) {
            mainSn = mainSn + 1;
            historyDTO.setHstry_lclsf_sn(mainSn);  //연혁 대분류 일련번호
            historyDTO.setHstry_lclsf_ttl(map.get("main_input5").toString());  //연혁 대분류 제목
            historyDTO.setHstry_lclsf_sort_no(mainSn);  //연혁 대분류 정렬번호

            if (map.get("subInputList5") != null && !map.get("subInputList5").equals("")) {
                int subSn5 = 1;
                List<String> subInput5 = new ArrayList<>();
                subInput5 = (List) map.get("subInputList5");
                for (int i = 0; i < subInput5.size(); i++) {
                    historyDTO.setHstry_sclsf_sn(subSn5);  //연혁 소분류 일련번호
                    historyDTO.setHstry_sclsf_ttl(subInput5.get(i));  //연혁 소분류 제목
                    historyDTO.setHstry_sclsf_sort_no(subSn5);  //연혁 소분류 정렬번호
                    historyDTO.setHstry_sclsf_up_lclsf_sn(mainSn);  //연혁 소분류 상위 대분류 일련번호

                    int ins = historyDAO.insert(historyDTO);
                    if (ins != 1) {
                        historyDAO.delete(historyDTO);
                        resultMap.put("errorCd", "90");
                        resultMap.put("errorMsg", "수정 중 오류가 발생했습니다.");
                        return resultMap;
                    }
                    subSn5 = subSn5 + 1;
                }
            }
        }

        //항목 6 저장
        if (map.get("main_input6") != null && !map.get("main_input6").equals("")) {
            mainSn = mainSn + 1;
            historyDTO.setHstry_lclsf_sn(mainSn);  //연혁 대분류 일련번호
            historyDTO.setHstry_lclsf_ttl(map.get("main_input6").toString());  //연혁 대분류 제목
            historyDTO.setHstry_lclsf_sort_no(mainSn);  //연혁 대분류 정렬번호

            if (map.get("subInputList6") != null && !map.get("subInputList6").equals("")) {
                int subSn6 = 1;
                List<String> subInput6 = new ArrayList<>();
                subInput6 = (List) map.get("subInputList6");
                for (int i = 0; i < subInput6.size(); i++) {
                    historyDTO.setHstry_sclsf_sn(subSn6);  //연혁 소분류 일련번호
                    historyDTO.setHstry_sclsf_ttl(subInput6.get(i));  //연혁 소분류 제목
                    historyDTO.setHstry_sclsf_sort_no(subSn6);  //연혁 소분류 정렬번호
                    historyDTO.setHstry_sclsf_up_lclsf_sn(mainSn);  //연혁 소분류 상위 대분류 일련번호

                    int ins = historyDAO.insert(historyDTO);
                    if (ins != 1) {
                        historyDAO.delete(historyDTO);
                        resultMap.put("errorCd", "90");
                        resultMap.put("errorMsg", "수정 중 오류가 발생했습니다.");
                        return resultMap;
                    }
                    subSn6 = subSn6 + 1;
                }
            }
        }

        //항목 7 저장
        if (map.get("main_input7") != null && !map.get("main_input7").equals("")) {
            mainSn = mainSn + 1;
            historyDTO.setHstry_lclsf_sn(mainSn);  //연혁 대분류 일련번호
            historyDTO.setHstry_lclsf_ttl(map.get("main_input7").toString());  //연혁 대분류 제목
            historyDTO.setHstry_lclsf_sort_no(mainSn);  //연혁 대분류 정렬번호

            if (map.get("subInputList7") != null && !map.get("subInputList7").equals("")) {
                int subSn7 = 1;
                List<String> subInput7 = new ArrayList<>();
                subInput7 = (List) map.get("subInputList7");
                for (int i = 0; i < subInput7.size(); i++) {
                    historyDTO.setHstry_sclsf_sn(subSn7);  //연혁 소분류 일련번호
                    historyDTO.setHstry_sclsf_ttl(subInput7.get(i));  //연혁 소분류 제목
                    historyDTO.setHstry_sclsf_sort_no(subSn7);  //연혁 소분류 정렬번호
                    historyDTO.setHstry_sclsf_up_lclsf_sn(mainSn);  //연혁 소분류 상위 대분류 일련번호

                    int ins = historyDAO.insert(historyDTO);
                    if (ins != 1) {
                        historyDAO.delete(historyDTO);
                        resultMap.put("errorCd", "90");
                        resultMap.put("errorMsg", "수정 중 오류가 발생했습니다.");
                        return resultMap;
                    }
                    subSn7 = subSn7 + 1;
                }
            }
        }

        //항목 8 저장
        if (map.get("main_input8") != null && !map.get("main_input8").equals("")) {
            mainSn = mainSn + 1;
            historyDTO.setHstry_lclsf_sn(mainSn);  //연혁 대분류 일련번호
            historyDTO.setHstry_lclsf_ttl(map.get("main_input8").toString());  //연혁 대분류 제목
            historyDTO.setHstry_lclsf_sort_no(mainSn);  //연혁 대분류 정렬번호

            if (map.get("subInputList8") != null && !map.get("subInputList8").equals("")) {
                int subSn8 = 1;
                List<String> subInput8 = new ArrayList<>();
                subInput8 = (List) map.get("subInputList8");
                for (int i = 0; i < subInput8.size(); i++) {
                    historyDTO.setHstry_sclsf_sn(subSn8);  //연혁 소분류 일련번호
                    historyDTO.setHstry_sclsf_ttl(subInput8.get(i));  //연혁 소분류 제목
                    historyDTO.setHstry_sclsf_sort_no(subSn8);  //연혁 소분류 정렬번호
                    historyDTO.setHstry_sclsf_up_lclsf_sn(mainSn);  //연혁 소분류 상위 대분류 일련번호

                    int ins = historyDAO.insert(historyDTO);
                    if (ins != 1) {
                        historyDAO.delete(historyDTO);
                        resultMap.put("errorCd", "90");
                        resultMap.put("errorMsg", "수정 중 오류가 발생했습니다.");
                        return resultMap;
                    }
                    subSn8 = subSn8 + 1;
                }
            }
        }

        //항목 9 저장
        if (map.get("main_input9") != null && !map.get("main_input9").equals("")) {
            mainSn = mainSn + 1;
            historyDTO.setHstry_lclsf_sn(mainSn);  //연혁 대분류 일련번호
            historyDTO.setHstry_lclsf_ttl(map.get("main_input9").toString());  //연혁 대분류 제목
            historyDTO.setHstry_lclsf_sort_no(mainSn);  //연혁 대분류 정렬번호

            if (map.get("subInputList9") != null && !map.get("subInputList9").equals("")) {
                int subSn9 = 1;
                List<String> subInput9 = new ArrayList<>();
                subInput9 = (List) map.get("subInputList9");
                for (int i = 0; i < subInput9.size(); i++) {
                    historyDTO.setHstry_sclsf_sn(subSn9);  //연혁 소분류 일련번호
                    historyDTO.setHstry_sclsf_ttl(subInput9.get(i));  //연혁 소분류 제목
                    historyDTO.setHstry_sclsf_sort_no(subSn9);  //연혁 소분류 정렬번호
                    historyDTO.setHstry_sclsf_up_lclsf_sn(mainSn);  //연혁 소분류 상위 대분류 일련번호

                    int ins = historyDAO.insert(historyDTO);
                    if (ins != 1) {
                        historyDAO.delete(historyDTO);
                        resultMap.put("errorCd", "90");
                        resultMap.put("errorMsg", "수정 중 오류가 발생했습니다.");
                        return resultMap;
                    }
                    subSn9 = subSn9 + 1;
                }
            }
        }

        //항목 10 저장
        if (map.get("main_input10") != null && !map.get("main_input10").equals("")) {
            mainSn = mainSn + 1;
            historyDTO.setHstry_lclsf_sn(mainSn);  //연혁 대분류 일련번호
            historyDTO.setHstry_lclsf_ttl(map.get("main_input10").toString());  //연혁 대분류 제목
            historyDTO.setHstry_lclsf_sort_no(mainSn);  //연혁 대분류 정렬번호

            if (map.get("subInputList10") != null && !map.get("subInputList10").equals("")) {
                int subSn10 = 1;
                List<String> subInput10 = new ArrayList<>();
                subInput10 = (List) map.get("subInputList10");
                for (int i = 0; i < subInput10.size(); i++) {
                    historyDTO.setHstry_sclsf_sn(subSn10);  //연혁 소분류 일련번호
                    historyDTO.setHstry_sclsf_ttl(subInput10.get(i));  //연혁 소분류 제목
                    historyDTO.setHstry_sclsf_sort_no(subSn10);  //연혁 소분류 정렬번호
                    historyDTO.setHstry_sclsf_up_lclsf_sn(mainSn);  //연혁 소분류 상위 대분류 일련번호

                    int ins = historyDAO.insert(historyDTO);
                    if (ins != 1) {
                        historyDAO.delete(historyDTO);
                        resultMap.put("errorCd", "90");
                        resultMap.put("errorMsg", "수정 중 오류가 발생했습니다.");
                        return resultMap;
                    }
                    subSn10 = subSn10 + 1;
                }
            }
        }

        resultMap.put("errorCd", "00");
        resultMap.put("errorMsg", "연혁이 정상적으로 수정 되었습니다.");

        return resultMap;
    }

    /**
     * 연혁 삭제
     * @param hstry_yr
     * @return
     */
    public int delete(String hstry_yr) {
        HistoryDTO historyDTO = new HistoryDTO();
        historyDTO.setHstry_yr(hstry_yr);
        return historyDAO.delete(historyDTO);
    }

    public String existYearData(String hstryYr) {
        //해당년도의 데이터 존재 여부 확인
        HistoryDTO historyDTO = new HistoryDTO();
        historyDTO.setHstry_yr(hstryYr);
        int totalCnt = historyDAO.selectCount(historyDTO);
        return totalCnt > 0 ? "Y" : "N";
    }
}
