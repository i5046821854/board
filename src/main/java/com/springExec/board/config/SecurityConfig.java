package com.springExec.board.config;

import com.springExec.board.dto.UserAccountDto;
import com.springExec.board.repository.UserAccountRepository;
import com.springExec.board.security.BoardPrincipal;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .mvcMatchers(
                        HttpMethod.GET,"/", "/articles", "/articles/search-hashtag"
                ).permitAll()
                        .anyRequest().authenticated())  //이외의 요청들은 인증 요
                .formLogin()
                .and()
                .logout() //로그아웃 페이지
                .logoutSuccessUrl("/")
                .and()
                .build();
    }

//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer(){
//        return ((web) -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations()));
//    }

    @Bean
    public UserDetailsService userDetailsService(UserAccountRepository userAccountRepository)
    {
        //loadUserByUsername에 해당하는 코드
        return username -> userAccountRepository
                .findById(username)
                .map(UserAccountDto::from)
                .map(BoardPrincipal::from)
                .orElseThrow(()-> new UsernameNotFoundException("유저를 찾을 수 없습니다"));
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
