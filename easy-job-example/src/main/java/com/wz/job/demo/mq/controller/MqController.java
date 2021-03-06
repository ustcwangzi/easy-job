package com.wz.job.demo.mq.controller;

import com.wz.job.demo.mq.service.ProducerMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>http方式触发mq消息发送</p>
 * @author wangzi
 * Created by wangzi on 2017-10-11.
 */
@RestController
@RequestMapping("/order")
public class MqController {
    @Autowired
    private ProducerMsg producer;

    @RequestMapping("/run")
    public void run(String str){
        producer.sendMsg(str);
    }
}
