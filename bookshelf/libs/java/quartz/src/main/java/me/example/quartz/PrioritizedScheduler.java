package me.example.quartz;

import me.example.quartz.jobs.JobA;
import me.example.quartz.jobs.JobB;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

public class PrioritizedScheduler {

    public static void main(String[] args) {

        SchedulerFactory schedFact = new StdSchedulerFactory();
        try {
            Scheduler sched = schedFact.getScheduler();

            JobDetail jobA = JobBuilder.newJob(JobA.class)
                    .withIdentity("jobA", "group2")
                    .build();
            JobDetail jobB = JobBuilder.newJob(JobB.class)
                    .withIdentity("jobB", "group2")
                    .build();

            Trigger triggerA = TriggerBuilder.newTrigger()
                    .withIdentity("triggerA", "group2")
                    .withPriority(15)
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                            .withIntervalInSeconds(40)
                            .repeatForever())
                    .startNow()
                    .build();
            Trigger triggerB = TriggerBuilder.newTrigger()
                    .withIdentity("triggerB", "group2")
                    .withPriority(10)
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                            .withIntervalInSeconds(20)
                            .repeatForever())
                    .startNow()
                    .build();

            sched.scheduleJob(jobA, triggerA);
            sched.scheduleJob(jobB, triggerB);
            sched.start();

        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
