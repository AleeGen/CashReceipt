package ru.clevertec.cheque.cache.algorithm.impl;

import ru.clevertec.cheque.cache.algorithm.Cache;

import java.util.*;

/**
 * A cache class that is implemented using the LRU algorithm based on the "least frequently used" principle.
 * Using maps and LinkedSet allows you to achieve a constant execution time for all operations
 *
 * @autor Alexey Leonenko
 * @see Cache
 */
public class LFUCache<K, V> implements Cache<K, V> {
    /**
     * Return the cache capacity
     */
    private final int capacity;
    /**
     * Returns a map with all elements
     */
    private final Map<K, V> keyValueMap;
    /**
     * Returns map where the value is the number of key uses
     */
    private final Map<K, Integer> keyFreqMap;
    /**
     * Returns a map where the key is the number of elements used,and the value is the associated set
     * with all the elements that were used in the number of "key" times
     */
    private final Map<Integer, Set<K>> freqMap;
    /**
     * Returns the minimum number of uses of items in the cache that are in the tail of the priority
     */
    int min = 0;

    /**
     * The constructor initializes the cache {@link #capacity}, {@link #keyValueMap},
     * {@link #keyFreqMap}, {@link #freqMap} and adds the initial linkedSet to {@link #freqMap}
     *
     * @param capacity capacity cache
     */
    public LFUCache(int capacity) {
        this.capacity = capacity;
        keyValueMap = new HashMap<>();
        keyFreqMap = new HashMap<>();
        freqMap = new HashMap<>();
        freqMap.put(1, new LinkedHashSet<>());
    }

    /**
     * Returns cache capacity
     *
     * @return cache capacity
     */
    @Override
    public int getCapacity() {
        return capacity;
    }

    /**
     * Returns an element by key thereby increasing its priority of use, if available
     *
     * @param key the key by which the cache elements are accessed
     * @return an element by key or null if no such element is found
     */
    @Override
    public V get(K key) {
        if (!keyValueMap.containsKey(key)) {
            return null;
        }
        int freq = keyFreqMap.get(key);
        keyFreqMap.put(key, freq + 1);
        freqMap.get(freq).remove(key);
        if (freq == min && freqMap.get(freq).isEmpty()) {
            min++;
        }
        if (!freqMap.containsKey(freq + 1)) {
            freqMap.put(freq + 1, new LinkedHashSet<>());
        }
        freqMap.get(freq + 1).add(key);
        return keyValueMap.get(key);
    }

    /**
     * Deletes an item with the specified key in this cache.
     *
     * @param key of the element to be removed
     */
    @Override
    public void delete(K key) {
        keyValueMap.remove(key);
        Integer freq = keyFreqMap.get(key);
        if (freq == null) {
            return;
        }
        freqMap.get(freq).remove(key);
    }

    /**
     * A way to put an item in the cache.
     * If the item is already in the cache, then its priority is increased,
     * and if not, then the minimum priority is set.
     * If the cache size is equal to its capacity, then the element with the least amount of use is deleted,
     * and if there are more than one such elements, the element with the oldest use is deleted. If
     *
     * @param key   the key by which the cache elements are accessed
     * @param value the value that stores the cache by key
     */
    @Override
    public void put(K key, V value) {
        if (keyValueMap.containsKey(key)) {
            keyValueMap.put(key, value);
            get(key);
        } else {
            if (keyValueMap.size() == capacity) {
                Set<K> set = freqMap.get(min);
                K top = poll(set);
                keyValueMap.remove(top);
            }
            keyValueMap.put(key, value);
            keyFreqMap.put(key, 1);
            min = 1;
            freqMap.get(1).add(key);
        }
    }

    private K poll(Set<K> set) {
        Iterator<K> iterator = set.iterator();
        K top = iterator.next();
        set.remove(top);
        return top;
    }

}
