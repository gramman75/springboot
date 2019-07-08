package com.gramman75;

import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.CacheControl;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.gramman75.formatter.PersonFormatter;
import com.gramman75.interceptor.AnotherInterceptor;
import com.gramman75.interceptor.GreetingInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	/*
	 * Spring Web MVC를 사용할 경우 Config에서 Formatter를 등록해 줘야 함.
	 * Spring Boot에서는 Formatter를 Bean(@Component)으로 등록 하면 자동 Formmatter로 등록 됨.
	 */
	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addFormatter(new PersonFormatter());
	}
	
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new GreetingInterceptor()).order(0);
		registry.addInterceptor(new AnotherInterceptor()).order(-1).addPathPatterns("/hi");
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/mobile/**")
		        .addResourceLocations("classpath:/mobile/");
//		        .setCacheControl(CacheControl.maxAge(10, TimeUnit.MINUTES));
	}

	@Bean
	public Jaxb2Marshaller jaxb2Marshaller() {
		Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
		jaxb2Marshaller.setPackagesToScan(Person.class.getPackageName());
		return jaxb2Marshaller;
	}
	

}
