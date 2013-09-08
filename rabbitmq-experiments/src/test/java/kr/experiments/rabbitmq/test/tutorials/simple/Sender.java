package kr.experiments.rabbitmq.test.tutorials.simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import static org.apache.commons.codec.binary.StringUtils.getBytesUtf8;

/**
 * kr.experiments.rabbitmq.test.tutorials.simple.Sender
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 9. 8. 오전 2:21
 */
@Slf4j
public class Sender {

    private final static String QUEUE_NAME = "hello";

    private Connection connection;
    private Channel channel;

    public void connectQueue() throws IOException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        connection = factory.newConnection();
        channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
    }

    public void close() throws IOException {
        channel.close();
        connection.close();
    }

    public void send() throws IOException {
        final String message = "Hello World";
        channel.basicPublish("", QUEUE_NAME, null, getBytesUtf8(message));
        log.debug(" [x] Sent [{}]", message);
    }
}
