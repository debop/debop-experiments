package kr.experiments.jersey.helloworld;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * kr.experiments.jersey.helloworld.HelloWorldResource
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 8. 11. 오전 12:29
 */
@Path("helloworld")
public class HelloWorldResource {

    public static final String CLICHED_MESSAGE = "Hello World!";


    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getHello() {
        return CLICHED_MESSAGE;
    }
}
