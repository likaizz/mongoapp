package com.example.mongoapp.entity;

import lombok.Data;
import org.bson.types.ObjectId;

@Data
//@NodeEntity
//@Document
public class DemoCopy extends  BaseNode {

    @org.springframework.data.annotation.Id
    @org.neo4j.ogm.annotation.Index(unique=true)
//    @Convert(value = ObjectIdStringConvert.class ,graphPropertyType = ObjectIdStringConvert.class)
    private String mongoId;

    private String name;
    private long no;
    public DemoCopy(String name) {
        this.name = name;
    }
    public DemoCopy(ObjectId id) {
        this.mongoId = id.toString();
    }

    public DemoCopy() {
    }

    @Override
    public boolean check() {
        return this.mongoId!=null;
    }
}
