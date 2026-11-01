package tasks;

import java.lang.reflect.InvocationTargetException;

public class Main {
    public static void main(String[] args) {
        
        TestRunner runner = new TestRunner("tasks.DemoTest");
        runner.scanClass();
        runner.runMethods();
        

    }

}
