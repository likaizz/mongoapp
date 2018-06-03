package com.example.mongoapp.dao.neo4j;

import com.example.mongoapp.entity.Check;
import com.example.mongoapp.entity.Person;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NPersonDao extends Neo4jRepository<Person,Long> {
    @Query("unwind {list} as one create(x:Person)set x=one")
    void insertMany(@Param("list") List<? extends Check> list);
//    @Query("unwind  {queryResult} as one create(x:Person)set  x=one")//x:{page.labelName} and  {page} as page
//    void myInsert(@Param("pageResult")PageResult page);
}
