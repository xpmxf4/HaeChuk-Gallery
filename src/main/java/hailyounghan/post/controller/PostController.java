package hailyounghan.post.controller;

import hailyounghan.post.dto.query.PostDbDTO;
import hailyounghan.post.dto.request.PostRegiDTO;
import hailyounghan.post.dto.request.PostUpdateDTO;
import hailyounghan.post.dto.response.PostResponseDTO;
import hailyounghan.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    /**
     * <p>게시물 추가 API</p>
     *
     * <p><b>Params:</b></p>
     * <ul>
     *     <li><b>postRegiDTO</b> – 게시물 생성에 필요한 데이터:</li>
     *     <ul>
     *         <li><b>memberId:</b> 게시물 작성자의 ID. Not nullable.</li>
     *         <li><b>title:</b> 게시물의 제목. Not blank, 1-50 characters.</li>
     *         <li><b>content:</b> 게시물의 내용. Not blank, 1-1000 characters.</li>
     *     </ul>
     * </ul>
     *
     * <p><b>Returns:</b></p>
     * <p>생성된 게시물 정보와 HTTP 상태 코드 201 (생성됨)</p>
     */
    @PostMapping
    public ResponseEntity<Void> register(@RequestBody @Valid PostRegiDTO postRegiDTO) {
        postService.registerPost(postRegiDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * <p>특정 게시글 수정 API</p>
     *
     * <p><b>Params:</b></p>
     * <ul>
     *     <li><b>post_id</b> – 수정할 게시물의 ID</li>
     *     <li><b>postUpdateDTO</b> – 수정할 게시물의 정보:</li>
     *     <ul>
     *         <li><b>title:</b> 게시물의 제목. Not blank, 1-50 characters.</li>
     *         <li><b>content:</b> 게시물의 내용. Not blank, 1-1000 characters.</li>
     *     </ul>
     * </ul>
     *
     * <p><b>Returns:</b></p>
     * <p>HTTP 상태 코드 200 (성공)</p>
     */
    @PutMapping("/{post_id}")
    public ResponseEntity<Void> update(@PathVariable Long post_id, @RequestBody PostUpdateDTO postUpdateDTO) {
        postService.updatePost(post_id, postUpdateDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * <p>특정 게시글 조회 API</p>
     *
     * <p><b>Params:</b></p>
     * <ul>
     *     <li><b>post_id</b> – 조회할 게시물의 ID</li>
     * </ul>
     *
     * <p><b>Returns:</b></p>
     * <p>주어진 ID의 게시물 정보와 HTTP 상태 코드 200 (성공)</p>
     */
    @GetMapping("/{post_id}")
    public ResponseEntity<PostDbDTO> getSinglePost(@PathVariable Long post_id) {
        PostDbDTO res = postService.getSinglePost(post_id);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    /**
     * <p>전체 게시글 조회 API</p>
     *
     * <p><b>Returns:</b></p>
     * <p>모든 게시물의 정보와 HTTP 상태 코드 200 (성공)</p>
     */
    @GetMapping
    public ResponseEntity<PostResponseDTO> getAllPosts(@RequestParam Integer offset, @RequestParam Integer limit) {
        PostResponseDTO postRes = postService.getAllPosts(offset, limit);
        return new ResponseEntity<>(postRes, HttpStatus.OK);
    }

    /**
     * <p>특정 사용자의 게시글 조회 API</p>
     *
     * <p><b>Params:</b></p>
     * <ul>
     *     <li><b>memberId</b> – 게시물을 조회할 사용자의 ID</li>
     * </ul>
     *
     * <p><b>Returns:</b></p>
     * <p>해당 사용자의 게시물 정보와 HTTP 상태 코드 200 (성공)</p>
     */
    @GetMapping("/member/{memberId}")
    public ResponseEntity<PostResponseDTO> getPostsByMemberId(@PathVariable Long memberId, @RequestParam Integer offset, @RequestParam Integer limit) {
        PostResponseDTO postRes = postService.getPostsByMemberId(memberId, offset, limit);
        return new ResponseEntity<>(postRes, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<PostResponseDTO> searchPostsWithKeyword(@RequestParam String keyword, @RequestParam Integer offset, @RequestParam Integer limit) {
        PostResponseDTO result = postService.searchByKeyword(keyword, offset, limit);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * <p>특정 사용자의 삭제된 게시글 조회 API</p>
     *
     * <p><b>Params:</b></p>
     * <ul>
     *     <li><b>memberId</b> – 삭제된 게시물을 조회할 사용자의 ID</li>
     * </ul>
     *
     * <p><b>Returns:</b></p>
     * <p>해당 사용자의 삭제된 게시물 정보와 HTTP 상태 코드 200 (성공)</p>
     */
    @GetMapping("/member/{memberId}/deleted")
    public ResponseEntity<PostResponseDTO> getDeletedPostsByMemberId(@PathVariable Long memberId, @RequestParam Integer offset, @RequestParam Integer limit) {
        PostResponseDTO postRes = postService.findDeletedPostsByMemberId(memberId, offset, limit);
        return new ResponseEntity<>(postRes, HttpStatus.OK);
    }

    /**
     * <p>게시물 삭제 API</p>
     *
     * <p><b>Params:</b></p>
     * <ul>
     *     <li><b>post_id</b> – 삭제할 게시물의 ID</li>
     * </ul>
     *
     * <p><b>Returns:</b></p>
     * <p>HTTP 상태 코드 200 (성공)</p>
     */
    @DeleteMapping("/{post_id}")
    public ResponseEntity<Long> deletePost(@PathVariable Long post_id) {
        postService.deletePost(post_id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
