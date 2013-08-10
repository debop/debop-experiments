package kr.experiments.jersey.helloworld.webapp;

import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

/**
 * ASM 버전을 맞춰야 합니다.
 * 참고: http://stackoverflow.com/questions/12166382/java-lang-incompatibleclasschangeerror-implementing-class-deploying-to-app-engi
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 8. 11. 오전 2:09
 */
@ApplicationPath("/")
public class MyApplication extends ResourceConfig {

    public MyApplication() {
        packages(true, "kr.experiments.jersey.helloworld");
    }

//    @Override
//    public Set<Class<?>> getClasses() {
//        final Set<Class<?>> classes = new HashSet<Class<?>>();
//
//        // register root resource
//        classes.add(HelloWorldResource.class);
//        return classes;
//    }

}
