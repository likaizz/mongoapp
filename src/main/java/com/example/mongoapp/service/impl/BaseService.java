package com.example.mongoapp.service.impl;

import com.example.mongoapp.async.AsyncTask;
import com.example.mongoapp.dao.OriginBasicDao;
import com.example.mongoapp.entity.Check;
import com.example.mongoapp.service.IBaseService;
import com.example.mongoapp.utils.thread.Page;
import com.example.mongoapp.utils.thread.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@Service
public class BaseService implements IBaseService {
//    @Autowired
//    private GraphicBaseDao graphicBaseDao;
    @Autowired
    private AsyncTask asyncTask;

    public long transData(Page page, OriginBasicDao originBasicDao, String label) throws InterruptedException {//List<? extends Check>
        long startTime=System.currentTimeMillis();
        List<Page> subPages=subPages(page,400000);
        int cycle=subPages.size();
        CountDownLatch sign=new CountDownLatch(cycle);
        for (int i = 0; i < cycle; i++) {
            Page subPage=subPages.get(i);
            List<? extends Check> result = originBasicDao.findByIdx(subPage.getStartIdx(), subPage.getEndIdx());
            System.out.println("originBasicDao:  "+result.size());
            PageResult pageResult = new PageResult(label, result,subPage);
            asyncTask.insert(pageResult,sign);
        }
        sign.await();
        return System.currentTimeMillis()-startTime;
    }
    /*public long transData(Page page, String label) throws InterruptedException {//List<? extends Check>
        long startTime=System.currentTimeMillis();
        List<Page> subPages=subPages(page,400000);
        int cycle=subPages.size();
        CountDownLatch sign=new CountDownLatch(cycle);
        for (int i = 0; i < cycle; i++) {
            Page subPage=subPages.get(i);
            List<? extends Check> result = originBasicDao.findByIdx(subPage.getStartIdx(), subPage.getEndIdx());
            System.out.println("originBasicDao:  "+result.size());
            PageResult pageResult = new PageResult(label, result,subPage);
            asyncTask.insert(pageResult,sign);
        }
        sign.await();
        return System.currentTimeMillis()-startTime;
    }*/


    public List<Page> subPages(Page page,int size) {
        double num = page.getEndIdx() - page.getStartIdx();
        int count = (int) Math.ceil(num / size);
        List<Page> queue = new ArrayList<Page>(count);
        for (int x = 0; x < count; x++) {
            long startIdx = x * size+1;
            long endIdx = startIdx + size;
            if (x == count - 1) endIdx = (int) num+1;
            queue.add(new Page(startIdx, endIdx));
        }
        return queue;
    }
}
