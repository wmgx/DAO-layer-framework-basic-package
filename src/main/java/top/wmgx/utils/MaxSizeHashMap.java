package top.wmgx.utils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 限制最大数量的map
 * @author 11092
 *
 * @param <K> key类型
 * @param <V> value 类型
 */
public class MaxSizeHashMap<K, V> extends LinkedHashMap<K, V> {
    private final int maxSize;
    /**
     * 
     * @param maxSize 最大个数
     */
    public MaxSizeHashMap(int maxSize) {
        this.maxSize = maxSize;
    }


  
    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > maxSize;
    }
}