package com.andrespr.springboot.app;

import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer{

	private final Logger log = LoggerFactory.getLogger(getClass());
	//Para registrar directorio para guardar imagenes
	//FORMA 1 DE SUBIR IMAGENES EN NUESTRO PROYECTO
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		
		WebMvcConfigurer.super.addResourceHandlers(registry);
		
		String reSourcePath = Paths.get("uploads").toAbsolutePath().toUri().toString();
		registry.addResourceHandler("/uploads/**").addResourceLocations("file:/opt/tomcat/webapps/uploads/");
		log.info(reSourcePath);
		//registry.addResourceHandler("/uploads/**").addResourceLocations(reSourcePath);
		
	}
	
	
	//Metodo para crear un ControllerView para los errores
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/errors/error_403").setViewName("/errors/error_403");
		
		
	}
	
	@Bean
	public static BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
}
