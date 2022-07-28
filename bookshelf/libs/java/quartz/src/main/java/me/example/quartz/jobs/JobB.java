package me.example.quartz.jobs;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

public class JobB implements Job {

    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        System.out.println("Job B runs at " + (new Date()));
    }

}
