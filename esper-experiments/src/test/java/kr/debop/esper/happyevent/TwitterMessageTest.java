package kr.debop.esper.happyevent;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import twitter4j.*;

/**
 * kr.debop.esper.happyevent.TwitterMessageTest
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 6. 18. 오후 3:37
 */
@Slf4j
public class TwitterMessageTest {

    private static HappyEventListener listener;
    private static EPServiceProvider provider;

    @Before
    public void before() {
        Configuration cfg = new Configuration();
        cfg.addEventType("HappyMessage", HappyMessage.class.getName());
        provider = EPServiceProviderManager.getDefaultProvider();

        String expr = "select user, sum(ctr) from " + HappyMessage.class.getName() + ".win:time(10 seconds) having sum(ctr) > 2";
        EPStatement statement = provider.getEPAdministrator().createEPL(expr);

        listener = new HappyEventListener();
        statement.addListener(listener);
    }

    private void raiseEvent(EPServiceProvider provider, String name, Status status) {
        HappyMessage msg = new HappyMessage();
        msg.setUser(status.getUser().getScreenName());
        provider.getEPRuntime().sendEvent(msg);
    }

    private StatusListener getStatusListner() {
        return new StatusListener() {
            @Override
            public void onStatus(Status status) {
                String[] searchStrs = new String[]{ "lol", "ㅎㅎ", "ㅋㅋ", "하하" };
                for (String searchStr : searchStrs) {
                    if (status.getText().indexOf(searchStr) > 0) {
                        log.debug("******** [{}] *********** : [{}]", searchStr, status.getText());
                        raiseEvent(provider, status.getUser().getScreenName(), status);
                        break;
                    }
                }
            }

            @Override
            public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
                log.debug("Get a status deletion notice id=[{}]", statusDeletionNotice.getStatusId());
            }

            @Override
            public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
                log.debug("Get track limitation notice: [{}]", numberOfLimitedStatuses);
            }

            @Override
            public void onScrubGeo(long userId, long upToStatusId) {
                log.debug("Get scrub_geo event userId=[{}], upToStatusId=[{}]", userId, upToStatusId);
            }

            @Override
            public void onStallWarning(StallWarning warning) {
                // nothing
            }

            @Override
            public void onException(Exception ex) {
                log.error("예외가 발생했습니다.", ex);
            }
        };
    }

    @Test
    public void twitterStreamTest() throws Exception {
        TwitterStream twitterStream = Twitters.getTwitterStream();
        StatusListener listener = getStatusListner();

        twitterStream.addListener(listener);
        twitterStream.sample();

        Thread.sleep(3000);
    }
}
