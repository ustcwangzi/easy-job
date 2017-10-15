package com.wz.job.demo.mq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>MQ方式demo</p>
 * Created by wangzi on 2017-10-11.
 */
@SpringBootApplication
public class MqJobApplication {
    public static void main(String[] args) {
        SpringApplication.run(MqJobApplication.class, args);
        synchronized (MqJobApplication.class){
            while (true){
                try{
                    MqJobApplication.class.wait();
                }catch (Throwable throwable){
                }
            }
        }
    }
}
