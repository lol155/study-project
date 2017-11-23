package cn.signalfire.bigdata.zk;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

import static org.apache.zookeeper.ZooDefs.Ids;


public class SimpleDemo {
    private static Logger logger = LoggerFactory.getLogger(SimpleDemo.class);
    private ZooKeeper zkClient;

    @Before
    public void init() throws IOException {
        zkClient = new ZooKeeper("zk1:2181,zk2:2181,zk3:2181", 2000, new Watcher() {
            public void process(WatchedEvent watchedEvent) {
                System.out.println(watchedEvent.getType());
                try {
                    zkClient.getChildren("/", true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Test
    public void testCreatNode() throws Exception {
        // 参数1：要创建的节点的路径 参数2：节点大数据 参数3：节点的权限 参数4：节点的类型
        String zNode = zkClient.create("/uu12", "aaa".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println(zNode);
    }

    @Test
    public void testExist() throws KeeperException, InterruptedException {
        Stat stat = zkClient.exists("/uu", false);
        System.out.println(stat==null?"not exist":"exist");
    }

    @Test
    public void testGetChildren() throws KeeperException, InterruptedException {
        List<String> children = zkClient.getChildren("/", true);
        for (String child : children) {
            System.out.println(child);
        }
        Thread.sleep(Long.MAX_VALUE);
    }

    @Test
    public void testGetData() throws KeeperException, InterruptedException {
        byte[] data = zkClient.getData("/uu", false, null);
        System.out.println(new String(data));

    }

    @Test
    public void testDeleteZnode() throws KeeperException, InterruptedException {
        //参数2：指定要删除的版本，-1表示删除所有版本
        zkClient.delete("/uu",  -1);
    }

    @Test
    public void testSetData() throws KeeperException, InterruptedException {
        Stat stat = zkClient.setData("/uu1", "bbb".getBytes(), -1);
        byte[] data = zkClient.getData("/uu1", false, null);
        System.out.println(new String(data));
    }
}
