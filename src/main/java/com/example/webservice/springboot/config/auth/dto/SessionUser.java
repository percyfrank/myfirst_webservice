package com.example.webservice.springboot.config.auth.dto;

import com.example.webservice.springboot.domain.user.User;
import lombok.Getter;

import java.io.Serializable;

/**
 * 인증된 사용자 정보만 필요
 * name, email, picture 외의 필드 제외
 */
@Getter
public class SessionUser implements Serializable {
    private String name;
    private String email;
    private String picture;

    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}
