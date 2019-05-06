package com.thenextmediagroup.dsod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableTransactionManagement
@PropertySource({ "classpath:log4j.properties"})
public class ContentApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(ContentApplication.class, args);
	}

}
