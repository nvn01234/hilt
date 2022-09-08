package com.demo.processors.custom.generators.datetime;

import com.demo.processors.custom.Options;
import com.demo.processors.custom.generators.Generator;
import com.demo.processors.custom.generators.numeric.IntegerGenerator;
import org.apache.nifi.components.Validator;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

public class TimestampGenerator implements Generator {

    private static final String CONFIG_ZONE = "zone";

    private static final String CONFIG_FORMAT = "format";

    private static final String CONFIG_FROM_DATE = "from_date";

    private static final String CONFIG_TO_DATE = "to_date";

    private final String name;

    private DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME
            .withZone(ZoneId.systemDefault());

    private ZoneId zone = ZoneId.systemDefault();

    private Date fromDate = Date.from(Instant.EPOCH);

    private Date toDate;

    public TimestampGenerator(String name) {
        this.name = name;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public void config(Options generatorConfig) {
        String zone = generatorConfig.getString(CONFIG_ZONE);
        if(null != zone)
            this.zone = ZoneId.of(zone);

        String format = generatorConfig.getString(CONFIG_FORMAT);
        if(null != format)
            this.formatter = DateTimeFormatter
                    .ofPattern(format)
                    .withZone(this.zone);

        String fromDate = generatorConfig.getString(CONFIG_FROM_DATE);
        if(null != fromDate) {
            TemporalAccessor accessor = this.formatter.parse(fromDate);
            Instant ins = Instant.from(accessor);
            this.fromDate = new Date(ins.toEpochMilli());
        }

        String toDate = generatorConfig.getString(CONFIG_TO_DATE);
        if(null != fromDate) {
            TemporalAccessor accessor = this.formatter.parse(toDate);
            Instant ins = Instant.from(accessor);
            this.toDate = new Date(ins.toEpochMilli());
        }
    }

    @Override
    public Object generate() {
        long dateFromAsLong = fromDate.getTime();
        long dateToAsLong = toDate != null ? toDate.getTime() : Instant.now().toEpochMilli();

        IntegerGenerator generator = IntegerGenerator.of(
                "timestamp_millis_generator", dateToAsLong, dateFromAsLong);
        long rdDateAsLong = Long.parseLong(generator.generate().toString());
        Instant rdDateAsInstant = (new Date(rdDateAsLong)).toInstant();

        return formatter.format(rdDateAsInstant);
    }
}
