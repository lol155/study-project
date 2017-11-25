package cn.signalfire.bigdata.zk.dist;

import org.apache.zookeeper.*;

public class DistributedServer {

    private static final String connectString = "zk1:2181,zk2:2181,zk2:2181";
    private static final int sessionTimeout = 2000;
    private static final String parentNode = "/servers/";

    private ZooKeeper zk = null;


    public static void main(String[] args) throws Exception {
        //获取zk链接
        DistributedServer server = new DistributedServer();
        server.getConnect();

        //利用zk链接注册服务器信息
        server.registerServer(args[0]);

        //启动业务功能
        server.handleBussiness(args[0]);
    }

    private void handleBussiness(String hostname) throws InterruptedException {
        System.out.println(hostname + "start working ..");
        Thread.sleep(Long.MAX_VALUE);

    }

    private void registerServer(String hostname) throws Exception {
        String create = zk.create(parentNode + "server", hostname.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.println(hostname + "is online .. " + create);
    }

    private void getConnect() throws Exception {
        new ZooKeeper(connectString, sessionTimeout, new Watcher() {
            public void process(WatchedEvent watchedEvent) {
                System.out.println(watchedEvent.getType() + "---" + watchedEvent.getPath());
                // 收到事件通知后的回调函数（应该是我们自己的事件处理逻辑）
                try {
                    zk.getChildren("/", true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
