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
 * Created by wangzi on 2017-10-11.
 */
@Slf4j
@JobHandlerService(Constants.TYPE_MQ)
public class MQJobHandler implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobTask task = (JobTask) context.getJobDetail().getJobDataMap().get("job");
        log.info("execute job {}", task.getJobName());
        JobLogMapper mapper = (JobLogMapper) context.getJobDetail().getJobDataMap().get("mapper");
        JobLog jobLog = null;
        List<JobLog> logs = mapper.queryLogsByJobId(task.getJobId());
        if (logs != null && logs.size() == Constants.DEFAULT_LOGSIZE) {
            jobLog = Collections.min(logs);
        }
        boolean flag = false;
        String result = "no result";
        if (jobLog == null) {
            flag = true;
            jobLog = new JobLog();
            jobLog.setJobId(task.getJobId());
        }
        String uri = task.getSelectMethod();
        if (StringUtils.isNotBlank(task.getParams())) {
            uri += "?str=" + task.getParams();
        }
        try {
            String response = HttpUtils.get(uri);
            if (StringUtils.isNotBlank(response)) {
                result = response;
            }
            jobLog.setExecuteResult(result);
            saveLog(mapper, jobLog, flag);
        } catch (Exception e) {
            log.error("execute job error {}", e.getMessage());
            jobLog.setExecuteResult(e.getMessage());
            saveLog(mapper, jobLog, flag);
        }
    }

    private void saveLog(JobLogMapper mapper, JobLog jobLog, boolean saveFlag) {
        if (saveFlag) {
            mapper.insertLog(jobLog);
        } else {
            mapper.updateLog(jobLog);
        }
    }
}
