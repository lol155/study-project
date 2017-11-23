package cn.signalfire.bigdata.zk;

import org.apache.zookeeper.*;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static org.apache.zookeeper.ZooDefs.*;


public class SimpleDemo {
    private static Logger logger = LoggerFactory.getLogger(SimpleDemo.class);
    private ZooKeeper zkClient;

    @Before
    public void init() throws IOException {
        zkClient = new ZooKeeper("zk1:2181,zk2:2181,zk3:2181", 2000, new Watcher() {
            public void process(WatchedEvent watchedEvent) {
                System.out.println(watchedEvent.getType());
            }
        });
    }

    @Test
    public void creatNode() throws Exception {
        // 参数1：要创建的节点的路径 参数2：节点大数据 参数3：节点的权限 参数4：节点的类型
        String zNode = zkClient.create("/uu", "aaa".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println(zNode);
    }
}
