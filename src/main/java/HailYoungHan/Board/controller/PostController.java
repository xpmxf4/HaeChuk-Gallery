package HailYoungHan.Board.controller;

import HailYoungHan.Board.dto.PostDTO;
import HailYoungHan.Board.dto.PostRegiDTO;
import HailYoungHan.Board.dto.PostUpdateDTO;
import HailYoungHan.Board.entity.Post;
import HailYoungHan.Board.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    /**
     * 게시물 추가 API
     *
     * @param postRegiDTO
     * @return
     */
    @PostMapping
    public ResponseEntity<Post> register(@RequestBody PostRegiDTO postRegiDTO) {
        Post post = postService.registerPost(postRegiDTO);
        return new ResponseEntity<>(post, HttpStatus.CREATED);
    }

    /**
     * post_id 글 제목or내용 수정
     *
     * @param post_id
     * @param postUpdateDTO
     * @return
     */
    @PutMapping("/{post_id}")
    public ResponseEntity<Long> update(@PathVariable Long post_id, @RequestBody PostUpdateDTO postUpdateDTO) {

        Long updatedId = postService.updatePost(post_id, postUpdateDTO);

        return new ResponseEntity<>(updatedId, HttpStatus.ACCEPTED);
    }

}
