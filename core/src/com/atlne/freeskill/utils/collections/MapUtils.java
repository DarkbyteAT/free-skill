package com.atlne.freeskill.utils.collections;

import com.github.czyzby.kiwi.util.tuple.immutable.Pair;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class MapUtils {

    @SafeVarargs
    public static <T, U> Map<T, U> pairsToMap(Pair<T, U>... pairs) {
        return Arrays.stream(pairs).collect(Collectors.toMap(Pair::getKey, Pair::getValue));
    }
}
