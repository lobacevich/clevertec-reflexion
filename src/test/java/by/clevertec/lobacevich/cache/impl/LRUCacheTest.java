package by.clevertec.lobacevich.cache.impl;

import by.clevertec.lobacevich.entity.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class LRUCacheTest {

    LRUCache cache = new LRUCache();

    @Test
    void getById() {
        User expected = UserTestData.getUser1();
        cache.put(expected);

        User actual = cache.getById(1L);

        assertEquals(expected, actual);
    }

    @Test
    void put() {
        cache.put(UserTestData.getUser1());
        cache.put(UserTestData.getUser2());
        cache.put(UserTestData.getUser3());
        cache.put(UserTestData.getUser4());

        assertNull(cache.getById(1L));
    }

    @Test
    void delete() {
        User user = UserTestData.getUser1();
        cache.put(user);

        cache.deleteById(1L);

        assertNull(cache.getById(1L));
    }
}