package com.junhyeok.crud.post;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController // 클래스를 REST API 컨트롤러로 등록
@RequestMapping("/posts") // 컨트롤러가 처리하는 요청 URL의 공통 시작 경로를 지정
public class PostController {
    // 의존성이 변경되는 실수를 방지(변경될 이유가 없음)
    private final PostRepository postRepository; 

    public PostController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @GetMapping
    public List<Post> getPosts() {
        return postRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPost(@PathVariable Long id) {
        return postRepository.findById(id)
            .map(product -> ResponseEntity.ok(product))
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Post createPost(@RequestBody Post post) {
        return postRepository.save(post);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePostTitle(
        @PathVariable Long id,
        @RequestBody Post request
    ) {
        return postRepository.findById(id)
            .map(post -> {
                post.setTitle(request.getTitle());
                post.setContent(request.getContent());

                Post updatedPost = postRepository.save(post);
                return ResponseEntity.ok(updatedPost);
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    // Void를 문법적으로 반드시 써야되는 것은 아님
    // 응답 본문이 없다는 것을 명확히 표현하기 위해 명시
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        if (!postRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        postRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

