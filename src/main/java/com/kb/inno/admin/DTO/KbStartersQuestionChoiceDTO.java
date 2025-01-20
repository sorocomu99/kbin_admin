package com.kb.inno.admin.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KbStartersQuestionChoiceDTO {
    private int question_choice_no;
    private int question_no;
    private int choice_no;
    private String choice_content;
    private int move_question_no;
    private int frst_rgtr;
    private Date frst_reg_dt;
    private int last_mdfr;
    private Date last_mdfcn_dt;
}
