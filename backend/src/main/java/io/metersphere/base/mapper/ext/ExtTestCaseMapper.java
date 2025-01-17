package io.metersphere.base.mapper.ext;

import io.metersphere.base.domain.TestCase;
import io.metersphere.controller.request.BaseQueryRequest;
import io.metersphere.track.dto.TestCaseDTO;
import io.metersphere.track.request.testcase.QueryTestCaseRequest;
import io.metersphere.track.request.testcase.TestCaseBatchRequest;
import io.metersphere.track.response.TrackCountResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface ExtTestCaseMapper {

    List<TestCase> getTestCaseNames(@Param("request") QueryTestCaseRequest request);

    List<TestCaseDTO> list(@Param("request") QueryTestCaseRequest request);

    List<TestCaseDTO> listByMethod(@Param("request") QueryTestCaseRequest request);

    List<TestCaseDTO> listByTestCaseIds(@Param("request") TestCaseBatchRequest request);

    TestCase getMaxNumByProjectId(@Param("projectId") String projectId);

    /**
     * 获取不在测试计划中的用例
     *
     * @param request
     * @return
     */
    List<TestCase> getTestCaseByNotInPlan(@Param("request") QueryTestCaseRequest request);

    /**
     * 获取不在评审范围中的用例
     *
     * @param request
     * @return
     */
    List<TestCase> getTestCaseByNotInReview(@Param("request") QueryTestCaseRequest request);

    /**
     * 检查某工作空间下是否有某用例
     *
     * @param caseId
     * @param workspaceIds
     * @return TestCase ID
     */
    int checkIsHave(@Param("caseId") String caseId, @Param("workspaceIds") Set<String> workspaceIds);

    List<String> selectIds(@Param("request") BaseQueryRequest condition);

    /**
     * 按照用例等级统计
     * @param projectId 项目ID
     * @return 统计结果
     */
    List<TrackCountResult> countPriority(@Param("projectId") String projectId);

    long countCreatedThisWeek(@Param("projectId") String projectId, @Param("firstDayTimestamp") long firstDayTimestamp, @Param("lastDayTimestamp") long lastDayTimestamp);

    List<TrackCountResult> countStatus(@Param("projectId") String projectId);

    List<TrackCountResult> countRelevance(@Param("projectId") String projectId);

    long countRelevanceCreatedThisWeek(@Param("projectId") String projectId,@Param("firstDayTimestamp") long firstDayTimestamp, @Param("lastDayTimestamp") long lastDayTimestamp);

    List<TrackCountResult> countCoverage(@Param("projectId") String projectId);

    List<TrackCountResult> countFuncMaintainer(@Param("projectId") String projectId);

    List<TrackCountResult> countRelevanceMaintainer(@Param("projectId") String projectId);

    int getTestPlanBug(@Param("planId") String planId);
    int getTestPlanCase(@Param("planId") String planId);
    int getTestPlanPassCase(@Param("planId") String planId);



}
