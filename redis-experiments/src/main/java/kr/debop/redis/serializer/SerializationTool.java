package kr.debop.redis.serializer;

import java.io.Serializable;
import java.util.*;

/**
 * kr.hconnect.redis.serializer.SerializationTool
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 7. 24. 오후 1:57
 */
public abstract class SerializationTool {

    private SerializationTool() {}

    static final byte[] EMPTY_ARRAY = new byte[0];

    static boolean isEmpty(byte[] data) {
        return (data == null || data.length == 0);
    }

    @SuppressWarnings("unchecked")
    private static <T extends Collection<?>> T deserializeValues(Collection<byte[]> rawValues,
                                                                 Class<T> clazz,
                                                                 RedisSerializer<?> redisSerializer) {
        if (rawValues == null)
            return null;

        int valueCount = rawValues.size();
        Collection<Object> values =
                List.class.isAssignableFrom(clazz)
                        ? new ArrayList<Object>(valueCount)
                        : new HashSet<Object>(valueCount);

        for (byte[] bs : rawValues) {
            values.add(redisSerializer.deserialize(bs));
        }
        return (T) values;
    }

    @SuppressWarnings("unchecked")
    public static <T extends Serializable> Set<T> deserialize(Set<byte[]> rawValues, RedisSerializer<T> redisSerializer) {
        return deserializeValues(rawValues, Set.class, redisSerializer);
    }

    @SuppressWarnings("unchecked")
    public static <T extends Serializable> List<T> deserialize(List<byte[]> rawValues, RedisSerializer<T> redisSerializer) {
        return deserializeValues(rawValues, List.class, redisSerializer);
    }

    @SuppressWarnings("unchecked")
    public static <T extends Serializable> Collection<T> deserialize(Collection<byte[]> rawValues, RedisSerializer<T> redisSerializer) {
        return deserializeValues(rawValues, List.class, redisSerializer);
    }
}
