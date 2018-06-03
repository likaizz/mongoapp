package com.example.mongoapp.dao;

import com.example.mongoapp.entity.NodeDemo;
import org.springframework.data.neo4j.repository.Neo4jRepository;

//@Repository
public interface NodeDemoRepository extends  Neo4jRepository<NodeDemo,Long> {
}
