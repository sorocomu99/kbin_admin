package com.kb.inno.admin.DAO;

import com.kb.inno.admin.DTO.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface KbStartersSurvey {

    List<KbStartersSurveyDTO> getSurveyList(SearchDTO search);

    int countSurvey(SearchDTO search);

    int getMaxSurveyNo();

    int insertSurvey(KbStartersSurveyDTO survey);

    int updateSurvey(KbStartersSurveyDTO survey);

    int deleteSurvey(KbStartersSurveyDTO survey);

    KbStartersSurveyDTO getSurveyInfo(KbStartersSurveyDTO survey);

    List<KbStartersQuestionDTO> getQuestion(KbStartersQuestionDTO question);

    KbStartersQuestionDTO getOneQuestion(KbStartersQuestionDTO question);

    List<KbStartersQuestionChoiceDTO> getQuestionChoice(KbStartersQuestionDTO question);

    KbStartersQuestionChoiceDTO getOneQuestionChoice(KbStartersQuestionChoiceDTO choice);

    List<KbStartersApplyDTO> getApplyList(int survey_no, SearchDTO search);

    List<KbStartersApplyAnswerDTO> getApplyAnswers(KbStartersApplyDTO apply);

    int getMaxQuestionNo();

    int insertQuestion(KbStartersQuestionDTO question);

    int updateQuestion(KbStartersQuestionDTO question);

    int deleteQuestions(List<Integer> deleteQuestionNoList);

    int getMaxQuestionChoiceNo();

    int insertQuestionChoice(KbStartersQuestionChoiceDTO choice);

    int updateQuestionChoice(KbStartersQuestionChoiceDTO choice);

    int deleteQuestionChoices(List<Integer> deleteChoiceNoList);

}
