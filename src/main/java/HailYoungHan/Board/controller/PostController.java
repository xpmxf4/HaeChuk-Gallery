package HailYoungHan.Board.controller;

import HailYoungHan.Board.dto.post.PostDTO;
import HailYoungHan.Board.dto.post.PostRegiDTO;
import HailYoungHan.Board.dto.post.PostResponseDTO;
import HailYoungHan.Board.dto.post.PostUpdateDTO;
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
     * 요청받은 데이터를 통해 게시물을 생성합니다.
     *
     * @param postRegiDTO 게시물 생성에 필요한 데이터
     * @return 생성된 게시물 정보와 상태 코드
     */
    @PostMapping
    public ResponseEntity<Post> register(@RequestBody PostRegiDTO postRegiDTO) {
        Post post = postService.registerPost(postRegiDTO);
        return new ResponseEntity<>(post, HttpStatus.CREATED);
    }

    /**
     * 특정 게시글 수정 API
     * 주어진 ID의 게시물을 수정합니다.
     *
     * @param post_id 게시물 ID
     * @param postUpdateDTO 수정할 게시물의 정보
     * @return 수정된 게시물의 ID와 상태 코드
     */
    @PutMapping("/{post_id}")
    public ResponseEntity<Long> update(@PathVariable Long post_id, @RequestBody PostUpdateDTO postUpdateDTO) {
        Long updatedId = postService.updatePost(post_id, postUpdateDTO);
        return new ResponseEntity<>(updatedId, HttpStatus.ACCEPTED);
    }

    /**
     * 특정 게시글 조회 API
     * 주어진 ID의 게시물을 조회합니다.
     *
     * @param post_id 게시물 ID
     * @return 조회된 게시물 정보와 상태 코드
     */
    @GetMapping("/{post_id}")
    public ResponseEntity<PostDTO> getSinglePost(@PathVariable Long post_id) {
        PostDTO res = postService.getSinglePost(post_id);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    /**
     * 전체 게시글 조회 API
     * 모든 게시물을 조회합니다.
     *
     * @return 모든 게시물의 정보와 상태 코드
     */
    @GetMapping
    public ResponseEntity<PostResponseDTO> getAllPosts() {
        List<PostDTO> allPosts = postService.getAllPosts();
        PostResponseDTO res = new PostResponseDTO(allPosts);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    /**
     * 특정 사용자의 게시글 조회 API
     * 주어진 사용자 ID의 게시물을 조회합니다.
     *
     * @param member_id 사용자 ID
     * @return 해당 사용자의 게시물 정보와 상태 코드
     */
    @GetMapping("/member/{member_id}")
    public ResponseEntity<PostResponseDTO> getPostsByMemberId(@PathVariable Long member_id) {
        List<PostDTO> memberPosts = postService.findPostsByMemberId(member_id);
        PostResponseDTO result = new PostResponseDTO(memberPosts);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * 특정 사용자의 삭제된 게시글 조회 API
     * 주어진 사용자 ID의 삭제된 게시물을 조회합니다.
     *
     * @param member_id 사용자 ID
     * @return 해당 사용자의 삭제된 게시물 정보와 상태 코드
     */
    @GetMapping("/member/{member_id}/deleted")
    public ResponseEntity<PostResponseDTO> getDeletedPostsByMemberId(@PathVariable Long member_id) {
        List<PostDTO> deletedPosts = postService.findDeletedPostsByMemberId(member_id);
        PostResponseDTO result = new PostResponseDTO(deletedPosts);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * 게시물 삭제 API
     * 주어진 ID의 게시물을 삭제합니다.
     *
     * @param post_id 게시물 ID
     * @return 상태 코드
     */
    @DeleteMapping("/{post_id}")
    public ResponseEntity<Long> deletePost(@PathVariable Long post_id) {
        postService.deletePost(post_id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
