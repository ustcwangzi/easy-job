package com.wz.job.handler;

/**
 * <p>任务处理接口</p>
 * Created by wangzi on 2017-09-22.
 */
public interface AbstractJobHandler {

    void handleUpdate(String path, String status);
}
