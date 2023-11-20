package by.clevertec.lobacevich.service.impl;

import by.clevertec.lobacevich.dao.UserDao;
import by.clevertec.lobacevich.dao.UserDaoImpl;
import by.clevertec.lobacevich.data.UserDto;
import by.clevertec.lobacevich.db.Connect;
import by.clevertec.lobacevich.entity.User;
import by.clevertec.lobacevich.mapper.UserMapper;
import by.clevertec.lobacevich.mapper.UserMapperImpl;
import by.clevertec.lobacevich.service.UserService;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {

    private static final Connection CONNECTION = Connect.getConnection();
    private final UserDao dao = new UserDaoImpl();
    private final UserMapper mapper = new UserMapperImpl();

    @Override
    public UserDto createUser(UserDto userDto) {
        return mapper.toUserDto(dao.createUser(mapper.toUser(userDto), CONNECTION));
    }

    @Override
    public void updateUser(UserDto userDto) {
        dao.updateUser(mapper.toUser(userDto), CONNECTION);
    }

    @Override
    public boolean deleteUser(Long id) {
        Optional<User> userOptional = dao.findUserById(id, CONNECTION);
        if (userOptional.isEmpty()) {
            return false;
        } else {
            dao.deleteUser(userOptional.get(), CONNECTION);
            return true;
        }
    }

    @Override
    public UserDto findUserById(Long id) {
        Optional<User> userOptional = dao.findUserById(id, CONNECTION);
        if (userOptional.isEmpty()) {
            System.out.println("User not found");
            return null;
        } else {
            return mapper.toUserDto(userOptional.get());
        }
    }

    @Override
    public List<UserDto> getAll() {
        return dao.findAllUsers(CONNECTION).stream()
                .map(mapper::toUserDto)
                .toList();
    }
}
