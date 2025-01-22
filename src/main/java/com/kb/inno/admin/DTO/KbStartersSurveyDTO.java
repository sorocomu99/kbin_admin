package com.kb.inno.admin.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KbStartersSurveyDTO {

    private int survey_no;
    private String survey_title;
    private String banner_file_path;
    private String banner_filename;
    private String delete_yn;

    private int survey_info_no;
    private String survey_category;
    private String survey_date_range;
    private String survey_target_heading;
    private String survey_target_sub;
    private String survey_field_heading;
    private String survey_field_sub;
    private String survey_select_process;
    private String survey_benefit1;
    private String survey_benefit2;
    private String survey_benefit3;
    private String survey_benefit4;

    private List<KbStartersQuestionDTO> questions;

    private int frst_rgtr;               //최초등록자
    private Date frst_reg_dt;            //최초 등록 일시
    private int last_mdfr;               //최종 수정자
    private Date last_mdfcn_dt;          //최종 수정 일시

}
