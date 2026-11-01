package tasks;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import tasks.annotations.AfterEach;
import tasks.annotations.BeforeEach;
import tasks.annotations.Skip;
import tasks.annotations.Test;

public class TestRunner {
    private final List<Method> testMethods = new ArrayList<>();
    private final List<Method> beforeEachMethods = new ArrayList<>();
    private final List<Method> afterEachMethods = new ArrayList<>();
    private Class<?> clazz;

    private int skipped = 0;
    private int failed = 0;
    private int passed = 0;
    private int total = 0;

    public TestRunner(String str) {
        try {
            clazz = Class.forName(str);
        } catch (ClassNotFoundException e) {
            System.out.println("класс не найден");
        }
    }

    public void scanClass() {
        try {

            Method[] methods = clazz.getDeclaredMethods();
            for (Method m : methods) {
                m.setAccessible(true);
                if (m.isAnnotationPresent(Test.class)) {
                    total++;
                    if (m.isAnnotationPresent(Skip.class)) {
                        skipped++;
                        System.out.println("[SKIP] " + m.getName());
                    } else {
                        testMethods.add(m);
                    }
                }
                if (m.isAnnotationPresent(BeforeEach.class))
                    beforeEachMethods.add(m);
                if (m.isAnnotationPresent(AfterEach.class))
                    afterEachMethods.add(m);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void runMethods() {
        for (Method m : testMethods) {
            Object instance;
            try {
                instance = clazz.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                e.printStackTrace();
                failed++;
                continue;
            }

            try {
                for (Method bm : beforeEachMethods) {
                    bm.invoke(instance);
                }
                m.invoke(instance);
                passed++;
                System.out.println("[PASS] " + m.getName());
            } catch (InvocationTargetException e) {
                Throwable cause = e.getTargetException();
                System.out.println("[FAIL] " + m.getName() + " -> " +
                        cause.getClass().getSimpleName() + ": " + cause.getMessage());
                failed++;
            } catch (IllegalAccessException e) {
                System.out.println("[FAIL] " + m.getName() + " -> " +
                        e.getClass().getSimpleName() + ": " + e.getMessage());
                        failed++;
            } finally {
                for (int i = afterEachMethods.size() - 1; i >= 0; i--) {
                    Method am = afterEachMethods.get(i);
                    try {
                        am.invoke(instance);
                    } catch (InvocationTargetException e) {
                        Throwable cause = e.getTargetException();
                        System.out.println(cause.getClass().getSimpleName() + ": " + cause.getMessage());
                        
                    } catch (IllegalAccessException e) {
                        System.out.println(e.getClass().getSimpleName());
                    }
                }
            }
        }

        printSummary();
    }

    private void printSummary() {
        System.out.println("\n========== СВОДКА ==========");
        System.out.println("Всего тестов: " + total);
        System.out.println("Пройдено:     " + passed);
        System.out.println("Провалено:    " + failed);
        System.out.println("Пропущено:    " + skipped);
        System.out.println("============================");
    }
}
