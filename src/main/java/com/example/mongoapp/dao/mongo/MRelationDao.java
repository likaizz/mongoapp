package com.example.mongoapp.dao.mongo;

import com.example.mongoapp.entity.Relation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MRelationDao extends MongoRepository<Relation,String> {
    @Query(value = "{}",fields = "{'_id':1,}")
    public List<Relation> myFind();
}
