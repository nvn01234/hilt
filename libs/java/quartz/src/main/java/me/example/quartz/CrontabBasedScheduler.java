package me.example.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.time.ZoneId;
import java.util.TimeZone;

public class CrontabBasedScheduler {

    public static void main(String args[]) {

        SchedulerFactory schedFact = new StdSchedulerFactory();
        try {

            Scheduler sched = schedFact.getScheduler();

            JobDetail job = JobBuilder.newJob(JobC.class)
                    .withIdentity("jobC", "group3")
                    .build();

            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("myTrigger", "group1")
                    .withSchedule(CronScheduleBuilder
                            .atHourAndMinuteOnGivenDaysOfWeek(5, 5, 2, 3, 4, 5, 6))
                    .startNow()
                    .build();

            sched.scheduleJob(job, trigger);
            sched.start();

        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

}
