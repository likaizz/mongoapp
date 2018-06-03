package com.example.mongoapp.testDao;

import com.example.mongoapp.MongoappApplication;
import com.example.mongoapp.dao.mysql.DemoDaoOrigin;
import com.example.mongoapp.entity.Demo;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest(classes=MongoappApplication.class)
@RunWith(SpringRunner.class)
public class ODemoDaoTest {
    @Autowired
    private DemoDaoOrigin demoDao;

    @Test
    public void test(){
//        System.out.println(UUID.randomUUID().toString());
        List<Demo> list=create(50000);

        long s=System.currentTimeMillis();
        demoDao.insertMany(list);
//        demoDao.insertOne(list.get(0));
        long e=System.currentTimeMillis();
        System.out.println(e-s);  //1615  不加   加了multiquery 1547  1535
    }

    @Test
    public void multiThread() throws InterruptedException {
        int num=4;
        CountDownLatch sign = new CountDownLatch(num);
        ExecutorService service = Executors.newFixedThreadPool(num);
        long start = System.currentTimeMillis();
        for (int i = 0; i < num; i++) {
            Runnable runnable = new Runnable() {
                public void run() {
                    try {
                        demoDao.insertMany(create(25000));
                        sign.countDown();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            service.execute(runnable);
        }
        service.shutdown();
        sign.await();
        long end = System.currentTimeMillis();
        long last = (end - start) ;
        System.out.println(last);
    }

    public List<Demo>create(int num){
        List<Demo> list=new ArrayList<>(num);
        for(int x=0;x<num;x++){
            Demo d=new Demo();
            d.setName(UUID.randomUUID().toString());
            d.setAge(RandomUtils.nextInt(0,100));
            list.add(d);
        }
        return list;
    }


}
