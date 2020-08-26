package com.laughing.zookeepertest.config;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * @author Fu zihao
 * @version 1.0
 * @Description: Zookeeper初始化配置类
 * @date 20202020/8/26 14:10
 */
@Configuration
public class ZookeeperConfiguration {
    private String connect = "49.232.61.212:2181";
    private int sessionTimeout = 3000;

    @Bean(name = "zkClient")
    public ZooKeeper zkClient() {
        ZooKeeper zkClient = null;

        try {
            zkClient = new ZooKeeper(connect, sessionTimeout, new Watcher() {
                @Override
                public void process(WatchedEvent watchedEvent) {

                }
            });
        } catch (IOException e) {
            System.out.println("Zookeeper连接异常");
            e.printStackTrace();
        }
        return zkClient;
    }
}
