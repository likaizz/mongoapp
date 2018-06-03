package com.example.mongoapp.dao.mongo;

import com.example.mongoapp.entity.Demo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MDemoCopyDao extends MongoRepository<Demo,String> {
}
