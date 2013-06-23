package kr.debop.experiments.spring.integration;

import org.springframework.integration.Message;
import org.springframework.integration.handler.AbstractReplyProducingMessageHandler;

/**
 * kr.debop.experiments.spring.integration.AbstractMessageSplitter
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 6. 24. 오전 12:40
 */
public class AbstractMessageSplitter extends AbstractReplyProducingMessageHandler {

    @Override
    protected Object handleRequestMessage(Message<?> message) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
