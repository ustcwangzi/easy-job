package com.wz.job.common.mapper;

import com.wz.job.common.model.JobTask;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>任务Mapper</p>
 * Created by wangzi on 2017-09-24.
 */
public interface JobTaskMapper {
    JobTask queryJobById(@Param("jobId") Integer jobId);

    List<JobTask> queryJobs(@Param("jobName")String jobName, @Param("pageSize")String pageSize, @Param("offset")String offset);

    int queryJobCount(@Param("jobName")String jobName);

    int insertJob(JobTask job);

    int deleteJob(@Param("jobId") Integer jobId);

    int updateJob(JobTask job);
}
