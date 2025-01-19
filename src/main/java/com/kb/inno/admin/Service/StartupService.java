package com.kb.inno.admin.Service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.kb.inno.admin.DAO.StartupDAO;
import com.kb.inno.admin.DTO.NoticeDTO;
import com.kb.inno.admin.DTO.SearchDTO;
import com.kb.inno.admin.DTO.StartupDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StartupService {

    // 서비스 연결
    private final StartupDAO startupDAO;

    // 스타트업 리스트 조회
    public void selectList(int menuId, Model model, String type, String keyword, int page) {
        // Search DTO에 담기
        StartupDTO startupDTO = new StartupDTO();
        startupDTO.setType(type);
        startupDTO.setKeyword(keyword);

        // 페이지의 전체 글 갯수
        int allCount = startupDAO.selectPageCount(startupDTO);

        // 한 페이지당 글 갯수
        int pageLetter = 10;

        // 전체 글 갯수 / 한 페이지에 나올 글 갯수
        int repeat = allCount / pageLetter;
        // 나머지가 0이 아니면
        if(allCount % pageLetter != 0){
            // 더하기
            repeat += 1;
        }

        // repeat이 0이면
        if(repeat == 0) {
            repeat = 1;
        }

        // 만약 가져온 페이지가 repeat 보다 크다면
        if(repeat < page) {
            page = repeat;
        }

        // 만약 가져온 페이지가 0이라면
        if(page < 1) {
            page = 1;
        }

        // 끝 페이지
        int end = page * pageLetter;
        startupDTO.setEnd(end);
        // 시작 페이지
        int start = end + 1 - pageLetter;

        startupDTO.setStart(start);

        // 리스트 조회
        List<StartupDTO> selectList = startupDAO.selectList(startupDTO);

        model.addAttribute("repeat", repeat);
        model.addAttribute("currentPage", page);
        model.addAttribute("selectList", selectList);
        model.addAttribute("menuId", menuId);
        model.addAttribute("type", startupDTO.getType());
        model.addAttribute("keyword", startupDTO.getKeyword());
    } 
    
    /**
    // KB 스타터스 조회
    public List<StartupDTO> selectList() {
    	List<StartupDTO> selectList = startupDAO.selectList();
        return selectList;
    }
    
   
    // KB 스타터스 조회
    public List<StartersDTO> select() {
    	List<StartersDTO>selectList = startersDAO.select();
        return selectList;
    }

    // KB 스타터스 등록
    public int insert(StartersDTO startersDTO, int loginId) {
        // 로그인 한 아이디 대입
        startersDTO.setFrst_rgtr(loginId);
        startersDTO.setLast_mdfr(loginId);

        return startersDAO.insert(startersDTO);
    }

    // KB 스타터스 수정
    public int update(StartersDTO startersDTO, int loginId) {
        // 로그인 한 아이디 대입
        startersDTO.setLast_mdfr(loginId);

        return startersDAO.update(startersDTO);
    }
    */
    
    
}