package ru.clevertec.cheque.cache;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.clevertec.cheque.cache.algorithm.Cache;
import ru.clevertec.cheque.cache.algorithm.impl.LFUCache;
import ru.clevertec.cheque.cache.algorithm.impl.LRUCache;

/**
 * Factory for creating caches based on their capacity and algorithm
 *
 * @autor Alexey Leonenko
 */
@Component
public class FactoryCache {
    /**
     * Returns the cache capacity, which is read by ${cache.capacity} from the /resources/application.yml
     */
    @Value("${cache.capacity}")
    private int capacity;
    /**
     * Returns the type algorithm, which is read by ${cache.algorithm} from the /resources/application.yml
     */
    @Value("${cache.algorithm}")
    private String algorithm;

    public FactoryCache() {
    }

    /**
     * Returns a specific cache implementation
     *
     * @return a specific cache implementation
     */
    public Cache<Object, Object> getCache() {
        return switch (algorithm) {
            case "lfu" -> new LFUCache<>(capacity);
            default -> new LRUCache<>(capacity);
        };
    }

}
