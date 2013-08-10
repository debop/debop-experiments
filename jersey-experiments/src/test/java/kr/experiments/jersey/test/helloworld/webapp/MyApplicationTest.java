package kr.experiments.jersey.test.helloworld.webapp;

import kr.experiments.jersey.helloworld.HelloWorldApp;
import kr.experiments.jersey.helloworld.webapp.MyApplication;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;

import static org.fest.assertions.Assertions.assertThat;

/**
 * kr.experiments.jersey.test.helloworld.webapp.MyApplicationTest
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 8. 11. 오전 2:54
 */
public class MyApplicationTest extends JerseyTest {

    @Override
    protected Application configure() {
        return new MyApplication();
    }

    @Override
    protected URI getBaseUri() {
        return UriBuilder.fromUri(super.getBaseUri()).path("helloworld-webapp").build();
    }

    @Test
    public void testClientStringResponse() {
        String s = target().path(HelloWorldApp.ROOT_PATH).request().get(String.class);
        assertThat(s).isEqualTo("Hello World!");
    }
}
