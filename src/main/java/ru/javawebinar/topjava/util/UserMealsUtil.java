package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        System.out.println("filteredByCycles");
        filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000).forEach(System.out::println);

        System.out.println("filteredByStreams");
        filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000).forEach(System.out::println);

        System.out.println("filteredByCyclesOptional2");
        filteredByCyclesOptional2(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000).forEach(System.out::println);
    }

    public static List<UserMealWithExcess> filteredByCyclesOptional2(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay)
            throws NoSuchFieldException, IllegalAccessException {
        // Enabling Boolean modification
        Field field = Boolean.class.getDeclaredField("value");
        field.setAccessible(true);

        List<UserMealWithExcess> mealsWithExcess = new ArrayList<>();
        Map<LocalDate, Integer> caloriesMap = new HashMap<>();
        Map<LocalDate, Boolean> excessMap = new HashMap<>();

        for (UserMeal meal : meals) {
            LocalDate date = meal.getDateTime().toLocalDate();
            Integer caloriesSum = caloriesMap.merge(date, meal.getCalories(), Integer::sum);
            Boolean hasExcess = excessMap.computeIfAbsent(date, d -> new Boolean(false));
            if (caloriesSum > caloriesPerDay) {
                // Changing by Reflection hasExcess=true
                field.setBoolean(hasExcess, true);
            }
            if (TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime)) {
                mealsWithExcess.add(convertToTransferObject(meal, hasExcess));
            }
        }
        field.setAccessible(false);
        return mealsWithExcess;
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // Summing calories per each day
        Map<LocalDate, Integer> caloriesMap = new HashMap<>();
        for (UserMeal meal : meals) {
            caloriesMap.merge(meal.getDateTime().toLocalDate(), meal.getCalories(), Integer::sum);
        }

        List<UserMealWithExcess> mealsWithExcess = new ArrayList<>();
        for (UserMeal meal : meals) {
            if (TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime)) {
                int dayCalories = caloriesMap.get(meal.getDateTime().toLocalDate());
                mealsWithExcess.add(convertToTransferObject(meal, (dayCalories > caloriesPerDay)));
            }
        }
        return mealsWithExcess;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> map = meals.stream()
                .collect(Collectors.groupingBy(m -> m.getDateTime().toLocalDate(),
                        Collectors.summingInt(UserMeal::getCalories)));

        return meals.stream()
                .filter(m -> TimeUtil.isBetweenHalfOpen(m.getDateTime().toLocalTime(), startTime, endTime))
                .map(m -> convertToTransferObject(m, map.get(m.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    public static UserMealWithExcess convertToTransferObject(UserMeal meal, Boolean excess) {
        return new UserMealWithExcess(meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess);
    }

}
