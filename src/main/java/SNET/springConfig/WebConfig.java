package SNET.springConfig;

import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
		registry.addViewController("/login").setViewName("/user/login");
        registry.addViewController("/access-denied").setViewName("access-denied");
	}
	
	@Bean
	public LocaleResolver localeResolver() {
	    SessionLocaleResolver localeResolver = new SessionLocaleResolver();
	    localeResolver.setDefaultLocale(Locale.ENGLISH);
	    return localeResolver;
	}		
}
