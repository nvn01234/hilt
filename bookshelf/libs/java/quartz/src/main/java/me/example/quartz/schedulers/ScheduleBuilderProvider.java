package me.example.quartz.schedulers;

import org.quartz.*;

import java.util.HashMap;
import java.util.Map;

public class ScheduleBuilderProvider {

    public ScheduleBuilderProvider(){

    }

    private static final Map<String, String> cronAnnotationMapping = new HashMap<>(){{
        put("@daily", "0 0 18 * * * *");
        put("@weekly", "0 0 18 ? * SUN *");
        put("@monthly", "0 0 18 1 * ? *");
        put("@annual", "0 0 18 1 1 ? *");
    }};

    public ScheduleBuilder<? extends Trigger> getScheduleBuilder() {
        return cronScheduleBuilder();
    }

    public CronScheduleBuilder cronScheduleBuilder() {
        return CronScheduleBuilder.cronSchedule("")
                .withMisfireHandlingInstructionIgnoreMisfires();
    }

    public SimpleScheduleBuilder simpleScheduleBuilder() {
        return SimpleScheduleBuilder.simpleSchedule()
                .withMisfireHandlingInstructionIgnoreMisfires()
                .withIntervalInSeconds(3)
                .withRepeatCount(1);
    }

    // Not yet useful
    public CalendarIntervalScheduleBuilder calendarIntervalScheduleBuilder() {
        return CalendarIntervalScheduleBuilder.calendarIntervalSchedule()
                .withIntervalInSeconds(3)
                .withMisfireHandlingInstructionIgnoreMisfires();
    }



}
