package by.clevertec.lobacevich.cache.impl;

import by.clevertec.lobacevich.cache.Cache;
import by.clevertec.lobacevich.entity.User;
import by.clevertec.lobacevich.util.YamlReader;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;

public class LFUCache implements Cache {

    private final Map<Integer, User> map;
    private final Map<Integer, Integer> frequency;
    private final Map<Integer, Deque<Integer>> frequencyList;
    private int minFrequency;
    public int capacity;

    public LFUCache() {
        setCapacity();
        this.map = new HashMap<>();
        this.frequency = new HashMap<>();
        this.frequencyList = new HashMap<>();
        minFrequency = 0;
    }

    private void setCapacity() {
        this.capacity = Integer.parseInt(YamlReader.getData().get("Cache.capacity"));
    }

    @Override
    public Optional<User> getById(Integer id) {
        if (map.containsKey(id)) {
            increaseFrequency(id);
            return Optional.of(map.get(id));
        } else {
            return Optional.empty();
        }
    }

    private void increaseFrequency(int id) {
        int freq = frequency.get(id);
        frequency.put(id, freq + 1);
        frequencyList.get(freq).remove(id);
        if (freq == minFrequency && frequencyList.get(freq).isEmpty()) {
            minFrequency++;
        }
        if (!frequencyList.containsKey(freq + 1)) {
            frequencyList.put(freq + 1, new LinkedList<>());
        }
        frequencyList.get(freq + 1).addLast(id);
    }

    @Override
    public void put(User user) {
        int id = user.getId();
        if (map.containsKey(id)) {
            map.put(id, user);
            increaseFrequency(id);
        } else {
            if (map.size() >= capacity) {
                int idToRemove = frequencyList.get(minFrequency).getFirst();
                frequencyList.get(minFrequency).removeFirst();
                frequency.remove(idToRemove);
                map.remove(idToRemove);
            }
            if (!frequencyList.containsKey(1)) {
                frequencyList.put(1, new LinkedList<>());
            }
            minFrequency = 1;
            frequencyList.get(1).addLast(id);
            frequency.put(id, 1);
            map.put(id, user);
        }
    }

    @Override
    public boolean deleteById(Integer id) {
        if (!map.containsKey(id)) {
            return false;
        }
        int freq = frequency.get(id);
        frequencyList.get(freq).remove(id);
        frequency.remove(id);
        map.remove(id);
        if (freq == minFrequency && frequencyList.get(freq).isEmpty()) {
            findMinFrequency();
        }
        return true;
    }
    private void findMinFrequency() {
        if (map.isEmpty()) {
            minFrequency = 0;
            return;
        }
        do {
            minFrequency++;
        } while (!frequencyList.containsKey(minFrequency) ||
                frequencyList.get(minFrequency).isEmpty());
    }
}
