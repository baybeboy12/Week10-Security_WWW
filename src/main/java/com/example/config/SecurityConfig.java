package com.example.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder();
    }
//
//@Autowired
//public void  globalConfig(AuthenticationManagerBuilder authenticationManagerBuilder,PasswordEncoder encoder) throws  Exception{
//    authenticationManagerBuilder.inMemoryAuthentication()
//            .withUser(User.withUsername("admin")
//                    .password(encoder.encode("admin"))
//                    .roles("ADMIN")
//                    .build())
//            .withUser(User.withUsername("Dung")
//                    .password(encoder.encode("Dung"))
//                    .roles("DUNG")
//                    .build())
//            .withUser(User.withUsername("Teo")
//                    .password(encoder.encode("Teo"))
//                    .roles("USER")
//                    .build())
//    ;
//}
@Autowired
public void  globalConfig(AuthenticationManagerBuilder authenticationManagerBuilder, PasswordEncoder encoder, DataSource dataSource) throws  Exception{
    authenticationManagerBuilder.jdbcAuthentication()
            .dataSource(dataSource)
            .withDefaultSchema()
            .withUser(User.withUsername("admin")
                    .password(encoder.encode("admin"))
                    .roles("ADMIN")
                    .build())
            .withUser(User.withUsername("user")
                    .password(encoder.encode("Teo"))
                    .roles("USER")
                    .build())
    ;
}
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws  Exception{
        http.authorizeHttpRequests(
                auth -> auth

                        .requestMatchers("/","/home","/index").permitAll()
                        .requestMatchers("/api/**").hasAnyRole("ADMIN","USER","DUNG")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
        );
        http.csrf().ignoringRequestMatchers("/h2-console/**")
                .and()
                .headers().frameOptions().sameOrigin();

        http.httpBasic(Customizer.withDefaults());
        return  http.build();
    }

}
