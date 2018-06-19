package com.example.mongoapp.entity;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.typeconversion.DateLong;

import java.util.Date;
public class NodeDemo  {
    @GraphId
    private Long id;
    private String name;
    private Integer age;
    @DateLong
    private Date birthday;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "{" +
                "id="+id+","+
                "name='" + name + '\'' +
                ", age=" + age +
//                ", birthday=" + birthday.getTime() +
                '}';
    }
}
