package com.kb.inno.admin.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KbStartersQuestionChoiceRequestDTO {
    private int question_choice_no;
    private int question_no;
    private String choice_content;
    private int move_question_no;
}
