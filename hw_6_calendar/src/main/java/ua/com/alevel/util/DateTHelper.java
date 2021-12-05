package ua.com.alevel.util;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateTHelper {

    public String createRegexFromPattern(String pattern) {
        String d = "(3[01]|[12][0-9]|[1-9])";
        String dd = "(0?[1-9]|[12][0-9]|3[01])";
        String mmm = "(Январь|Февраль|Март|Апрель|Май|Июнь|Июль|Август|Сентябрь|Октябрь|Ноябрь|Декабрь)";
        String mm = "(0?[1-9]|1[012])";
        String m = "([1-9]|1[012])";
        String yyyy = "([0-9]{4})";
        String timeMillis = "([01][0-9]|2[0-4]):([0-5][0-9]):([0-5][0-9]):([0-9][0-9][0-9])";
        String timeSeconds = "([01][0-9]|2[0-4]):([0-5][0-9]):([0-5][0-9])";
        String timeMinutes = "([01][0-9]|2[0-4]):([0-5][0-9])";

        return "^" + pattern
                .replace("-", " ")
                .replace("dd", dd)
                .replace("d", d)
                .replace("mmm", mmm)
                .replace("mm", mm)
                .replace("m", m)
                .replace("yyyy", yyyy)
                .replace("00:00:00:000", timeMillis)
                .replace("00:00:00", timeSeconds)
                .replace("00:00", timeMinutes)
                + "$";
    }

    private void setDateFromMap(DateT dateT, Map<String, Integer> dateTimeMap) {
        Integer year = dateTimeMap.get("year");
        if(year != null) {
            dateT.setYear(year);
        }
        Integer month = dateTimeMap.get("month");
        if(month != null) {
            dateT.setMonth(month);
        }
        Integer day = dateTimeMap.get("day");
        if(day != null) {
            dateT.setDay(day);
        }
        Integer hours = dateTimeMap.get("hours");
        if(hours != null) {
            dateT.setHours(hours);
        }
        Integer minutes = dateTimeMap.get("minutes");
        if(minutes != null) {
            dateT.setMinutes(minutes);
        }
        Integer seconds = dateTimeMap.get("seconds");
        if(seconds != null) {
            dateT.setSeconds(seconds);
        }
        Integer millis = dateTimeMap.get("millis");
        if(millis != null) {
            dateT.setMillis(millis);
        }
    }

    private List<String> createDateTimeList(String pattern, String dateString) {
        List<String> dateTimeList = new ArrayList<>();
        Pattern regexPattern = Pattern.compile(createRegexFromPattern(pattern));
        Matcher matcher = regexPattern.matcher(dateString);
        if (matcher.find()) {
            for (int j = 1; j != matcher.groupCount() + 1; j++) {
                dateTimeList.add(matcher.group(j));
            }
        }
        return dateTimeList;
    }

    private Map<String, Integer> createDateTimeMap(String pattern, List<String> dateTimeList) {
        String parseRegex = "^(dd|d|mmm|mm|m|yyyy)[/-](dd|d|mmm|mm|m)[/-](dd|d|mmm|mm|m|yyyy) (00):(00):?(00)?:?(000)?$";
        Map<String, Integer> dateTimeMap = new HashMap<>();
        dateTimeMap.put("day", null);
        dateTimeMap.put("month", null);
        dateTimeMap.put("year", null);
        dateTimeMap.put("hours", null);
        dateTimeMap.put("minutes", null);
        dateTimeMap.put("seconds", null);
        dateTimeMap.put("millis", null);

        Matcher patternMatcher = Pattern.compile(parseRegex).matcher(pattern);
        if (patternMatcher.find()) {
            for (int i = 1; i != patternMatcher.groupCount() + 1; i++) {
                String group = patternMatcher.group(i);
                if (group != null) {
                    switch (group) {
                        case "d", "dd" -> dateTimeMap.put("day", Integer.parseInt(dateTimeList.get(i - 1)));
                        case "m", "mm" -> dateTimeMap.put("month", Integer.parseInt(dateTimeList.get(i - 1)));
                        case "mmm" -> {
                            Month[] months = Month.values();
                            for (int m = 0; m < months.length; m++) {
                                if (months[m].getName().equalsIgnoreCase(dateTimeList.get(i - 1))) {
                                    dateTimeMap.put("month", m + 1);
                                }
                            }
                        }
                        case "yyyy" -> dateTimeMap.put("year", Integer.parseInt(dateTimeList.get(i - 1)));
                    }
                    switch (i) {
                        case 4 -> dateTimeMap.put("hours", Integer.parseInt(dateTimeList.get(i - 1)));
                        case 5 -> dateTimeMap.put("minutes", Integer.parseInt(dateTimeList.get(i - 1)));
                        case 6 -> dateTimeMap.put("seconds", Integer.parseInt(dateTimeList.get(i - 1)));
                        case 7 -> dateTimeMap.put("millis", Integer.parseInt(dateTimeList.get(i - 1)));
                    }
                }
            }
        }
        return dateTimeMap;
    }

    public DateT createDateFromString(String pattern, String dateString) {
        DateT dateT = new DateT();
        dateT.setPattern(pattern);

        List<String> dateTimeList = createDateTimeList(pattern, dateString);

        Map<String, Integer> dateTimeMap = createDateTimeMap(pattern, dateTimeList);

        setDateFromMap(dateT, dateTimeMap);

        return dateT;
    }

    public static void main(String[] args) {
        String pattern = "d-mm-yyyy 00:00";
        String dateString = "15 04 1991 21:00";
        DateTHelper creator = new DateTHelper();
        DateT dateT = creator.createDateFromString(pattern, dateString);
        System.out.println("dateT = " + dateT);
    }

}
