package kr.experiments.avro.rpc;

import kr.experiments.avro.rpc.impl.DummyEntityService;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.ipc.CallFuture;
import org.apache.avro.ipc.NettyServer;
import org.apache.avro.ipc.NettyTransceiver;
import org.apache.avro.ipc.specific.SpecificRequestor;
import org.apache.avro.ipc.specific.SpecificResponder;
import org.junit.*;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

/**
 * kr.experiments.avro.rpc.EntityServiceTest
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 8. 10. 오후 8:19
 */
@Slf4j
public class EntityServiceTest {

    private static final String ENTITY_SERVICE_HOST = "localhost";
    private static final int ENTITY_SERVICE_PORT = 8899;
    private static NettyServer entityServer;

    private NettyTransceiver transceiver;
    private EntityService.Callback asyncEntityClient;

    @BeforeClass
    public static void beforeClass() {
        log.info("EntityServer용 Server를 생성합니다... host=[{}], port=[{}]", ENTITY_SERVICE_HOST, ENTITY_SERVICE_PORT);
        entityServer = new NettyServer(new SpecificResponder(EntityService.class, new DummyEntityService()),
                                       new InetSocketAddress(ENTITY_SERVICE_HOST, ENTITY_SERVICE_PORT));
        log.info("EntityService용 Server를 생성했습니다.");
    }

    @AfterClass
    public static void afterClass() {
        if (entityServer != null)
            entityServer.close();
    }

    @Before
    public void before() throws Exception {
        log.info("EntityService용 Server에 접속합니다...");
        transceiver = new NettyTransceiver(new InetSocketAddress(ENTITY_SERVICE_HOST, ENTITY_SERVICE_PORT));
        asyncEntityClient = (EntityService.Callback) SpecificRequestor.getClient(EntityService.Callback.class, transceiver);
    }

    @After
    public void after() {
        if (transceiver != null) {
            transceiver.close();
            transceiver = null;
        }
    }

    @Test
    public void setupTest() throws Exception {
        assertThat(asyncEntityClient).isNotNull();

        CallFuture<CharSequence> future = new CallFuture<CharSequence>();
        asyncEntityClient.ping(future);

        assertThat(future.get().toString()).isEqualToIgnoringCase("pong");
        assertThat(asyncEntityClient.ping().toString()).isEqualToIgnoringCase("pong");
    }

    @Test
    public void asyncTest() throws Exception {
        // 비동기 방식으로 메소드를 여러번 호출합니다.
        int count = 10;
        List<CallFuture<Void>> futures = new ArrayList<CallFuture<Void>>(count);
        for (int i = 0; i < count; i++) {
            CallFuture<Void> future = new CallFuture<Void>();
            asyncEntityClient.clearAll(future);
            futures.add(future);
        }

        log.debug("Call server by asynchronous... with [{}] counts", count);

        for (CallFuture<Void> future : futures) {
            log.debug("get....");
            future.get();
        }
    }
}
