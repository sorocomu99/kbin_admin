package com.kb.inno.admin.Scheduler;

import com.kb.inno.admin.DAO.KbStartersSurvey;
import com.kb.inno.admin.Service.SurveyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class SchedulerTask {

    private final KbStartersSurvey surveyRepository;

    private final SurveyService surveyService;

    public SchedulerTask(KbStartersSurvey surveyRepository, SurveyService surveyService) {
        this.surveyRepository = surveyRepository;
        this.surveyService = surveyService;
    }

    @Scheduled(cron = "0 0 1 * * ?")
    public void deleteSurvey() {
        List<Integer> surveyList = surveyRepository.getSurveyListForRemoval();
        log.info("Beginning survey deletion batch...");
        if (surveyList != null
                && !surveyList.isEmpty()) {
            for (Integer surveyNo : surveyList) {
                try{
                    surveyService.deleteSurvey(surveyNo);
                }catch (Exception ignored) {

                }
            }
        }
        log.info("End survey deletion batch...");
    }
}
