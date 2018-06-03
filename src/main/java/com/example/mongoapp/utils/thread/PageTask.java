package com.example.mongoapp.utils.thread;

import com.example.mongoapp.dao.GraphicBaseDao;
import lombok.Data;
import lombok.NonNull;

import java.util.List;
import java.util.concurrent.CountDownLatch;
@Data
public class PageTask implements Runnable {
    private List<PageResult> list;
//    private PageResult pageResult;
    private GraphicBaseDao graphicBaseDao;
    private CountDownLatch sign;
//    private CountDownLatch pageSign;

    public PageTask(@NonNull List<PageResult> list /*PageResult pageResult*/,@NonNull  GraphicBaseDao graphicBaseDao, @NonNull CountDownLatch sign) {
        this.list = list;
//        this.pageResult=pageResult;
        this.graphicBaseDao = graphicBaseDao;
        this.sign = sign;
        System.out.println("PageTask():"+list.size());
//        this.pageSign=new CountDownLatch(list.size());
    }

    @Override
    public void run() {
        String threadName=Thread.currentThread().getName();
        for (int i = 0; i < list.size(); i++) {
            PageResult pageResult=list.get(i);
            System.out.print("线程 "+threadName+":正在处理"+pageResult.getPage());
            System.out.println("线程 "+threadName+":正在处理"+pageResult.getQueryResult().size());
            graphicBaseDao.insertMany(pageResult);
//            pageSign.countDown();
        }
//        graphicBaseDao.insertMany(pageResult);
        sign.countDown();
    }
}
