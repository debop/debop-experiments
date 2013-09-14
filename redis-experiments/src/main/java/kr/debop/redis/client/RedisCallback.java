package kr.debop.redis.client;

import redis.clients.jedis.Jedis;

/**
 * Jedis에 대한 일을 수행합니다.
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 7. 24. 오후 2:00
 */
public interface RedisCallback<T> {

    /**
     * 작업을 수행합니다.
     *
     * @param jedis Jedis 인스턴스
     * @return 수행 결과
     */
    public T doWork(Jedis jedis);
}
