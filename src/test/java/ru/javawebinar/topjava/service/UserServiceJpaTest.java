package ru.javawebinar.topjava.service;

import org.springframework.test.context.ActiveProfiles;

import static ru.javawebinar.topjava.Profiles.JPA;

@ActiveProfiles(value = JPA)
public class UserServiceJpaTest extends UserServiceTest {
}
