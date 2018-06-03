package com.example.mongoapp.utils.thread;

import lombok.Data;

@Data
public class Page<T> {
    private long startIdx;
    private long endIdx;
    private int pageSize;
    private int page;
//    private String labelName;
//    private List<? extends Check> queryResult;
    /**
     * querySign用来表示
     */
//    private CountDownLatch querySign=new CountDownLatch(1);//2表示需要执行2次任务,分别是从数据源查询数据,以及写入
//[)
    public Page(/*@NonNull String labelName,*/ long startIdx, long endIdx) {
        if(/*"".equals(labelName)||*/startIdx<0||endIdx<=startIdx)throw new RuntimeException("Ilegal params for Page Settings!");
        this.startIdx = startIdx;
        this.endIdx = endIdx;
//        this.pageSize= (int) (endIdx-startIdx+1);//[]
        this.pageSize= (int) (endIdx-startIdx);//[)
        this.page= (int) (startIdx-1)/(pageSize)+1;
    }

    public Page(/*@NonNull String labelName,*/ int page , int pageSize) {
        if(/*"".equals(labelName)||*/page<=0||pageSize<=0)throw new RuntimeException("Ilegal params for Page Settings!");
        this.pageSize = pageSize;
        this.page = page;
//        this.labelName = labelName;
        this.startIdx=(page-1l)*pageSize+1;
        this.endIdx=startIdx+pageSize;
    }

   /* public Page subPage(List sublist){
        Page subPage=new Page(this.labelName,);
        subPage.setQueryResult(sublist);
        return subPage;
    }*/
}
