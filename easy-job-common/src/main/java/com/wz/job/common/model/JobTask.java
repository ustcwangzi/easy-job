package com.wz.job.common.model;

import lombok.Data;

import java.util.Date;

/**
 * <p>任务实体</p>
 * @author wangzi
 * Created by wangzi on 2017-09-20.
 */
@Data
public class JobTask {
    /**
     * 任务ID
     */
    private Integer jobId;
    /**
     * 任务名称
     */
    private String jobName;
    /**
     * 描述
     */
    private String describes;
    /**
     * 任务类型,dobbo、http、mq
     */
    private String jobType;
    /**
     * cron表达式
     */
    private String cron;
    /**
     * 开启/关闭
     */
    private boolean status;
    /**
     * 数据抓取方法
     */
    private String selectMethod;
    /**
     * 任务执行方法
     */
    private String executeMethod;
    /**
     * 入参
     */
    private String params;
    /**
     * zookeeper地址，用于Dubbo方式
     */
    private String zkAddress;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 所有人ID
     */
    private String ownerId;
    /**
     * 是否分发“数据获取”任务
     */
    private String isDispatch;
    /**
     * 协议
     */
    private String dubboAppProtocol;
    /**
     * app名称
     */
    private String dubboAppName;
    /**
     * 版本
     */
    private String dubboAppVersion;
}
