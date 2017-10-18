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
 *
 * @author wangzi
 *         Created by wangzi on 2017-10-11.
 */
@Slf4j
@JobHandlerService(Constants.TYPE_HTTP)
public class HttpJobHandler implements Job {

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
            uri = uri + "?str=" + task.getParams();
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
                        result = result + Constants.LOG_SPLIT + HttpUtils.post(task.getExecuteMethod(), list.toString());
                    }
                } else {
                    result = HttpUtils.post(task.getExecuteMethod(), response);
                }
            }

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
