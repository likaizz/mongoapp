/*
package com.example.mongoapp.controller;

import com.example.mongoapp.dao.NodeDemoRepository;
import com.example.mongoapp.entity.NodeDemo;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/path")
public class NodeController {
    @Autowired
    private NodeDemoRepository dao;

    @GetMapping("/save/single/{num}")
    public long saveBySingle(@PathVariable("num") int num) {
        List<NodeDemo> list = find(num);
        long s = System.currentTimeMillis();
        dao.saveAll(list);
        long e = System.currentTimeMillis();
        return e - s;
    }

    @GetMapping(
"/save/multi/{startIdx}/{endIdx}"
"/save/multi/{num}")
    @ResponseBody
    public long saveByMulti(@PathVariable("num") long num,
("startIdx") long startIdx, @PathVariable("endIdx") long endIdx,
 int batchSize, int thread) throws InterruptedException {
//        long num = endIdx - startIdx + 1;
        int cycleTimes = (int) Math.ceil((0.0 + num) / (batchSize * thread));
        ExecutorService pool = Executors.newFixedThreadPool(thread);
        CountDownLatch sign = new CountDownLatch(cycleTimes * thread);
        long s = System.currentTimeMillis();
        for (int time = 0; time < cycleTimes; time++) {
            for (int i = 0; i < thread; i++) {
                final int curTime = time;
                final int curIdx = i;
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
//                        long sIdx = startIdx + curTime*(batchSize*thread)+curIdx*batchSize;
//                        int size = batchSize;
//                        if (curTime == (cycleTimes - 1)) {
//                            size = (int) (num - batchSize * thread * curTime - batchSize * (curIdx + 1));
//                            if (size <0) size = (int) (num - batchSize * thread * curTime - batchSize * curIdx);
//                        }
//                        long eIdx = sIdx + size-1;
                        List<NodeDemo> list = find(batchSize);
                        System.out.println("curTime:" + curTime + "curIdx" + curIdx + "   "
+ "startIdx:"+sIdx +"  endIdx:"+eIdx
);
                        dao.saveAll(list);
                        sign.countDown();
                    }
                };
                pool.submit(runnable);

            }
        }
        pool.shutdown();
        sign.await();
        long e = System.currentTimeMillis();
        return e - s;
    }

    //    public List<NodeDemo> find(long startIdx, long endIdx) {
    public List<NodeDemo> find(int num) {
//        int num = (int) (endIdx - startIdx + 1);
        List<NodeDemo> list = new ArrayList<>(num);
        Date d = new Date();
        for (int x = 0; x < num; x++) {
            NodeDemo demo = new NodeDemo();
            demo.setName("demo" + x);
            demo.setAge(RandomUtils.nextInt(0, 150));
            demo.setBirthday(d);
            list.add(demo);
        }
        return list;
    }

}
*/
