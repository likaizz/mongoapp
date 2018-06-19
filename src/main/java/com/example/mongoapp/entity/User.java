package com.example.mongoapp.entity;

import org.bson.types.ObjectId;
import org.neo4j.ogm.annotation.typeconversion.DateLong;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Date;

//@Document(collection="user")
//@NodeEntity(label = "User")
public class User implements Serializable {

    @Id   //@Index(unique=true)
//    @Convert(value= ObjectIdConvert.class,graphPropertyType= ObjectIdConvert.class)
    private ObjectId mongoId;
//    @GraphId  //@Indexed(unique = true)
    private Long neo4jId;
    public User(ObjectId mongoId) {
        this.mongoId = mongoId;
    }

    public User(Long neo4jId) {
        this.neo4jId = neo4jId;
    }

    public User() {
    }

    private String name;
    private Double age;
    @DateLong
    private Date birthday;

    public ObjectId getMongoId() {
        return mongoId;
    }

    public void setMongoId(ObjectId mongoId) {
        this.mongoId = mongoId;
    }

    public Long getNeo4jId() {
        return neo4jId;
    }

    public void setNeo4jId(Long neo4jId) {
        this.neo4jId = neo4jId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getAge() {
        return age;
    }

    public void setAge(Double age) {
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
        return "User{" +
                "mongoId=" + mongoId.toString() +
                ", neo4jId=" + neo4jId +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", birthday=" + birthday +
                '}';
    }
}
