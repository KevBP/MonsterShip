package com.monstership.util;

import java.sql.Timestamp;
import java.util.Date;

public class EntityUtils {
    private EntityUtils() {
    }

    public static Timestamp now() {
        return new Timestamp((new Date()).getTime());
    }
}
