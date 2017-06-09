package by.brawl.util;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class Mappers {

    private Mappers() {
        // Lock class from instantiation
    }

    public static <T, R> List<R> asList(Collection<T> collection, Function<T, R> function) {

        return collection.stream()
                         .map(function)
                         .collect(Collectors.toList());
    }

    public static <T, R> Set<R> asSet(Collection<T> collection, Function<T, R> function) {

        return collection.stream()
                         .map(function)
                         .collect(Collectors.toSet());
    }
}
