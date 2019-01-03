package com.cms.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.cms.service.JwtService;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@PropertySource("classpath:db.properties")
@EnableWebMvc
@EnableSwagger2
@ComponentScan(AppConfig.PACKAGE)
public class AppConfig implements WebMvcConfigurer {
	public final static String PACKAGE = "com.cms";

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any()).build();
	}

//	@Bean
//	SecurityConfiguration security() {
//		return new SecurityConfiguration(null, null, null, null, "Bearer " + jwtService.generateJWT("SWAGGER", new Long(1), 0), ApiKeyVehicle.HEADER, "Authorization", ",");
//	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");

		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins("*");
	}

	@Autowired
	private Environment env;

	@Autowired
	private JwtService jwtService;

//	@Bean
//	public DataSource getDataSource() {
//		BasicDataSource dataSource = new BasicDataSource();
//		dataSource.setDriverClassName(env.getProperty("db.driver"));
//		dataSource.setUrl(env.getProperty("db.url"));
//		dataSource.setUsername(env.getProperty("db.username"));
//		dataSource.setPassword(env.getProperty("db.password"));
//		return dataSource;
//	}

	@Bean
	public JavaMailSender getMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

		// Using gmail
		mailSender.setHost(env.getProperty("smtp.mail.host"));
		mailSender.setPort(Integer.parseInt(env.getProperty("smtp.mail.port")));
		/*
		 * mailSender.setUsername("Your-gmail-id");
		 * mailSender.setPassword("Your-gmail-password");
		 */
		Properties props = new Properties();
		mailSender.setJavaMailProperties(props);
		props.put("mail.smtp.auth", "false");

		return mailSender;
	}
}