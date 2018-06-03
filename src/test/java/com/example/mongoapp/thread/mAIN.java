package com.example.mongoapp.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class mAIN {
    public static void main(String[] args) {
        ExecutorService pool= Executors.newFixedThreadPool(2);
        for(int x=0;x<10;x++){
             final int no=x;
            Runnable run=new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                        System.out.println(Thread.currentThread().getName()+"    "+no);
                        if(no==4||no==7){
                           throw  new RuntimeException("异常");
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            pool.submit(run);
        }

        pool.shutdown();
    }
}
