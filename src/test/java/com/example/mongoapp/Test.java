package com.example.mongoapp;

public class Test {
    public static void main(String[] args) {
        int pageSize= (int) Math.ceil(3.14);
        System.out.println(pageSize);
        long x=Integer.MAX_VALUE+23;//溢出
        System.out.println(x+"");

        long s=(Integer.MAX_VALUE+1l)*1000+1;
        long e=s+1000-1;
        System.out.println( s+"  "+e );
    }
}
