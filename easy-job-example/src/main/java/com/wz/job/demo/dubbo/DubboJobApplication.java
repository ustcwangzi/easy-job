package com.wz.job.demo.dubbo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by wangzi on 2017-09-19.
 */
@SpringBootApplication
public class DubboJobApplication {
    public static void main(String[] args) {
        SpringApplication.run(DubboJobApplication.class, args);
        synchronized (DubboJobApplication.class){
            while (true){
                try {
                    DubboJobApplication.class.wait();
                } catch (InterruptedException e) {
                }
            }
        }
    }
}
