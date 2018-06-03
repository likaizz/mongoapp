package com.example.mongoapp.dao.mongo;

import com.example.mongoapp.entity.Demo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MDemoDao extends MongoRepository<Demo,String>{
    @Query(value="{}",fields = "{\"_id\":1,'name':1}")
    public List<Demo> myfindAll(/*Pageable page*/);//Pageable page
}
