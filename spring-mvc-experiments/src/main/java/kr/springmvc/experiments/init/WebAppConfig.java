package kr.springmvc.experiments.init;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import java.util.Locale;

/**
 * kr.springmvc.experiments.init.WebAppConfig
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 7. 26. 오전 11:08
 */
@Configuration
@ComponentScan("kr.springmvc.experiments.controllers")
@EnableAsync
@EnableWebMvc       // Enable to use Spring's annotations in the code
public class WebAppConfig {

    @Bean
    public UrlBasedViewResolver viewResolver() {
        UrlBasedViewResolver resolver = new UrlBasedViewResolver();
        resolver.setPrefix("/WEB-INF/pages");
        resolver.setSuffix(".jsp");
        resolver.setViewClass(JstlView.class);
        return resolver;
    }


    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver lr = new SessionLocaleResolver();
        lr.setDefaultLocale(Locale.KOREAN);
        return lr;
    }
}
