package com.wz.job.demo.mq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>MQ方式demo</p>
 * Created by wangzi on 2017-10-11.
 */
@SpringBootApplication
public class MQJobApplication {
    public static void main(String[] args) {
        SpringApplication.run(MQJobApplication.class, args);
        synchronized (MQJobApplication.class){
            while (true){
                try{
                    MQJobApplication.class.wait();
                }catch (Throwable throwable){
                }
            }
        }
    }
}
