package com.wz.job.demo.dubbo.service;

import com.wz.job.demo.bean.Order;

import java.util.List;

/**
 * <p>任务接口</p>
 *
 * @author wangzi
 * Created by wangzi on 2017-09-19.
 */
public interface OrderService {
    /**
     * 获取任务项
     *
     * @param str
     * @return
     */
    List<Order> selectTasks(String str);

    /**
     * 执行任务
     *
     * @param orders
     * @return
     */
    boolean execute(List<Order> orders);
}
