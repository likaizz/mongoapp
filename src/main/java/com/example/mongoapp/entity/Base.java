package com.example.mongoapp.entity;

import lombok.Data;

@Data
public class Base extends BaseNode {
    @Override
    public boolean check() {
        return this.mongoId!=null;
    }
    @org.springframework.data.annotation.Id
    @org.neo4j.ogm.annotation.Index(unique=true)
//    @Convert(value = ObjectIdStringConvert.class ,graphPropertyType = ObjectIdStringConvert.class)
    private String mongoId;
    private String name;
    private long no;
    public Base() {
    }

}
