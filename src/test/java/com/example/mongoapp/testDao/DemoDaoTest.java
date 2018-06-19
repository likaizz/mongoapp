package com.example.mongoapp.testDao;

import com.example.mongoapp.MongoappApplication;
import com.example.mongoapp.dao.mongo.MDemoDao;
import com.example.mongoapp.dao.neo4j.NDemoDao;
import com.example.mongoapp.dao.neo4j.NRelationDao;
import com.example.mongoapp.entity.Demo;
import com.example.mongoapp.entity.Relation;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.mapreduce.MapReduceOptions;
import org.springframework.data.mongodb.core.mapreduce.MapReduceResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.*;

//@PropertySource(value="classpath:application-app0.properties")
//@ComponentScan(basePackages={"com.example.mongoapp.dao"})
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MongoappApplication .class)
public class DemoDaoTest {
//    private static final Logger LOGGER=Logger.ROOT_LOGGER_NAME;
    @Autowired
    private ConfigurableApplicationContext context;
    @Autowired
    private MDemoDao dao;
    @Autowired
    private NDemoDao ndao;
    @Autowired
    private MongoTemplate template;
    @Autowired
    private NRelationDao nRelationDao;

    public void rela(){
        Optional<Relation>optional=nRelationDao.findById(896246l);
        Relation r=optional.get();
        r.setEnd("5b13c4167937fc20c0fdaa26");
        nRelationDao.save(r);
    }


    @Test
    public void test() throws InterruptedException {
        Demo d=new Demo();
        d.setName("yasuo");
        d.setMongoId(new ObjectId().toString());
//        d.setMongoId(new ObjectId());
//        ndao.myInsertMany(d,"Demo");
        List list=new ArrayList();
        list.add(d);
        ndao.saveAll(list);
        System.out.println(d);
    }


//    @Before
    public void before(){
        String[]beans=context.getBeanDefinitionNames();
        Arrays.sort(beans);
        for (String x:beans) {
            System.out.println(x);
        }
    }

    @Test
    public void httpRequestTest(){
        RestTemplate req = new RestTemplate();
        ResponseEntity<Integer> resp=req.exchange("http://localhost:8085/myPort", HttpMethod.GET,null,Integer.class);
        System.out.println(resp.getBody());
    }




    @Test
    public void myfindAll(){
        List<Demo> result= dao.myfindAll(/*new QPageRequest(1,100)*/);
        for (int i = 0; i <result.size() ; i++) {
            System.out.println(result.get(i));
        }
        System.out.println(dao);

//        Demo d=new Demo("亚索");
//        d=dao.insert(d);
//        System.out.println("第一次insert:"+d);
//        System.out.println(d);
//        d=dao.insert(d);
//        System.out.println("第二次insert:"+d);
    }

//    @Test
    public List testAggregate(){
//        Aggregation.newAggregation().withOptions();
        AggregationOptions allowDiskUse= AggregationOptions.builder().allowDiskUse(true).build();
        new AggregationOperation() {
            @Override
            public Document toDocument(AggregationOperationContext context) {
                return new Document("$out","test");
            }
        };
//        Aggregation aggregation=Aggregation.newAggregation().withOptions(allowDiskUse);
        AggregationResults<Map>aggregationResults;
        aggregationResults = template.aggregate(Aggregation.newAggregation(Aggregation.group("end").count().as("count"),Aggregation.match(Criteria.where("count").gte(2))).withOptions(allowDiskUse),"BaseRelation",Map.class);
        List<Map>list=aggregationResults.getMappedResults();
        List result=new ArrayList<>(list.size());
        for (int i = 0; i < list.size(); i++) {
            Map obj=list.get(i);
            System.out.println(obj);
            result.add(obj.get("_id"));
        }
        System.out.println(list.size());
        return result;
    }
    @Test
    public void testMapReduce(){
        MapReduceResults<Map>mapReduceResults= template.mapReduce("BaseRelation","function(){emit(this.end,1);}","function(k,v){return Array.sum(v);}", MapReduceOptions.options(),Map.class);
        Iterator<Map>iterator=mapReduceResults.iterator();
        while(iterator.hasNext()){
            Map obj=iterator.next();
            System.out.println(obj);
        }
        System.out.println(mapReduceResults.getCounts());//全部查询结果
    }
    @Test
    public void testExclued(){
        List besides=testAggregate();
        Query query=new Query().addCriteria(Criteria.where("end").nin(besides));
        List<Relation>list= template.find(query,Relation.class,"BaseRelation");
        for (int i = 0; i <list.size() ; i++) {
            System.out.println(list.get(i));
        }
        System.out.println(list.size());
    }

    @Test
    public void testProcedure(){
//       template.execute();
    }


}
