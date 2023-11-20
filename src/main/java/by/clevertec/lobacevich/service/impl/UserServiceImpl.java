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

    /***
     * Преобразует userDto в User и передает в слой dao для записи в базу данных
     * @param userDto дто сущности User
     * @return возвращает дто сущности User, полученной из слоя dao
     */
    @Override
    public UserDto createUser(UserDto userDto) {
        return mapper.toUserDto(dao.createUser(mapper.toUser(userDto), CONNECTION));
    }

    /***
     * Преобразует userDto в User и передает в слой dao для обновления записи в базе данных
     * @param userDto дто сущности User
     */
    @Override
    public void updateUser(UserDto userDto) {
        dao.updateUser(mapper.toUser(userDto), CONNECTION);
    }

    /***
     * обращается к методу слоя dao для удаления сущности с указанным id
     * @param id получает id сущности, которую надо удалить из базы данных
     * @return возвращает значение boolean true, если объект удален, и false, если нет
     */
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

    /***
     * вызывает в слое dao метод для поиска сущности с данным id, получает Optional данной сущности
     * @param id получает id сущности для поиска
     * @return возвращае dto сущности с указанным id
     */
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

    /***
     * вызывает метод в слое dao для получения всех сущностей из базы данных
     * @return возвращает List dto всех сущностей из базы данных
     */
    @Override
    public List<UserDto> getAll() {
        return dao.findAllUsers(CONNECTION).stream()
                .map(mapper::toUserDto)
                .toList();
    }
}
