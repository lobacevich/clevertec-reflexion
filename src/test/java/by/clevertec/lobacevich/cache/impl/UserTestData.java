package by.clevertec.lobacevich.cache.impl;

import by.clevertec.lobacevich.entity.User;

public class UserTestData {

    public static User getUser1() {
        return User.builder()
                .id(1)
                .firstname("Alex")
                .lastname("Murfhy")
                .email("1234@gmail.com")
                .build();
    }

    public static User getUser2() {
        return User.builder()
                .id(2)
                .firstname("Nick")
                .lastname("Smith")
                .email("1235@gmail.com")
                .build();
    }

    public static User getUser3() {
        return User.builder()
                .id(3)
                .firstname("Mike")
                .lastname("Carter")
                .email("1236@gmail.com")
                .build();
    }

    public static User getUser4() {
        return User.builder()
                .id(4)
                .firstname("Bob")
                .lastname("Bond")
                .email("1237@gmail.com")
                .build();
    }
}
