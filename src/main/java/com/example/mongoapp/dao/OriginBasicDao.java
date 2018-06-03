package com.example.mongoapp.dao;


import com.example.mongoapp.entity.Check;

import java.util.List;

public interface OriginBasicDao {
    public List<? extends Check> findByIdx(long startIdx, long endIdx);
    public long getCount();
}
