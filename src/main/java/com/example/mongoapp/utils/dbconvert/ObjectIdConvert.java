package com.example.mongoapp.utils.dbconvert;

import org.bson.types.ObjectId;
import org.neo4j.ogm.typeconversion.AttributeConverter;

public class ObjectIdConvert implements AttributeConverter<ObjectId, /*int[]*/String> {
//    @Override
//    public int[] toGraphProperty(ObjectId value) {
//        int[]result=new int[4];
//        result[0]=value.getTimestamp();
//        result[1]=value.getMachineIdentifier();
//        result[2]=value.getProcessIdentifier();
//        result[3]=value.getCounter();
//        return result;
//    }
//
//
//    @Override
//    public ObjectId toEntityAttribute(int[] value) {
//        return new ObjectId(value[0],value[1],(short)value[2],value[3]);
//    }

    @Override
    public String toGraphProperty(ObjectId value) {
        return value.toString();
    }

    @Override
    public ObjectId toEntityAttribute(String value) {
        return new ObjectId(value);
    }
}
