//package com.example.mongoapp.testDao;
//
//import com.example.mongoapp.dao.UserDao;
//import com.example.mongoapp.entity.User;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.SpringBootConfiguration;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.data.domain.Example;
//import org.springframework.data.domain.Sort;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.data.mongodb.core.query.Criteria;
//import org.springframework.data.mongodb.core.query.Query;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//import java.util.UUID;
//@SpringBootConfiguration
//@SpringBootTest
//@RunWith(SpringRunner.class)
//@ComponentScan(basePackages={"com.example.mongoapp.dao"})
////@ContextConfiguration(locations = {"classpath:application-app0.properties"})  //报错
//public class UserDaoTest {
//    @Autowired  //@Qualifier("myUserDao")
//    private UserDao dao;
//
//
//    @Test
//    public void myspecialfindTest(){
//        Query query=new Query().addCriteria(Criteria.where("age").gte(10));
//       List<User>result= dao.findAll();//Examplequery
//        for (User x:result
//             ) {
//            System.out.println(x);
//        }
//    }
//
//
//    @Test
//    public void testSave() {
//        List<User>list=new ArrayList<>(5);
//        for(int x=1;x<=5;x++) {
//            User u = new User();
//            u.setName(UUID.randomUUID().toString());
//            u.setAge(Math.random() * 100);
////            u.setId(x+1);
//            list.add(u);
////            dao.save(u);
//
//        }
//        dao.saveAll(list);
////       List<User>u= dao.find(Query.query(Criteria.where("age").gte(18).lte(65)).with(Sort.by(Sort.Order.desc("age"))));
////       for(User x:u){
////           System.out.println(x);
////       }
//    }
//
//    @Test
//    public void testDelete(){
//        List<User>list=new ArrayList<>(1);
//        User u=new User();
////        u.setId(4);
//        list.add(u);
////        dao.delete(u);
//        dao.deleteAll(list);
//    }
//
//    @Test
//    public void testFind(){
//        User u=new User();
//        u.setName("cb9eb076-cc7b-4d8a-b7a9-0d7091783f69");
//        Optional<User> result=dao.findOne(Example.of(u));
//        User uu=result.get();
//        uu.setAge(36.0);
//        dao.save(uu);
//    }
//
//    @Test
//    public void testUpdate(){
//        User u=new User();
//        u.setName("cb9eb076-cc7b-4d8a-b7a9-0d7091783f69");
////        dao.
//    }
//
//
//    @Autowired
//    private MongoTemplate template;
//
//    @Test
//    public void testInsert(){
////        User u = new User();
////        u.setName(UUID.randomUUID().toString());
////        u.setAge(Math.random() * 100);
////        u.setId(7);
////        template.insert(u);
//        List<User>list=new ArrayList<>(5);
//        for(int x=0;x<5;x++){
//            User u = new User();
//            u.setName(UUID.randomUUID().toString());
//            u.setAge((double)Math.round(Math.random() * 100));
////            u.setId(8+x);
//            list.add(u);
//        }
//        template.insert(list,User.class);
//    }
//
//    @Test
//    public void testRemove(){
//        User u=new User();
////        u.setName("61501fe4-554a-450f-90aa-87ec9c195ad9");//不行
////        u.setId(10); //可以根据主键id删除数据
//        template.remove(u);
////        template.remove(Query.query(Criteria.where("name").alike("")))
//
//    }
//
//    @Test
//    public void find(){
//        User u=new User();
//        u.setName("61501fe4-554a-450f-90aa-87ec9c195ad9");
////        Query sql= Query.query(Criteria.where("name").is("61501fe4-554a-450f-90aa-87ec9c195ad9"));
//        Query sql= Query.query( Criteria.where("age").gte(18).lte(65));//.andOperator(Criteria.where("id").
//        Criteria.where("age").in(18,65);
//        Criteria.byExample(u);
//        sql.with(Sort.by(Sort.Order.desc("age")));//,Sort.Order.asc("")
//                //regex("/.*-450f-90aa-.*/"));
////        List<User> list=template.find(sql,User.class);
//        List<User> list=template.find(Query.query(Criteria.byExample(u)),User.class);
//        System.out.println(list);
//    }
//
//
//
//}
