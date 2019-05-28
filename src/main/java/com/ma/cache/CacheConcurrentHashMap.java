package com.ma.cache;

import java.util.concurrent.ConcurrentHashMap;

public class CacheConcurrentHashMap {

    private static ConcurrentHashMap<String, String> cacheMap = new ConcurrentHashMap<>();

    /**
     * 获取缓存的对象
     *
     * @param account
     * @return
     */
    public static String getCache(String account) {

        account = getCacheKey(account);
        // 如果缓冲中有该账号，则返回value
        if (cacheMap.containsKey(account)) {
            return cacheMap.get(account);
        }
        // 如果缓存中没有该账号，把该帐号对象缓存到concurrentHashMap中
        initCache(account);
        return cacheMap.get(account);
    }

    /**
     * 初始化缓存
     *
     * @param account
     */
    private static void initCache(String account) {
        // 一般是进行数据库查询，将查询的结果进行缓存
        cacheMap.put(account, "18013093863");
    }

    /**
     * 拼接一个缓存key
     *
     * @param account
     * @return
     */
    private static String getCacheKey(String account) {
        return Thread.currentThread().getId() + "-" + account;
    }

    /**
     * 移除缓存信息
     *
     * @param account
     */
    public static void removeCache(String account) {
        cacheMap.remove(getCacheKey(account));
    }
}
