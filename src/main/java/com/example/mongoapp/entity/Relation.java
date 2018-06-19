package com.example.mongoapp.entity;

import com.example.mongoapp.utils.dbconvert.ObjectIdStringConvert;
import lombok.Data;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.StartNode;
import org.neo4j.ogm.annotation.typeconversion.Convert;
import org.springframework.data.neo4j.annotation.QueryResult;

@Data  // lombok插件,自动生成get/set方法
@QueryResult  //这个注解是用来声明映射的(自定义的neo4j持久化操作)  重要
//@RelationshipEntity(type = "Relation")  //这个注解会导致mongo的持久操作实例化失败(冲突)  重要
public class Relation {
    private long no;
    @org.springframework.data.annotation.Id
    @org.neo4j.ogm.annotation.Index(unique = true)
    @Convert(value = ObjectIdStringConvert.class, graphPropertyType = ObjectIdStringConvert.class)
    private String mongoId;
    //    @org.neo4j.ogm.annotation.Id  //这个主键不能为null
    @GraphId
    private Long neo4jId;
    private String type;
    @StartNode
//    @DBRef()//lazy=true
    public String start;
    @EndNode
//    @DBRef()//lazy=true
    public String end;

    /* public int[] getStart() {
         ObjectId id = new ObjectId(start.getMongoId());
         int[] result = new int[4];
         result[0] = id.getTimestamp();
         result[1] = id.getMachineIdentifier();
         result[2] = id.getProcessIdentifier();
         result[3] = id.getCounter();
         return result;
     }*/
    public String getStart() {
        return this.start/*.getNo()*/;
    }

    /* public void setStart(int[] val){
             this.start=new Demo(new ObjectId(val[0],val[1],(short)val[2],val[3]));
             //new ObjectId(val[0],val[1],(short)val[2],val[3]).toString()
     }*/
    public void setStart(String start) {
        this.start = start;
    }

    public void setEnd(String end) {
        this.end = end;
    }


    /*public int[] getEnd() {
        ObjectId id = new ObjectId(end.getMongoId());
        int[] result = new int[4];
        result[0] = id.getTimestamp();
        result[1] = id.getMachineIdentifier();
        result[2] = id.getProcessIdentifier();
        result[3] = id.getCounter();
        return result;
    }*/
    public String getEnd() {
        return this.end;/*.getNo();*/
    }
    /*public void setEnd(int[] val){
        this.end=new DemoCopy(new ObjectId(val[0],val[1],(short)val[2],val[3]));
        //new ObjectId(val[0],val[1],(short)val[2],val[3]).toString()
    }*/

    public Relation() {
    }

    @Override
    public String toString() {
        return "{" +
                "no:" + no +
                ", mongoId:'" + mongoId + '\'' +
//                ", neo4jId=" + neo4jId +
//                ", type='" + type + '\'' +
                ", start:'" + start + '\'' +
                ", end:'" + end + '\'' +
                '}';
    }
}
