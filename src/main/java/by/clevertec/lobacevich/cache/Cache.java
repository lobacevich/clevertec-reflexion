package by.clevertec.lobacevich.cache;

import by.clevertec.lobacevich.entity.User;

import java.util.Optional;

public interface Cache {

    public Optional<User> getById(Integer id);

    public void put(User user);

    public boolean deleteById(Integer id);
}
