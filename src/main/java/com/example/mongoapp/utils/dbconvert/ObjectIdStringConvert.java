package com.example.mongoapp.utils.dbconvert;

import org.bson.types.ObjectId;
import org.neo4j.ogm.typeconversion.AttributeConverter;

public class ObjectIdStringConvert implements AttributeConverter<String,int[]> {
    private final ObjectId getObjectId(String value){
            return new ObjectId(value);
    }
    @Override
    public int[] toGraphProperty(String value) {
        ObjectId id=getObjectId(value);
        int[] result=new int[4];
        result[0]=id.getTimestamp();
        result[1]=id.getMachineIdentifier();
        result[2]=id.getProcessIdentifier();
        result[3]=id.getCounter();
        return result;
    }

    @Override
    public String toEntityAttribute(int[] value) {
        ObjectId id=new ObjectId(value[0],value[1],(short)value[2],value[3]);
        return id.toString();
    }
}
