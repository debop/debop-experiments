package kr.debop.api.twitter;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.User;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

/**
 * kr.debop.api.twitter.TimelineTest
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 6. 18. 오후 3:17
 */
@Slf4j
public class TimelineTest {

    @Test
    public void getHomeTimeline() throws Exception {
        Twitter twitter = Twitters.getTwitter();

        List<Status> statuses = twitter.getHomeTimeline(new Paging(1, 100));
        assertThat(statuses).isNotNull();
        assertThat(statuses.size()).isGreaterThan(0);

        for (Status status : statuses) {
            log.debug("[{}]:[{}] at [{}]", status.getUser().getName(), status.getText(), status.getCreatedAt());
        }
    }

    @Test
    public void getUserTimeline() throws Exception {
        Twitter twitter = Twitters.getTwitter();

        List<Status> statuses = twitter.getUserTimeline("debop68", new Paging(1, 20));
        assertThat(statuses).isNotNull();
        assertThat(statuses.size()).isEqualTo(20);

        for (Status status : statuses) {
            log.debug("[{}]:[{}] at [{}]", status.getUser().getName(), status.getText(), status.getCreatedAt());
        }
    }

    @Test
    public void getUserTest() throws Exception {
        Twitter twitter = Twitters.getTwitter();

        List<Status> timeline = twitter.getUserTimeline();
        assertThat(timeline).isNotNull();
        assertThat(timeline.size()).isGreaterThan(0);
        User user = timeline.get(0).getUser();
        assertThat(user).isNotNull();
        log.debug("User=[{}]", Twitters.getUserAsString(user));
    }
}
