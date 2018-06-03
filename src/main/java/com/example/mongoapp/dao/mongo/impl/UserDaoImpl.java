//package com.example.mongoapp.dao.impl;
//
//public class UserDaoImpl {
////    @Autowired
////    private MongoTemplate template;
////
////    private MongoCollection collection;//
////
////    @PostConstruct
////    public void initCollection(){
////        boolean flag=template==null?false:true;
////        collection=(flag?template.getCollection("user"):null);
////        System.out.println(flag?"初始化成功":"初始化失败");
////    }
////
////    public List<User> myspecialfind(Query query){
////        List<User>list=template.find(query,User.class);
////        return list;
////    };
//}
