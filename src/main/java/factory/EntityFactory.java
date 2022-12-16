package factory;

import java.util.List;
import java.util.Map;

public interface EntityFactory<K, V> {
    Map<K, V> getListEntity(String path);
}
