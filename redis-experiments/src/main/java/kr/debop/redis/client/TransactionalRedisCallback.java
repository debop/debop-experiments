package kr.debop.redis.client;

import redis.clients.jedis.Transaction;

/**
 * Jedis 의 Transaction을 이용하여 복수의 작업을 수행하도록 합니다.
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 7. 24. 오후 2:01
 */
public interface TransactionalRedisCallback {

    /**
     * Transaction 하에서 작업을 수행합니다.
     *
     * @param tx Jedis Transaction
     */
    public void doWork(Transaction tx);
}
