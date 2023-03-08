package com.fhyj.common.base.utils;

import org.apache.commons.collections4.map.LRUMap;

/**
 * 幂等性判断
 */
public class IdempotentUtils {

    // 根据 LRU(Least Recently Used，最近最少使用)算法淘汰数据的 Map 集合，最大容量 100 个
    private final static LRUMap<String, Integer> reqCache = new LRUMap<>(100);

    /**
     * 幂等性判断
     *
     * @return
     */
    public static boolean judge(String id, final Object lockClass) {
        synchronized (lockClass) {
            // 重复请求判断
            if (reqCache.containsKey(id)) {
                // 重复请求
                return false;
            }
            // 非重复请求，存储请求 ID
            reqCache.put(id, 1);
        }
        return true;
    }
}
