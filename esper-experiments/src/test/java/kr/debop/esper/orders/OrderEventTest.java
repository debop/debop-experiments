package kr.debop.esper.orders;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

/**
 * kr.debop.esper.orders.OrderEventTest
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 6. 18. 오후 3:02
 */
@Slf4j
public class OrderEventTest {

    private MyListener listener;
    private EPServiceProvider provider;

    @Before
    public void before() {
        listener = new MyListener();
        provider = EPServiceProviderManager.getDefaultProvider();

        String expression = "select avg(price) from " + OrderEvent.class.getName() + ".win:time(30 sec)";
        EPStatement statement = provider.getEPAdministrator().createEPL(expression);
        statement.addListener(listener);
    }

    @Test
    public void sendEvent() throws Exception {
        log.debug("Start send event...");

        Random rnd = new Random(DateTime.now().getMillis());

        for(int i=0; i <100; i++) {
            OrderEvent event = new OrderEvent("shirt", rnd.nextInt(100));
            provider.getEPRuntime().sendEvent(event);
        }

        log.debug("End send event...");

        Thread.sleep(1000);
    }
}
