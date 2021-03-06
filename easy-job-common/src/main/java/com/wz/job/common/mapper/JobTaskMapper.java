package com.wz.job.common.mapper;

import com.wz.job.common.model.JobTask;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>任务Mapper</p>
 *
 * @author wangzi
 * Created by wangzi on 2017-09-24.
 */
public interface JobTaskMapper {
    /**
     * 根据id查找任务
     *
     * @param jobId 任务id
     * @return
     */
    JobTask queryJobById(@Param("jobId") Integer jobId);

    /**
     * 查找满足条件的任务
     *
     * @param jobName  任务名称
     * @param pageSize 当前页码
     * @param offset   条数
     * @return
     */
    List<JobTask> queryJobs(@Param("jobName") String jobName, @Param("pageSize") String pageSize, @Param("offset") String offset);

    /**
     * 查找符合条件的任务个数
     *
     * @param jobName 任务名称
     * @return
     */
    int queryJobCount(@Param("jobName") String jobName);

    /**
     * 保存任务
     *
     * @param job
     * @return
     */
    int insertJob(JobTask job);

    /**
     * 删除任务
     *
     * @param jobId 任务id
     * @return
     */
    int deleteJob(@Param("jobId") Integer jobId);

    /**
     * 更新任务
     *
     * @param job
     * @return
     */
    int updateJob(JobTask job);
}
