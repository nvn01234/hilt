package me.example.quartz.jobs;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class LongJob implements Job {
    private static final Random rd = new Random();
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        String jobId = UUID.randomUUID().toString();
        System.out.println("Long job " + jobId + " runs at " + (new Date()));

        JobDataMap dataMap = context.getJobDetail().getJobDataMap();
        int jobRun = dataMap.getInt("jobRun");
        int extraRun = dataMap.getInt("extraRun");
        try {
            Thread.sleep(jobRun + rd.nextInt(rd.nextInt(2 * extraRun) - extraRun));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Long job " + jobId + " finishes at " + (new Date()));
    }
}
