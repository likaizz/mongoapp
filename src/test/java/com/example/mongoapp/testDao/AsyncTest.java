package com.example.mongoapp.testDao;

import com.example.mongoapp.async.AsyncTask;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class AsyncTest extends DemoDaoTest {
    @Autowired
    private AsyncTask task;

    @Test
    public void testAsync() throws InterruptedException {
//        task.test();
    }
}
