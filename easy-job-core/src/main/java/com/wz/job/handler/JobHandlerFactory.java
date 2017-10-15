package com.wz.job.handler;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>获取处理方法</p>
 * @author wangzi
 * Created by wangzi on 2017-09-22.
 */
@Service
public class JobHandlerFactory implements BeanPostProcessor {
    private Map<String, Class> handlerMap = new ConcurrentHashMap<>();

    public Class getHandler(String type) {
        return handlerMap.get(type);
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        JobHandlerService handlerService = AnnotationUtils.findAnnotation(bean.getClass(), JobHandlerService.class);
        if (handlerService == null) {
            return bean;
        }
        String type = handlerService.value();
        if (StringUtils.isNotBlank(type)) {
            handlerMap.put(type, bean.getClass());
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
