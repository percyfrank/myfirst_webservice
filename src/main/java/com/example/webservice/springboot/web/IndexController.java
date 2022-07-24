package com.example.webservice.springboot.web;

import com.example.webservice.springboot.config.auth.dto.SessionUser;
import com.example.webservice.springboot.service.posts.PostsService;
import com.example.webservice.springboot.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;
    private final HttpSession httpSession;

    /**
     * 전체 조회 화면 추가
     */
    @GetMapping("/")
    public String index(Model model) { // 서버 템플릿 엔진에서 사용할 수 있는 객체를 저장
        //postsService.findAllDesc()로 가져온 결과를 posts로 index.mustache에 전달
        model.addAttribute("posts", postsService.findAllDesc());

        // 로그인 성공 시 값 가져오기 가능
        SessionUser user = (SessionUser) httpSession.getAttribute("user");

        // 세션에 저장된 값 있으면 model에 userName으로 등록
        // 세션에 저장된 값 없으면 로그인 버튼 노출
        if (user != null) {
            model.addAttribute("userName", user.getName());
        }

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
