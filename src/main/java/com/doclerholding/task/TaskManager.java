package com.doclerholding.task;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class TaskManager {
    private volatile boolean runForever = true;

    public void scheduleAtFixDelay(final Task task, long delayInSec){
        new Thread(
                () -> {
                    while(runForever) {
                        try {
                            TimeUnit.SECONDS.sleep(delayInSec);
                            task.execute();
                        } catch (Exception ex) {
                            log.error("Error while running task: {}", ex.getMessage());
                        }
                    }
                }).start();
    }

    public void stop() {
        runForever = false;
    }
}
