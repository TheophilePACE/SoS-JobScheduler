package org.jobscheduler.dashboard.util;

import java.util.*;

/**
 * Convert one type of object into another
 */
public interface Converter<S, T> {
    public T convert(S s);
}
