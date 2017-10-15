package com.wz.job.demo.http;

import com.wz.job.demo.bean.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>任务获取和执行</p>
 * @author wangzi
 * Created by wangzi on 2017-10-11.
 */
@Slf4j
@RestController
@RequestMapping("/order")
public class HttpController {
    private static final int COUNT = 10000;

    @RequestMapping("/selectTasks")
    public List<Order> selectTasks(String str) {
        List<Order> list = new ArrayList<>();
        for (int i = 0; i < COUNT; i++) {
            list.add(new Order(i, str + i));
        }
        return list;
    }

    @RequestMapping("/execute")
    public boolean execute(@RequestBody List<Order> orders) {
        orders.forEach(order -> {
            log.info("处理订单：{}", order.getDetail());
        });
        return true;
    }
}
