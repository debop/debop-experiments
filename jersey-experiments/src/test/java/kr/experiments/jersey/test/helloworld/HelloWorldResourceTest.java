package kr.experiments.jersey.test.helloworld;

import kr.experiments.jersey.helloworld.HelloWorldApp;
import kr.experiments.jersey.helloworld.HelloWorldResource;
import lombok.extern.slf4j.Slf4j;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.InvocationCallback;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.fest.assertions.Assertions.assertThat;

/**
 * kr.experiments.jersey.test.helloworld.HelloWorldResourceTest
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 8. 11. 오전 1:02
 */
@Slf4j
public class HelloWorldResourceTest extends JerseyTest {

    @Override
    protected Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        return new ResourceConfig(HelloWorldResource.class);
    }

    @Test
    public void testConnection() {
        Response response = target().path(HelloWorldApp.ROOT_PATH).request("text/plain").get();
        assertThat(response.getStatus()).isEqualTo(200);
    }

    @Test
    public void testClientStringResponse() {
        String s = target().path(HelloWorldApp.ROOT_PATH).request().get(String.class);
        assertThat(s).isEqualTo(HelloWorldResource.CLICHED_MESSAGE);
    }

    @Test
    public void testAsyncClientRequests() throws Exception {
        final int REQUESTS = 10;
        final CountDownLatch latch = new CountDownLatch(REQUESTS);
        final long tic = System.currentTimeMillis();

        for (int i = 0; i < REQUESTS; i++) {
            final int id = i;
            target().path(HelloWorldApp.ROOT_PATH).request().async().get(new InvocationCallback<Response>() {
                @Override
                public void completed(Response response) {
                    try {
                        final String result = response.readEntity(String.class);
                        assertThat(result).isEqualTo(HelloWorldResource.CLICHED_MESSAGE);
                    } finally {
                        latch.countDown();
                    }
                }

                @Override
                public void failed(Throwable error) {
                    error.printStackTrace();
                    latch.countDown();
                }
            });
        }

        latch.await(10, TimeUnit.SECONDS);
        final long toc = System.currentTimeMillis();
        log.debug("Executed in: [{}] ms", toc - tic);
    }

    @Test
    public void testHead() {
        Response response = target().path(HelloWorldApp.ROOT_PATH).request().head();
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getMediaType()).isEqualTo(MediaType.TEXT_PLAIN_TYPE);
    }

    @Test
    public void testFooBarOptions() {
        Response response = target().path(HelloWorldApp.ROOT_PATH).request().header("Accept", "foo/bar").options();
        assertThat(response.getStatus()).isEqualTo(200);
        _checkAllowContent(response.getHeaderString("Allow"));
        assertThat(response.getMediaType().toString()).isEqualTo("foo/bar");
        assertThat(response.getLength()).isEqualTo(0);
    }

    @Test
    public void testTextPlainOptions() {
        Response response = target().path(HelloWorldApp.ROOT_PATH).request().header("Accept", MediaType.TEXT_PLAIN).options();
        assertThat(response.getStatus()).isEqualTo(200);
        _checkAllowContent(response.getHeaderString("Allow"));
        assertThat(response.getMediaType().toString()).isEqualTo(MediaType.TEXT_PLAIN);
        _checkAllowContent(response.readEntity(String.class));
    }

    private void _checkAllowContent(final String content) {
        log.trace("content=[{}]", content);
        assertThat(content.contains("GET")).isTrue();
        assertThat(content.contains("HEAD")).isTrue();
        assertThat(content.contains("OPTIONS")).isTrue();
    }

    @Test
    public void testMissingResourceNotFound() {
        Response response = target().path(HelloWorldApp.ROOT_PATH + "arbitrary").request().get();
        assertThat(response.getStatus()).isEqualTo(404);

        response = target().path(HelloWorldApp.ROOT_PATH).path("arbitrary").request().get();
        assertThat(response.getStatus()).isEqualTo(404);
    }

    @Test
    public void testLoggingFilterClientClass() {
        Client client = client();
        client.register(CustomLoggingFilter.class).property("foo", "bar");
        CustomLoggingFilter.preFilterCalled.set(0);
        CustomLoggingFilter.postFilteredCalled.set(0);

        String s = target().path(HelloWorldApp.ROOT_PATH).request().get(String.class);
        assertThat(s).isEqualTo(HelloWorldResource.CLICHED_MESSAGE);
        assertThat(CustomLoggingFilter.preFilterCalled.get()).isEqualTo(1);
        assertThat(CustomLoggingFilter.postFilteredCalled.get()).isEqualTo(1);
    }

    @Test
    public void testLoggingFilterTargetClass() {
        WebTarget target = target().path(HelloWorldApp.ROOT_PATH);
        target.register(CustomLoggingFilter.class).property("foo", "bar");
        CustomLoggingFilter.preFilterCalled.set(0);
        CustomLoggingFilter.postFilteredCalled.set(0);

        String s = target.request().get(String.class);
        assertThat(s).isEqualTo(HelloWorldResource.CLICHED_MESSAGE);
        assertThat(CustomLoggingFilter.preFilterCalled.get()).isEqualTo(1);
        assertThat(CustomLoggingFilter.postFilteredCalled.get()).isEqualTo(1);
    }

    @Test
    public void testLoggingFilterTargetInstance() {
        WebTarget target = target().path(HelloWorldApp.ROOT_PATH);
        target.register(new CustomLoggingFilter()).property("foo", "bar");
        CustomLoggingFilter.preFilterCalled.set(0);
        CustomLoggingFilter.postFilteredCalled.set(0);

        String s = target.request().get(String.class);
        assertThat(s).isEqualTo(HelloWorldResource.CLICHED_MESSAGE);
        assertThat(CustomLoggingFilter.preFilterCalled.get()).isEqualTo(1);
        assertThat(CustomLoggingFilter.postFilteredCalled.get()).isEqualTo(1);
    }

    @Test
    public void testConfigurationUpdate() {
        Client client1 = client();
        client1.register(CustomLoggingFilter.class).property("foo", "bar");

        Client client = ClientBuilder.newClient(client1.getConfiguration());
        CustomLoggingFilter.preFilterCalled.set(0);
        CustomLoggingFilter.postFilteredCalled.set(0);
        String s = target().path(HelloWorldApp.ROOT_PATH).request().get(String.class);
        assertThat(s).isEqualTo(HelloWorldResource.CLICHED_MESSAGE);
        assertThat(CustomLoggingFilter.preFilterCalled.get()).isEqualTo(1);
        assertThat(CustomLoggingFilter.postFilteredCalled.get()).isEqualTo(1);
    }
}
