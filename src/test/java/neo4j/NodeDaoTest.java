package neo4j;

import com.example.mongoapp.dao.UserDao;
import com.example.mongoapp.entity.NodeDemo;
import com.example.mongoapp.entity.User;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
@SpringBootConfiguration
@SpringBootTest
@RunWith(SpringRunner.class)
@ComponentScan(basePackages = {"com.example.mongoapp.dao"})
public class NodeDaoTest {

    @Autowired
    private UserDao dao;  //NodeDemoRepository

    @Test
    public void testSave(){
        NodeDemo demo=new NodeDemo();
        demo.setName("demo");
        demo.setBirthday(new Date());
//        demo=dao.save(demo);
        System.out.println(demo);
    }

    @Test
    public void Save(){
        User demo=new User();
        demo.setName("demo");
        demo.setBirthday(new Date());
        demo.setMongoId(new ObjectId());
        demo=dao.save(demo);
        System.out.println(demo);
    }

}
