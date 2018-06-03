package com.example.mongoapp.utils.thread;

import com.example.mongoapp.entity.Check;
import lombok.Data;
import lombok.NonNull;

import java.util.List;
@Data
public class PageResult {
    private String labelName;
    private List<? extends Check> queryResult;
    private Page page;

    public PageResult(@NonNull String labelName,@NonNull List<? extends Check> queryResult,@NonNull Page page) {
        this.labelName = labelName;
        this.queryResult = queryResult;
        this.page = page;
    }

    public PageResult subPageResult(int arrStartIdx, int arrOffset){
        List subList=this.queryResult.subList(arrStartIdx,arrOffset);
        long pageStartIdx=page.getStartIdx()+arrStartIdx;
        long pageEndIdx=pageStartIdx+arrOffset-arrStartIdx;
        Page subPage=new Page(pageStartIdx,pageEndIdx);
        PageResult subPageResult=new PageResult(labelName,subList,subPage);
        return subPageResult;
    }
}
