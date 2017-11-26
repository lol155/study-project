package cn.signalfire.bigdata.zk.dist;

import org.apache.zookeeper.ZooKeeper;

import java.util.List;

public class DistributedClient {
    private static final String connectString = "zk1:2181,zk2:2181,zk2:2181";
    private static final int sessionTimeout = 2000;
    private static final String parentNode = "/servers/";

    private volatile List<String> serverList;
    private ZooKeeper zk = null;

    public static void main(String[] args) {
        //获取zk链接
        DistributedClient client = new DistributedClient();
        client.getConnect();
        //获取servers的子节点信息（并监听），从中获取服务器信息列表
        client.getServerList();
        //业务线程启动
        client.handleBussuness();

    }
}
