package com.wz.job.demo.dubbo.service;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>任务实体</p>
 * Created by wangzi on 2017-09-19.
 */
@Data
public class Order implements Serializable {
    private Integer id;
    private String detail;

    public Order(Integer id, String detail) {
        this.id = id;
        this.detail = detail;
    }
}
