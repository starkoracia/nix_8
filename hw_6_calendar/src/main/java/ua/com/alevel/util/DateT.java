package ua.com.alevel.util;

public class DateT implements Comparable<DateT> {

    private static final long ONE_SECOND = 1000;
    private static final long ONE_MINUTE = 60 * ONE_SECOND;
    private static final long ONE_HOUR = 60 * ONE_MINUTE;
    private static final long ONE_DAY = 24 * ONE_HOUR;
    private static final long ONE_WEEK = 7 * ONE_DAY;

    private static final long YEAR_DAYS = 365;
    private static final long LEAP_YEAR_DAYS = 366;
    private static final long YEAR_IN_MILLIS = YEAR_DAYS * ONE_DAY;
    private static final long LEAP_YEAR_IN_MILLIS = LEAP_YEAR_DAYS * ONE_DAY;

    static final int MONTH_LENGTH[]
            = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    static final int LEAP_MONTH_LENGTH[]
            = {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    private Long dateTimeInMillis = 0L;

    private Integer year = 0;
    private Integer month = 0;
    private Integer day = 0;
    private Integer hours = 0;
    private Integer minutes = 0;
    private Integer seconds = 0;
    private Integer millis = 0;
    private String pattern = "dd/mm/yyyy 00:00:00";

    public DateT() {

    }

    public DateT(Long dateTimeInMillis) {
        this.dateTimeInMillis = dateTimeInMillis;
        createDateFromMillis(dateTimeInMillis);
    }

    public DateT(DateT dateT) {
        this.dateTimeInMillis = createMillisFromDate(dateT);
        createDateFromMillis(dateTimeInMillis);
    }

    public DateT(DateT dateT, String pattern) {
        this.dateTimeInMillis = createMillisFromDate(dateT);
        createDateFromMillis(dateTimeInMillis);
        this.pattern = pattern;
    }

    public DateT(Long dateTimeInMillis, String pattern) {
        this.dateTimeInMillis = dateTimeInMillis;
        createDateFromMillis(dateTimeInMillis);
        this.pattern = pattern;
    }

    public void create() {
        this.dateTimeInMillis = createMillisFromDate(this);
    }

    private void resetFields() {
        this.year = 0;
        this.month = 0;
        this.day = 0;
        this.hours = 0;
        this.minutes = 0;
        this.seconds = 0;
        this.millis = 0;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public void addYears(int years) {
        this.year += years;
        this.dateTimeInMillis = createMillisFromDate(this);
    }

    public void addDays(int days) {
        addMillis(days * ONE_DAY);
    }

    public void addHours(int hours) {
        addMillis(hours * ONE_HOUR);
    }

    public void addMinutes(int minutes) {
        addMillis(minutes * ONE_MINUTE);
    }

    public void addSeconds(int seconds) {
        addMillis(seconds * ONE_SECOND);
    }

    public void addMillis(long millis) {
        this.dateTimeInMillis += millis;
        resetFields();
        createDateFromMillis(this.dateTimeInMillis);
    }

    private long createMillisFromDate(DateT dateT) {
        long tempMillis = 0L;
        int year = 0;
        while (year < dateT.getYear()) {
            if (isLeap(year)) {
                tempMillis += LEAP_YEAR_IN_MILLIS;
            } else {
                tempMillis += YEAR_IN_MILLIS;
            }
            year++;
        }

        for (int month = 0; month < dateT.getMonth() - 1; month++) {
            long currentMonthInMillis = monthInMillis(month, isLeap(year));
            tempMillis += currentMonthInMillis;
        }

        long daysInMillis = (dateT.getDay() - 1) * ONE_DAY;
        tempMillis += daysInMillis;

        long hoursInMillis = dateT.getHours() * ONE_HOUR;
        tempMillis += hoursInMillis;

        long minutesInMillis = dateT.getMinutes() * ONE_MINUTE;
        tempMillis += minutesInMillis;

        long secondsInMillis = dateT.getSeconds() * ONE_SECOND;
        tempMillis += secondsInMillis;

        tempMillis += dateT.getMillis();

        return tempMillis;
    }

    private void createDateFromMillis(Long dateTimeInMillis) {
        long tempMillis = 0L;
        while (true) {
            if (isLeap(year)) {
                if (tempMillis + LEAP_YEAR_IN_MILLIS > dateTimeInMillis) {
                    break;
                }
                tempMillis += LEAP_YEAR_IN_MILLIS;
            } else {
                if (tempMillis + YEAR_IN_MILLIS > dateTimeInMillis) {
                    break;
                }
                tempMillis += YEAR_IN_MILLIS;
            }
            year++;
        }
        long monthsInMillis = dateTimeInMillis - tempMillis;

        tempMillis = 0L;
        for (; month < 12; month++) {
            long currentMonthInMillis = monthInMillis(month, isLeap(year));
            if (tempMillis + currentMonthInMillis > monthsInMillis) {
                month++;
                break;
            }
            tempMillis += currentMonthInMillis;
        }
        long daysInMillis = monthsInMillis - tempMillis;
        day += (int) (daysInMillis / ONE_DAY + 1);

        long hoursInMillis = daysInMillis % ONE_DAY;
        hours += (int) (hoursInMillis / ONE_HOUR);

        long minutesInMillis = hoursInMillis % ONE_HOUR;
        minutes += (int) (minutesInMillis / ONE_MINUTE);

        long secondsInMillis = minutesInMillis % ONE_MINUTE;
        seconds += (int) (secondsInMillis / ONE_SECOND);

        millis += (int) (secondsInMillis % ONE_SECOND);
    }

    public DateT difference(DateT dateT) {
        long differenceInMillis = Math.abs(this.dateTimeInMillis - dateT.getDateTimeInMillis());
        return new DateT(differenceInMillis);
    }

    private boolean isLeap(int year) {
        return year % 400 == 0 || (year % 4 == 0 && year % 100 != 0);
    }

    private Long monthInMillis(int month, boolean isLeap) {
        if (isLeap) {
            return LEAP_MONTH_LENGTH[month] * ONE_DAY;
        } else {
            return MONTH_LENGTH[month] * ONE_DAY;
        }
    }

    public Long getDateTimeInMillis() {
        return dateTimeInMillis;
    }

    public void setDateTimeInMillis(Long dateTimeInMillis) {
        this.dateTimeInMillis = dateTimeInMillis;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getHours() {
        return hours;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }

    public Integer getMinutes() {
        return minutes;
    }

    public void setMinutes(Integer minutes) {
        this.minutes = minutes;
    }

    public Integer getSeconds() {
        return seconds;
    }

    public void setSeconds(Integer seconds) {
        this.seconds = seconds;
    }

    public Integer getMillis() {
        return millis;
    }

    public void setMillis(Integer millis) {
        this.millis = millis;
    }

    @Override
    public String toString() {
        if(this.pattern == null) {
            return "DateT{" +
                    "dateTimeInMillis=" + dateTimeInMillis +
                    ", year=" + year +
                    ", month=" + month +
                    ", day=" + day +
                    ", hours=" + hours +
                    ", minutes=" + minutes +
                    ", seconds=" + seconds +
                    ", millis=" + millis +
                    '}';
        } else {
            return this.pattern
                    .replace("dd", String.format("%02d", this.getDay()))
                    .replace("d", String.format("%d", this.getDay()))
                    .replace("mmm", String.format("%s", Month.values()[this.getMonth() - 1].getName()))
                    .replace("mm", String.format("%02d", this.getMonth()))
                    .replace("m", String.format("%d", this.getMonth()))
                    .replace("yyyy", String.format("%04d", this.getYear()))
                    .replace("-", " ")
                    .replace("00:00:00:000", String.format("%02d:%02d:%02d:%03d", this.getHours(), this.getMinutes(), this.getSeconds(), this.getMillis()))
                    .replace("00:00:00", String.format("%02d:%02d:%02d", this.getHours(), this.getMinutes(), this.getSeconds()))
                    .replace("00:00", String.format("%02d:%02d", this.getHours(), this.getMinutes()));
        }
    }

    @Override
    public int compareTo(DateT o) {
        return Long.compare(this.dateTimeInMillis, o.getDateTimeInMillis());
    }
}
