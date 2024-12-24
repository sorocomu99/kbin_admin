/**
 * 파일명     : HubService.java
 * 화면명     : HUB 센터 소식 관리
 * 설명       : HUB 센터 소식 조회 및 등록, 수정, 삭제 처리
 * 최초개발일 : 2024.11.14
 * 최초개발자 : 양윤지
 * ==========================================================
 *   수정일            수정자           설명
 * ==========================================================
 */
package com.kb.inno.admin.Service;

import com.kb.inno.admin.DAO.HubDAO;
import com.kb.inno.admin.DTO.*;
import com.kb.inno.common.FileUploader;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HubService {

    // DAO 연결
    private final HubDAO hubDAO;

    // 멤버 서비스 연결
    private final MemberService memberService;

    // 파일 경로
    @Value("src/main/resources/static/")
    private String staticPath;

    // HUB 센터 소식 리스트 조회
    public void selectList(int menuId, Model model, String type, String keyword, int page) {
        // Search DTO에 담기
        SearchDTO search = new SearchDTO();
        search.setType(type);
        search.setKeyword(keyword);

        // 페이지의 전체 글 갯수
        int allCount = hubDAO.selectPageCount(search);

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
        search.setEnd(end);
        // 시작 페이지
        int start = end + 1 - pageLetter;

        search.setStart(start);

        List<HubDTO> selectList = hubDAO.selectList(search);

        model.addAttribute("repeat", repeat);
        model.addAttribute("currentPage", page);
        model.addAttribute("selectList", selectList);
        model.addAttribute("menuId", menuId);
    }

    // 파일 저장
    public List<FileDTO> insertFile(HubDTO hubDTO, int loginId) {

        // 파일을 등록했는 지 확인
        int fileYn1 = hubDTO.getFile_yn1();
        int fileYn2 = hubDTO.getFile_yn2();
        int fileYn3 = hubDTO.getFile_yn3();

        // 파일 꺼내기
        // 리스트에 담기
        MultipartFile file1, file2, file3;
        List<MultipartFile> files = new ArrayList<>();

        if(fileYn1 == 1) {
            file1 = hubDTO.getHub_img();
            files.add(file1);
        }

        if(fileYn2 == 1) {
            file2 = hubDTO.getHub_mov();
            files.add(file2);
        }

        if(fileYn3 == 1) {
            file3 = hubDTO.getHub_file();
            files.add(file3);
        }

        // 파일 경로 설정
        Path path = Paths.get("D:\\");
        String savePath = path + "\\upload\\";

        // 파일 디렉토리 생성(없으면)
        File directory = new File(savePath);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        List<FileDTO> fileList = new ArrayList<>();

        // 파일 Array 길이만큼 반복
        for(int i = 0; i < files.size(); i++) {
            // 파일이 null 이면 패스, 아니면 파일 저장 실행
            if(files.get(i).getSize() == 0) {
                continue;
            } else {
                // 오리지널 파일 이름
                String originalFilename = files.get(i).getOriginalFilename();
                // 확장자 꺼내기
                String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
                // 파일 이름 설정
                String fileName = UUID.randomUUID().toString() + fileExtension;
                // 파일 저장
                try {
                    files.get(i).transferTo(new File(savePath, fileName));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                // 파일 사이즈 구하기
                int bytes = (int) files.get(i).getSize();

                // 빈 DTO 생성
                FileDTO fileDTO = new FileDTO();

                // DTO에 객체 담기
                fileDTO.setFile_nm(fileName);
                fileDTO.setOrgnl_file_nm(originalFilename);
                fileDTO.setFile_extn(fileExtension);
                fileDTO.setFile_path("\\upload\\");
                fileDTO.setFile_sz(bytes);

                // 파일 DTO에 로그인 한 사람 추가
                fileDTO.setFrst_rgtr(loginId);
                fileDTO.setLast_mdfr(loginId);

                // 파일 테이블 저장
                int result = hubDAO.insertFile(fileDTO);

                // 결과가 있으면 fileDTO return
                if(result == 1) {
                    fileList.add(fileDTO);
                }
            }
        }

        return fileList;
    }
    
    // HUB 센터 소식 등록
    public int insert(HubDTO hubDTO, int loginId) {
        // 로그인 한 사람 대입
        hubDTO.setFrst_rgtr(loginId);
        hubDTO.setLast_mdfr(loginId);

        // 1. 파일 디렉토리 및 테이블에 저장
        List<FileDTO> files = insertFile(hubDTO, loginId);

        // 2. 제휴 사례 저장
        if(!files.isEmpty() || files != null) {
            // 파일 일련 번호 대입
            for (int i = 0; i < files.size(); i++) {
                if (i == 0) {
                    hubDTO.setAtch_file_sn1(files.get(i).getFile_sn());
                }
                if (i == 1) {
                    hubDTO.setAtch_file_sn2(files.get(i).getFile_sn());
                }
                if (i == 2) {
                    hubDTO.setAtch_file_sn3(files.get(i).getFile_sn());
                }
            }
        }

        return hubDAO.insert(hubDTO);
    }

    // HUB 센터 소식 상세 조회
    public HubDTO select(int hub_sn) {
        return hubDAO.select(hub_sn);
    }
    
    // HUB 센터 소식 수정
    public int update(HubDTO hubDTO, int loginId) {

        // 경로 설정
        Path path = Paths.get("D:\\");
        // 0. 기존 파일 재조회
        HubDTO basicFile = hubDAO.select(hubDTO.getHub_sn());

        // 만약 파일을 삭제 한다면
        if(hubDTO.getDel_yn().equals("Y")) {
            File delete = new File(path + hubDTO.getHub_path() + hubDTO.getHub_file_name());
            int file_sn = hubDTO.getAtch_file_sn3();

            // 파일 삭제
            boolean removed = delete != null && delete.delete();

            // 2. 만약 경로에 파일이 지워졌다면
            if(removed) {
                // 테이블에 있는 파일 삭제
                hubDAO.deleteFile(file_sn);
            }
        }

        // 파일을 새로 등록했는 지 확인
        List<Integer> fileYnList = new ArrayList<>();

        // 파일 등록 여부를 리스트에 담기
        fileYnList.add(hubDTO.getFile_yn1());
        fileYnList.add(hubDTO.getFile_yn2());
        fileYnList.add(hubDTO.getFile_yn3());

        // 파일 여부
        List<Integer> fileSnList = new ArrayList<>();

        // 만약 파일을 새로 등록했다면 파일 테이블 포함 저장
        for(int i = 0; i < fileYnList.size(); i++) {
            // 새로 등록한 파일이 아니면
            if(fileYnList.get(i) == 0) {
                if(i == 0) {
                    fileSnList.add(hubDTO.getAtch_file_sn1());
                }

                if(i == 1) {
                    fileSnList.add(hubDTO.getAtch_file_sn2());
                }

                if(i == 2) {
                    fileSnList.add(hubDTO.getAtch_file_sn3());
                }
            } else {
                // 1. 기존 경로에 있는 파일 삭제
                int file_sn = 0;
                File deleteFile = null;

                // 만약 첫 번째라면
                if(i == 0) {
                    deleteFile = new File(path + basicFile.getHub_path_img() + basicFile.getHub_img_name());
                    file_sn = basicFile.getAtch_file_sn1();
                }

                // 만약 두 번째라면
                if(i == 1) {
                    deleteFile = new File(path + basicFile.getHub_path_mov() + basicFile.getHub_mov_name());
                    file_sn = basicFile.getAtch_file_sn2();
                }

                // 만약 세 번째라면
                if(i == 2) {
                    deleteFile = new File(path + basicFile.getHub_path() + basicFile.getHub_file_name());
                    file_sn = basicFile.getAtch_file_sn3();
                }

                // 파일 삭제
                boolean removed = deleteFile != null && deleteFile.delete();

                // 2. 만약 경로에 파일이 지워졌다면
                if(removed) {
                    // 테이블에 있는 파일 삭제
                    hubDAO.deleteFile(file_sn);
                }

                // 3. 새로운 파일 경로에 저장
                List<FileDTO> fileSave = insertFile(hubDTO, loginId);

                // 4. 저장된 파일의 PK 값을 SN LIST에 추가
                for(int j = 0; j < fileSave.size(); j++) {
                    fileSnList.add(fileSave.get(j).getFile_sn());
                }
            }
        }

        // 파일 번호 담기
        for(int k = 0; k < fileSnList.size(); k++) {
            if(k == 0) {
                hubDTO.setAtch_file_sn1(fileSnList.get(k));
            }

            if(k == 1) {
                hubDTO.setAtch_file_sn2(fileSnList.get(k));
            }

            if(k == 2) {
                hubDTO.setAtch_file_sn3(fileSnList.get(k));
            }
        }

        // 최종 수정자 대입
        hubDTO.setLast_mdfr(loginId);

        return hubDAO.update(hubDTO);
    }

    // HUB 센터 소식 삭제
    public void delete(int hub_sn) {
        // 0. HUB 센터 소식 상세 조회
        HubDTO basicFile = hubDAO.select(hub_sn);

        // 1. 경로 설정
        Path path = Paths.get("D:\\");

        // 2. File_sn 담기
        ArrayList<Integer> list = new ArrayList<>();

        int file_sn1 = basicFile.getAtch_file_sn1();
        int file_sn2 = basicFile.getAtch_file_sn2();
        int file_sn3 = basicFile.getAtch_file_sn3();

        list.add(file_sn1);
        list.add(file_sn2);
        list.add(file_sn3);

        // 3. File_sn의 리스트 만큼 반복
        for(int i = 0; i < list.size(); i++) {

            // 1. file_sn이 있으면
            if(list.get(i) != 0) {

                // 변수 생성
                int file_sn = 0;
                File deleteFile = null;

                // 만약 첫 번째라면
                if(i == 0) {
                    deleteFile = new File(path + basicFile.getHub_path_img() + basicFile.getHub_img_name());
                    file_sn = basicFile.getAtch_file_sn1();
                }

                // 만약 두 번째라면
                if(i == 1) {
                    deleteFile = new File(path + basicFile.getHub_path_mov() + basicFile.getHub_mov_name());
                    file_sn = basicFile.getAtch_file_sn2();
                }

                // 만약 세 번째라면
                if(i == 2) {
                    deleteFile = new File(path + basicFile.getHub_path() + basicFile.getHub_mov_name());
                    file_sn = basicFile.getAtch_file_sn3();
                }

                // 2. 파일 삭제
                boolean removed = deleteFile != null && deleteFile.delete();

                // 3. 만약 경로에 파일이 지워졌다면
                if(removed) {
                    // 테이블에 있는 파일 삭제
                    hubDAO.deleteFile(file_sn);
                }
            }
        }
        
        // 4. HUB 센터 소식 삭제
        hubDAO.delete(hub_sn);
    }

    // HUB 미리보기 페이지 이동
    public void preview(Model model, HubDTO hub, int loginId) {
        // 사용자 조회
        MemberDTO member = memberService.detail(loginId);
        model.addAttribute("member", member);

        int ntc_sn = hub.getHub_sn();

        if(ntc_sn > 0) {
            HubDTO ntc = hubDAO.select(ntc_sn);
            hub.setHub_path(ntc.getHub_path());
            hub.setHub_file_name(ntc.getHub_file_name());
            hub.setOrigin_file_name(ntc.getOrigin_file_name());
            hub.setHub_path_mov(ntc.getHub_path_mov());
            hub.setHub_mov_name(ntc.getHub_mov_name());
            hub.setOrigin_mov_name(ntc.getOrigin_mov_name());
        }

        MultipartFile file = hub.getHub_file();

        if(file.getSize() > 0) {
            // 파일 저장 후 조회
            FileUploader fileUploader = new FileUploader();
            FileDTO fileSave = fileUploader.insertFile(file, loginId);

            // 파일 테이블에 저장
            int result = hubDAO.insertFile(fileSave);

            // 게시글 저장
            if(fileSave != null && result == 1) {
                // 파일 일련번호 대입
                hub.setHub_path(fileSave.getFile_path());
                hub.setHub_file_name(fileSave.getFile_nm());
                hub.setOrigin_file_name(fileSave.getOrgnl_file_nm());
                hub.setAtch_file_sn3(fileSave.getFile_sn());
            }
        }

        MultipartFile mov = hub.getHub_mov();

        if(mov.getSize() > 0) {
            // 파일 저장 후 조회
            FileUploader fileUploader = new FileUploader();
            FileDTO fileSave = fileUploader.insertFile(mov, loginId);

            // 파일 테이블에 저장
            int result = hubDAO.insertFile(fileSave);

            // 게시글 저장
            if(fileSave != null && result == 1) {
                // 파일 일련번호 대입
                hub.setHub_path_mov(fileSave.getFile_path());
                hub.setHub_mov_name(fileSave.getFile_nm());
                hub.setOrigin_mov_name(fileSave.getOrgnl_file_nm());
                hub.setAtch_file_sn2(fileSave.getFile_sn());
            }
        }

        model.addAttribute("hub", hub);
    }
}
