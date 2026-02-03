package com.andrespr.springboot.app;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.andrespr.springboot.app.handler.LoginSuccessHandler;
import com.andrespr.springboot.app.services.JpaUserDetailsService;

//Clase de configuración de SpringSecurity									//prePostEnable no es necesario, solo otra forma
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true) // Anotacion para que se pueda usar @Secured
																			// en los controladores
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private LoginSuccessHandler sucessHandler;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private JpaUserDetailsService userDetailsService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		// Rutas públicas y autorizadas
		http.authorizeRequests()
				.antMatchers("/", "/css/**", "/js/**", "/img/**","/uploads/**", "/altairAudiovisuales/home", "/worker/create", "/worker/save",
						"/producer/create", "/producer/save")
				.permitAll()
				/*
				 * .antMatchers("/ver/**").hasAnyRole("USER")
				 * .antMatchers("/uploads/**").hasAnyRole("USER")
				 * .antMatchers("/form/**").hasAnyRole("ADMIN")
				 * .antMatchers("/eliminar/**").hasAnyRole("ADMIN")
				 * .antMatchers("/factura/**").hasAnyRole("ADMIN")
				 */
				.anyRequest().authenticated().and().formLogin().successHandler(sucessHandler).loginPage("/login")
				.permitAll().and().logout().permitAll().and().exceptionHandling().accessDeniedPage("/errors/error_403")// si
																														// quieres
																														// tener
																														// este
																														// error
																														// personalizado
																														// debe
																														// de
																														// estar
																														// en
																														// una
																														// carpeta
																														// diferente
																														// a
																														// /error
		;

	}

	@Autowired
	public void configurerGlobal(AuthenticationManagerBuilder builder) throws Exception {
		// Autentificar a los usuarios que vengan de la base de datos
		/*
		 * builder.jdbcAuthentication() .dataSource(dataSource)
		 * .passwordEncoder(passwordEncoder)
		 * .usersByUsernameQuery("select username, password, enabled from users where username=?"
		 * )
		 * .authoritiesByUsernameQuery("select u.username, r.nombre_rol from roles r inner join users u on (r.user_id=u.id) where u.username=?"
		 * );
		 */
		// Método para tener usuario cargamos en memoria
		
		  PasswordEncoder encoder= passwordEncoder; 
		  UserBuilder users = User.builder().passwordEncoder(password -> encoder.encode(password));
		 
		  builder.inMemoryAuthentication()
		  .withUser(users.username("migra1").password("1234").roles("ADMIN"))
		  .withUser(users.username("migra2").password("1234").roles("USER")) ;
		 

		builder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);

	}

}
