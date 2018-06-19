package com.example.mongoapp.async;

import com.example.mongoapp.dao.GraphicBaseDao;
import com.example.mongoapp.dao.OriginBasicDao;
import com.example.mongoapp.dao.oracle.OPersonDaoOrigin;
import com.example.mongoapp.entity.Person;
import com.example.mongoapp.utils.thread.Page;
import com.example.mongoapp.utils.thread.PageResult;
import com.example.mongoapp.utils.thread.PageTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Component
public class AsyncTask {
    private static final Logger log = LoggerFactory.getLogger(AsyncTask.class);

    /**
     * 异常调用返回Future
     * 对于返回值是Future，不会被AsyncUncaughtExceptionHandler处理，需要我们在方法中捕获异常并处理
     * 或者在调用方在调用Futrue.get时捕获异常进行处理
     *
     * @param i
     * @return
     */
    @Async
    public Future<String> asyncInvokeReturnFuture(int i) {
        log.info("asyncInvokeReturnFuture, parementer={}", i);
        Future<String> future;
        try {
            Thread.sleep(1000 * 1);
            future = new AsyncResult<String>("success:" + i);
            throw new IllegalArgumentException("a");
        } catch (InterruptedException e) {
            future = new AsyncResult<String>("error");
        } catch (IllegalArgumentException e) {
            future = new AsyncResult<String>("error-IllegalArgumentException");
        }
        return future;
    }


    @Autowired
    private GraphicBaseDao graphicBaseDao;
    private ExecutorService pool = Executors.newFixedThreadPool(4);

    @Async
    public Future<PageResult> query(String label, Page page, OriginBasicDao originBasicDao/*, CountDownLatch sign*/) throws InterruptedException {
        long startTime = System.currentTimeMillis();
        List result = originBasicDao.findByIdx(page.getStartIdx(), page.getEndIdx());
        PageResult pageResult = new PageResult(label, result, page);
//        System.out.println("query耗时:" + (System.currentTimeMillis() - startTime) + "ms");
//        if (sign != null) sign.countDown();
        return new AsyncResult<PageResult>(pageResult);
    }

    //    @Async
    public void insert(PageResult pageResult, CountDownLatch sign) throws InterruptedException {
        List<PageResult> subPages = subPages(pageResult, 10000);
        int num = subPages.size();
        if (num <= 10) {//单线程多次循环
            for (int i = 0; i < num; i++) {
                System.out.println(subPages.get(i)/*.getQueryResult()*/);
                graphicBaseDao.insertMany(subPages.get(i));
            }
        } else {//多线程  每个线程承载的数据量在10万以内<=10万 平均分配  4个线程
            insertByMulti(subPages, 4);

            /*int threadNum = 4;
            int poolCount = (int) Math.ceil(num / 400000.0);
            int threadSize = poolCount == 1 ? (int) Math.ceil(num / (double) (threadNum)) : 100000;
            CountDownLatch subSign = new CountDownLatch(threadNum * poolCount);
            for (int t = 0; t < poolCount; t++) {

            }*/

        }
        if (sign != null) sign.countDown();
    }

    private void insertByMulti(List<PageResult> subPages/*PageResult pageResult*/, int threadNum) throws InterruptedException {
        double num = subPages.size();
        int threadSize = (int) Math.ceil(num / threadNum);
        CountDownLatch sign = new CountDownLatch(threadNum);
        for (int i = 0; i < threadNum; i++) {
            int startIdx = i * threadSize;
            int endIdx = startIdx + threadSize;
            if (i == threadNum - 1) endIdx = (int) num;
            List<PageResult> single = subPages.subList(startIdx, endIdx);
            PageTask pageTask = new PageTask(single, graphicBaseDao, sign);
            pool.execute(pageTask);
        }
        sign.await();
    }

    /*@Async
    public void subInsert(PageResult page, int cycleSize) throws InterruptedException {
        List list = page.getQueryResult();
        double num = list.size();
        int count = (int) Math.ceil(num / cycleSize);
        CountDownLatch subSign = new CountDownLatch(count);
        for (int x = 0; x < count; x++) {
            int startIdx = x * cycleSize;
            int endIdx = startIdx + cycleSize;
            if (x == count - 1) endIdx = (int) num;
            List subList = list.subList(startIdx, endIdx);
            PageResult subPage = new PageResult();//null;//page.subPage();
            subPage.setQueryResult(subList);
            subPage.setLabelName(page.getLabelName());
            subPage.setPage(new Page(startIdx, endIdx));
            insert(subPage, subSign);
        }
    }*/

    public List<PageResult> subPages(PageResult page, int size) {
        List list = page.getQueryResult();
        double num = list.size();
        int count = (int) Math.ceil(num / size);
        List<PageResult> queue = new ArrayList<PageResult>(count);
        for (int x = 0; x < count; x++) {
            int startIdx = x * size;
            int endIdx = startIdx + size;
            if (x == count - 1) endIdx = (int) num;
//            List subList = list.subList(startIdx, endIdx);
//            PageResult subPage = new PageResult();//null;//page.subPage();
//            subPage.setQueryResult(subList);
//            subPage.setLabelName(page.getLabelName());
//            subPage.setPage(new Page(startIdx, endIdx));
            queue.add(page.subPageResult(startIdx, endIdx));
        }
        return queue;
    }

    @Autowired
    OPersonDaoOrigin oPersonDaoOrigin;

    //    @Async
    public Person test(String name) throws InterruptedException {

        System.out.println("AsyncTask.test():" + Thread.currentThread().getName());
        Person p = oPersonDaoOrigin.findOne();
        p.setName(name);
        oPersonDaoOrigin.update(p);
        return p;
    }

}