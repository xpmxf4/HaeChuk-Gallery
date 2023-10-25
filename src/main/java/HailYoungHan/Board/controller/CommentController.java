package HailYoungHan.Board.controller;

import HailYoungHan.Board.dto.comment.query.CommentDbDTO;
import HailYoungHan.Board.dto.comment.request.CommentRegiDTO;
import HailYoungHan.Board.dto.comment.request.CommentUpdateDTO;
import HailYoungHan.Board.dto.comment.response.CommentResponseDTO;
import HailYoungHan.Board.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    /**
     * 댓글 생성 API
     *
     * @param dto - 댓글 생성을 위한 요청 데이터
     *             content: 댓글 내용
     *             memberId: 댓글 작성자의 ID
     *             postId: 댓글이 달릴 게시글의 ID
     *             parentCommentId: 대댓글의 경우 부모 댓글의 ID
     * @return HTTP 상태 코드 201 (생성됨)
     */
    @PostMapping
    public ResponseEntity<Void> addComment(@RequestBody CommentRegiDTO dto) {
        commentService.addComment(dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * 댓글 수정 API
     *
     * @param updateDTO - 댓글 수정을 위한 요청 데이터
     *                   content: 수정할 댓글 내용
     *                   isDeleted: 댓글 삭제 여부
     * @param commentId - 수정할 댓글의 ID
     * @return HTTP 상태 코드 200 (성공)
     */
    @PutMapping("/{commentId}")
    public ResponseEntity<Void> updateComment(@RequestBody CommentUpdateDTO updateDTO, @PathVariable Long commentId) {
        commentService.updateComment(commentId, updateDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 특정 댓글 조회 API
     *
     * @param commentId - 조회할 댓글의 ID
     * @return 댓글 데이터와 HTTP 상태 코드 200 (성공)
     */
    @GetMapping("/{commentId}")
    public ResponseEntity<CommentDbDTO> getSingleComment(@PathVariable Long commentId) {
        CommentDbDTO memberComment = commentService.getSinglePost(commentId);
        return new ResponseEntity<>(memberComment, HttpStatus.OK);
    }

    /**
     * 모든 댓글 조회 API
     *
     * @return 댓글 목록 데이터와 HTTP 상태 코드 200 (성공)
     */
    @GetMapping
    public ResponseEntity<CommentResponseDTO> getAllComments() {
        CommentResponseDTO commentRes = commentService.getAllComments();
        return new ResponseEntity<>(commentRes, HttpStatus.OK);
    }

    /**
     * 특정 유저의 댓글 조회 API
     *
     * @param memberId - 조회할 유저의 ID
     * @param isDeleted - 삭제된 댓글 포함 여부 (true: 삭제된 댓글 포함, false: 삭제되지 않은 댓글만)
     * @return 댓글 목록 데이터와 HTTP 상태 코드 200 (성공)
     */
    @GetMapping("/member/{memberId}")
    public ResponseEntity<CommentResponseDTO> getMemberComments(@PathVariable Long memberId, @RequestParam(required = false) boolean isDeleted) {
        CommentResponseDTO commentRes = commentService.getMemberComments(memberId, isDeleted);
        return new ResponseEntity<>(commentRes, HttpStatus.OK);
    }

    /**
     * 특정 댓글 삭제 API
     *
     * @param commentId - 삭제할 댓글의 ID
     * @return 삭제 메시지와 HTTP 상태 코드 200 (성공)
     */
    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return new ResponseEntity<>(commentId + "deleted", HttpStatus.OK);
    }
}
