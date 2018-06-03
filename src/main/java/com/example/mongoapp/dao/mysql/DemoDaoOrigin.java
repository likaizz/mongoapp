package com.example.mongoapp.dao.mysql;

import com.example.mongoapp.dao.OriginBasicDao;
import com.example.mongoapp.entity.Demo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DemoDaoOrigin extends OriginBasicDao {
   @Insert({"<script>" +
            "INSERT INTO demo values " +//(name)  ,#{one.age}
            "  <foreach collection=\"list\" item=\"one\"  separator=\",\" > " +
            "        (#{one.name},#{one.age}) " +
            "    </foreach>" +
            "</script>"})   //1万数据1s内,8万数据报错  Packet for query is too large (4560031 > 4194304 )
  /* @Insert({"<script>" +
            "begin "+
           "  <foreach collection=\"list\" item=\"one\"   > " +  //separator=";"
           " INSERT INTO demo(name) values " +
           "        (#{one.name}); " +
           "    </foreach>" +
           " end ;"+
           "</script>"})*///mysql用不了
    public void insertMany(@Param("list") List<Demo> list);
   @Insert("insert into demo(name) values (#{one.name})")
    public void insertOne(@Param("one") Demo one);

   @Select("select * from demo")
   public List<Demo>findAll();

    @Override
    List<Demo> findByIdx(long startIdx, long endIdx);

    @Select("select count(1) from demo")
    long getCount();
}
