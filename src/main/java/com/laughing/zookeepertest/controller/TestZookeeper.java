package com.laughing.zookeepertest.controller;

import org.apache.zookeeper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;

/**
 * @author Fu zihao
 * @version 1.0
 * @Description: zookeeper API 基本操作
 * @date 20202020/8/26 13:48
 */
@RestController
public class TestZookeeper {

    @Autowired
    private ZooKeeper zkClient;


    /**
     * 创建zookeeper 持久化节点
     * http://localhost:9090/new/laughing8/aaa
     *
     * @param path
     * @param data
     * @throws KeeperException
     * @throws InterruptedException
     */
    @GetMapping("/new/{path}/{data}")
    public void newNodes(@PathVariable("path") String path, @PathVariable("data") String data) throws KeeperException, InterruptedException {
        System.out.println("节点" + path + "创建,data:" + data);
        // CreateMode.PERSISTENT 持久  EPHEMERAL_SEQUENTIAL 暂时序列
        // ZooDefs.Ids.OPEN_ACL_UNSAFE访问权限
        zkClient.create("/" + path, data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

    }

    /**
     * 判断节点是否存在
     *
     * @param path
     * @param needWatch
     * @return
     * @throws KeeperException
     * @throws InterruptedException
     */
    @GetMapping("/existe/{path}")
    public Stat existe(@PathVariable("path") String path, boolean needWatch) throws KeeperException, InterruptedException {

        return zkClient.exists("/" + path, false);

    }

    /**
     * 修改节点
     * @param path
     * @param data
     * @throws KeeperException
     * @throws InterruptedException
     */
    @GetMapping("/update/{path}/{data}")
    public void updateNodes(@PathVariable("path") String path, @PathVariable("data") String data) throws KeeperException, InterruptedException {
        //zk的数据版本是从0开始计数的。如果客户端传入的是-1，则表示zk服务器需要基于最新的数据进行更新。如果对zk的数据节点的更新操作没有原子性要求则可以使用-1.
        //version参数指定要更新的数据的版本, 如果version和真实的版本不同, 更新操作将失败. 指定version为-1则忽略版本检查

        zkClient.setData(path, data.getBytes(), -1);

    }

    /**
     * 获取当前节点的子节点(不包含孙子节点)
     *
     * @param path 父节点path
     */
    @GetMapping("/getChildren/{path}")
    public List<String> getChildren(@PathVariable("path") String path) throws KeeperException, InterruptedException {
        List<String> list = zkClient.getChildren("/" + path, false);
        return list;
    }

    /**
     * 获取当前节点的子节点(不包含孙子节点) 持续监听
     *
     * @param path 父节点path
     */
    @GetMapping("/getChildren/{path}")
    public List<String> getChildrenWatch(@PathVariable("path") String path) throws KeeperException, InterruptedException {
        List<String> list = zkClient.getChildren("/" + path, true);
        return list;
    }



}
