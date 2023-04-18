package ru.clevertec.cheque.cache.algorithm;

/**
 * The interface of the caching algorithm
 *
 * @param <K> the key by which the cache elements are accessed
 * @param <V> the value that stores the cache by key
 * @autor Alexey Leonenko
 */
public interface Cache<K, V> {
    /**
     * When performing this operation, it is possible to displace other cache elements,
     * depending on the implementation of the algorithm
     */
    void put(K key, V value);

    /**
     * Returns an element by key. When performing this operation,
     * it is possible to change the priority of elements
     *
     * @return cache element, or null if there is no such key
     */
    V get(K key);

    /**
     * Deletes an element from the cache by key
     */
    void delete(K key);

    /**
     * Returns the allowed cache size
     *
     * @return capacity cache
     */
    int getCapacity();

}