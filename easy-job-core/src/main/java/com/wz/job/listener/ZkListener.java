package com.wz.job.listener;

import com.wz.job.common.utils.Constants;
import com.wz.job.common.utils.ZkClient;
import com.wz.job.handler.EventHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.recipes.leader.LeaderLatchListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * <p>监听ZK事件</p>
 * @author wangzi
 * Created by wangzi on 2017-09-20.
 */
@Slf4j
@Component
public class ZkListener implements LeaderLatchListener {
    @Autowired
    private ZkClient zkClient;
    @Autowired
    private EventHandler handle;

    @PostConstruct
    private void init() {
        zkClient.selectLeader(Constants.LEADER_PATH, this);
    }

    @Override
    public void isLeader() {
        log.info("I am leader");
        zkClient.listenNode(Constants.ROOT_PATH, handle);
    }

    @Override
    public void notLeader() {
        log.info("I am follower");
    }
}
