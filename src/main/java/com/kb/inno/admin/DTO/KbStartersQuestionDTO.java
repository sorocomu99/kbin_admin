package com.kb.inno.admin.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KbStartersQuestionDTO {
    private int question_no;
    private int question_type_no;
    private String question_type_nm;
    private int survey_no;
    private String question_title;
    private String question_description;
    private String required_yn;
    private int question_order;
    private int frst_rgtr;
    private Date frst_reg_dt;
    private int last_mdfr;
    private Date last_mdfcn_dt;
    private List<KbStartersQuestionChoiceDTO> choices;
}
