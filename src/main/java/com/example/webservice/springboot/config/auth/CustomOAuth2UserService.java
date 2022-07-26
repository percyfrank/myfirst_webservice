package com.example.webservice.springboot.config.auth;

import com.example.webservice.springboot.config.auth.dto.OAuthAttributes;
import com.example.webservice.springboot.config.auth.dto.SessionUser;
import com.example.webservice.springboot.domain.user.User;
import com.example.webservice.springboot.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        /**
         * 현재 로그인 진행 중인 서비스 구분 코드
         * Ex. 네이버 로그인 or 구글 로그인 구분
         */
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        /**
         * OAuth2 로그인 진행 시 키가 되는 필드값
         * 구글 기본 코드 sub 지원, 네이버/카카오 미지원
         * 동시 지원을 위해 사용
         */
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();

        /**
         *  OAuth2User의 attribute를 담을 클래스
         *  다른 소셜 로그인도 사용
         */
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        User user = saveOrUpdate(attributes);

        /**
         * 세션에 사용자 정보를 저장하기 위한 Dto 클래스
         */
        httpSession.setAttribute("user", new SessionUser(user));

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }

    /**
     * 구글 사용자 정보 업데이트 기능
     * 이름, 프로필 사진 변경시 User 엔티티에 반영
     */
    private User saveOrUpdate(OAuthAttributes attributes) {
        User user = userRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
                .orElse(attributes.toEntity());

        return userRepository.save(user);
    }
}
