package com.wz.job.demo.mq.config;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.protocol.heartbeat.MessageModel;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>mq配置</p>
 * Created by wangzi on 2017-10-11.
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "mq")
public class MqConfiguration {
    private String producerGroup;
    private String instanceName;
    private String nameServerAddress;
    private String subscriberID;
    private String topic;

    @Bean
    public DefaultMQProducer defaultMQProducer() throws MQClientException {
        DefaultMQProducer producer = new DefaultMQProducer(producerGroup);
        producer.setNamesrvAddr(nameServerAddress);
        producer.setInstanceName(instanceName);
        producer.setVipChannelEnabled(false);
        producer.start();
        return producer;
    }

    @Bean
    public DefaultMQPushConsumer defaultMQPushConsumer(){
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(subscriberID);
        consumer.setNamesrvAddr(nameServerAddress);
        consumer.setMessageModel(MessageModel.CLUSTERING);
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        consumer.setVipChannelEnabled(false);
        consumer.setPullBatchSize(20);
        consumer.setConsumeMessageBatchMaxSize(200);
        try{
            consumer.subscribe(topic, "*");
        }catch (MQClientException e){
            e.printStackTrace();
        }
        return consumer;
    }
}
