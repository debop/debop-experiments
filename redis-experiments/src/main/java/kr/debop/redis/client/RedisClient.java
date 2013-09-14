package kr.debop.redis.client;

import kr.debop.redis.serializer.BinaryRedisSerializer;
import kr.debop.redis.serializer.RedisSerializer;
import kr.debop.redis.serializer.SerializationTool;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

import java.util.List;
import java.util.Set;

/**
 * Redis 서버와 통신하는 Client 모듈입니다.
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 7. 24. 오전 11:45
 */
public class RedisClient {

    private static final Logger log = LoggerFactory.getLogger(RedisClient.class);
    private static final boolean isTraceEnabled = log.isTraceEnabled();
    private static final boolean isDebugEnabled = log.isDebugEnabled();

    @Getter
    private final JedisPool pool;

    @Getter @Setter int database;
    @Getter @Setter int expiryInSeconds;

    @Getter @Setter RedisSerializer keySerializer = new BinaryRedisSerializer();
    @Getter @Setter RedisSerializer valueSerializer = new BinaryRedisSerializer();

    public RedisClient() {
        this(new JedisPool("localhost"));
    }

    public RedisClient(JedisPool pool) {
        this.pool = pool;
    }

    public String ping() {
        return doWork(new RedisCallback<String>() {
            @Override
            public String doWork(Jedis jedis) {
                return jedis.ping();
            }
        });
    }

    private <T> T doWork(final RedisCallback<T> callback) {
        Jedis jedis = pool.getResource();
        try {
            jedis.select(database);
            return callback.doWork(jedis);
        } catch (Throwable t) {
            log.error("redis 작업 중에 예외가 발생했습니다.", t);
            pool.returnBrokenResource(jedis);
            jedis = null;
            throw new RuntimeException(t);
        } finally {
            if (jedis != null)
                pool.returnResource(jedis);
        }
    }

    private List<Object> doWorkWithTx(final TransactionalRedisCallback callback) {
        Jedis jedis = pool.getResource();

        try {
            Transaction tx = jedis.multi();
            jedis.select(database);
            callback.doWork(tx);
            return tx.exec();
        } catch (Throwable t) {
            log.error("redis 작업 중에 예외가 발생했습니다.", t);
            pool.returnBrokenResource(jedis);
            jedis = null;
            throw new RuntimeException(t);
        } finally {
            if (jedis != null)
                pool.returnResource(jedis);
        }
    }

    private Set deserializeKeys(Set<byte[]> rawKeys) {
        return SerializationTool.deserialize(rawKeys, getKeySerializer());
    }

    private List deserializeValues(List<byte[]> rawValues) {
        return SerializationTool.deserialize(rawValues, getValueSerializer());
    }
}
