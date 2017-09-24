package com.wz.job.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.recipes.cache.ChildData;

/**
 * <p>节点变更默认处理方法</p>
 * Created by wangzi on 2017-09-21.
 */
@Slf4j
public abstract class AbstractEventHandler {
    public void handleAdd(ChildData data){
        log.info("handleAdd");
    }

    public void handleRemove(ChildData data){
        log.info("handleRemove");
    }

    public void handleUpdate(ChildData data){
        log.info("handleUpdate");
    }
}
