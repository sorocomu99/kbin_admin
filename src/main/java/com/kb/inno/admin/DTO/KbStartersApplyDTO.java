package com.kb.inno.admin.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KbStartersApplyDTO {
    private int apply_no;
    private int survey_no;
    private String email;
    private String company_name;
    private int frst_rgtr;
    private Date frst_reg_dt;
    private int last_mdfr;
    private Date last_mdfcn_dt;
    private List<KbStartersApplyAnswerDTO> answers;
}
