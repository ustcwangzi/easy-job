package com.wz.job.demo.mq.service;

import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.common.message.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * <p>生产消息</p>
 * @author wangzi
 * Created by wangzi on 2017-10-11.
 */
@Slf4j
@Component
public class ProducerMsg {
    private static final int COUNT = 10000;
    @Autowired
    private DefaultMQProducer defaultMQProducer;
    @Value("${mq.topic}")
    private String topic;
    @Value("${mq.tag}")
    private String tag;

    public void sendMsg(String str) {
        try {
            for (int i = 0; i < COUNT; i++) {
                Message message = new Message(topic, tag, "MQ" + i, (str + i).getBytes());
                defaultMQProducer.send(message);
            }
        } catch (Exception e) {
            log.error("信息发送失败, {}", e.getMessage());
        }
    }
}
