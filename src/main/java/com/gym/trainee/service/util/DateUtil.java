package com.gym.trainee.service.util;

import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class DateUtil {
    public static OffsetDateTime now() {
        OffsetDateTime now = OffsetDateTime.now(ZoneId.of("UTC"));
        return now.minusHours(now.getHour())
                .minusMinutes(now.getMinute())
                .minusSeconds(now.getSecond())
                .minusNanos(now.getNano());
    }

    public static OffsetDateTime parse(OffsetDateTime offsetDateTime) {
        return offsetDateTime.atZoneSameInstant(ZoneId.of("UTC"))
                .minusHours(offsetDateTime.getHour())
                .minusMinutes(offsetDateTime.getMinute())
                .minusSeconds(offsetDateTime.getSecond())
                .minusNanos(offsetDateTime.getNano())
                .toOffsetDateTime();
    }

    public static Long toUnix(OffsetDateTime time){
        return Timestamp.valueOf(time.atZoneSameInstant(ZoneOffset.UTC).toLocalDateTime()).getTime();
    }
}
