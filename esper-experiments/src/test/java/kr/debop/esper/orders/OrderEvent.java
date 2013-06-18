package kr.debop.esper.orders;

import lombok.Getter;

/**
 * kr.debop.esper.orders.OrderEvent
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 6. 17. 오후 9:56
 */
public class OrderEvent {

    @Getter private final String itemName;

    @Getter private final double price;

    public OrderEvent(String itemName, double price) {
        this.itemName = itemName;
        this.price = price;
    }
}
