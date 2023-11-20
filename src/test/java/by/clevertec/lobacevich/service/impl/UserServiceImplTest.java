package by.clevertec.lobacevich.service.impl;

import by.clevertec.lobacevich.cache.impl.UserTestData;
import by.clevertec.lobacevich.dao.UserDao;
import by.clevertec.lobacevich.data.UserDto;
import by.clevertec.lobacevich.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserMapper mapper;

    @Mock
    private UserDao dao;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void createUser() {
//        UserDto expected = mapper(UserTestData.getUser1());
    }

    @Test
    void updateUser() {
    }

    @Test
    void deleteUser() {
    }

    @Test
    void findUserById() {
    }

    @Test
    void getAll() {
    }
}