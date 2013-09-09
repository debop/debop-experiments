package kr.experiments.rabbitmq.test.tutorials.simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.StringUtils;

import java.io.IOException;

/**
 * kr.experiments.rabbitmq.test.tutorials.simple.Receiver
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 9. 8. 오전 2:29
 */
@Slf4j
public class Receiver {

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


    public void receive() throws IOException, InterruptedException {
        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(QUEUE_NAME, true, consumer);

        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = StringUtils.newStringUtf8(delivery.getBody());
            log.debug(" [x] Received = [{}]", message);
        }
    }

    public void close() throws IOException {
        channel.close();
        connection.close();
    }
}
