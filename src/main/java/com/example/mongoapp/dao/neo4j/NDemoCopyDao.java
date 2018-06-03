package com.example.mongoapp.dao.neo4j;

import com.example.mongoapp.entity.DemoCopy;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface NDemoCopyDao extends Neo4jRepository<DemoCopy,Long> {
    @Query("unwind {list} as one create (x:DemoCopy) set x=one")
    public void insertMany(@Param("list")List<DemoCopy> list);
    @Query("match (x:DemoCopy) return x.mongoId  order by id(x) desc limit {num}")
    public List<String> get(@Param("num") long num);
}
