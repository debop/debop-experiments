package kr.debop.spring.tests.akka.provider;

import kr.debop.spring.tests.SpringConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Provider;

import static org.fest.assertions.Assertions.assertThat;

/**
 * ProviderTest
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 6. 24. 오후 7:02
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { SpringConfiguration.class })
public class ProviderTest {

    @Autowired
    SingletonBean singletonBeanA;
    @Autowired
    SingletonBean singletonBeanB;

    @Test
    public void singletonBean() {
        assertThat(singletonBeanA).isEqualTo(singletonBeanB);
    }

    @Test
    public void providerBean() {
        PrototypeBean prototypeBeanA = singletonBeanA.getPrototypeBean();
        PrototypeBean prototypeBeanB = singletonBeanB.getPrototypeBean();

        assertThat(prototypeBeanA).isNotEqualTo(prototypeBeanB);
    }
}

@Service
class SingletonBean {

    @Autowired Provider<PrototypeBean> prototypeBeanProvider;

    PrototypeBean getPrototypeBean() {
        return prototypeBeanProvider.get();
    }
}

@Service
@Scope("prototype")
class PrototypeBean {

}
