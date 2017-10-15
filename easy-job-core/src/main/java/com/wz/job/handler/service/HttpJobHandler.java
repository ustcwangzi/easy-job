package com.wz.job.handler.service;

import com.alibaba.fastjson.JSONArray;
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
 * <p>处理http类型的任务</p>
 * @author wangzi
 * Created by wangzi on 2017-10-11.
 */
@Slf4j
@JobHandlerService(Constants.TYPE_HTTP)
public class HttpJobHandler implements Job {

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
                JSONArray array = JSONArray.parseArray(response);
                if (array.size() > Constants.MAX_JOB_COUNT) {
                    int div = array.size() % Constants.MAX_JOB_COUNT;
                    //循环次数
                    int round = div == 0 ? array.size() / Constants.MAX_JOB_COUNT : array.size() / Constants.MAX_JOB_COUNT + 1;
                    for (int i = 0; i < round; i++) {
                        List list = array.subList(i * Constants.MAX_JOB_COUNT, (i + 1) * Constants.MAX_JOB_COUNT);
                        result = HttpUtils.post(task.getExecuteMethod(), list.toString());
                        jobLog.setExecuteResult(result);
                        saveLog(mapper, jobLog, flag);
                    }
                } else {
                    result = HttpUtils.post(task.getExecuteMethod(), response);
                    jobLog.setExecuteResult(result);
                    saveLog(mapper, jobLog, flag);
                }
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
