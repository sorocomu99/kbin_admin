package com.kb.inno.admin.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KbStartersSurveyRequestDTO {

    List<KbStartersQuestionRequestDTO> data;

    List<Integer> deleteQuestionNoList;

    List<Integer> deleteChoiceNoList;

}
