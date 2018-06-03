package com.example.mongoapp.dao;

import com.example.mongoapp.entity.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;

//@Repository
public interface UserDao extends Neo4jRepository<User,Long> {  //MongoRepository<User,ObjectId>
//    List<User> myspecialfind(Query query);
}
