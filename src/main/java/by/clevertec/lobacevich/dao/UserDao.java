package by.clevertec.lobacevich.dao;

import by.clevertec.lobacevich.entity.User;
import by.clevertec.lobacevich.exception.DataBaseException;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public interface UserDao {

    User createUser(User user, Connection connection) throws DataBaseException;

    void updateUser(User user, Connection connection) throws DataBaseException;

    void deleteUser(User user, Connection connection) throws DataBaseException;

    Optional<User> findUserById(Long id, Connection connection) throws DataBaseException;

    List<User> findAllUsers(Connection connection);
}
