package com.example.mongoapp.entity;

import lombok.Data;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.typeconversion.DateLong;

import java.util.Date;
@Data
public class Person  extends BaseNode{
    @Index
    private String o_id;
    private String name;
    private Integer age;
    private Long person_id;
    @DateLong
    private Date create_date;

    @Override
    public boolean check() {
        return this.o_id!=null ;
    }
}
