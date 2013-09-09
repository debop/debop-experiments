package kr.experiments.avro.rpc;

import kr.experiments.avro.rpc.impl.DummyEntityService;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.ipc.NettyServer;
import org.apache.avro.ipc.NettyTransceiver;
import org.apache.avro.ipc.specific.SpecificResponder;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.execution.ExecutionHandler;
import org.jboss.netty.handler.execution.OrderedMemoryAwareThreadPoolExecutor;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * kr.experiments.avro.rpc.DummyEntityServiceServer
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 8. 21. 오후 12:28
 */
@Slf4j
public class DummyEntityServiceServer {

    private static final String ENTITY_SERVICE_HOST = "localhost";
    private static final int ENTITY_SERVICE_PORT = 8899;
    private static NettyServer entityServer;

    private NettyTransceiver transceiver;
    private EntityService.Callback asyncEntityClient;

    public static void startServer() {
        ExecutorService es = Executors.newCachedThreadPool();
        OrderedMemoryAwareThreadPoolExecutor executor =
            new OrderedMemoryAwareThreadPoolExecutor(Runtime.getRuntime().availableProcessors(), 0, 0);
        ExecutionHandler executionHandler = new ExecutionHandler(executor);

        log.info("EntityServer용 Server를 생성합니다... host=[{}], port=[{}]", ENTITY_SERVICE_HOST, ENTITY_SERVICE_PORT);
        entityServer = new NettyServer(new SpecificResponder(EntityService.Callback.class, new DummyEntityService()),
                                       new InetSocketAddress(ENTITY_SERVICE_HOST, ENTITY_SERVICE_PORT),
                                       new NioServerSocketChannelFactory(es, es),
                                       executionHandler);
        entityServer.start();
    }

    public static void main(String[] args) {
        startServer();
    }
}
