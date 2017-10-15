package com.wz.job.common.model;

import lombok.Data;

import java.util.Date;

/**
 * <p>任务执行历史实体</p>
 * @author wangzi
 * Created by wangzi on 2017-09-24.
 */
@Data
public class JobLog implements Comparable {
    /**
     * ID
     */
    private Integer logId;
    /**
     * 任务ID
     */
    private Integer jobId;
    /**
     * 执行时间
     */
    private Date executeDate;
    /**
     * 执行结果
     */
    private String executeResult;

    @Override
    public int compareTo(Object o) {
        if (this == o){
            return 0;
        }
        if (o == null || getClass() != o.getClass()){
            return -1;
        }

        JobLog jobLog = (JobLog) o;
        return executeDate.compareTo(jobLog.executeDate);
    }
}
