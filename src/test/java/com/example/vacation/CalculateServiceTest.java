package com.example.vacation;

import com.example.service.CalculateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class CalculateServiceTest {

    @Mock
    private CalculateService.HolidayService holidayService;

    private CalculateService calculateService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        calculateService = new CalculateService(holidayService);
    }

    @Test
    public void testCalculateVacationPayWithoutDates() {
        double salary = 100.0;
        int days = 10;
        double expectedPay = 1000.0;

        double actualPay = calculateService.calculateVacationPay(salary, days, null, null);

        assertEquals(expectedPay, actualPay);
    }

    @Test
    public void testCalculateVacationPayWithDates() {
        double salary = 100.0;
        LocalDate startDate = LocalDate.of(2023, 5, 1);
        LocalDate endDate = LocalDate.of(2023, 5, 10);
        double expectedPay = 800.0;

        when(holidayService.isHoliday(any()))
                .thenReturn(false);

        double actualPay = calculateService.calculateVacationPay(salary, 10, startDate, endDate);

        assertEquals(expectedPay, actualPay);
    }
}
