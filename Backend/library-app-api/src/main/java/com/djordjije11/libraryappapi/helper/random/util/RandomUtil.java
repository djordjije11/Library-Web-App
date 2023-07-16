package com.djordjije11.libraryappapi.helper.random.util;

import java.util.List;
import java.util.Random;

public final class RandomUtil {
    public static <T> T getOne(Random random, List<T> list){
        return list.get(random.nextInt(list.size()));
    }
}
