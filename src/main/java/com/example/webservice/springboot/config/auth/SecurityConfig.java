package com.example.webservice.springboot.config.auth;

import com.example.webservice.springboot.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity // 스프링 시큐리티 설정들 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                /**
                 * h2-console 화면 사용을 위해 해당 옵션 disable
                  */
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                /**
                 * URL별 권한 관리를 설정하는 옵션의 시작점
                 */
                .authorizeRequests()
                /**
                 * 권한 관리 대상 지정 옵션
                 * permitAll() 전체 열람 권한
                 * */
                .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**", "/profile").permitAll()
                .antMatchers("/api/v1/**").hasRole(Role.USER.name())
                /**
                 * 설정된 값들 이외 나머지 URL
                 */
                .anyRequest().authenticated() // 나머지 URL들은 인증된 사용자(로그인 사용자)에게만 허용
                .and()
                /**
                 * 로그아웃 기능에 대한 여러 설정의 진입점
                 */
                .logout()
                .logoutSuccessUrl("/")
                .and()
                /**
                 * OAuth 2 로그인 기능에 대한 여러 설정의 진입점
                 */
                .oauth2Login()
                /**
                 * 소셜 로그인 성공 시 후속 조치 진행할 구현체 등록
                 */
                .userInfoEndpoint()
                .userService(customOAuth2UserService);
    }
}
