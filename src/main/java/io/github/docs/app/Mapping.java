package io.github.docs.app;

import com.google.common.collect.ImmutableList;

import java.util.List;

/**
 * Generic interface for mapping stuff from one type to another
 *
 * @param <V1>
 * @param <V2>
 */
public interface Mapping<V1, V2> {

    V1 toValue1(V2 vs);

    V2 toValue2(V1 vs);

    default List<V1> toValue1(List<V2> vs) {
        return vs.stream().map(this::toValue1).collect(ImmutableList.toImmutableList());
    }

    default List<V2> toValue2(List<V1> vs) {
        return vs.stream().map(this::toValue2).collect(ImmutableList.toImmutableList());
    }
}
