package com.example.mongoapp.entity;

import lombok.Data;
import org.neo4j.ogm.annotation.GraphId;

import java.io.Serializable;
@Data
public abstract class BaseNode implements Check,Serializable {
   @GraphId
   public Long graphicId;
   public Long rn;
}
