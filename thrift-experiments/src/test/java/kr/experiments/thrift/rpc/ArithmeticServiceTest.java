package kr.experiments.thrift.rpc;

import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.async.TAsyncClientManager;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingSocket;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransportException;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

/**
 * kr.experiments.thrift.rpc.ArithmeticServiceTest
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 8. 21. 오후 1:58
 */
@Slf4j
public class ArithmeticServiceTest {

    @Test
    public void syncClient() throws TException {
        log.debug("client 생성 시작...");

        TSocket transport = new TSocket("localhost", 7911);
        TProtocol protocol = new TBinaryProtocol(transport);
        ArithmeticService.Client client = new ArithmeticService.Client(protocol);
        try {
            log.debug("cleint trasport open...");
            transport.open();
        } catch (TTransportException e) {
            log.error("서버 접속에 예외가 발생했습니다.", e);
        }

        assertThat(client.add(1, 1)).isEqualTo(2);
        assertThat(client.add(1, 3)).isEqualTo(4);
        assertThat(client.multiply(5, 5)).isEqualTo(25);
        assertThat(client.multiply(0, 5)).isEqualTo(0);

        transport.close();
    }

    @Test
    public void nonblockingClient() throws TException {
        log.debug("client 생성 시작...");

        TSocket socket = new TSocket("localhost", 7911);
        TFramedTransport transport = new TFramedTransport(socket);
        TProtocol protocol = new TBinaryProtocol(transport);
        ArithmeticService.Client client = new ArithmeticService.Client(protocol);
        try {
            log.debug("cleint trasport open...");
            transport.open();
        } catch (TTransportException e) {
            log.error("서버 접속에 예외가 발생했습니다.", e);
        }

        assertThat(client.add(1, 1)).isEqualTo(2);
        assertThat(client.add(1, 3)).isEqualTo(4);
        assertThat(client.multiply(5, 5)).isEqualTo(25);
        assertThat(client.multiply(0, 5)).isEqualTo(0);

        transport.close();
    }

    @Test
    public void asyncClient() throws Exception {
        log.debug("client 생성 시작...");

        ArithmeticService.AsyncClient client =
            new ArithmeticService.AsyncClient(new TBinaryProtocol.Factory(),
                                              new TAsyncClientManager(),
                                              new TNonblockingSocket("localhost", 7911));

        // NOTE: 이렇게 비동기 방식으로 결과 얻기 전에 반복 호출하게 되면 중복호출이라는 예외가 발생한다.
        // NOTE: 우리가 보통 아는 비동기 호출과는 달리 connection 하나당 응답이 필요없는 단순 요청은 여러 개를 할 수 있으나,
        // NOTE: 결과를 받아야 하는 경우는 꼭 동기 방식과 다를 바 없다.
        // for (int i = 0; i < 10; i++) {
        client.add(1, 1, new AsyncMethodCallback<ArithmeticService.AsyncClient.add_call>() {
            @Override
            public void onComplete(ArithmeticService.AsyncClient.add_call add_call) {
                try {
                    assertThat(add_call.getResult()).isEqualTo(2);
                    log.debug("added = [{}]", add_call.getResult());
                } catch (TException ignored) {}
            }

            @Override
            public void onError(Exception e) {}
        });
        // }
        Thread.sleep(1100);
    }
}
