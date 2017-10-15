package com.wz.job.demo.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>任务实体</p>
 * @author wangzi
 * Created by wangzi on 2017-09-19.
 */
@Data
public class Order implements Serializable {
    private Integer id;
    private String detail;

    public Order() {
    }

    public Order(Integer id, String detail) {
        this.id = id;
        this.detail = detail;
    }
}
