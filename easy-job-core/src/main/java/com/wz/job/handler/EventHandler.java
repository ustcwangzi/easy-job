package com.wz.job.handler;

import com.wz.job.common.mapper.JobLogMapper;
import com.wz.job.common.mapper.JobTaskMapper;
import com.wz.job.common.model.JobTask;
import com.wz.job.common.utils.AbstractEventHandler;
import com.wz.job.common.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;

/**
 * <p>根据任务类型进行不同的处理</p>
 * Created by wangzi on 2017-09-22.
 */
@Slf4j
@Component
public class EventHandler extends AbstractEventHandler {
    @Autowired
    private JobHandlerFactory factory;
    @Autowired
    private JobTaskMapper taskMapper;
    @Autowired
    private JobLogMapper logMapper;

    @Override
    public void handleAdd(ChildData childData) {
        log.info("handleAdd path:{}", childData.getPath());
        handleUpdate(childData);
    }

    @Override
    public void handleRemove(ChildData childData) {
        log.info("handleRemove path:{}", childData.getPath());
    }

    @Override
    public void handleUpdate(ChildData childData) {
        String path = childData.getPath();
        String data = new String(childData.getData(), Charset.forName(Constants.DEFAULT_CHARSET));
        if (StringUtils.isBlank(data)) {
            return;
        }
        String[] arr = data.split("_"); //status_type
        if (arr.length == 2) {
            String status = arr[0];
            log.info("handleUpdate path:{}, status:{}", path, status);
            String id = path.substring(path.lastIndexOf("/") + 1);
            if (StringUtils.isBlank(id)) {
                return;
            }
            JobUtils jobUtils = null;
            try {
                jobUtils = new JobUtils(new StdSchedulerFactory().getScheduler());
            } catch (Exception e) {
                log.error("create schedule error:{}", e.getMessage());
            }
            JobTask task = taskMapper.queryJobById(Integer.parseInt(id));
            if (task == null){
                log.error("Can not found job {}", path);
                return;
            }
            if (jobUtils != null && Constants.STATUS_STOP.equals(status)) {
                log.info("stop job: {}", task.getJobName());
                jobUtils.stop();
            } else if (jobUtils != null && Constants.STATUS_START.equals(status)) {
                log.info("start job: {}", task.getJobName());
                jobUtils.start(task, logMapper, factory.getHandler(arr[1]));
            }
        }
    }
}
