package kr.debop.esper.orders;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;
import lombok.extern.slf4j.Slf4j;

/**
 * kr.debop.esper.orders.MyListener
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 6. 17. 오후 9:52
 */
@Slf4j
public class MyListener implements UpdateListener {

    @Override
    public void update(EventBean[] eventBeans, EventBean[] eventBeans2) {
        EventBean eventBean = eventBeans[0];

        if (log.isTraceEnabled())
            log.trace("arg=[{}]", eventBean.get("avg(price)"));
    }
}
