package me.example.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class JobC implements Job {

    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        System.out.println("This is the job C");
    }

}
