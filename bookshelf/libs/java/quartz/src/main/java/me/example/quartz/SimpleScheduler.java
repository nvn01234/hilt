package me.example.quartz;

import me.example.quartz.jobs.JobA;
import me.example.quartz.jobs.LongJob;
import me.example.quartz.jobs.SimpleJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

public class SimpleScheduler {
    public static void main(String[] args) {


        SchedulerFactory schedFact = new StdSchedulerFactory();
        try {

            Scheduler sched = schedFact.getScheduler();

            JobDetail job = JobBuilder.newJob(LongJob.class)
                    .withIdentity("myJob", "group1")
                    .usingJobData("jobRun", 3000)
                    .usingJobData("extraRun", 1000)
                    .build();

            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("myTrigger", "group1")
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                            .repeatForever()
//                            .withMisfireHandlingInstructionIgnoreMisfires()
//                            .withMisfireHandlingInstructionFireNow()
//                            .withMisfireHandlingInstructionNextWithExistingCount()
                            .withMisfireHandlingInstructionNextWithRemainingCount()
//                            .withMisfireHandlingInstructionNowWithExistingCount()
//                            .withMisfireHandlingInstructionNowWithRemainingCount()
                            .withIntervalInMilliseconds(3500))
                    .startNow()
                    .build();

            sched.scheduleJob(job, trigger);
            sched.start();

            System.out.println("Sleeping");
            Thread.sleep(5000);
            System.out.println("Waked up");
            JobDetail otherJob = JobBuilder.newJob(JobA.class)
                    .withIdentity("jobA", "group3")
                    .build();
            sched.scheduleJob(otherJob, trigger);

        } catch (SchedulerException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
