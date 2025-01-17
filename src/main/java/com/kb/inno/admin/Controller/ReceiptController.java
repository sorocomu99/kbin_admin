package com.kb.inno.admin.Controller;



import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    

    @GetMapping("/receiptList/{menuId}")
    public String receiptList(@PathVariable int menuId, Model model, 
    						  @RequestParam(value = "page", required = false, defaultValue = "1") int page,
    						  @RequestParam(value = "srvy_sn" , required = false, defaultValue = "1") int srvy_sn) {
    	
    	receiptService.receiptList(menuId, page, model, srvy_sn);
    	System.out.println("================================");
        return directory + "/receipt_list";
    }

    @GetMapping("/receiptTrash/{menuId}")
    public String receiptTrashList(@PathVariable int menuId, Model model, @RequestParam(value = "page", required = false, defaultValue = "1") int page) {
    	receiptService.selectList(menuId, page, model);
    		
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
