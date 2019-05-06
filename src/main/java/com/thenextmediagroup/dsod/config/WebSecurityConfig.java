package com.thenextmediagroup.dsod.config;

import javax.servlet.MultipartConfigElement;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;



@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@PropertySource({ "classpath:log4j.properties"})
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Value("${spring.server.MaxFileSize}")
	private String MaxFileSize;
	@Value("${spring.server.MaxRequestSize}")
	private String MaxRequestSize;
    
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
   

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        // @formatter:off
		http
		.authorizeRequests()
			// swagger start
	        .antMatchers("/swagger-ui.html").permitAll()
	        .antMatchers("/swagger-resources/**").permitAll()
	        .antMatchers("/images/**").permitAll()
	        .antMatchers("/webjars/**").permitAll()
	        .antMatchers("/v2/api-docs").permitAll()
	        .antMatchers("/configuration/ui").permitAll()
	        .antMatchers("/configuration/security").permitAll()
	        // swagger end
	        .antMatchers("/v1/content/**").permitAll()
//	        .antMatchers("/v1/category/**").permitAll()
	        .antMatchers("/v1/file/downloadFileByObjectId**").permitAll()
	        .antMatchers("/v1/content/public/trending").permitAll()
			.antMatchers("/v1/content/findAllByValue").permitAll()
	        .antMatchers("/v1/content/public/findAllByValue").permitAll()
	        .antMatchers("/v1/content/public/findOneContents").permitAll()
	        .antMatchers("/v1/content/public/findAllContents").permitAll()
	        .antMatchers("/v1/content/public/findAllBySearch").permitAll()
	        .antMatchers("/v1/author/getAll").permitAll()
	        .antMatchers("/v1/author/findOneById").permitAll()
	        .antMatchers("/v1/category/findAllCategory").permitAll()
	        .antMatchers("/v1/category/findAllContentType").permitAll()
	        .antMatchers("/v1/comment/findAllByContent").permitAll()
	        .antMatchers("/v1/content/visualEssay/findOneById").permitAll()
	        .antMatchers("/v1/category/findAllCatogoryBySponsor").permitAll()
	        .antMatchers("/v1/sponsor/getAll").permitAll()
		    .anyRequest().authenticated()
		.and()
		.exceptionHandling()
		.authenticationEntryPoint(new LoginUrlAuthenticationEntryPointExtention("/login"))
		.and().csrf().disable();
		// @formatter:on
    }
    
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(MaxFileSize); // KB,MB
        factory.setMaxRequestSize(MaxRequestSize);
        return factory.createMultipartConfig();
    }
    
    @Bean
    public HttpFirewall defaultHttpFirewall() {
        return new DefaultHttpFirewall();
    }

}
