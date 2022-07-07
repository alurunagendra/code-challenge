/**
 * This AppUtility has a method to check the duplicate elements
 * and returns distinct elements.
 *
 * @author Nagendra Kumar Aluru
 */

package com.flowers.codechallenge.util;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

public class AppUtility {

    /**
     * This utility method checks for duplicate
     * elements and returns distinct element
     *
     * @return Predicate<T>
     */
    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }
}
