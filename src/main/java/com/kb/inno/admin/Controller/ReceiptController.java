package com.kb.inno.admin.Controller;



import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kb.inno.admin.DTO.ReceiptDTO;
import com.kb.inno.admin.DTO.ReceiptListDTO;
import com.kb.inno.admin.DTO.SurveyDTO;
import com.kb.inno.admin.Service.ReceiptService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/receipt")
public class ReceiptController {
    // 디렉터리 공통
    @Value("/receipt")
    private String directory;

    //Service 연결
    private final ReceiptService receiptService;
    
    
    /**
     * 지원서 임시 보관함 영구 삭제
     * @param menuId
     * @param model
     * @param page
     * @return
     */
    @ResponseBody
    @PostMapping("/receiptDelete")
    public String receiptDelete(@RequestParam("srvy_sn") int srvy_sn) {
    	ReceiptDTO receiptDTO = new ReceiptDTO();
    	receiptDTO.setSrvy_sn(srvy_sn);
        receiptService.receiptDelete(receiptDTO);
        return "success";
    }
    /**
     * 지원서 임시 보관함 삭제 취소
     * @param menuId
     * @param model
     * @param page
     * @return
     */
    @ResponseBody
    @PostMapping("/deleteCancel")
    public String deleteCancel(@RequestParam("srvy_sn") int srvy_sn) {
    	ReceiptDTO receiptDTO = new ReceiptDTO();
    	receiptDTO.setSrvy_sn(srvy_sn);
        receiptService.deleteCancel(receiptDTO);
        return "success";
    }
    /**
     * 지원서 접수 관리 임시 삭제
     * @param menuId
     * @param model
     * @param page
     * @return
     */
    @ResponseBody
    @PostMapping("/tempDelete")
    public String tempDelete(@RequestParam("srvy_sn") int srvy_sn) {
    	ReceiptDTO receiptDTO = new ReceiptDTO();
    	receiptDTO.setSrvy_sn(srvy_sn);
        receiptService.tempDelete(receiptDTO);
        return "success";
    }

    @ResponseBody
    @PostMapping("/updateAlert")
    public String updateAlert(@RequestParam("stts") String stts,
    						 @RequestParam("conm") String conm) {
    	ReceiptListDTO receiptListDTO = new ReceiptListDTO();
    	receiptListDTO.setPrgrs_stts(stts);
    	receiptListDTO.setConm(conm);
    	
    	System.out.println("/updateAlert : prgrs_stts ["+stts+"]  conm ["+conm+"]");
    	System.out.println("/updateAlert : prgrs_stts ["+receiptListDTO.getPrgrs_stts()+"]  conm ["+receiptListDTO.getConm()+"]");
    	
    	receiptService.updateAlert(receiptListDTO);
        return "success";
    }

    /**
     * 설문 조회
     * @param menuId
     * @param model
     * @param page
     * @return
     */
    @GetMapping("/list/{menuId}")
    public String receiptMainList(@PathVariable int menuId, Model model, @RequestParam(value = "page", required = false, defaultValue = "1") int page) {
    	
    	receiptService.selectList(menuId, page, model);
    		
        return directory + "/receipt";
    }
    
    /**
     * 지원서 접수 관리 > 지원서 상세보기 클릭
     * @param menuId
     * @param model
     * @param page
     * @return
     *     @GetMapping("/list/{menuId}")
    public String mailList(@PathVariable int menuId, Model model,
                           @RequestParam(value = "type", required = false, defaultValue = "") String type,
                           @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
                           @RequestParam(value = "page", required = false, defaultValue = "1") int page) {
        sendMailService.selectListMail(menuId, model, type, keyword, page);
        model.addAttribute("menuId", menuId);
        return directory + "/mail";
    }
     */
    @GetMapping("/receiptList/{menuId}")
    public String receiptList(@PathVariable int menuId, Model model, 
    						  @RequestParam(value = "type", required = false, defaultValue = "all") String type,
    						  @RequestParam(value = "srvy_sn" , required = false, defaultValue = "1") int srvy_sn) {
    	
    	System.out.println("================================receiptList type["+type+"]   srvy_sn["+srvy_sn+"]");
    	receiptService.receiptTenList(menuId, type, model, srvy_sn);
    	
        model.addAttribute("menuId", menuId);
        return directory + "/receipt_list";
    }

    /**
     * 지원서 임시 보관함 리스트
     * @param menuId
     * @param model
     * @param page
     * @return
     */
    @GetMapping("/receiptTrash/{menuId}")
    public String receiptTrashList(@PathVariable int menuId, Model model, @RequestParam(value = "page", required = false, defaultValue = "1") int page) {
    	receiptService.selecTemptList(menuId, page, model);
    		
        return directory + "/receipt_trash";
    }
    

    @RequestMapping("/sendMail/{menuId}")
    public String insert(@PathVariable int menuId, Model model) {
        model.addAttribute("menuId", menuId);
        return directory + "/receipt_mail";
    }

    @GetMapping("/receiptMail/{menuId}")
    public String receiptMailList(@PathVariable int menuId, Model model, @RequestParam(value = "page", required = false, defaultValue = "1") int page) {
    	receiptService.selectList(menuId, page, model);
    		
        return directory + "/receipt_mail";
    }
    
    
    

}
