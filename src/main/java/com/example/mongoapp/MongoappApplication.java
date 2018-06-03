package com.example.mongoapp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.Arrays;

//@Configuration
@SpringBootApplication
@PropertySource({"classpath:application-app0.properties","classpath:application.properties"})
@RestController
public class MongoappApplication {
    @Value("${lol}")
    private String lol;
    @Value("${server.port}")
    private int port;

    @PostConstruct
    public void init() {
        System.out.println(lol);
    }
//	@Value("${spring.data.neo4j.uri}")
//	private String databaseUrl;
//
//	@Value("${spring.data.neo4j.username}")
//	private String userName;
//
//	@Value("${spring.data.neo4j.password}")
//	private String password;

//	@Bean
//	public SessionFactory sessionFactory() {
//		Configuration configuration = new Configuration();
//		configuration.set("uri",databaseUrl);	//.uri(databaseUrl)
//		configuration.set("username",userName)	;//	.credentials(userName, password)
//		configuration.set("password",password)	;//	.credentials(userName, password)
//		return new SessionFactory(configuration, "com.example.mongoapp.entity");
//	}

    @Bean("defaultSDF")
    public SimpleDateFormat defaultDateFormat() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return sdf;
    }

    public static void main(String[] args) {

        ConfigurableApplicationContext context = SpringApplication.run(MongoappApplication.class, args);
        String[] beans = context.getBeanDefinitionNames();
        Arrays.sort(beans);
        for (String x : beans) {
			System.out.println(x);
        }
    }

    @GetMapping("/otherPort")
    public Integer getOtherPort(int port) {
        RestTemplate req = new RestTemplate();
        ResponseEntity<Integer> resp=req.exchange("http://localhost:"+port+"/myPort", HttpMethod.GET,null,Integer.class);
        return resp.getBody();
    }

    @GetMapping("/myPort")
    public Integer getMyPort() {
        return port;
    }


}
