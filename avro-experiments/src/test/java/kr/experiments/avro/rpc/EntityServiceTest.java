package kr.experiments.avro.rpc;

import kr.experiments.avro.rpc.impl.DummyEntityService;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.ipc.CallFuture;
import org.apache.avro.ipc.NettyServer;
import org.apache.avro.ipc.NettyTransceiver;
import org.apache.avro.ipc.specific.SpecificRequestor;
import org.apache.avro.ipc.specific.SpecificResponder;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.execution.ExecutionHandler;
import org.jboss.netty.handler.execution.OrderedMemoryAwareThreadPoolExecutor;
import org.junit.*;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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

        ExecutorService es = Executors.newCachedThreadPool();
        OrderedMemoryAwareThreadPoolExecutor executor =
            new OrderedMemoryAwareThreadPoolExecutor(8, 0, 0);
        ExecutionHandler executionHandler = new ExecutionHandler(executor);

        log.info("EntityServer용 Server를 생성합니다... host=[{}], port=[{}]", ENTITY_SERVICE_HOST, ENTITY_SERVICE_PORT);
        entityServer = new NettyServer(new SpecificResponder(EntityService.class, new DummyEntityService()),
                                       new InetSocketAddress(ENTITY_SERVICE_HOST, ENTITY_SERVICE_PORT),
                                       new NioServerSocketChannelFactory(es, es),
                                       executionHandler);

        entityServer.start();
        log.info("EntityService용 Server를 시작했습니다.");
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

        asyncEntityClient.ping();
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
    public void syncTest() throws Exception {
        int count = 10;
        for (int i = 0; i < count; i++) {
            log.debug("request clearAll...");
            asyncEntityClient.clearAll();
        }
    }

    // HINT: 하나의 Connection에서는 비동기 방식으로 요청을 할 수는 있지만, 서버애서는 커넥션당 하나의 인스턴스를 할당하므로, 순차적으로 결과를 수행한다.
    // HINT: 여라작업을 수행하려면 Connection을 여러개 만드는 수 밖에 없다.
    // NOTE: 이렇게 비동기 방식으로 결과 얻기 전에 반복 호출하게 되면 중복호출이라는 예외가 발생한다.
    // NOTE: 우리가 보통 아는 비동기 호출과는 달리 connection 하나당 응답이 필요없는 단순 요청은 여러 개를 할 수 있으나,
    // NOTE: 결과를 받아야 하는 경우는 꼭 동기 방식과 다를 바 없다.
    @Test
    public void asyncTest() throws Exception {

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

    @Test
    public void parallelTest() throws Exception {

        ExecutorService es = Executors.newCachedThreadPool();

        Callable<CallFuture<Void>> callable = new Callable<CallFuture<Void>>() {
            @Override
            public CallFuture<Void> call() {
                NettyTransceiver transceiver = null;
                CallFuture<Void> future = new CallFuture<Void>();
                try {
                    transceiver = new NettyTransceiver(new InetSocketAddress(ENTITY_SERVICE_HOST, ENTITY_SERVICE_PORT));
                    EntityService.Callback client = (EntityService.Callback) SpecificRequestor.getClient(EntityService.Callback.class, transceiver);
                    client.clearAll(future);
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                return future;
            }
        };
        int count = 10;
        List<Future> futures = new ArrayList<Future>();

        for (int i = 0; i < count; i++) {
            Future future = es.submit(callable);
            futures.add(future);
        }

        log.debug("Call server by asynchronous... with [{}] counts", count);

        for (Future future : futures) {
            log.debug("get....");
            future.get();
        }
    }
}
