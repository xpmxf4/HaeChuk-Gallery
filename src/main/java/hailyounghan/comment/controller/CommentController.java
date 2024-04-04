package hailyounghan.comment.controller;

import hailyounghan.comment.dto.query.CommentDbDTO;
import hailyounghan.comment.dto.request.CommentRegiDTO;
import hailyounghan.comment.dto.request.CommentUpdateDTO;
import hailyounghan.comment.dto.response.CommentResponseDTO;
import hailyounghan.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    /**
     * <p>댓글 생성 API</p>
     *
     * <p><b>Params:</b></p>
     * <ul>
     *     <li><b>dto</b> – 댓글 생성을 위한 요청 데이터:</li>
     *     <ul>
     *         <li><b>content:</b> 댓글 내용</li>
     *         <li><b>memberId:</b> 댓글 작성자의 ID</li>
     *         <li><b>postId:</b> 댓글이 달릴 게시글의 ID</li>
     *         <li><b>parentCommentId:</b> 대댓글의 경우 부모 댓글의 ID</li>
     *     </ul>
     * </ul>
     * <p><b>Returns:</b></p>
     * <p>HTTP 상태 코드 201 (생성됨)</p>
     */
    @PostMapping
    public ResponseEntity<Void> createComment(@RequestBody @Valid CommentRegiDTO dto) {
        commentService.addComment(dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * <p>댓글 수정 API</p>
     *
     * <p><b>Params:</b></p>
     * <ul>
     *     <li><b>updateDTO:</b> 댓글 수정을 위한 요청 데이터</li>
     *     <ul>
     *         <li><b>content:</b> 수정할 댓글 내용</li>
     *         <li><b>isDeleted:</b> 댓글 삭제 여부</li>
     *     </ul>
     *     <li><b>commentId:</b> 수정할 댓글의 ID</li>
     * </ul>
     * <p><b>Returns:</b></p>
     * <p>HTTP 상태 코드 200 (성공)</p>
     */
    @PutMapping("/{commentId}")
    public ResponseEntity<Void> updateComment(@RequestBody @Valid CommentUpdateDTO updateDTO, @PathVariable Long commentId) {
        commentService.updateComment(commentId, updateDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * <p>특정 댓글 조회 API</p>
     *
     * <p><b>Params:</b></p>
     * <ul>
     *     <li><b>commentId:</b> 조회할 댓글의 ID</li>
     * </ul>
     * <p><b>Returns:</b></p>
     * <p>댓글 데이터와 HTTP 상태 코드 200 (성공)</p>
     */
    @GetMapping("/{commentId}")
    public ResponseEntity<CommentDbDTO> getSingleComment(@PathVariable Long commentId) {
        CommentDbDTO memberComment = commentService.getSingleCommentById(commentId);
        return new ResponseEntity<>(memberComment, HttpStatus.OK);
    }

    /**
     * <p>모든 댓글 조회 API</p>
     *
     * <p><b>Returns:</b></p>
     * <p>댓글 목록 데이터와 HTTP 상태 코드 200 (성공)</p>
     */
    @GetMapping
    public ResponseEntity<CommentResponseDTO> getAllComments() {
        CommentResponseDTO commentRes = commentService.getAllComments();
        return new ResponseEntity<>(commentRes, HttpStatus.OK);
    }

    /**
     * <p>특정 유저의 댓글 조회 API</p>
     *
     * <p><b>Params:</b></p>
     * <ul>
     *     <li><b>memberId:</b> 조회할 유저의 ID</li>
     * </ul>
     * <p><b>Returns:</b></p>
     * <p>댓글 목록 데이터와 HTTP 상태 코드 200 (성공)</p>
     */
    @GetMapping("/members/{memberId}/")
    public ResponseEntity<CommentResponseDTO> getMemberComments(@PathVariable Long memberId) {
        CommentResponseDTO commentRes = commentService.getMemberAllComments(memberId);
        return new ResponseEntity<>(commentRes, HttpStatus.OK);
    }

    @GetMapping("/members/{memberId}/isDeleted")
    public ResponseEntity<CommentResponseDTO> getMemberDeletedComments(@PathVariable Long memberId) {
        CommentResponseDTO commentRes = commentService.getMemberAllDeletedComments(memberId);
        return new ResponseEntity<>(commentRes, HttpStatus.OK);
    }

    /**
     * <p>특정 댓글 삭제 API</p>
     *
     * <p><b>Params:</b></p>
     * <ul>
     *     <li><b>commentId:</b> 삭제할 댓글의 ID</li>
     * </ul>
     * <p><b>Returns:</b></p>
     * <p>삭제 메시지와 HTTP 상태 코드 200 (성공)</p>
     */
    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return new ResponseEntity<>(commentId + " deleted", HttpStatus.OK);
    }
}
