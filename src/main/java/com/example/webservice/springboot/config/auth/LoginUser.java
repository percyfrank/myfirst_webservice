package com.example.webservice.springboot.config.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 어노테이션 생성될 수 있는 위치 지정
  */
@Target(ElementType.PARAMETER) // 메소드의 파라미터로 선언된 객체에서만 사용 가능
@Retention(RetentionPolicy.RUNTIME)

// 어노테이션 클래스 지정
public @interface LoginUser {
}