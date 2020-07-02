package com.doclerholding.task;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

public class TaskManagerTest {

    private TaskManager taskManager;

    private static final int ONE_SEC = 1;
    private static final int ITERATION = 5;

    @BeforeEach
    public void init(){
        taskManager = new TaskManager();
    }

    @Test
    public void testSchedule() throws InterruptedException {
        AtomicInteger atomicInteger = new AtomicInteger();
        taskManager.scheduleAtFixDelay(atomicInteger::getAndIncrement, ONE_SEC);

        checkAfterWait(atomicInteger::get);

        taskManager.stop();

        checkAfterWait(atomicInteger::get);

    }

    private void checkAfterWait(Supplier<Integer> intSupplier) throws InterruptedException {
        TimeUnit.SECONDS.sleep(ITERATION);
        int value = intSupplier.get();
        Assertions.assertTrue(Math.abs(value - ITERATION) < 2);
    }
}
