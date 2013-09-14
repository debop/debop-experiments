package kr.debop.redis.serializer;

import java.io.Serializable;

/**
 * kr.hconnect.redis.serializer.RedisSerializer
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 7. 24. 오전 11:46
 */
public interface RedisSerializer<T extends Serializable> {

    /**
     * Serialize Object
     */
    byte[] serialize(T graph);

    /**
     * Deserialize to object
     */
    T deserialize(byte[] bytes);
}
