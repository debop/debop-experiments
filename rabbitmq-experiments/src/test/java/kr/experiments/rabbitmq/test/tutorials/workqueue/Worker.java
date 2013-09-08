package kr.experiments.rabbitmq.test.tutorials.workqueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.StringUtils;

import java.io.IOException;

/**
 * kr.experiments.rabbitmq.test.tutorials.workqueue.Worker
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 9. 8. 오전 10:45
 */
@Slf4j
public class Worker {

    private final static String TASK_QUEUE_NAME = "task.queue";

    private Connection connection;
    private Channel channel;

    public void connectQueue() throws IOException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        connection = factory.newConnection();
        channel = connection.createChannel();
        channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);

        log.debug(" [*] Waiting for messages...");
    }


    public void receive() throws IOException, InterruptedException {
        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(TASK_QUEUE_NAME, false, consumer);

        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = StringUtils.newStringUtf8(delivery.getBody());
            log.debug(" [x] Received = [{}]", message);

            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        }
    }

    public void close() throws IOException {
        channel.close();
        connection.close();
    }
}
