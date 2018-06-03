package com.example.mongoapp;

import org.bson.types.ObjectId;

public class ObjectIdDemo {
    public static void main(String[] args) {
        ObjectId id=new ObjectId();
        byte[]bs=id.toByteArray();

//        System.out.println(id);
//        System.out.println(id.toString());
//        System.out.println(id.toHexString());
//        System.out.println(Long.getLong(id.toString()));
//        System.out.println(id.hashCode());   //-616921925
//        System.out.println(Arrays.toString(id.toByteArray()));
        StringBuilder sb=new StringBuilder();
        System.out.println(id.getTimestamp());
        System.out.println(id.getMachineIdentifier());
        System.out.println(id.getProcessIdentifier());
        System.out.println(id.getCounter());
////        long x=356166663664373039666461303030663738666239366366l;
//
//        int timestamp=id.getTimestamp();
//        int machineIdentifier=id.getMachineIdentifier();
//        short x=id.getProcessIdentifier();
//        int counter=id.getCounter();
//        ObjectId objectId=new ObjectId(timestamp,machineIdentifier,x,counter);
//        System.out.println(objectId);

    }
}
