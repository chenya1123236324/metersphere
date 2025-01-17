package io.metersphere.track.controller;


import io.metersphere.commons.constants.RoleConstants;
import io.metersphere.performance.base.ChartsData;
import io.metersphere.track.response.BugStatustics;
import io.metersphere.track.response.TrackCountResult;
import io.metersphere.track.response.TrackStatisticsDTO;
import io.metersphere.track.service.TrackService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.util.List;

@RestController
@RequestMapping("/track")
@RequiresRoles(value = {RoleConstants.ADMIN, RoleConstants.TEST_MANAGER, RoleConstants.TEST_USER, RoleConstants.TEST_VIEWER, RoleConstants.ORG_ADMIN}, logical = Logical.OR)
public class TrackController {

    @Resource
    private TrackService trackService;

    @GetMapping("/count/{projectId}")
    public TrackStatisticsDTO getTrackCount(@PathVariable String projectId) {
        TrackStatisticsDTO statistics = new TrackStatisticsDTO();

        List<TrackCountResult> priorityResults = trackService.countPriority(projectId);
        statistics.countPriority(priorityResults);

        long size = trackService.countCreatedThisWeek(projectId);
        statistics.setThisWeekAddedCount(size);

        List<TrackCountResult> statusResults = trackService.countStatus(projectId);
        statistics.countStatus(statusResults);

        long total = statistics.getPrepareCount() + statistics.getPassCount() + statistics.getUnPassCount();
        if (total != 0) {
            float reviewed = (float) (statistics.getPassCount() + statistics.getUnPassCount()) * 100 / total;
            DecimalFormat df = new DecimalFormat("0.0");
            statistics.setReviewRage(df.format(reviewed) + "%");
        }

        statistics.setP0CountStr("P0&nbsp;&nbsp;<br/><br/>" + statistics.getP0CaseCountNumber());
        statistics.setP1CountStr("P1&nbsp;&nbsp;<br/><br/>" + statistics.getP1CaseCountNumber());
        statistics.setP2CountStr("P2&nbsp;&nbsp;<br/><br/>" + statistics.getP2CaseCountNumber());
        statistics.setP3CountStr("P3&nbsp;&nbsp;<br/><br/>" + statistics.getP3CaseCountNumber());
        return statistics;
    }

    @GetMapping("/relevance/count/{projectId}")
    public TrackStatisticsDTO getRelevanceCount(@PathVariable String projectId) {
        TrackStatisticsDTO statistics = new TrackStatisticsDTO();

        List<TrackCountResult> relevanceResults = trackService.countRelevance(projectId);
        statistics.countRelevance(relevanceResults);

        long size = trackService.countRelevanceCreatedThisWeek(projectId);
        statistics.setThisWeekAddedCount(size);

        List<TrackCountResult> coverageResults = trackService.countCoverage(projectId);
        statistics.countCoverage(coverageResults);

        long total = statistics.getUncoverageCount() + statistics.getCoverageCount();

        if (total != 0) {
            float coverageRageNumber = (float) statistics.getCoverageCount() * 100 / total;
            DecimalFormat df = new DecimalFormat("0.0");
            statistics.setCoverageRage(df.format(coverageRageNumber) + "%");
        }

        statistics.setApiCaseCountStr("接口用例&nbsp;&nbsp;<br/><br/>" + statistics.getApiCaseCount());
        statistics.setPerformanceCaseCountStr("性能用例&nbsp;&nbsp;<br/><br/>" + statistics.getPerformanceCaseCount());
        statistics.setScenarioCaseStr("场景用例&nbsp;&nbsp;<br/><br/>" + statistics.getScenarioCaseCount());

        return statistics;
    }

    @GetMapping("/case/bar/{projectId}")
    public List<ChartsData> getCaseMaintenanceBar(@PathVariable String projectId) {
        return trackService.getCaseMaintenanceBar(projectId);
    }

    @GetMapping("/bug/count/{projectId}")
    public BugStatustics getBugStatistics(@PathVariable String projectId) {
        return trackService.getBugStatistics(projectId);
    }
}
