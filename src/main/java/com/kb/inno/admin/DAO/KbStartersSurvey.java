package com.kb.inno.admin.DAO;

import com.kb.inno.admin.DTO.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface KbStartersSurvey {

    List<KbStartersSurveyDTO> getSurveyList(SearchDTO search);

    int countSurvey(SearchDTO search);

    KbStartersSurveyDTO getSurvey(KbStartersSurveyDTO survey);

    int getMaxSurveyNo();

    int insertSurvey(KbStartersSurveyDTO survey);

    int updateSurvey(KbStartersSurveyDTO survey);

    int deleteSurvey(KbStartersSurveyDTO survey);

    KbStartersSurveyDTO getSurveyInfo(KbStartersSurveyDTO survey);

    int getMaxSurveyInfoNo();

    int insertSurveyInfo(KbStartersSurveyDTO survey);

    int updateSurveyInfo(KbStartersSurveyDTO survey);

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

    int deleteSurveyInfoBySurvey(KbStartersSurveyDTO survey);

    int deleteQuestionBySurvey(KbStartersSurveyDTO survey);

    int deleteQuestionChoiceByQuestion(KbStartersQuestionDTO question);

    int deleteQuestionChoiceBySurvey(KbStartersSurveyDTO survey);

    int moveApply(KbStartersApplyDTO apply);

    int moveApplyAnswer(KbStartersApplyAnswerDTO answer);

    List<KbStartersApplyDTO> getApplyBySurvey(@Param("survey") KbStartersSurveyDTO survey, @Param("search") SearchDTO search);

    List<KbStartersApplyAnswerDTO> getApplyAnswerByApply(KbStartersApplyDTO apply);

    int insertApply(KbStartersApplyDTO apply);

    int insertApplyAnswer(KbStartersApplyAnswerDTO answer);

    int deleteApplyBySurvey(KbStartersSurveyDTO survey);

    int deleteApplyAnswerByApply(KbStartersApplyDTO apply);

    KbStartersApplyAnswerDTO getOneApplyAnswer(int apply_answer_no);

    int deleteApply(KbStartersApplyDTO apply);

    int updateApplyStatus(KbStartersApplyDTO apply);

    int updatePrevChoiceSurvey(KbStartersSurveyDTO survey);

    int updateChoiceSurvey(KbStartersSurveyDTO survey);

}
