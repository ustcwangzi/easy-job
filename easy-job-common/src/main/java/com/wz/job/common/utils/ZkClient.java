package com.wz.job.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.framework.recipes.leader.LeaderLatch;
import org.apache.curator.framework.recipes.leader.LeaderLatchListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.charset.Charset;

/**
 * <p>封装zk的操作</p>
 * @author wangzi
 * Created by wangzi on 2017-09-21.
 */
@Slf4j
@Component
public class ZkClient {
    @Value("${zookeeper.host}")
    private String host;
    @Value("${zookeeper.timeout}")
    private int timeout;
    @Value("${zookeeper.retries}")
    private int retries;
    private CuratorFramework client;

    @PostConstruct
    private void init() {
        client = CuratorFrameworkFactory.newClient(host, new ExponentialBackoffRetry(timeout, retries));
        client.start();
        log.info("start curator {}", host);
    }

    /**
     * 创建节点
     *
     * @param path
     */
    public void createPath(String path) {
        try {
            client.create().creatingParentsIfNeeded().forPath(path);
            log.info("create path {}", path);
        } catch (Exception e) {
            log.error("create path {} error: {}", path, e.getMessage());
        }
    }

    /**
     * 创建节点
     *
     * @param path
     * @param value
     */
    public void createPath(String path, String value) {
        try {
            client.create().creatingParentsIfNeeded().forPath(path, value.getBytes(Charset.forName(Constants.DEFAULT_CHARSET)));
            log.info("create path {}", path);
        } catch (Exception e) {
            log.error("create path {} error: {}", path, e.getMessage());
        }
    }

    /**
     * 创建临时节点
     *
     * @param path
     */
    public void createTmpPath(String path) {
        try {
            client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(path);
            log.info("create path {}", path);
        } catch (Exception e) {
            log.error("create path {} error: {}", path, e.getMessage());
        }
    }

    /**
     * 创建临时顺序节点
     *
     * @param path
     */
    public String createTmpSequencePath(String path) {
        String str = null;
        try {
            str = client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath(path);
            log.info("create path {}", str);
        } catch (Exception e) {
            log.error("create path {} error: {}", path, e.getMessage());
        }
        return str;
    }

    /**
     * 删除节点
     *
     * @param path
     */
    public void deletePath(String path) {
        try {
            client.delete().deletingChildrenIfNeeded().forPath(path);
            log.info("delete path {}", path);
        } catch (Exception e) {
            log.error("delete path {} error: {}", path, e.getMessage());
        }
    }

    /**
     * 强制删除节点
     *
     * @param path
     */
    public void deletePathForce(String path) {
        try {
            client.delete().guaranteed().deletingChildrenIfNeeded().forPath(path);
            log.info("delete path {}", path);
        } catch (Exception e) {
            log.error("delete path {} error: {}", path, e.getMessage());
        }
    }

    /**
     * 获取数据
     *
     * @param path
     * @return
     */
    public String getData(String path) {
        String str = null;
        try {
            byte[] bytes = client.getData().forPath(path);
            str = new String(bytes, Charset.forName(Constants.DEFAULT_CHARSET));
        } catch (Exception e) {
            log.error("get data for path {} error: {}", path, e.getMessage());
        }
        return str;
    }

    /**
     * 更新数据
     *
     * @param path
     * @param value
     * @return
     */
    public void updateData(String path, String value) {
        try {
            client.setData().forPath(path, value.getBytes(Charset.forName(Constants.DEFAULT_CHARSET)));
            log.info("update path {}", path);
        } catch (Exception e) {
            log.error("update data for path {} error: {}", path, e.getMessage());
        }
    }

    /**
     * 监听节点
     *
     * @param path
     * @param handle
     */
    public void listenNode(String path, AbstractEventHandler handle) {
        try {
            TreeCache cache = new TreeCache(client, path);
            cache.getListenable().addListener((framework, event) -> {
                switch (event.getType()) {
                    case NODE_ADDED:
                        handle.handleAdd(event.getData());
                        break;
                    case NODE_REMOVED:
                        handle.handleRemove(event.getData());
                        break;
                    case NODE_UPDATED:
                        handle.handleUpdate(event.getData());
                        break;
                    default:
                        break;
                }
            });
            cache.start();
        } catch (Exception e) {
            log.error("listenChildren {} error :{}", path, e.getMessage());
        }
    }

    /**
     * 选主
     *
     * @param path
     * @param listener
     * @return
     */
    public void selectLeader(String path, LeaderLatchListener listener) {
        LeaderLatch leaderLatch = new LeaderLatch(client, path);
        leaderLatch.addListener(listener);
        try {
            leaderLatch.start();
        } catch (Exception e) {
        }
    }
}
