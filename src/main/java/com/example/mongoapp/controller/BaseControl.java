package com.example.mongoapp.controller;

import com.example.mongoapp.async.AsyncTask;
import com.example.mongoapp.dao.GraphicBaseDao;
import com.example.mongoapp.dao.mongo.MRelationDao;
import com.example.mongoapp.dao.neo4j.NRelationDao;
import com.example.mongoapp.entity.Base;
import com.example.mongoapp.entity.Relation;
import com.example.mongoapp.utils.thread.Page;
import com.example.mongoapp.utils.thread.PageResult;
import org.apache.commons.lang3.RandomUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/base")
public class BaseControl {
    @Autowired
    @Qualifier(value = "defaultSDF")
    private SimpleDateFormat defaultSDF;
    @Autowired
    private MongoTemplate template;
    @Autowired
    private NRelationDao nRelationDao;
    @Autowired
    private MRelationDao mRelationDao;
    @Autowired
    private GraphicBaseDao graphicBaseDao;
    @Autowired
    private AsyncTask asyncTask;
    static final String[]Types={"Create","Read","Update","Delete"};

    private List createBase(String label,int num){
        Date date = new Date();
        List<Base> list = new ArrayList<Base>(num);
        for (int x = 0; x < num; x++) {
            Base d = new Base();
            d.setNo(date.getTime() + x);
            d.setMongoId(new ObjectId().toString());
            d.setName((label + defaultSDF.format(date)) + x);
            list.add(d);
        }
        return list;
    }
    @GetMapping("/singleInsert/{type}/{num}")
    public long autoInsertBySingle(@PathVariable("type") String type, @PathVariable("num") int num) throws InterruptedException {
        long startTime=System.currentTimeMillis();
        List list=createBase(type,num);
        PageResult pageResult=new PageResult(type,list,new Page(1,num));
        int cycleTimes=(int) Math.ceil(num/10000.0);
        for(int i=0;i<cycleTimes;i++){
            int from=i*10000;
            int to=from+10000;
            if(i==cycleTimes-1)throw new RuntimeException("test单线程循环插入异常");
            graphicBaseDao.insertMany(pageResult.subPageResult(from,to));
        }
        return System.currentTimeMillis()-startTime;
    }

    @GetMapping("/autoInsert/{type}/{num}")
    public long autoInsert(@PathVariable("type") String type, @PathVariable("num") int num) throws InterruptedException {
        long startTime=System.currentTimeMillis();
        List list=createBase(type,num);
//        template.insert(list,type);
        CountDownLatch sign=new CountDownLatch(1);
        PageResult pageResult=new PageResult(type,list,new Page(1,num));
        asyncTask.insert(pageResult,sign);
        sign.await();
        return System.currentTimeMillis()-startTime;
    }
    @GetMapping("/saveRelation/{num}")
    public long saveRelation(@PathVariable("num") int num,String startName,String endName) {
        Query query = new Query().limit(num).with(Sort.by(Sort.Order.desc("_id")));
        List<Base> starts = template.find(query, Base.class,startName);
        List<Base> ends = template.find(query, Base.class,endName);
        List<Relation> relations = new ArrayList<>(num);
        long s=System.currentTimeMillis();
        for (Base x : starts) {
            int count = RandomUtils.nextInt(0, 4);
            for (int i = 1; i < count; i++) {
                Relation r = new Relation();
                r.setType(Types[RandomUtils.nextInt(0, 4)]);
                r.setStart(x.getMongoId());
                r.setEnd(ends.get(RandomUtils.nextInt(0, num)).getMongoId());
                relations.add(r);
            }
        }
        int size=relations.size();
        int batchSize= (int) Math.ceil(size/2.0);
        for(int i=0;i<2;i++){
            int from=i*batchSize;
            int to=from+batchSize;
            if(i==1)to=size;
            template.insert(relations.subList(from,to),"BaseRelation");
        }
        return System.currentTimeMillis()-s;
    }

    @GetMapping("/transfer/{num}")
    public long transRelation(@PathVariable("num") int num) throws InterruptedException {
        long s0 = System.currentTimeMillis();
        Query query = new Query().limit(num).with(Sort.by(Sort.Order.desc("_id")));
        List<Relation> list = template.find(query, Relation.class,"BaseRelation");
//        List<Relation> list = template.findAll(Relation.class,"BaseRelation");
        long e0 = System.currentTimeMillis();
        System.out.println("mongoTemplate查询耗时:" + (e0 - s0) / 1000);
        long s = System.currentTimeMillis();
        int size=list.size();
        int threadSize= (int) Math.ceil(size/4.0);
        CountDownLatch sign=new CountDownLatch(4);
        ExecutorService service= Executors.newFixedThreadPool(4);
        for(int i=0;i<4;i++){
            int fromIdx=i*threadSize;
            int toIdx=fromIdx+threadSize;
            if(i==3)toIdx=size;
            final List<Relation> subList=list.subList(fromIdx,toIdx);
            Runnable run=new Runnable() {
                @Override
                public void run() {
//                        nRelationDao.insertMany(subList);
                    graphicBaseDao.insertRelation(list,"BaseRelation");
                    sign.countDown();
                }
            };
            service.execute(run);
        }
        service.shutdown();
        sign.await();
        long e = System.currentTimeMillis();
        return e-s;
    }


}
