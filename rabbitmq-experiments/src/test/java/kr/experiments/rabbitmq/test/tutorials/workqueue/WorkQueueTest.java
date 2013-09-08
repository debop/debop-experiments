package kr.experiments.rabbitmq.test.tutorials.workqueue;

import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * kr.experiments.rabbitmq.test.tutorials.workqueue.WorkQueueTest
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 9. 8. 오전 10:45
 */
@Slf4j
public class WorkQueueTest {

    NewTask newTask;
    Worker worker;
    Worker worker2;

    @Before
    public void before() throws IOException {
        newTask = new NewTask();
        newTask.connectQueue();

        worker = new Worker();
        worker.connectQueue();

        worker2 = new Worker();
        worker2.connectQueue();
    }

    @After
    public void after() throws IOException {
        newTask.close();
        worker.close();
        worker2.close();
    }

    @Test
    public void workQueueTest() throws Exception {

        final String taskMessage = "Task Messages ";
        Thread taskThread = new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 0;
                try {
                    while (true) {
                        Thread.sleep(10);
                        newTask.send(taskMessage + i++);
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
                    worker.receive();
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
                    worker.receive();
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
