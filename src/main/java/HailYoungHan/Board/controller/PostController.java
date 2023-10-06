package HailYoungHan.Board.controller;

import HailYoungHan.Board.dto.PostDTO;
import HailYoungHan.Board.dto.PostRegiDTO;
import HailYoungHan.Board.entity.Post;
import HailYoungHan.Board.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    /**
     * 게시물 추가 API
     * @param postRegiDTO
     * @return
     */
    @PostMapping
    public ResponseEntity<Post> register(@RequestBody PostRegiDTO postRegiDTO) {
        Post post = postService.registerPost(postRegiDTO);
        return new ResponseEntity<>(post, HttpStatus.CREATED);
    }
}
