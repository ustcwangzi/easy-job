package com.wz.job.handler.service;

import com.wz.job.common.mapper.JobLogMapper;
import com.wz.job.common.model.JobLog;
import com.wz.job.common.model.JobTask;
import com.wz.job.common.utils.Constants;
import com.wz.job.common.utils.HttpUtils;
import com.wz.job.handler.JobHandlerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Collections;
import java.util.List;

/**
 * <p>处理MQ类型的任务</p>
 *
 * @author wangzi
 *         Created by wangzi on 2017-10-11.
 */
@Slf4j
@JobHandlerService(Constants.TYPE_MQ)
public class MqJobHandler implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobTask task = (JobTask) context.getJobDetail().getJobDataMap().get("job");
        JobLogMapper mapper = (JobLogMapper) context.getJobDetail().getJobDataMap().get("mapper");
        if (task == null || mapper == null) {
            log.error("data error, JobTask or JobLogMapper is null, please check it");
            return;
        }
        log.info("execute job {}", task.getJobName());
        JobLog jobLog;
        // 更新/新增标识，false：更新，true：新增
        boolean flag = false;
        String result = "";
        List<JobLog> logs = mapper.queryLogsByJobId(task.getJobId());
        if (logs != null && logs.size() == Constants.DEFAULT_LOGSIZE) {
            jobLog = Collections.min(logs);
        } else {
            flag = true;
            jobLog = new JobLog();
            jobLog.setJobId(task.getJobId());
        }
        String uri = task.getSelectMethod();
        if (StringUtils.isNotBlank(task.getParams())) {
            uri += "?str=" + task.getParams();
        }
        try {
            result = HttpUtils.get(uri);
        } catch (Exception e) {
            result = e.getMessage();
            log.error("execute job error {}", result);
        } finally {
            jobLog.setExecuteResult(result);
            if (flag) {
                mapper.insertLog(jobLog);
            } else {
                mapper.updateLog(jobLog);
            }
        }
    }
}
