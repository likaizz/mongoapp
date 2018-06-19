package neo4j;

import com.example.mongoapp.MongoappApplication;
import com.example.mongoapp.dao.neo4j.NDemoDao;
import com.example.mongoapp.entity.NodeDemo;
import com.example.mongoapp.entity.Relation;
import com.example.mongoapp.entity.User;
import com.sun.org.apache.xerces.internal.xs.StringList;
import org.apache.commons.lang3.RandomUtils;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.neo4j.ogm.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//@SpringBootConfiguration
@SpringBootTest(classes = MongoappApplication.class)
@RunWith(SpringRunner.class)
//@ComponentScan(basePackages = {"com.example.mongoapp.dao"})
//@EnableAsync
//@Component
public class NodeDaoTest {

    //    @Autowired
//    private UserDao dao;  //NodeDemoRepository
    @Autowired
    private NDemoDao nDemoDao;

    @Test
    public void testSave() {
        NodeDemo demo = new NodeDemo();
        demo.setName("demo");
        demo.setBirthday(new Date());
//        demo=dao.save(demo);
        System.out.println(demo);
    }

    @Test
    public void Save() {
        User demo = new User();
        demo.setName("demo");
        demo.setBirthday(new Date());
        demo.setMongoId(new ObjectId());
//        demo=dao.save(demo);

        System.out.println(demo);
    }

    @Autowired
    private Session session;

    @Test
    public void test() {
        List list = (List) nDemoDao.findAll();
        System.out.println(list);
    }

    @Test
    public void create() {

        for (int x = 0; x < 5; x++) {
            List list = new ArrayList(400000);
            for (int i = 1; i <= 400000; i++) {
                String str = String.format("%07d", x * 400000 + i);
                list.add(str);
            }
            String insert = "unwind {list} as one create (x:ENDX) set x.id=one ";
            Map<String, List> map = new HashMap<>();
            map.put("list", list);
            session.query(insert, map);
            list.clear();
        }

    }

    @Test
    public void createX() {

        List list = new ArrayList(400000);
        for (int i = 1; i <= 400000; i++) {
            String str = String.format("%06d", i);
            list.add(str);
        }
        String insert = "unwind {list} as one create (x:START) set x.id=one ";
        Map<String, List> map = new HashMap<>();
        map.put("list", list);
        session.query(insert, map);
        list.clear();

    }

    @Autowired
    private MongoTemplate template;

    @Async
    public void insertRelation(int i, List list, CountDownLatch sign) throws InterruptedException {
        Thread.sleep(3000);
        System.out.println(Thread.currentThread().getName());
        String insert = "unwind {list} as one match (s:START),(e:ENDX) where s.id=one.start and e.id=one.end " +
                "create (s)-[r:relationx]->(e) set r=one return count(r)";
        Map map = new HashMap<>();
        map.put("list", list);
        long count = ((List<Long>) session.query(Long.class, insert, map)).get(0);
        if (count != list.size()) System.out.println(i + "数量不相等," + count + "list:" + list.get(0));
        if (sign != null) sign.countDown();
    }

    @Test
    public void createRela() {
        List list = new ArrayList(100000);
        for (int i = 1; i <= 400000; i++) {
            int rnum = 8;
            HashSet<Integer> set = new HashSet<Integer>(10);
            for (int j = 0; j < rnum; j++) {
                Relation r = new Relation();
                r.setStart(String.format("%06d", i));
                /*int from = 0, to = 0;
                if (i % 2 == 1) {
                    int n = (i + 1) / 2;
                    to = n * 10 + 1;
                } else {
                    int n = (i / 2);
                    to = n * 10 + 6;
                }
                from = to - 10;
                int e = 0;
                do {
                    e = RandomUtils.nextInt(from, to);
                } while (!set.add(e));*/
                String end = String.format("%07d", RandomUtils.nextInt(1, 2000001));
                r.setEnd(end);
                list.add(r);
                if (list.size() == 100000) {
//                        System.out.println(list);
                    template.insert(list, "ralationx");
                    list.clear();
                }
            }
        }
    }

    public void dropNodes(String label) {

        String count = "match (x:" + label + ") return  count(1) ";
        Map map = new HashMap();
        long num = ((List<Long>) session.query(Long.class, count, map)).get(0);
        int cycle = (int) Math.ceil(num / 100000.0);
        String delete = "match (x:" + label + ") with x limit 100000 detach delete  x ";
        for (int i = 0; i < cycle; i++) {
            session.query(delete, map);
        }
    }

    public void dropRelation(String label, String startLabel, String endLabel) {

        String count = "match (" + startLabel + ")-[x:" + label + "]->(" + endLabel + ") return  count(1) ";
        Map map = new HashMap();
        long num = ((List<Long>) session.query(Long.class, count, map)).get(0);
        int cycle = (int) Math.ceil(num / 100000.0);
        String delete = "match ((" + startLabel + ")-[x:" + label + "]->(" + endLabel + ")with x limit 100000  delete  x ";
        for (int i = 0; i < cycle; i++) {
            session.query(delete, map);
        }
    }

    @Test
    public void dropTest() {
        dropNodes("START");
    }

    @Test
    public void dropTest1() {
        dropNodes("END");
    }

//    private Session session;

    @Test
    public void testsave() throws InterruptedException {
//10609927
        ExecutorService service = Executors.newFixedThreadPool(8);
        CountDownLatch sign = new CountDownLatch(16);
        for (int i = 0; i < 16; i++) {
//            final int from = i * 100000;
//            final int to = from + 100000;
            final int idx = i;
            Runnable task1 = new Runnable() {
                @Override
                public void run() {
                    try {
//                        List<Relation> list = template.find(new Query().skip(from).limit(100000), Relation.class, "ralationx");
                        List list = new ArrayList();
                        for (int i = 0; i < 50000; i++) {
                            NodeDemo demo = new NodeDemo();
                            demo.setName(UUID.randomUUID().toString());
                            demo.setAge(RandomUtils.nextInt(0, 200));
                            list.add(demo);

                        }
                        System.out.println(list.size());
                        Map<String, List> map = new HashMap<>();
                        map.put("list", list);
//                        List<NodeDemo>result=(List<NodeDemo>)nDemoDao.saveAll(list);
                        /*List<Map>*/
                        Map result = ((List<Map>) session.query(Map.class, "unwind {list} as one create (x:Demo) set x=one return {max:max(id(x)),min:min(id(x))} ", map)).get(0);
                        StringBuffer sb = new StringBuffer();
                        sb.append("i:" + idx);
                        sb.append("i:" + idx);
                        sb.append(" {first:" + result.get("min"));
                        sb.append(" {last:" + result.get("max"));
                        sb.append(" }");
//                        System.out.println("i:"+idx+" {first:"+result.get(0).getId()+",last:"+result.get(result.size()-1).getId()+"}");
                        System.out.println(sb.toString());
//                        insertRelation(from,list, sign);
                    } catch (/*Interrupted*/Exception e) {
                        e.printStackTrace();
                    } finally {
                        sign.countDown();
                    }
                }
            };
            service.execute(task1);
//            insertRelation(null,null);
//            insertRelation(null,null);
        }
        sign.await();
    }

    @Test
//    @Transactional(rollbackFor = RuntimeException.class)
    public void sdace() {
        long s = System.currentTimeMillis();
        for (int y = 0; y < 50; y++) {
            List list = new ArrayList(50000);
            for (int i = 0; i < 50000; i++) {
                NodeDemo demo = new NodeDemo();
                demo.setName(UUID.randomUUID().toString());
                demo.setAge(RandomUtils.nextInt(0, 200));
                list.add(demo);

            }
            nDemoDao.saveAll(list);
        }
        System.out.println("耗时:" + (System.currentTimeMillis() - s));
        throw new RuntimeException("myException");
//        List<NodeDemo>result=(List<NodeDemo>)nDemoDao.saveAll(list);
        /*for(NodeDemo x:result){
            System.out.println(x);
        }*/
        /*for (int i = 0; i < result.size(); i++) {
            System.out.println(list.get(i)==result.get(i));
            System.out.println(result.get(i));
        } */       /*NodeDemo demo1 = new NodeDemo();
        demo1.setName("timsad");
        demo1.setAge(26);
        list.add(demo1);*/

//        BoltRequest req=new BoltRequest();
    }


    @Test
//    @Transactional(rollbackFor = RuntimeException.class)
    public void myInsert() {
        long s = System.currentTimeMillis();
        String save = "unwind {list} as one create (x:Doc) set x=one return id(x)";
        List list = new ArrayList(250000);
        for (int i = 0; i < 250000; i++) {
            NodeDemo demo = new NodeDemo();
            demo.setName(UUID.randomUUID().toString());
            demo.setAge(RandomUtils.nextInt(0, 200));
            list.add(demo);
        }
        System.out.println(list.size());
        System.out.println(list.get(0));
        nDemoDao.saveAll(list);
//        System.out.println(list.size());
//        Map<String,List>param=new HashMap<>();
//        param.put("list",list);
//        session.query(save,param,false);
//        session.
        System.out.println("耗时:" + (System.currentTimeMillis() - s));
//        throw new RuntimeException("myException");
    }

}
