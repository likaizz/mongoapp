package com.example.mongoapp.dao.mongo;

import com.example.mongoapp.entity.Demo;
import com.example.mongoapp.utils.ApplicationContextProvider;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MDemoCopyDao extends MongoRepository<Demo,String> {
    MongoTemplate mongotemplate= ApplicationContextProvider.getBean(MongoTemplate.class);
    default  public void queryByMaxMin(String min,String max){
//        mongotemplate.getCollection("demo").find();
        System.out.println(mongotemplate);
    }
}
