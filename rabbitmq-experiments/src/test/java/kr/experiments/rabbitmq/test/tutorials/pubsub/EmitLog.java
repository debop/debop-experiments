package kr.experiments.rabbitmq.test.tutorials.pubsub;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import static org.apache.commons.codec.binary.StringUtils.getBytesUtf8;

/**
 * EmitLog
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 9. 8. 오후 5:58
 */
@Slf4j
public class EmitLog {
    private final static String EXCHANGE_NAME = "logs";

    private Connection connection;
    private Channel channel;

    public void connectQueue() throws IOException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        connection = factory.newConnection();
        channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
    }

    public void close() throws IOException {
        channel.close();
        connection.close();
    }

    public void send(String message) throws IOException {
        channel.basicPublish(EXCHANGE_NAME, "", null, getBytesUtf8(message));
        log.debug(" [x] Sent [{}]", message);
    }
}
