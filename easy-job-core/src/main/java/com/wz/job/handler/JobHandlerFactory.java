package com.wz.job.handler;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>获取处理方法</p>
 * Created by wangzi on 2017-09-22.
 */
@Service
public class JobHandlerFactory implements BeanPostProcessor {
    private Map<String, AbstractJobHandler> handlerMap = new ConcurrentHashMap<>();

    public AbstractJobHandler getHandler(String type) {
        return handlerMap.get(type);
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        JobHandlerService handlerService = AnnotationUtils.findAnnotation(bean.getClass(), JobHandlerService.class);
        if (handlerService == null) {
            return bean;
        }
        String type = handlerService.value();
        if (!StringUtils.isEmpty(type)) {
            handlerMap.put(type, (AbstractJobHandler) bean);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
