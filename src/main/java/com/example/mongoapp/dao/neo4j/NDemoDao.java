package com.example.mongoapp.dao.neo4j;

import com.example.mongoapp.entity.Demo;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NDemoDao extends Neo4jRepository<Demo, Long> {
//    public static final NDemoDaoImpl impl;//=new NDemoDaoImpl();

    @Query("unwind {list} as one create (x:Demo) set x=one")
    public void insertMany(@Param("list") List<Demo> list);

    @Query("match (x:Demo) return x.mongoId   order by id(x) desc limit {num}")
    public List<String> get(@Param("num") long num);

/*
    default public <T> List<T> myInsertMany(List<T> list, String label) {
        Map<String, List<T>> map = new HashMap<>();
//        Demo demo=new Demo();
//        demo.setMongoId(new ObjectId().toString());
//        demo.setName("yasuo");
        map.put("list", list);
//        map.put("name","yasuo");
        StringBuilder sb = new StringBuilder();
        sb.append("unwind {list} as one");
        sb.append("create (x:");
        sb.append(label);
        sb.append(")");
        sb.append("set x={one} return x");
        Result r = ss.query(sb.toString(), map);
        Iterator<Map<String, Object>> it = r.iterator();
        while (it.hasNext()) {
            Map<String, T> m = (Map<String, T>) it.next();
            for (String key : m.keySet()) {
                T val = m.get(key);
                System.out.println(val.getClass());
                System.out.println(key + ":" + val);
            }
        }
    }*/

}
