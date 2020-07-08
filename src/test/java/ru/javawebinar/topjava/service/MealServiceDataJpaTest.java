package ru.javawebinar.topjava.service;


import org.springframework.test.context.ActiveProfiles;

import static ru.javawebinar.topjava.Profiles.DATAJPA;


@ActiveProfiles(value = DATAJPA)
public class MealServiceDataJpaTest extends MealServiceTest {
}
