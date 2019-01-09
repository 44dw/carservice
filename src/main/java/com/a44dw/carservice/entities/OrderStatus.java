package com.a44dw.carservice.entities;

import java.util.HashMap;

public class OrderStatus {
    public static final HashMap<String, Integer> STATUS_MAP = new HashMap<>();
    static {
        STATUS_MAP.put("Запланирован", 0);
        STATUS_MAP.put("Выполнен", 1);
        STATUS_MAP.put("Принят клиентом", 2);
    }

    public static String getStatus(int status) {
        for(String key : STATUS_MAP.keySet()) {
            if(STATUS_MAP.get(key) == status)
                return key;
        }
        return null;
    }
}