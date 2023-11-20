package by.clevertec.lobacevich.runner;

import by.clevertec.lobacevich.entity.User;
import by.clevertec.lobacevich.mapper.UserMapper;
import by.clevertec.lobacevich.mapper.UserMapperImpl;
import by.clevertec.lobacevich.service.UserService;
import by.clevertec.lobacevich.service.impl.UserServiceImpl;

import java.time.LocalDate;

public class Main {

    private static UserService service = new UserServiceImpl();
    private static UserMapper mapper = new UserMapperImpl();

    public static void main(String[] args) {
        User user = User.builder()
                .firstname("Mike")
                .lastname("Carter")
                .dateOfBirth(LocalDate.of(2001, 12, 06))
                .email("1236@gmail.com")
                .build();
        System.out.println(service.createUser(mapper.toUserDto(user)));
//        System.out.println(service.deleteUser(6));
//        System.out.println(service.findUserById(10L));
//        System.out.println(service.getAll());

    }
}
