package kr.experiments.gcm.test;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import com.google.common.collect.Lists;
import kr.debop4j.core.Action1;
import kr.debop4j.core.parallelism.Parallels;
import lombok.extern.slf4j.Slf4j;
import org.fest.assertions.Assertions;
import org.junit.Test;

import java.util.Date;
import java.util.List;

/**
 * kr.experiments.gcm.test.GoolgleCloudMessagingTest
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 9. 9. 오전 11:13
 */
@Slf4j
public class GoolgleCloudMessagingTest {

    // Application ID
    static final String PROJECT_ID = "1028834060650";

    // Sender 인증 토큰
    static final String SERVER_API_KEY = "AIzaSyCVQy7ncg8UXg7S7V2tviEel0mIaAN-dlc";

    // Registration ID
    static final String DEVICE_ID_PANTEC = "APA91bFaYD1sUkGi6V7NxWxb9TwEID20qo0yxmn74fD0LSZcBrwFQDNHOCFGofKZUrGR1IC019aV75jZDFDUnHSK_R1nTmWgunwqLc_E87_wfQyTct0J68OZNJi2-lJGzlL17ehN4ZUeGoeyw3fUwimaqHCOslZ7Tw";
    static final String DEVICE_ID_LG = "APA91bGRhJN9CeuB2_CpMh0TAc0NZfD1m38l0CdXHKa5VdYt0J9b6PzH5lCk7t0rB20GN-TryCn7jjiU3J4ZO9zji312Rq91itZpVgplwV0Xyg_9Hs0gVtGMCzPooE9lIeH0oofPRDUJq9_wJFWymStjxStBO5lppA";

    final int pushCount = 150;

    @Test
    public void sendPushTest() throws Exception {

        Sender sender = new Sender(SERVER_API_KEY);

        for (int i = 0; i < pushCount; i++) {
            Message message = new Message.Builder().addData("서버", "배성혁")
                                                   .addData("MSG", "Message Push Test " + i)
                                                   .addData("SendTime", String.valueOf(new Date().getTime()))
                                                   .build();

            log.debug("Send Message [{}]", i);

            Result result = sender.send(message, DEVICE_ID_LG, 3);
            Assertions.assertThat(result.getMessageId()).isNotEmpty();

            result = sender.send(message, DEVICE_ID_PANTEC, 3);
            Assertions.assertThat(result.getMessageId()).isNotEmpty();
        }
    }

    @Test
    public void sendPushAsParallelTest() throws Exception {
        final Sender sender = new Sender(SERVER_API_KEY);

        final List<String> registrationIds = Lists.newArrayList(DEVICE_ID_LG, DEVICE_ID_PANTEC);

        Parallels.runPartitions(pushCount, new Action1<List<Integer>>() {
            @Override
            public void perform(List<Integer> arg) {
                for (Integer i : arg) {
                    Message message =
                            new Message.Builder()
                                    .addData("msg", "Message Push Test " + i)
                                    .addData("서버", "배성혁")
                                    .addData("SendTime", String.valueOf(new Date().getTime()))
                                    .delayWhileIdle(false)
                                    .timeToLive(5 * 60)     // 5 minutes
                                    .build();

                    log.debug("Send Message [{}]", i);
                    try {
                        MulticastResult result = sender.send(message, registrationIds, 3);
                        Assertions.assertThat(result.getSuccess()).isEqualTo(registrationIds.size());
                    } catch (Exception e) {
                        log.warn("Push 발송에 예외가 발생했습니다.", e);
                    }
                }
            }
        });
    }
}
