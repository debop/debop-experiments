package kr.debop.esper.happyevent;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;
import lombok.extern.slf4j.Slf4j;

/**
 * kr.debop.esper.happyevent.HappyEventListener
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 6. 18. 오후 2:47
 */
@Slf4j
public class HappyEventListener implements UpdateListener {

    @Override
    public void update(EventBean[] newEvents, EventBean[] oldEvents) {
        if (newEvents == null || newEvents.length == 0)
            return;

        try {
            EventBean event = newEvents[0];
            log.debug("executed the count, actual=[{}]", event.get("sum(ctr)"));
        } catch (Exception e) {
            log.error("예외가 발생했습니다.", e);
            throw new RuntimeException(e);
        }
    }

}
