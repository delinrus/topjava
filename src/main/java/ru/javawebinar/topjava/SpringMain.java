package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.web.SecurityUtil;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.util.Arrays;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 automatic resource management
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            adminUserController.create(new User(null, "userName", "email@mail.ru", "password", Role.ADMIN));

            System.out.println("\nMealsList:");
            MealRestController mealRestController = appCtx.getBean(MealRestController.class);

            SecurityUtil.setAuthUserId(2);
            mealRestController.delete(56783);
            mealRestController.getAll().forEach(System.out::println);

            UserRepository userRes = appCtx.getBean(UserRepository.class);
            userRes.save(new User(null, "Василий", "bpetro@mail.ru", "asdfadsf", Role.USER));
            userRes.save(new User(null, "Василий", "abpetro@mail.ru", "asdfadsf", Role.USER));
            userRes.save(new User(null, "Аасилий", "brpetro@mail.ru", "asdfadsf", Role.USER));
            userRes.save(new User(null, "БВасилий", "bdpetro@mail.ru", "asdfadsf", Role.USER));
            System.out.println("/n/n");
            userRes.getAll().forEach(System.out::println);
        }
    }
}
