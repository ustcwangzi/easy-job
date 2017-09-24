package com.wz.job.demo.dubbo.service;

import java.util.List;

/**
 * <p>任务接口</p>
 * Created by wangzi on 2017-09-19.
 */
public interface OrderService {
    List<Order> selectTasks(String str);

    boolean execute(List<Order> orders);
}
