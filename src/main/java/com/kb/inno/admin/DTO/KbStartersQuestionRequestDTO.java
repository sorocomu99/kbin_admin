package com.kb.inno.admin.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KbStartersQuestionRequestDTO {

    int question_no;
    int question_type_no;
    int survey_no;
    String question_title;
    String question_description;
    String required_yn;
    int question_order;

    List<KbStartersQuestionChoiceRequestDTO> choices;


}
