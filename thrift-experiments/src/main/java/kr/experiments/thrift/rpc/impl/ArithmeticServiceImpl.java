package kr.experiments.thrift.rpc.impl;

import kr.experiments.thrift.rpc.ArithmeticService;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TException;

/**
 * kr.experiments.thrift.rpc.impl.ArithmeticServiceImpl
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 8. 21. 오후 1:51
 */
@Slf4j
public class ArithmeticServiceImpl implements ArithmeticService.Iface {

    @Override
    public long add(int x, int y) throws TException {
        log.debug("add [{}] + [{}]", x, y);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return x + y;
    }

    @Override
    public long multiply(int x, int y) throws TException {
        log.debug("multiply [{}] * [{}]", x, y);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return x * y;
    }
}
