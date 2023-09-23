package com.example.collections_backend.utils;

import java.util.Objects;
import java.util.function.Consumer;

public class ConsumerFunctions {

    public static <V> void setIfNotNull(V value, Consumer<V> setter) {
        if (Objects.nonNull(value)) {
            setter.accept(value);
        }
    }

}
