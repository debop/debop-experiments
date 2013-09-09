package kr.experiments.thrift.rpc;

import kr.experiments.thrift.rpc.impl.ArithmeticServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TTransportException;

import java.net.InetSocketAddress;

/**
 * kr.experiments.thrift.rpc.NonblockingArithmeticServer
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 8. 21. 오후 2:21
 */
@Slf4j
public class NonblockingArithmeticServer {

    TServer server;

    public void start() {
        try {
            TNonblockingServerSocket serverTransport = new TNonblockingServerSocket(new InetSocketAddress("localhost", 7911));
            ArithmeticService.Processor processor = new ArithmeticService.Processor(new ArithmeticServiceImpl());
            server = new TNonblockingServer(new TNonblockingServer.Args(serverTransport).processor(processor));
            log.info("Starting server on 7911...");
            server.serve();

        } catch (TTransportException e) {
            log.error("서버 구동시 예외가 발생했습니다...", e);
        }
    }


    public static void main(String[] args) {
        NonblockingArithmeticServer srv = new NonblockingArithmeticServer();
        srv.start();
    }
}
