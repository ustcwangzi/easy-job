package com.wz.job.demo.mq.service;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.message.MessageExt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>消费消息</p>
 * @author wangzi
 * Created by wangzi on 2017-10-11.
 */
@Slf4j
@Component
public class ConsumerMsg implements ApplicationListener<ContextRefreshedEvent>{
    @Autowired
    private DefaultMQPushConsumer defaultMQPushConsumer;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        defaultMQPushConsumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> messageExtList, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                for(MessageExt messageExt : messageExtList){
                    String topic = messageExt.getTopic();
                    String tags = messageExt.getTags();
                    String keys = messageExt.getKeys();
                    byte[] content = messageExt.getBody();
                    log.info("topic:" + topic + ",tags:" + tags + ",keys:" + keys + ",msg: " + new String(content));
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        try{
            defaultMQPushConsumer.start();
            log.info("Consumer start ...");
        }catch (MQClientException e){
            log.error("Consumer start error! {}", e.getMessage());
        }
    }
}
