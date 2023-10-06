package HailYoungHan.Board.controller;

import HailYoungHan.Board.dto.PostDTO;
import HailYoungHan.Board.dto.PostRegiDTO;
import HailYoungHan.Board.dto.PostResponseDTO;
import HailYoungHan.Board.dto.PostUpdateDTO;
import HailYoungHan.Board.entity.Post;
import HailYoungHan.Board.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
     * 특정 게시글 수정
     * @param post_id
     * @param postUpdateDTO
     * @return
     */
    @PutMapping("/{post_id}")
    public ResponseEntity<Long> update(@PathVariable Long post_id, @RequestBody PostUpdateDTO postUpdateDTO) {

        Long updatedId = postService.updatePost(post_id, postUpdateDTO);

        return new ResponseEntity<>(updatedId, HttpStatus.ACCEPTED);
    }

    /**
     * 특정 게시글 조회
     * @param post_id
     * @return PostDTO
     */
    @GetMapping("/{post_id}")
    public ResponseEntity<PostDTO> getSinglePost(@PathVariable Long post_id) {
        PostDTO res = postService.getSinglePost(post_id);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    /**
     * 전체 게시글 조회
     * @return PostResponseDTO
     */
    @GetMapping
    public ResponseEntity<PostResponseDTO> getAllPosts() {
        List<PostDTO> allPosts = postService.getAllPosts();
        PostResponseDTO res = new PostResponseDTO(allPosts);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    /**
     *
     * @param memberId
     * @return PostResponseDTO
     */
    @GetMapping("/member/{memberId}")
    public ResponseEntity<PostResponseDTO> getPostsByMemberId(@PathVariable Long memberId) {
        List<PostDTO> memberPosts = postService.findPostsByMemberId(memberId);
        PostResponseDTO result = new PostResponseDTO(memberPosts);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
