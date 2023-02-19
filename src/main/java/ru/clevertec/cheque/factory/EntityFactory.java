package ru.clevertec.cheque.factory;

import java.util.Map;

public interface EntityFactory<K, V> {
    Map<K, V> getMapEntity(String path);
}
