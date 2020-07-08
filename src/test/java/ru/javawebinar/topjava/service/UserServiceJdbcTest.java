package ru.javawebinar.topjava.service;

import org.springframework.test.context.ActiveProfiles;

import static ru.javawebinar.topjava.Profiles.JDBC;

@ActiveProfiles(value = JDBC)
public class UserServiceJdbcTest extends UserServiceTest {
}
