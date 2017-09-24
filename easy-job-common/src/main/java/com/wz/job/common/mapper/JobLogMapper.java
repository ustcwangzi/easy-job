package com.wz.job.common.mapper;

import com.wz.job.common.model.JobLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>任务历史Mapper</p>
 * Created by wangzi on 2017-09-24.
 */
public interface JobLogMapper {
    List<JobLog> queryLogsByJobId(@Param("jobId") Integer jobId);

    int insertLog(JobLog log);

    int deleteLogByJobId(@Param("jobId") Integer jobId);

    int deleteLogByLogId(@Param("logId") Integer logId);

    int updateLog(JobLog log);
}
