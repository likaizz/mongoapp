package com.example.mongoapp.service;

import com.example.mongoapp.dao.OriginBasicDao;
import com.example.mongoapp.utils.thread.Page;

public interface IBaseService {
    public long transData(Page page, OriginBasicDao originBasicDao, String label) throws Exception;
}
