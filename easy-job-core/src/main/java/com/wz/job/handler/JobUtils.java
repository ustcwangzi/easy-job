package com.wz.job.handler;

import com.wz.job.common.mapper.JobLogMapper;
import com.wz.job.common.model.JobLog;
import com.wz.job.common.model.JobTask;
import lombok.extern.slf4j.Slf4j;
import org.quartz.CronExpression;
import org.quartz.JobDataMap;
import org.quartz.Scheduler;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.triggers.CronTriggerImpl;

/**
 * <p>任务开启/关闭</p>
 * Created by wangzi on 2017-09-22.
 */
@Slf4j
public class JobUtils {
    private Scheduler scheduler;

    public JobUtils(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public void start(JobTask task, JobLogMapper mapper, Class clazz) {
        JobDetailImpl detail = new JobDetailImpl();
        detail.setName(task.getJobName());
        JobDataMap map = new JobDataMap();
        map.put("job", task);
        map.put("mapper", mapper);
        detail.setJobDataMap(map);
        detail.setJobClass(clazz);
        CronTriggerImpl trigger = new CronTriggerImpl();
        trigger.setName(task.getJobName());
        try {
            trigger.setCronExpression(new CronExpression(task.getCron()));
            scheduler.scheduleJob(detail, trigger);
            scheduler.start();
        } catch (Exception e) {
            log.error("start jon error name:{},cron:{}, error:{}", task.getJobName(), task.getCron(), e.getMessage());
        }
    }

    public void stop() {
        try {
            scheduler.shutdown();
        } catch (Exception e) {
            log.error("stop job error, {}", e.getMessage());
        }
    }
}
