package com.wz.job;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>任务启动类</p>
 * @author wangzi
 * Created by wangzi on 2017-09-24.
 */
@SpringBootApplication
public class JobApplication {
    public static void main(String[] args) {
        SpringApplication.run(JobApplication.class, args);
        synchronized (JobApplication.class) {
            while (true) {
                try {
                    JobApplication.class.wait();
                } catch (InterruptedException e) {
                }
            }
        }
    }
}
