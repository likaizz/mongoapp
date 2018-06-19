package com.example.mongoapp.entity;

import lombok.Data;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.Id;

import java.io.Serializable;
@Data
public abstract class BaseNode implements Check,Serializable {
   @GraphId
   @Id
   public Long id;
   public Long rn;
}
