package com.example.webservice.springboot.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.xml.transform.sax.SAXResult;

@Getter
@NoArgsConstructor
public class PostsUpdateRequestDto {

    private String title;
    private String content;

    @Builder
    public PostsUpdateRequestDto(String title, String content) {
        this.title = title;
        this.content = content;
    }


}
