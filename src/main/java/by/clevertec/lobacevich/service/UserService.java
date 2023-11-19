package by.clevertec.lobacevich.service;

import by.clevertec.lobacevich.data.UserDto;

import java.util.List;

public interface UserService {

    void createUser(UserDto userDto);

    void updateUser(UserDto userDto);

    void deleteUser(Integer id);

    UserDto findUserById(Integer id);

    List<UserDto> getAll();
}
