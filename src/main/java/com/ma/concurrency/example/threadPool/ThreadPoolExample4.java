package com.ma.concurrency.example.threadPool;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
public class ThreadPoolExample4 {
    public static void main(String[] args) {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(5);
        executor.schedule(new Runnable() {
            @Override
            public void run() {
                log.warn("schedule run!");
            }
        },3,TimeUnit.SECONDS);
        executor.shutdown();

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                log.info("time schedule ");
            }
        },new Date(),5*1000);
    }
}
