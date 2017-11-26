package cn.signalfire.bigdata.thread;

public class TestThread extends Thread {
    private String flag;

    public TestThread(String flag) {
        this.flag = flag;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() +" 。。。" + this.flag);

    }

    public static void main(String[] args) {
        new TestThread("1").start();
        new TestThread("2").start();
    }
}
