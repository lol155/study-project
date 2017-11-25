package cn.signalfire.bigdata.zk.dist;

import org.apache.zookeeper.ZooKeeper;

public class DistributedServer {

    private static final String connectString = "zk1:2181,zk2:2181,zk2:2181";
    private static final int sessionTimeout = 2000;
    private static final String parentNode = "/servers/";

    private ZooKeeper zooKeeper = null;


    public static void main(String[] args) {
        //获取zk链接
        DistributedServer server = new DistributedServer();
        server.getConnect();

        //利用zk链接注册服务器信息
        server.registerServer(args[0]);

        //启动业务功能
        server.handleBussiness(args[0]);
    }
}
