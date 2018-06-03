package com.example.mongoapp.dao;

import com.example.mongoapp.entity.Check;
import com.example.mongoapp.entity.Relation;
import com.example.mongoapp.utils.thread.PageResult;

import java.util.List;

public interface GraphicBaseDao {
//    public void insertMany(List<? extends Check> list);
    public void insertMany(List<? extends Check> list, String labelName);
    public void insertMany(PageResult pageResult);
    public void insertRelation(List<Relation> list, String labelName);
}
