package com.wz.job.common.mapper;

import com.wz.job.common.model.JobLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>任务历史Mapper</p>
 * @author wangzi
 * Created by wangzi on 2017-09-24.
 */
public interface JobLogMapper {
    /**
     * 根据jobId查找执行历史
     * @param jobId
     * @return
     */
    List<JobLog> queryLogsByJobId(@Param("jobId") Integer jobId);

    /**
     * 保存执行历史
     * @param log
     * @return
     */
    int insertLog(JobLog log);

    /**
     * 根据jobId删除执行历史
     * @param jobId
     * @return
     */
    int deleteLogByJobId(@Param("jobId") Integer jobId);

    /**
     * 根据logId删除执行历史
     * @param logId
     * @return
     */
    int deleteLogByLogId(@Param("logId") Integer logId);

    /**
     * 更新执行历史
     * @param log
     * @return
     */
    int updateLog(JobLog log);
}
