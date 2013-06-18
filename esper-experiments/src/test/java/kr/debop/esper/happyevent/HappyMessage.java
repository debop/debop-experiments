package kr.debop.esper.happyevent;

import kr.debop4j.core.ValueObjectBase;
import lombok.Getter;
import lombok.Setter;

/**
 * kr.debop.esper.happyevent.HappyMessage
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 6. 18. 오후 2:54
 */
public class HappyMessage extends ValueObjectBase {

    @Getter
    @Setter
    private String user;

    @Getter
    private final int ctr = 1;

    private static final long serialVersionUID = 7457432896327566100L;
}
