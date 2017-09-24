package com.wz.job.handler;

import com.wz.job.common.utils.AbstractEventHandler;
import com.wz.job.common.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;

/**
 * <p>根据任务类型进行不同的处理</p>
 * Created by wangzi on 2017-09-22.
 */
@Slf4j
@Component
public class EventHandler extends AbstractEventHandler {
    @Autowired
    private JobHandlerFactory factory;

    @Override
    public void handleAdd(ChildData childData) {
        log.info("handleAdd path:{}", childData.getPath());
        handleUpdate(childData);
    }

    @Override
    public void handleRemove(ChildData childData) {
        log.info("handleRemove path:{}", childData.getPath());
    }

    @Override
    public void handleUpdate(ChildData childData) {
        String path = childData.getPath();
        String data = new String(childData.getData(), Charset.forName(Constants.DEFAULT_CHARSET));
        if (StringUtils.isNotBlank(data)) {
            String[] arr = data.split("_");
            if (arr.length == 2) {
                factory.getHandler(arr[1]).handleUpdate(path, arr[0]);
            }
        }
    }
}
