package com.example.mongoapp.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = {"com.example.mongoapp.dao.mysql"}, sqlSessionTemplateRef = "mysqlSessionTemplate")//, sqlSessionTemplateRef = "userSqlSessionTemplate"
public class MysqlConfig {
    @Bean(name = "mysqlDataSource")
    @Primary //必须加此注解，不然报错，下一个类则不需要添加
    @ConfigurationProperties(prefix = "spring.datasource.mysql") // prefix值必须是application.properteis中对应属性的前缀
    public DataSource mysqlDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean("mysqlSessionFactory")
    public SqlSessionFactory mysqlSessionFactory(@Qualifier("mysqlDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        //添加XML目录
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            bean.setMapperLocations(resolver.getResources("classpath*:mapping/mysql/*.xml"));
            return bean.getObject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Bean("mysqlSessionTemplate")
    public SqlSessionTemplate mysqlSessionTemplate(@Qualifier("mysqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        SqlSessionTemplate template = new SqlSessionTemplate(sqlSessionFactory); // 使用上面配置的Factory
        return template;
    }
}