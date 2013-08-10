package kr.experiments.jersey.test.helloworld;

import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.client.ClientResponseContext;
import javax.ws.rs.client.ClientResponseFilter;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import static org.fest.assertions.Assertions.assertThat;

/**
 * kr.experiments.jersey.test.helloworld.CustomLoggingFilter
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 8. 11. 오전 1:03
 */
@Slf4j
public class CustomLoggingFilter implements ContainerRequestFilter, ContainerResponseFilter, ClientRequestFilter, ClientResponseFilter {

    static AtomicInteger preFilterCalled = new AtomicInteger(0);
    static AtomicInteger postFilteredCalled = new AtomicInteger(0);

    @Override
    public void filter(ClientRequestContext requestContext) throws IOException {
        log.trace("preFilter called...");
        assertThat(requestContext.getConfiguration().getProperty("foo")).isEqualTo("bar");
        preFilterCalled.incrementAndGet();
    }

    @Override
    public void filter(ClientRequestContext requestContext, ClientResponseContext responseContext) throws IOException {
        log.trace("postFilter called...");
        assertThat(requestContext.getConfiguration().getProperty("foo")).isEqualTo("bar");
        postFilteredCalled.incrementAndGet();
    }

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        log.trace("preFilter called...");
        assertThat(requestContext.getProperty("foo")).isEqualTo("bar");
        preFilterCalled.incrementAndGet();
    }

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        log.trace("postFilter called...");
        assertThat(requestContext.getProperty("foo")).isEqualTo("bar");
        postFilteredCalled.incrementAndGet();
    }
}
