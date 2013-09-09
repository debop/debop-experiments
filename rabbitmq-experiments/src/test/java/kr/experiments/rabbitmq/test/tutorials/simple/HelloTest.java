package kr.experiments.rabbitmq.test.tutorials.simple;

import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * kr.experiments.rabbitmq.test.tutorials.simple.HelloTest
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 9. 8. 오전 2:35
 */
@Slf4j
public class HelloTest {

    Sender sender;
    Receiver receiver;

    @Before
    public void before() throws IOException {
        sender = new Sender();
        sender.connectQueue();

        receiver = new Receiver();
        receiver.connectQueue();
    }

    @After
    public void after() throws IOException {
        sender.close();
        receiver.close();
    }

    @Test
    public void sendAndReceiveTest() throws Exception {

        Thread sendThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        Thread.sleep(10);
                        sender.send();
                    }
                } catch (InterruptedException e) {
                    // ignore
                } catch (IOException e) {
                    log.warn("데이터 보내기에 실패했습니다.", e);
                }
            }
        });
        sendThread.start();

        Thread receiveThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    receiver.receive();
                } catch (InterruptedException e) {
                    // ignore
                } catch (IOException e) {
                    log.warn("데이터 받기에 실패했습니다.", e);
                }
            }
        });
        receiveThread.start();

        Thread.sleep(1000);

        sendThread.interrupt();
        receiveThread.interrupt();
    }
}
