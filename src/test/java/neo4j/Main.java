//package neo4j;
//
//import com.alibaba.fastjson.JSON;
//import com.example.mongoapp.entity.NodeDemo;
//import org.junit.Test;
//import org.neo4j.driver.internal.value.ListValue;
//import org.neo4j.driver.v1.AuthTokens;
//import org.neo4j.driver.v1.Driver;
//import org.neo4j.driver.v1.GraphDatabase;
//import org.neo4j.driver.v1.Session;
//
//import java.util.*;
//
//public class Main {
//    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
//        Driver driver = GraphDatabase.driver( "bolt://localhost:7687", AuthTokens.basic( "neo4j", "root" ) );
//        Session session = driver.session();
//
//
////        new GraphClient();
////        Cypher.Unwind(nodes, "ns")
////                .Create("(n:NodeLable)")
////                .Set("n.NodeID=ns.NodeID")
////                .Set("n.Name=ns.Name")
////                .ExecuteWithoutResults();
//
////        List<NodeDemo> list=new ArrayList<>(100000);
////        for (int i = 0; i <100000 ; i++) {
////            NodeDemo demo=new NodeDemo();
////            demo.setName("李凯");
////            demo.setAge(26);
////            Calendar cal=Calendar.getInstance();
////            cal.set(RandomUtils.nextInt(1950,2018),RandomUtils.nextInt(1,13),RandomUtils.nextInt(1,28));
////            demo.setBirthday(cal.getTime());
////            list.add(demo);
////        }
//
//        NodeDemo demo=new NodeDemo();
//            demo.setName("timo");
//            demo.setAge(26);
////            Calendar cal=Calendar.getInstance();
////            cal.set(1997,7,1);//RandomUtils.nextInt(1950,2018),RandomUtils.nextInt(1,13),RandomUtils.nextInt(1,28)
////            demo.setBirthday(cal.getTime());
//
//
////        StatementResult result =
//
//        Map<String,Object>map=new HashMap(1);
//        List<NodeDemo> list=new ArrayList<>(1);list.add(demo);
//        String arrayJson= parse(list);  //"[{\"name\":\"张三\",\"age\":24},{\"name\":\"李四\",\"age\":32}]";
//        map.put("list",list);
//String commond="unwind {list} as item CREATE (x:Person {name: {item.name}, age: {item.age}})";//,birthday:{this.birthday}
//
//
//        ListValue value=new ListValue();
////        Statement statement=new Statement();
//
//        long s=System.currentTimeMillis();
////        StatementResult result =  )
////                session.run( " create (x:Person) return x",//,birthday:{this.birthday}
//        //
////                session.run( "unwind [{name:\"张三\",age:24},{name:\"李四\",age:32}] as item CREATE (x:Person {name: item.name, age: item.age})"//,birthday:{this.birthday}
//                session.run( "unwind {list} as item CREATE (x:Person {name: {item.name}, age: {item.age}})",//,birthday:{this.birthday}
//
//                        map     );//parameters( list )   parameters( demo )
//         long e=System.currentTimeMillis();
//        System.out.println("插入100000数据,耗时:"+(e-s)/1000);
//
////        StatementResult result = session.run( "MATCH (a:NodeDemo) WHERE a.name = {name} " +
////                        "RETURN a.name as name,a.age as age,a.birthday as birthday,id(a) as id",
////                parameters( demo) );
//
////
////        while ( result.hasNext() )
////        {
////            Record record = result.next();//    +record.get( "birthday" )  数字
////            List<String> list=record.keys();
////            Class cs=NodeDemo.class;
////            Field[]  fs= cs.getDeclaredFields();
////            NodeDemo dm=new NodeDemo();
////            for(Field x:fs){
////               if(list.contains(x.getName())){
//////                   Field field= cs.getDeclaredField(x);
////                   x.setAccessible(true);
////                   try {
////                       x.set(dm, record.get(x.getName()).asObject());
////                   }catch(Exception e){
////                       continue;
////                   }
////               }
////            }
////            System.out.println(dm);
//////            System.out.println( record.get( "name" ).asString() + " " +record.get( "age" )+ "  " +record.get( "id" ));
////        }
//
//
//
//
////        GraphDatabaseService graphdb = new GraphDatabaseFactory().newEmbeddedDatabase("db");
////        Index nodeIndex = graphdb.index().forNodes("nodex");
////        Transaction tx=graphdb.beginTx();
////        try{
////            // neo4j statement
////            tx.success();
////            System.out.println("successfully");
////        }
////        finally{
////            tx.finish();
////        }
//
//
//        //cyper statement
////        ExecutionEngine engine = new ExecutionEngine(graphdb);
////        Map<String, Object> params = new HashMap<String, Object>();
////        params.put( "id", Arrays.asList(1,2) );
////        ExecutionResult result = engine.execute( "xxx");
////
////        System.out.println(result);
////
////        List<String> columns = result.columns();
////        System.out.println( columns );
////
////        //***************************************************
////        registerShutdownHook(graphdb);
//
//        session.close();
//        driver.close();
//    }
//
//    public static Map parameters(Object obj){
//        Map map=null;//new HashMap();
//        map = JSON.parseObject(JSON.toJSONString(obj));
//        return  map;
//    }
//    public static String parse(List list){
//        return JSON.toJSONString(list);
//    }
//
//    @Test
//    public void test(){
//        List<NodeDemo> list=new ArrayList<>();
//        NodeDemo demo=new NodeDemo();
//        demo.setName("李凯");
//        demo.setAge(26);
//        Calendar cal=Calendar.getInstance();
//        cal.set(1992,11,27);
//        demo.setBirthday(cal.getTime());
//        list.add(demo);
//        System.out.println(JSON.toJSONString(list));
//
////        Map map=new HashMap();
////        Map map=parameters(list);
//    }
//
//
//
//}
