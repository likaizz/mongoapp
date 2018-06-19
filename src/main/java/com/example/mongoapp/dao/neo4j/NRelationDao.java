package com.example.mongoapp.dao.neo4j;

import com.example.mongoapp.entity.Relation;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NRelationDao extends Neo4jRepository<Relation,Long> {
    @Query(" unwind {list} as one match  (s:Start),(e:DemoCopy)" +
            " where s.no=one.start and e.no=one.end" +
            " merge (s)-[r:Relation]->(e) set r=one")
//    @Query(" unwind {list} as one match  (s:Demo),(e:DemoCopy)" +
//            " where s.mongoId=one.start and e.mongoId=one.end" +
//            " merge (s)-[r:Relation]->(e) set r=one")
    public void insertRelation(@Param("list")List<Relation> list);

    @Query("match()-[r:Relation]->() return id(r)")
    public List<Long> getAllIds();
    @Query(value = "match()-[r:Relation]->() return id(r) as neo4jId,r.mongoId as mongoId")
    public  List<Relation> getAllIdsObject();
}
