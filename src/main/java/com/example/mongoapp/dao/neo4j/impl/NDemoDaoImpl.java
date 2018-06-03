/*
package com.example.mongoapp.dao.neo4j.impl;

//import org.neo4j.driver.v1.Session;//false
import org.neo4j.ogm.model.Result;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
//
public class NDemoDaoImpl {
    @Autowired
//    private Neo4jTemplate template;
//    private org.neo4j.ogm.session.SessionFactory session;
    private org.neo4j.ogm.session.Session ss;  //TRUE
    @PostConstruct
    private void init(){
        System.out.println(ss==null);

    }

    public static <T> T myInsertMany(T one, String label) {
//        List result=new ArrayList();
        Map<String, T> map = new HashMap<>();
//        Demo demo=new Demo();
//        demo.setMongoId(new ObjectId().toString());
//        demo.setName("yasuo");
        map.put("one", one);
//        map.put("name","yasuo");
        StringBuilder sb = new StringBuilder();
        sb.append("create (x:");
        sb.append(label);
        sb.append(")");
        sb.append("set x={one} return x");
        Result r =ss.query(sb.toString(), map);
        Iterator<Map<String, Object>> it = r.iterator();
        while (it.hasNext()) {
            Map<String, T> m = (Map<String, T>) it.next();
            for (String key : m.keySet()) {
                T val = m.get(key);
                System.out.println(val.getClass());
                System.out.println(key + ":" + val);
            }
        }
        //x:Demo(no=0, mongoId=000000350000620030000032, neo4jId=1500042, name=yasuo)

       // template.executeSave();

        return null;
    }
}

*/
