package net.texala.employee.common;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class MyAppSecurityConfig extends WebSecurityConfigurerAdapter {

   @Autowired
   private DataSource dataSource;

   @Override
   protected void configure(AuthenticationManagerBuilder auth) throws Exception {
       PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

       auth.jdbcAuthentication()
           .dataSource(dataSource)
           .withDefaultSchema() 
           .withUser("yuva")
           .password(encoder.encode("yuva"))
           .roles("USER")
           .and()
           .withUser("admin")
           .password(encoder.encode("admin"))
           .roles("ADMIN")
           .and()
           .withUser("superadmin")
           .password(encoder.encode("superadmin"))
           .roles("SUPERADMIN");
   }

   @Override
   protected void configure(HttpSecurity http) throws Exception {
       http.csrf().disable()
           .authorizeRequests()
           .antMatchers("/emp/**").hasAnyRole("ADMIN", "USER")  // admin and user can access this Endpoint
           .antMatchers("/add/**").hasAnyRole("ADMIN", "SUPERADMIN", "USER")  // admin, superadmin and user can access this Endpoint
           .antMatchers("/dept/**").hasAnyRole("ADMIN", "SUPERADMIN") // admin and superadmin can access this Endpoint
           .antMatchers("/h2-console/**").permitAll()
           .anyRequest().authenticated()
           .and()
           .formLogin()
           .successHandler((request, response, authentication) -> {
               response.setStatus(HttpStatus.OK.value());
               response.getWriter().write("Logged in successfully");
           })
           .failureHandler((request, response, authentication) -> {
               response.setStatus(HttpStatus.UNAUTHORIZED.value());
               response.getWriter().write("Login failed");
           })
           .and()
           .logout()
           .logoutSuccessHandler((request, response, authentication) -> {
               response.setStatus(HttpStatus.OK.value());
               response.getWriter().write("Logout successful");
           })
           .and()
           .headers().frameOptions().sameOrigin();
   }
}
