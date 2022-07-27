package ru.javawebinar.topjava.web.user;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.javawebinar.topjava.repository.inmemory.InMemoryUserRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static ru.javawebinar.topjava.UserTestData.NOT_FOUND;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({"classpath:spring/spring-app.xml", "classpath:spring/inmemory.xml", "classpath:spring/spring-mvc.xml"})
@ExtendWith(SpringExtension.class)
public class InMemoryAdminRestControllerSpringTest {

    @Autowired
    private AdminRestController controller;

    @Autowired
    private InMemoryUserRepository repository;

    @BeforeEach
    public void setUp() {
        repository.init();
    }

    @Test
    public void delete() {
        controller.delete(USER_ID);
        Assertions.assertNull(repository.get(USER_ID));
    }

    @Test
    public void deleteNotFound() {
        Assertions.assertThrows(NotFoundException.class, () -> controller.delete(NOT_FOUND));
    }
}