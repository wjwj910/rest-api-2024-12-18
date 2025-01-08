package com.ll.rest.domain.post.post.controller;

import com.ll.rest.domain.post.post.entity.Post;
import com.ll.rest.domain.post.post.service.PostService;
import com.ll.rest.global.rsData.RsData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class ApiV1PostController {
    private final PostService postService;

    @GetMapping
    public List<Post> getItems() {
        return postService.findAllByOrderByIdDesc();
    }

    @GetMapping("/{id}")
    public Post getItem(@PathVariable long id) {
        return postService.findById(id).get();
    }

    @DeleteMapping("/{id}")
    public RsData deleteItem(@PathVariable long id) {
        Post post = postService.findById(id).get();
        postService.delete(post);
        
        return new RsData("200-1", "%d번 글을 삭제하였습니다.".formatted(id));
    }

    @AllArgsConstructor
    @Getter
    public static class PostModifyReqBody {
        private String title;
        private String content;
    }

    @PutMapping("/{id}")
    @Transactional
    public RsData modifyItem(@PathVariable long id,
                             @RequestBody PostModifyReqBody reqBody) {
        Post post = postService.findById(id).get();

        postService.modify(post, reqBody.getTitle(), reqBody.getContent());

        return new RsData("200-1", "%d번 글이 수정되었습니다.".formatted(id));
    }
}
