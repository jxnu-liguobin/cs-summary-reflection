/* All Contributors (C) 2020 */
package io.github.dreamylost.utils.time;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAdjusters;

public final class TimeUtils {

    public static final int DEFAULT_OFFSET = 8;
    public static final ZoneOffset DEFAULT_TIME_ZONE = ZoneOffset.ofHours(DEFAULT_OFFSET);

    private TimeUtils() {}

    public static ZonedDateTime daysBefore(long days) {
        return atStartOfDay(ZonedDateTime.now(DEFAULT_TIME_ZONE).minusDays(days));
    }

    public static ZonedDateTime daysAfter(long days) {
        return atStartOfDay(ZonedDateTime.now(DEFAULT_TIME_ZONE).plusDays(days));
    }

    public static ZonedDateTime atStartOfDay(ZonedDateTime zdt) {
        return zdt.toLocalDate().atStartOfDay(DEFAULT_TIME_ZONE);
    }

    public static ZonedDateTime yearsBefore(long years) {
        return ZonedDateTime.now(DEFAULT_TIME_ZONE).minusYears(years);
    }

    public static ZonedDateTime firstDayOfYear(ZonedDateTime zdt) {
        return zdt.with(TemporalAdjusters.firstDayOfYear());
    }

    public static ZonedDateTime lastDayOfYear(ZonedDateTime zdt) {
        return zdt.with(TemporalAdjusters.lastDayOfYear());
    }

    public static long toMills(ZonedDateTime zdt) {
        return zdt.toInstant().toEpochMilli();
    }

    public static ZonedDateTime fromEpochMills(long mills) {
        return ZonedDateTime.ofInstant(Instant.ofEpochMilli(mills), DEFAULT_TIME_ZONE);
    }
}
