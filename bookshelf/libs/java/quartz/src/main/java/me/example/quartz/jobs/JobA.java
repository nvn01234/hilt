package me.example.quartz.jobs;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

public class JobA implements Job {

    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        System.out.println("Job A runs at " + (new Date()));
    }

}
