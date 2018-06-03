package com.example.mongoapp.testDao;

import com.example.mongoapp.MongoappApplication;
import com.example.mongoapp.dao.mongo.MDemoDao;
import com.example.mongoapp.dao.neo4j.NDemoDao;
import com.example.mongoapp.entity.Demo;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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


    @Test
    public void test(){
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


    @Before
    public void before(){
        String[]beans=context.getBeanDefinitionNames();
        Arrays.sort(beans);
        for (String x:beans) {
            System.out.println(x);
        }
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

}
