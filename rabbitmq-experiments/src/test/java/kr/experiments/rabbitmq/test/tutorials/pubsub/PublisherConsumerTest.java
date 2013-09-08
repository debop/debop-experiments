package kr.experiments.rabbitmq.test.tutorials.pubsub;

import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * kr.experiments.rabbitmq.test.tutorials.pubsub.PublisherConsumerTest
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 9. 8. 오후 6:06
 */
@Slf4j
public class PublisherConsumerTest {
    EmitLog emitLog;
    ReceiveLogs receiveLog;
    ReceiveLogs receiveLog2;

    @Before
    public void before() throws IOException {
        emitLog = new EmitLog();
        emitLog.connectQueue();

        receiveLog = new ReceiveLogs();
        receiveLog.connectQueue();

        receiveLog2 = new ReceiveLogs();
        receiveLog2.connectQueue();
    }

    @After
    public void after() throws IOException {
        emitLog.close();
        receiveLog.close();
        receiveLog2.close();
    }

    @Test
    public void publishConsumerTest() throws Exception {

        final String taskMessage = "Log Message ";
        Thread taskThread = new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 0;
                try {
                    while (true) {
                        Thread.sleep(10);
                        emitLog.send(taskMessage + i++);
                    }
                } catch (InterruptedException e) {
                    // ignore
                } catch (IOException e) {
                    log.warn("데이터 보내기에 실패했습니다.", e);
                }
            }
        });
        taskThread.start();

        Thread workerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    receiveLog.receive();
                } catch (InterruptedException e) {
                    // ignore
                } catch (IOException e) {
                    log.warn("데이터 받기에 실패했습니다.", e);
                }
            }
        });
        workerThread.start();

        Thread workerThread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    receiveLog2.receive();
                } catch (InterruptedException e) {
                    // ignore
                } catch (IOException e) {
                    log.warn("데이터 받기에 실패했습니다.", e);
                }
            }
        });
        workerThread2.start();

        Thread.sleep(1000);

        taskThread.interrupt();
        workerThread.interrupt();
        workerThread2.interrupt();
    }
}
