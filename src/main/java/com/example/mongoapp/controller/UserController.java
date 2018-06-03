package com.example.mongoapp.controller;

import com.example.mongoapp.dao.UserDao;
import com.example.mongoapp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/user")
public class UserController {
    //    public  long start;
//    public  long end;
    @Autowired
    private UserDao dao;



    @GetMapping("/insert")
    public User insertOne(User u){
        if(u==null)throw new NullPointerException("参数为空");
        System.out.println(u);
         u=dao.save(u);
        return u;
    }

    @GetMapping("/autoInsert")
    public User autoInsert(){
        User demo=new User();
        demo.setName("demo");
        demo.setBirthday(new Date());
        demo.setMongoId(new ObjectId());
        demo=dao.save(demo);
        return demo;
    }



    @GetMapping("/insertMany")
    public String insertMany() {
        ExecutorService service = Executors.newCachedThreadPool();
        final CountDownLatch sign = new CountDownLatch(3);
        long start = System.currentTimeMillis();
        for (int i = 0; i < 3; i++) {
            Runnable runnable = new Runnable() {
                public void run() {
                    try {
                        int size = 100;//Integer.MAX_VALUE/256;
                        List<User> list = new ArrayList<>(size);
                        for (int x = 0; x < size; x++) {
                            User u = new User();
                            u.setName(UUID.randomUUID().toString());
                            u.setAge((double) Math.round(Math.random() * 100));
                            list.add(u);
                            System.out.println(Thread.currentThread().getName() + "正在制造第" + (x + 1) + "个对象");
                        }
//                    int m=cb.getNumberWaiting();
//                    if(m==0)start=System.currentTimeMillis();
                        System.out.println("开始存入mongodb...");
//                        dao.saveAll(list);
                        System.out.println("存入mongodb完毕");
//                    System.out.println("线程" + Thread.currentThread().getName() +
//                            "完成任务，当前已有" + (cb.getNumberWaiting() + 1) + "个完成，" + (cb.getNumberWaiting()==2?"都完成了":"正在等候"));
//                    cb.await();
                        sign.countDown();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            service.execute(runnable);
        }
        service.shutdown();
        try {
            // awaitTermination返回false即超时会继续循环，返回true即线程池中的线程执行完成主线程跳出循环往下执行，每隔10秒循环一次
//        while (!service.awaitTermination(10, TimeUnit.SECONDS));
            sign.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - start) / 1000);

        return "已经完成任务";

    }
//    @GetMapping("/getSpecial")
//    public List<User> myspecialfindTest(){
//        Query query=new Query().addCriteria(Criteria.where("age").gte(10));
//        List<User>result= dao.myspecialfind(query);//Examplequery
////        for (User x:result
////                ) {
////            System.out.println(x);
////        }
//        return result;
//    }
//     @GetMapping("/getAll")
//    public List<User> find() {
//        System.out.println(dao);
//        return dao.findAll();
//    }
//
//    @PutMapping("/update")
//    public void update(){
//
//    }




}
