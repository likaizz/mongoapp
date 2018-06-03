package com.example.mongoapp.entity;

import lombok.Data;

@Data
//@NodeEntity
//@Document
public class Demo  extends BaseNode{
    private long no;
    @org.springframework.data.annotation.Id
    @org.neo4j.ogm.annotation.Index(unique=true)
//    @Convert(value = ObjectIdConvert.class ,graphPropertyType = ObjectIdConvert.class)
//    private ObjectId /*String*/ mongoId;
    private  String mongoId;
//    @org.neo4j.ogm.annotation.Id  //这个主键不能为null
//    @GraphId
//    private Long neo4jId;
    private String name;
    private Integer age;

    public Demo(String name) {
        this.name = name;
    }
//    public Demo(ObjectId mongoId) {
//        this.mongoId = mongoId.toString();
//    }

    public Demo() {
    }

    @Override
    public boolean check() {
        return this.mongoId!=null;
    }
}
