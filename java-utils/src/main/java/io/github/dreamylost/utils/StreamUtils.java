/* All Contributors (C) 2020 */
package io.github.dreamylost.utils;

import java.util.Optional;
import java.util.stream.Stream;

public final class StreamUtils {

    private StreamUtils() {}

    /**
     * Turns an Optional<T> into a Stream<T> of length zero or one depending upon whether a value is
     * present.
     */
    public static <T> Stream<T> fromOptional(Optional<T> opt) {
        return opt.map(Stream::of).orElseGet(Stream::empty);
    }
}
