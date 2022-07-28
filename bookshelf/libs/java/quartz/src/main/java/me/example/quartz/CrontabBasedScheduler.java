package me.example.quartz;

import me.example.quartz.jobs.JobA;
import me.example.quartz.jobs.JobC;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

public class CrontabBasedScheduler {

    public static void main(String args[]) {

        SchedulerFactory schedFact = new StdSchedulerFactory();
        try {

            Scheduler sched = schedFact.getScheduler();

            JobDetail job = JobBuilder.newJob(JobC.class)
                    .withIdentity("jobC", "group3")
                    .build();

            ScheduleBuilder<? extends Trigger> scheduleBuilder = CronScheduleBuilder
                    .atHourAndMinuteOnGivenDaysOfWeek(5, 5, 2, 3, 4, 5, 6)
                    .withMisfireHandlingInstructionDoNothing();

            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("myTrigger", "group1")
                    .withSchedule(scheduleBuilder)
                    .startNow()
                    .build();

            sched.scheduleJob(job, trigger);
            sched.start();

        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

}
