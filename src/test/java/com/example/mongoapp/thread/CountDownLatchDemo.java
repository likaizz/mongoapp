//package com.example.mongoapp.thread;
//
//
//import org.springframework.data.domain.PageRequest;
//
//import java.util.List;
//import java.util.concurrent.CountDownLatch;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
//public class CountDownLatchDemo {
//    public static void main(String[] args) throws InterruptedException {
//        CountDownLatch sign = new CountDownLatch(3);
//        ExecutorService service = Executors.newFixedThreadPool(3);
//        long start = System.currentTimeMillis();
//        for (int i = 0; i < 3; i++) {
//            Runnable runnable = new Runnable() {
//                public void run() {
//                    try {
//                        Thread.sleep((long) (Math.random() * 10000));
//                        System.out.println("线程" + Thread.currentThread().getName() +
//                                "完成");//，当前已有" + (cb.getNumberWaiting()+1) + "个已经到达，" + (cb.getNumberWaiting()==2?"都到齐了，继续走啊":"正在等候"));
//                        sign.countDown();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            };
//            service.execute(runnable);
//        }
//        service.shutdown();
//        sign.await();
//        long end = System.currentTimeMillis();
//        long last = (end - start) / 1000;
//        System.out.println(last);
//
//    }
//
//    public long setAsynchronizedTsak(Runnable run, int poolSize, int threadSize, CountDownLatch count) {
//        long start = System.currentTimeMillis();
//        for (int x = 0; x < poolSize; x++) {
//            ExecutorService service = Executors.newFixedThreadPool(threadSize);
//            for (int y = 0; y < threadSize; y++) {
//                service.submit(run);
//            }
//        }
//
//        try {
//            count.await();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        long end = System.currentTimeMillis();
//        return (end - start) / 1000;
//
//
//    }
//
//    //一个线程池一次可以处理80万数据
//    public long setPoolTsak(long startIdx, long count,int threadSize) throws InterruptedException {
//        int pageSize= (int) Math.ceil(count/threadSize);
//        int firstPage=startIdx
//        CountDownLatch sign = new CountDownLatch(threadSize);
//        ExecutorService service = Executors.newFixedThreadPool(threadSize);
//        long start = System.currentTimeMillis();
//        for (int i = 0; i < threadSize; i++) {
//            TransTask runnable=new TransTask(i+1,pageSize,sign);
//            service.execute(runnable);
//        }
//        service.shutdown();
//        sign.await();
//        long end = System.currentTimeMillis();
//        long last = (end - start) / 1000;
//        return last;
//    }
//    public long setAsynchronizedTsak(long startIdx, long count,long thread) throws InterruptedException {
//        int num=(int)(Math.ceil(count/thread)); //需要调用几次线程池  8000000为每个数据量大小
//        for (int i = 0; i < num; i++) {
//            long start=startIdx+(i*thread+1);//单个线程池的起始索引
//            setPoolTsak(start,thread,4);
//        }
//
//
//}
//
//class TransTask implements Runnable {
//    private int pageNum;
//    private int pageSize;
//    private CountDownLatch sign;
//
//    public TransTask(int pageNum,int pageSize, CountDownLatch sign) {
//        this.pageNum = pageNum;
//        this.pageSize = pageSize;
//        this.sign = sign;
//    }
//    @Override
//    public void run() {
//        try {
//            PageRequest page = new PageRequest(pageNum, pageSize);
//            //查询
//            List find = null;
//            //插入Neo4j
//            sign.countDown();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//}
//
