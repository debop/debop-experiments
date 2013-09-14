package kr.debop.redis.test.redisinaction;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.HashMap;
import java.util.Map;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Redis In Action Chapter 01
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 9. 14. 오후 7:18
 */
@Slf4j
public class Chapter01 {

    private static final int ONE_WEEK_IN_SECONDS = 7 * 24 * 3600;
    private static final int VOTE_SCORE = 432;
    private static final int ARTICLES_PER_PAGE = 25;

    @Test
    public void testRedis() throws Exception {
        Jedis conn = new Jedis("localhost");
        // DB 선택 (0~15)
        conn.select(15);

        String articleId = postArticle(conn,
                                       "username",
                                       "A title",
                                       "http://www.google.com");
        Map<String, String> articleData = conn.hgetAll("article:" + articleId);
        for (Map.Entry<String, String> entry : articleData.entrySet()) {
            log.debug("{}: {}", entry.getKey(), entry.getValue());
        }

        articleVote(conn, "other_user", "article:" + articleId);
        String votes = conn.hget("article:" + articleId, "votes");
        log.debug("We voted for the article, it now has votes:[{}]", votes);
        assertThat(Integer.parseInt(votes)).isGreaterThan(1);
    }

    public String postArticle(Jedis conn, String user, String title, String link) {
        String articleId = String.valueOf(conn.incr("article:"));

        String voted = "voted:" + articleId;
        conn.sadd(voted, user);
        conn.expire(voted, ONE_WEEK_IN_SECONDS);

        long now = System.currentTimeMillis() / 1000L;
        String article = "article:" + articleId;
        HashMap<String, String> articleData = new HashMap<>();
        articleData.put("title", title);
        articleData.put("link", link);
        articleData.put("user", user);
        articleData.put("now", String.valueOf(now));
        articleData.put("votes", "1");

        Transaction tx = conn.multi();
        tx.hmset(article, articleData);
        tx.zadd("score:", now + VOTE_SCORE, article);
        tx.zadd("time:", now, article);

        tx.exec();

        return articleId;
    }

    public void articleVote(Jedis conn, String user, String article) {
        long cutoff = (System.currentTimeMillis() / 1000L) - ONE_WEEK_IN_SECONDS;
        if (conn.zscore("time:", article) < cutoff) {
            return;
        }

        String articleId = article.substring(article.indexOf(":") + 1);
        if (conn.sadd("voted:" + articleId, user) == 1) {
            conn.zincrby("score:", VOTE_SCORE, article);
            conn.hincrBy(article, "votes", 1L);
        }
    }
}
