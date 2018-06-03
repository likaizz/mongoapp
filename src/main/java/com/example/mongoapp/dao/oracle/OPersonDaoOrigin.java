package com.example.mongoapp.dao.oracle;

import com.example.mongoapp.dao.OriginBasicDao;
import com.example.mongoapp.entity.Person;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository("oPersonDao")
public interface OPersonDaoOrigin extends OriginBasicDao {
    @Select("select * from ( select rownum rn,t.* from (select id as o_id,name,age,person_id,create_time  create_date from person  where rownum<#{endIdx}) t )where rn>=#{startIdx}")
    public List<Person> findByIdx(@Param("startIdx") long startIdx,@Param("endIdx") long endIdx);
    @Select ("select count(1) from person")
    public long getCount();
}
