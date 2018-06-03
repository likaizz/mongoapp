package com.example.mongoapp.controller;

import com.example.mongoapp.dao.OriginBasicDao;
import com.example.mongoapp.service.IBaseService;
import com.example.mongoapp.utils.thread.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {
    @Autowired
    @Qualifier(value = "oPersonDao")
    private /*OPersonDaoOrigin*/ OriginBasicDao basicDao;

    @Autowired
    private IBaseService service;
    @PostConstruct
    private void init() {
        System.out.println("PersonController" + basicDao.getClass());
    }

    @GetMapping("/getCount")
    public String getCount() {
        long s = System.currentTimeMillis();
        long count = basicDao.getCount();
        return "Person:" + count + "条记录" + "   耗时:" + (System.currentTimeMillis() - s);
    }

    @GetMapping("/get/{s}/{e}")
    public List/*<Person>*/ getByIdx(@PathVariable("s") long s, @PathVariable("e") long e) {
        List result = basicDao.findByIdx(s, e);
        return result;
    }
    @GetMapping("/getTime/{type}/{s}/{e}")
    public long getTime(@PathVariable("type") String type, @PathVariable("s") long s, @PathVariable("e") long e, int cycleSize) {
        long startTime=System.currentTimeMillis();
        getTimerByIdx(type,s,e,cycleSize);
        return System.currentTimeMillis()-startTime;
        }

    @GetMapping("/get/{type}/{s}/{e}")
    public List getTimerByIdx(@PathVariable("type") String type, @PathVariable("s") long s, @PathVariable("e") long e, int cycleSize) {
        double num = (e - s);
        long startTime = System.currentTimeMillis();
        if ("once".equalsIgnoreCase(type)) {
            List result = basicDao.findByIdx(s, e);
            System.out.println(type + "耗时:" + (System.currentTimeMillis() - startTime) + "  size=" + result.size());
            return result;//type+"耗时:"+(System.currentTimeMillis()-startTime )+"  size="+result.size();
        } else {
            int count = (int) Math.ceil(num / cycleSize);
            List result = new ArrayList((int) num);
            for (int i = 0; i < count; i++) {
                long startIdx = i * cycleSize + s;
                long offset = startIdx + cycleSize;
                if (i == count - 1) offset = e;
                List subResult = basicDao.findByIdx(startIdx, offset);
                result.addAll(subResult);
                System.out.println("curCycle:" + i + " " + "startIdx=" + startIdx + "  offset=" + offset + "  size=" + subResult.size());
            }
            System.out.println(type+"耗时:"+(System.currentTimeMillis()-startTime +" size="+result.size()));
            return result;//
        }


//        basicDao.findByIdx(s,e);
//        return System.currentTimeMillis()-startTime;
    }

    @GetMapping("/transfer/{startIdx}/{e}")
    public long transferData(@PathVariable("startIdx") long startIdx, @PathVariable("e") long e) throws Exception {
//        long startTime = System.currentTimeMillis();
//        List result=basicDao.findByIdx(startIdx, e);
//        return System.currentTimeMillis() - startTime;
        Page page=new Page(startIdx,e);
        return service.transData(page,basicDao,"Person");
    }



}
