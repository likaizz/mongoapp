package com.example.mongoapp.testDao;

import com.example.mongoapp.MongoappApplication;
import com.example.mongoapp.dao.GraphicBaseDao;
import com.example.mongoapp.dao.neo4j.NPersonDao;
import com.example.mongoapp.entity.Demo;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

//import org.neo4j.ogm.session.delegates.SaveEventDelegate;

@SpringBootTest(classes=MongoappApplication.class)
@RunWith(SpringRunner.class)
public class PersonDaoTest {
    @Autowired
    private NPersonDao nPersonDao;
    @Autowired
    private GraphicBaseDao graphicBaseDao;
//    @Autowired
//    private Neo4jSession session;

    @Test
    public void test(){
        List list=new ArrayList<>();
        Demo d=new Demo();
        d.setMongoId(new ObjectId().toString());
//        d.setMongoId(new ObjectId()/*.toString()*/);
        d.setName("likaizz");
        list.add(d);
//        PageResult p=new PageResult();
//        p.setLabelName("Demo");
//        p.setPage(new Page(1,101));
//        p.setQueryResult(list);
//        graphicBaseDao.insertMany(list,"Demo");
//        graphicBaseDao.insertMany(p);
        nPersonDao.saveAll(list);

//        SaveEventDelegate eventsDelegate = new SaveEventDelegate(session);

    }
}
