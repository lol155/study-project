package cn.signalfire.bigdata.thread;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.*;



public class TestFuture {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService ex = Executors.newCachedThreadPool();

        ArrayList<Future<String>> futureList = Lists.newArrayList();
        for (int i = 0; i < 6; i++) {
            final int finalI = i;
            Future<String> submit = ex.submit(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    System.out.println(finalI);
                    Thread.sleep(new Random().nextInt(5000) + 1000);
                    return Thread.currentThread().getName() + "...count = " + finalI + "time.." + System.currentTimeMillis();
                }
            });
            futureList.add(submit);
        }


        for (Future<String> future : futureList) {
            System.out.println("=============");
            boolean done = future.isDone();
            System.out.println("done.." + done + " time.." + System.currentTimeMillis());
            System.out.println(future.get() + System.currentTimeMillis());
        }
        ex.shutdown();
    }
}
