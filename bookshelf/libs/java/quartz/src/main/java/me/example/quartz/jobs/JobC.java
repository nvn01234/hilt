package me.example.quartz.jobs;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

public class JobC implements Job {

    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        System.out.println("Job C runs at " + (new Date()));
    }

}
