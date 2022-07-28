package me.example.quartz.triggers;

import me.example.quartz.CrontabBasedScheduler;
import me.example.quartz.schedulers.ScheduleBuilderProvider;
import org.quartz.CronScheduleBuilder;
import org.quartz.ScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

import java.util.Date;

public class TriggerProvider {

    public Trigger getTrigger() {
        ScheduleBuilderProvider provider = new ScheduleBuilderProvider();
        CronScheduleBuilder scheduleBuilder = provider.cronScheduleBuilder();

        return TriggerBuilder.newTrigger()
                .withIdentity("", "")
                .usingJobData("key", "value")
                .startAt(new Date())
                .withSchedule(scheduleBuilder)
                .endAt(new Date())
                .build();
    }
}
