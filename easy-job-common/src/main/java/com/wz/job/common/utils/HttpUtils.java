package com.wz.job.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>http工具类</p>
 * @author wangzi
 * Created by wangzi on 2017-10-11.
 */
@Slf4j
public class HttpUtils {
    public static String post(String uri, String data) throws Exception {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost http = new HttpPost(uri);
        List<BasicNameValuePair> list = new ArrayList<>();
        http.setHeader("Content-Type", "application/json;charset=UTF-8");
        StringEntity entity = new StringEntity(data);
        entity.setContentType("text/json");
        http.setEntity(entity);
        log.info("post data to : {}", uri);
        CloseableHttpResponse response = client.execute(http);
        String result = null;
        if (response.getEntity() != null) {
            result = EntityUtils.toString(response.getEntity(), Constants.DEFAULT_CHARSET);
        }
        response.close();
        client.close();
        return result;
    }

    public static String get(String uri) throws Exception {
        String result = null;
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet http = new HttpGet(uri);
        log.info("get data from : {}", uri);
        CloseableHttpResponse response = client.execute(http);
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            result = EntityUtils.toString(entity);
        }
        response.close();
        client.close();
        return result;
    }
}
