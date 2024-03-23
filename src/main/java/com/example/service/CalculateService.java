package com.example.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CalculateService {

    private final HolidayService holidayService;

    public double calculateVacationPay(double salary, int days, LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            return calculateWithoutDates(salary, days);
        } else {
            return calculateWithDates(salary, startDate, endDate);
        }
    }

    private double calculateWithoutDates(double salary, int days) {
        return salary * days;
    }

    private double calculateWithDates(double salary, LocalDate startDate, LocalDate endDate) {
        List<LocalDate> vacationDays = getBusinessDays(startDate, endDate);
        int numberOfVacationDays = vacationDays.size();
        return salary * numberOfVacationDays;
    }

    private List<LocalDate> getBusinessDays(LocalDate startDate, LocalDate endDate) {
        List<LocalDate> businessDays = new ArrayList<>();
        LocalDate currentDate = startDate;

        while (!currentDate.isAfter(endDate)) {
            if (isBusinessDay(currentDate)) {
                businessDays.add(currentDate);
            }
            currentDate = currentDate.plusDays(1);
        }

        return businessDays;
    }

    private boolean isBusinessDay(LocalDate date) {
        return !isWeekend(date) && !isHoliday(date);
    }

    private boolean isWeekend(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
    }

    public interface HolidayService {
        boolean isHoliday(LocalDate date);
    }
    private boolean isHoliday(LocalDate date) {
        return holidayService.isHoliday(date);
    }

    @Service
    public static class HolidayServiceImpl implements HolidayService  {

        public interface HolidayService {
            boolean isHoliday(LocalDate date);
        }

        private static final List<LocalDate> HOLIDAYS = Arrays.asList(
                LocalDate.of(2024, 1, 1), // Новый год
                LocalDate.of(2024, 1, 7), // Рождество
                LocalDate.of(2024, 2, 23), // День защитника отечества
                LocalDate.of(2024, 3, 8), // Международный женский день
                LocalDate.of(2024, 5, 1), // Мир, май, труд
                LocalDate.of(2024, 5, 9), // День победы
                LocalDate.of(2024, 6, 12), // День России
                LocalDate.of(2024, 11, 4) // День народного единства
        );

        @Override
        public boolean isHoliday(LocalDate date) {
            return HOLIDAYS.contains(date);
        }
    }
}
