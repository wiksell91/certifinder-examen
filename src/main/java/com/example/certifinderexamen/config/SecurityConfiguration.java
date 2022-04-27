package com.example.certifinderexamen.config;

import com.example.certifinderexamen.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    @Autowired
    private JWTTokenHelper jwtTokenHelper;

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;



    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("Virre@gmail.com").password(passwordEncoder().encode("pellelelle"))
                .authorities("USER", "ADMIN");

        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    };

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .csrf().disable()
//                .authorizeRequests()
//                .antMatchers(HttpMethod.OPTIONS,"/**").permitAll()
//                .anyRequest().authenticated();
                //.and()
                //.formLogin().and()
                //.httpBasic();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint).and()
                .authorizeRequests((request) -> request.antMatchers("/api/v1/**").permitAll()
                        .antMatchers(HttpMethod.OPTIONS, "/**").permitAll().anyRequest().authenticated())
                .addFilterBefore(new JWTAuthenticationFilter(userService, jwtTokenHelper),
                        UsernamePasswordAuthenticationFilter.class);

//        http
//                .authorizeRequests()
//                .antMatchers("/**")
//                .permitAll()
//                .and().csrf().disable();
        http.csrf().disable().cors().and().headers().frameOptions().disable();



//        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().exceptionHandling()
  //              .authenticationEntryPoint(authenticationEntryPoint).and()
    //          .authorizeRequests().anyRequest().permitAll();

//                http.authorizeRequests((request) -> request.antMatchers("/api/v1/**").permitAll()
//                        .antMatchers(HttpMethod.OPTIONS, "/**").permitAll().anyRequest().authenticated())
//                .addFilterBefore(new JWTAuthenticationFilter(userService, jwtTokenHelper),
//                        UsernamePasswordAuthenticationFilter.class);

     //  http.csrf().disable().cors().and().headers().frameOptions().disable();

        //http.authorizeRequests().anyRequest().permitAll();
        //http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().exceptionHandling().authenticationEntryPoint(au)
        //http.authorizeRequests().anyRequest().authenticated();
        //http.formLogin();



    }
}
