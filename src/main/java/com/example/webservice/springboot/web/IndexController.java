package com.example.webservice.springboot.web;

import com.example.webservice.springboot.service.posts.PostsService;
import com.example.webservice.springboot.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;

    /**
     * 전체 조회 화면 추가
     */
    @GetMapping("/")
    public String index(Model model) { // 서버 템플릿 엔진에서 사용할 수 있는 객체를 저장
        //postsService.findAllDesc()로 가져온 결과를 posts로 index.mustache에 전달
        model.addAttribute("posts", postsService.findAllDesc());
        return "index";
    }

    /**
     * 게시글 등록 화면
     */
    @GetMapping("/posts/save")
    public String postSave() {

        return "post-save";
    }

    /**
     * 게시글 수정 화면
     */
    @GetMapping("/posts/update/{id}")
    public String postUpdate(@PathVariable Long id, Model model) {

        PostsResponseDto dto = postsService.findById(id);
        // postsService.findById(id)로 가져온 결과를 post로 posts-update에 전달
        model.addAttribute("post", dto);

        return "posts-update";
    }
}
