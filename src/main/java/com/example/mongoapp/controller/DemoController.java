package com.example.mongoapp.controller;

import com.example.mongoapp.async.AsyncTask;
import com.example.mongoapp.dao.GraphicBaseDao;
import com.example.mongoapp.dao.mongo.MDemoDao;
import com.example.mongoapp.dao.mongo.MRelationDao;
import com.example.mongoapp.dao.neo4j.NDemoCopyDao;
import com.example.mongoapp.dao.neo4j.NDemoDao;
import com.example.mongoapp.dao.neo4j.NPersonDao;
import com.example.mongoapp.dao.neo4j.NRelationDao;
import com.example.mongoapp.entity.Demo;
import com.example.mongoapp.entity.DemoCopy;
import com.example.mongoapp.entity.Person;
import com.example.mongoapp.entity.Relation;
import com.example.mongoapp.utils.thread.PageResult;
import org.apache.commons.lang3.RandomUtils;
import org.bson.types.ObjectId;
import org.neo4j.ogm.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/demo")
public class DemoController {
    @Autowired
    @Qualifier(value = "defaultSDF")
    private SimpleDateFormat defaultSDF;
    @Autowired
    private MongoTemplate template;
    @Autowired
    private MDemoDao mdao;
    @Autowired
    private NDemoDao ndao;
    @Autowired
    private NDemoCopyDao nDemoCopyDao;
    @Autowired
    private NRelationDao nRelationDao;

    @Autowired
    private MRelationDao mRelationDao;
    @Autowired
    private GraphicBaseDao graphicBaseDao;
    @Autowired
    private AsyncTask asyncTask;

    @PostConstruct
    public void init(){
        System.out.println("demo init");
    }

    @GetMapping("/insert/{type}/{num}")
    public String insertBySingle(@PathVariable("type") String type, @PathVariable("num") int num) {
        Date date = new Date();
        List<Demo> list = new ArrayList<>(num);
        for (int x = 0; x < num; x++) {
            Demo d = new Demo((type + defaultSDF.format(date)) + x);
//            d.setNo(date.getTime() + x);
//            d.setMongoId(new ObjectId());
            d.setMongoId(new ObjectId().toString());
            list.add(d);
        }
        long startTime = System.currentTimeMillis();
        if ("once".equalsIgnoreCase(type)) {
            ndao.saveAll(list);
            return (System.currentTimeMillis() - startTime) + "";
        } else {
            StringBuilder sb = new StringBuilder();
            int count = (int) Math.ceil(num / 10000.0);
            for (int x = 0; x < count; x++) {
                int startIdx = x * 10000;
                int endIdx = startIdx + 10000;
                if (x == count - 1) endIdx = num;
                List subList = list.subList(startIdx, endIdx);// [startIdx,endIdx)
                sb.append("x:" + " s=" + startIdx + "  ,e=" + endIdx + "  ,size=" + subList.size() + "\r");
                ndao.saveAll(subList);
            }
            return sb.append(System.currentTimeMillis() - startTime).toString();
        }

    }

    @GetMapping("/autoInsert/{type}/{num}")
    public long autoInsert(@PathVariable("type") String type, @PathVariable("num") int num) throws InterruptedException {
        Date date = new Date();
        List list = new ArrayList<>(num);
        long startTime = System.currentTimeMillis();
        if ("demo".equalsIgnoreCase(type)) {
            for (int x = 0; x < num; x++) {
                Demo d = new Demo((type + defaultSDF.format(date)) + x);
//                d.setNo(date.getTime() + x);
                list.add(d);
            }
//            Demo result =mdao.insert(list.get(0));
            //System.out.println(result==list.get(0));//返回的结果就是对象本身
            //list.add(result);
//            Demo result = list.get(0);
//            System.out.println("修改前的记录:" + result);
//            mdao.insert(result);
//            result.setName("修改名字");
//            mdao.insert(result);  //insert同一记录修改主键重复报错  index: _id_ dup key
//            mdao.save(result);     //save同一记录修改主键重复报错  index: _id_ dup key
//            ndao.saveAll(list);
//            System.out.println(result);
        } else {
            for (int x = 0; x < num; x++) {
                DemoCopy d = new DemoCopy((type + defaultSDF.format(date)) + x);
                d.setMongoId(new ObjectId().toString());
                d.setNo(date.getTime() + x);
                list.add(d);
            }
//            long s = System.currentTimeMillis();
//           if("my".equalsIgnoreCase("my")) ndao.insertMany(list);  //自定义存储方法  9s
//            if ("demo".equalsIgnoreCase(type)) mdao.insert(list);
//            else template.insert(copyList, DemoCopy.class);
//            nDemoCopyDao.saveAll(list);
//            long e = System.currentTimeMillis();
//            System.out.println("存入" + type + "集合中" + num + "条数据耗时:" + (e - s) / 1000 + " s");
        }
        CountDownLatch sign = new CountDownLatch(1);
        PageResult pageResult = null;//new PageResult(type, list, new Page(1, num));
        asyncTask.insert(pageResult, sign);
        sign.await();
        return System.currentTimeMillis() - startTime;
//        return list;     //插入完成后list集合中的对象主键有值
    }

    @GetMapping("/saveRelation")
    public long saveRelation() {
//        Query query = new Query().limit(1000000);//.with(Sort.by(Sort.Order.desc("_id")));
//        List<Demo> demos = template.find(query, Demo.class);
//        List<DemoCopy> demoCopys = template.find(query, DemoCopy.class);
//        List<Relation> relations = new ArrayList<>(100000);
        long s = System.currentTimeMillis();
        List<String> demos = ndao.get(200000);
        List<String> demoCopys = nDemoCopyDao.get(400000);
        List<Relation> relations = new ArrayList<>(400000);
        for (String x : demos) {
            int count = RandomUtils.nextInt(0, 4);
            for (int i = 1; i < count; i++) {
                Relation r = new Relation();
                r.setType((Math.random() > 0.5) ? "Father" : "Monther");
                r.setStart(x);
                r.setEnd(demoCopys.get(RandomUtils.nextInt(0, 400000)));
                relations.add(r);
            }
        }
        int size = relations.size();
        int batchSize = (int) Math.ceil(size / 2.0);
        for (int i = 0; i < 2; i++) {
            int from = i * batchSize;
            int to = from + batchSize;
            if (i == 1) to = size;
            template.insert(relations.subList(from, to), Relation.class);
        }
        return System.currentTimeMillis() - s;
    }

    @GetMapping("/transRelation/{num}")
    public long transRelation(@PathVariable("num") int num) throws InterruptedException {
        Query query = new Query().limit(num);//.with(Sort.by(Sort.Order.desc("_id")));
        long s0 = System.currentTimeMillis();
        List<Relation> list = template.find(query, Relation.class);
        long e0 = System.currentTimeMillis();
        System.out.println("mongoTemplate查询耗时:" + (e0 - s0) / 1000);
        long s = System.currentTimeMillis();
        int threadSize = (int) Math.ceil(num / 4.0);
        CountDownLatch sign = new CountDownLatch(4);
        ExecutorService service = Executors.newFixedThreadPool(4);
        for (int i = 0; i < 4; i++) {
            int fromIdx = i * threadSize;
            int toIdx = fromIdx + threadSize;
            if (i == 3) toIdx = num;
            final List<Relation> subList = list.subList(fromIdx, toIdx);
            Runnable run = new Runnable() {
                @Override
                public void run() {
//                        nRelationDao.insertMany(subList);
                    graphicBaseDao.insertRelation(list, "Relation");
                    sign.countDown();
                }
            };
            service.execute(run);
        }
        service.shutdown();
        sign.await();

        long e = System.currentTimeMillis();
        return e - s;
    }


    @GetMapping("/transNode/{type}")
    public Iterable transNode(@PathVariable("type") String type) {
        Query query = new Query().limit(100000);//.with(Sort.by(Sort.Order.desc("_id")));
        if ("Demo".equalsIgnoreCase(type)) {
            List<Demo> result = template.find(query, Demo.class);//
            long s = System.currentTimeMillis();
            Iterable<Demo> save = //ndao.save(result);
                    ndao.saveAll(result);
            long e = System.currentTimeMillis();
            System.out.println("数据迁移耗时:" + (e - s) / 1000 + "s");
            return save;
        } else {
            List<DemoCopy> result = template.find(query, DemoCopy.class);//
            long s = System.currentTimeMillis();
            Iterable<DemoCopy> save = nDemoCopyDao.//save(result);
                    saveAll(result);
            long e = System.currentTimeMillis();
            System.out.println("数据迁移耗时:" + (e - s) / 1000 + "s");
            return save;
        }
    }


    @GetMapping("/getAll")
    public List<Demo> getAll() {
        return mdao.findAll();
    }

//    @GetMapping("/getByMongoId/{id}")
//    public Demo getByMongoId(@PathVariable("id") String objectId) {
//        return mdao.findById(objectId).get();
//    }

   /* @GetMapping("/getByNeo4jId/{id}")
    public Demo getByNeo4jId(@PathVariable("id") Long id) {
        return    ndao.//findOne(id);//ndao.
         findById(id).get();
    }*/


    @GetMapping("/getIds")
    public List getSimpleObject() {
//        List<Demo>result=mdao.myfindAll();
//        List<Relation>result=template.findAll(Relation.class);
        List<Relation> result = mRelationDao.findAll();//myFindAll();
        Relation r = result.get(0);
        System.out.println(r.start);
        return result;
    }

    @GetMapping("/getNeoId")
    public List getNeoId() {
//        return nRelationDao.getAllIds();   //ok
//        SimpleNeo4jRepository
        System.out.println(nRelationDao.getClass());
        return nRelationDao.getAllIdsObject();
    }

    @Autowired
    private NPersonDao nPersonDao;
    @Autowired
    private Session session;
    @GetMapping("/get/all")
    public List list() {
        List list=(List)session.query(Person.class,"match (x:Person) return x limit 10",new HashMap<>());
         return list;// nPersonDao.findAll(new PageRequest(2,10));
    }


}
