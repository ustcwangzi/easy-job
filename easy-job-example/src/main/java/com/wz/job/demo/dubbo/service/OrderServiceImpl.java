package com.wz.job.demo.dubbo.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.wz.job.demo.bean.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>任务获取和执行</p>
 * @author wangzi
 * Created by wangzi on 2017-09-19.
 */
@Slf4j
@Component
@Service(version = "1.0.0")
public class OrderServiceImpl implements OrderService {
    private static final int COUNT = 10000;

    @Override
    public List<Order> selectTasks(String str) {
        List<Order> list = new ArrayList<>();
        for (int i = 0; i < COUNT; i++) {
            list.add(new Order(i, str + i));
        }
        return list;
    }

    @Override
    public boolean execute(List<Order> orders) {
        orders.forEach(order -> {
            log.info("处理订单：{}", order.getDetail());
        });
        return true;
    }
}
