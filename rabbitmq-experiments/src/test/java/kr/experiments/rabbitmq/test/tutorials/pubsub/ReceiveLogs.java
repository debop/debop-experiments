package kr.experiments.rabbitmq.test.tutorials.pubsub;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.StringUtils;

import java.io.IOException;

/**
 * kr.experiments.rabbitmq.test.tutorials.pubsub.ReceiveLogs
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 9. 8. 오후 6:01
 */
@Slf4j
public class ReceiveLogs {

    private final static String EXCHANGE_NAME = "logs";

    private Connection connection;
    private Channel channel;
    private String queueName;

    public void connectQueue() throws IOException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        connection = factory.newConnection();
        channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, EXCHANGE_NAME, "");

        log.debug(" [*] Waiting for messages...");
    }


    public void receive() throws IOException, InterruptedException {
        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(queueName, true, consumer);

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
