package com.example.mongoapp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SetTest {
    public static void main(String[] args) throws ParseException {
        List<Integer> list=new ArrayList<>(5);
        for(int x=0;x<8;x++) {
            list.add(x);
        }
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmssSSS");
        Date date=new Date();
        String dateString=sdf.format(date);
        System.out.println(dateString);
        Date date1=sdf.parse(dateString);
        System.out.println(date.equals(date1));

    }
}
