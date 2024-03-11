package com.example.farm.util;

import com.example.farm.exception.InvalidDataException;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtils {
    public static Date getCurrentDay() {
        Calendar calendar = new GregorianCalendar(Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        return calendar.getTime();
    }

    public static Date getDayDate(int day) {
        if (day > 31 || day < 1) {
            throw new InvalidDataException("Incorrect number of day.");
        }
        Calendar calendar = new GregorianCalendar(Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH), day);
        return calendar.getTime();
    }

    public static Date[] getMonthDate(int month) {
        if (month > 12 || month < 0) {
            throw new InvalidDataException("Incorrect number of month.");
        }
        month -= 1; // Т.к. Calendar.JANUARY == 0 и  Calendar.DECEMBER == 11
        Date[] date = new Date[2];
        Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        date[0] = calendar.getTime(); // Первый день текущего месяца
        if (month == 11) {
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            calendar.set(Calendar.MONTH, 0);
            calendar.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR) + 1);

        } else {
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            calendar.set(Calendar.MONTH, month + 1);
            calendar.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
        }
        date[1] = calendar.getTime(); // Первый день следующего месяца
        return date;
    }
}
