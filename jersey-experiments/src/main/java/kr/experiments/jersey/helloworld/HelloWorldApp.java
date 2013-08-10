package kr.experiments.jersey.helloworld;

import lombok.extern.slf4j.Slf4j;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;

/**
 * kr.experiments.jersey.helloworld.HelloWorldApp
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 8. 11. 오전 12:27
 */
@Slf4j
public class HelloWorldApp {

    public static final URI BASE_URI = URI.create("http://localhost:8080/base/");
    public static final String ROOT_PATH = "helloworld";

    public static void main(String[] args) {
        try {
            System.out.println("\"Hello World\" Jersey Example HelloWorldApp");

            final ResourceConfig resourceConfig = new ResourceConfig(HelloWorldResource.class);
            final HttpServer server = GrizzlyHttpServerFactory.createHttpServer(BASE_URI, resourceConfig);

            System.out.println(String.format("Application started.\n Try out %s%s\nHit enter to stop it...", BASE_URI, ROOT_PATH));
            System.in.read();
        } catch (IOException ex) {
            HelloWorldApp.log.error("Error", ex);
        }
    }
}
