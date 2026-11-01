package tasks;

import tasks.annotations.AfterEach;
import tasks.annotations.BeforeEach;
import tasks.annotations.Skip;
import tasks.annotations.Test;

public class DemoTest {

    @BeforeEach
    void before1() { System.out.println("before1"); }

    @BeforeEach
    void before2() { System.out.println("before2"); }

    @Test
    void test1() { System.out.println("TEST 1"); throw new RuntimeException("boom"); }

    @Test 
    void test2() {
        System.out.println("TEST 2");
    }

    @AfterEach
    void after1() { System.out.println("after1"); }

    @AfterEach
    void after2() { System.out.println("after2"); }

    @Skip
    @Test  
    void testSkip(){
        System.out.println("skip");
    }


}