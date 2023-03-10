package ru.clevertec.cheque.cache;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.clevertec.cheque.entity.Human;

/**
 * Factory for creating caches based on their capacity and algorithm
 *
 * @autor Alexey Leonenko
 */
@Configuration
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
    @Bean
    public Cache<Integer, Human> getCache() {
        return switch (algorithm) {
            case "lfu" -> new LFUCache<>(capacity);
            default -> new LRUCache<>(capacity);
        };
    }

}
