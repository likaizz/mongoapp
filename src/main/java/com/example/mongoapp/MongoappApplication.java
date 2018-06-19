package com.example.mongoapp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

//@Configuration
//@PropertySource({"classpath:application-app0.properties","classpath:application.properties"})
@SpringBootApplication
@RestController
@ConfigurationProperties
@EnableAsync
public class MongoappApplication {
    @Value("${lol}")
    private String lol;
    @Value("${server.port}")
    private int port;

    @PostConstruct
    public void init() {
        System.out.println(lol);
    }

    @Bean("defaultSDF")
    public SimpleDateFormat defaultDateFormat() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return sdf;
    }

    private Map<String, Object> myMap = new HashMap<>();

    @Bean("myMap")
    public Map getMyMap() {
        return myMap;
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(MongoappApplication.class, args);
        String[] beans = context.getBeanDefinitionNames();
        Arrays.sort(beans);
        for (String x : beans) {
            System.out.println(x);
        }
        Map map = context.getBean("myMap", Map.class);
        System.out.println(map);
        Set<String> set = map.keySet();
        for (String x : set) {
            Object val = map.get(x);
            System.out.println(x + ":" + val + "  ,class:" + val.getClass());
        }
    }

    @GetMapping("/otherPort")
    public Integer getOtherPort(int port) {
        RestTemplate req = new RestTemplate();
        ResponseEntity<Integer> resp = req.exchange("http://localhost:" + port + "/myPort", HttpMethod.GET, null, Integer.class);
        return resp.getBody();
    }

    @GetMapping("/myPort")
    public Integer getMyPort() {
        return port;
    }


}
