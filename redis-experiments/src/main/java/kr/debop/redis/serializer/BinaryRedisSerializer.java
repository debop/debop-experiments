package kr.debop.redis.serializer;

import java.io.*;

/**
 * Binary Serializer
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 7. 24. 오후 1:56
 */
public class BinaryRedisSerializer<T extends Serializable> implements RedisSerializer<T> {

    private static final byte[] EMPTY_BYTES = new byte[0];

    @Override
    public byte[] serialize(T graph) {
        if (graph == null) return EMPTY_BYTES;

        try (ByteArrayOutputStream os = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(os)) {
            oos.writeObject(graph);
            oos.flush();

            return os.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public T deserialize(byte[] bytes) {
        if (SerializationTool.isEmpty(bytes))
            return null;

        try (ByteArrayInputStream is = new ByteArrayInputStream(bytes);
             ObjectInputStream ois = new ObjectInputStream(is)) {
            return (T) ois.readObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
