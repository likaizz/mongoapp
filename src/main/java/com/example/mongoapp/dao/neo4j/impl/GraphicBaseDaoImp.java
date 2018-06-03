package com.example.mongoapp.dao.neo4j.impl;

import com.example.mongoapp.dao.GraphicBaseDao;
import com.example.mongoapp.entity.Check;
import com.example.mongoapp.entity.Demo;
import com.example.mongoapp.entity.Relation;
import com.example.mongoapp.utils.thread.PageResult;
import org.neo4j.ogm.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class GraphicBaseDaoImp implements GraphicBaseDao {
    @Autowired
    private Session session;

    public void insertMany(List<? extends Check> list, String labelName) {
        String query = "unwind {list} as one create (x:" + labelName + ") set x=one";
        Map<String, List> params = new HashMap(1);
        params.put("list", list);
        session.query(query, params);
    }

    @Override/*@Transactional*/
    public void insertMany(PageResult pageResult) {
        List list = pageResult.getQueryResult();
        String labelName = pageResult.getLabelName();
        Map<String, List> params = new HashMap(1);
        params.put("list", list);
        String query = "unwind {list} as one create (x:" + labelName + ") set x=one";
        session.query(Demo.class, query, params);
    }

    /*@Transactional
    public void insertMany(List<? extends Check> list,String labelName) {
        String query="unwind {list} as one create (x:"+labelName+") set x=one";
        session.query(query,new HashMap<>(0));
    }
    @Transactional
    public void insertMany(List<? extends Check> list) {
        String className=list.get(0).getClass().getSimpleName();
        insertMany(list,className);
    }*/
//    @Query(" unwind {list} as one match  (s:Demo),(e:DemoCopy)" +
//            " where s.no=one.start and e.no=one.end" +
//            " merge (s)-[r:Relation]->(e) set r=one")
    public void insertRelation(@Param("list") List<Relation> list, String labelName) {
        Map<String, List> params = new HashMap(1);
        params.put("list", list);
        String query = "unwind {list} as one match  (s:Start),(e:End) " +
                " where s.mongoId=one.start and e.mongoId=one.end" +
                " merge (s)-[r:" + labelName + "{mongoId:one.mongoId}]->(e) set r=one";
        session.query(query, params);
    }
}
