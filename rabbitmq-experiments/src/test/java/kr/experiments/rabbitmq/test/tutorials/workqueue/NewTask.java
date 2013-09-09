package kr.experiments.rabbitmq.test.tutorials.workqueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import static org.apache.commons.codec.binary.StringUtils.getBytesUtf8;

/**
 * kr.experiments.rabbitmq.test.tutorials.workqueue.NewTask
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 9. 8. 오전 10:45
 */
@Slf4j
public class NewTask {

    private final static String TASK_QUEUE_NAME = "task.queue";

    private Connection connection;
    private Channel channel;

    public void connectQueue() throws IOException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        connection = factory.newConnection();
        channel = connection.createChannel();
        channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
    }

    public void close() throws IOException {
        channel.close();
        connection.close();
    }

    public void send(String message) throws IOException {
        channel.basicPublish("",
                             TASK_QUEUE_NAME,
                             MessageProperties.PERSISTENT_TEXT_PLAIN,
                             getBytesUtf8(message));
        log.debug(" [x] Sent [{}]", message);
    }
}
