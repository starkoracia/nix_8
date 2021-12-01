package ua.com.alevel.util;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class DateT {

    private Long dateTimeInMillis;

    private static final int ONE_SECOND = 1000;
    private static final int ONE_MINUTE = 60 * ONE_SECOND;
    private static final int ONE_HOUR = 60 * ONE_MINUTE;
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

    public DateT(Long dateTimeInMillis) {
        this.dateTimeInMillis = dateTimeInMillis;
    }

    public long calculateYear(Long dateTimeInMillis) {
        Long tempMillis = 0L;
        int year = 1970;
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

        System.out.println("year = " + year);

        long monthsInMillis = dateTimeInMillis - tempMillis;

        tempMillis = 0L;
        int month;
        for (month = 0; month < 12; month++) {
            Long currentMonthInMillis = monthInMillis(month, isLeap(year));
            if (tempMillis + currentMonthInMillis > monthsInMillis) {
                month++;
                break;
            }
            tempMillis += currentMonthInMillis;
        }

        System.out.println("month = " + month);

        long daysInMillis = monthsInMillis - tempMillis;

        long days = daysInMillis / ONE_DAY + 1;

        System.out.println("days = " + days);

        long hoursInMillis = daysInMillis % ONE_DAY;
        long hours = hoursInMillis / ONE_HOUR;

        System.out.println("hours = " + hours);

        long minutesInMillis = hoursInMillis % ONE_HOUR;
        long minutes = minutesInMillis / ONE_MINUTE;

        System.out.println("minutes = " + minutes);

        long secondsInMillis = minutesInMillis % ONE_MINUTE;
        long seconds = secondsInMillis / ONE_SECOND;

        System.out.println("seconds = " + seconds);

        long millis = secondsInMillis % ONE_SECOND;

        System.out.println("millis = " + millis);
        return days;

    }

    private boolean isLeap(int year) {
        return year % 400 == 0 || (year % 4 == 0 && year % 100 != 0);
    }

    public Integer calculateMonth(Long monthsInMillis, boolean isLeap) {
        Long tempMillis = 0L;
        int month;
        for (month = 0; month < 12; month++) {
            Long currentMonthInMillis = monthInMillis(month, isLeap);
            if (tempMillis + currentMonthInMillis > monthsInMillis) {
                break;
            }
            tempMillis += currentMonthInMillis;
        }
        return month + 1;
    }

    private Long monthInMillis(int month, boolean isLeap) {
        if (isLeap) {
            return LEAP_MONTH_LENGTH[month] * ONE_DAY;
        } else {
            return MONTH_LENGTH[month] * ONE_DAY;
        }
    }

    public static void main(String[] args) {
        long currentTimeMillis = System.currentTimeMillis();
        System.out.println("currentTimeMillis = " + currentTimeMillis);

        DateT dateT = new DateT(currentTimeMillis);


//        Calendar calendar = new GregorianCalendar(2038, 5, 15);
        Calendar calendar = GregorianCalendar.getInstance();
        System.out.println("calendar = " + calendar);
        calendar.add(Calendar.DATE, 35);
        long time = calendar.getTimeInMillis();

        long calculateYear = dateT.calculateYear(time);
//        System.out.println("calculateYear = " + calculateYear);

//        long monthsMillis = currentTimeMillis - calculateYear;
//        Integer month = dateT.calculateMonth(monthsMillis, false);
//        System.out.println("month = " + month);
    }

}
