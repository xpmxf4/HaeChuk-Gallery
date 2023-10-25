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
     * 댓글 생성
     *
     * @param dto
     * @return
     */
    @PostMapping
    public ResponseEntity<Void> addComment(@RequestBody CommentRegiDTO dto) {
        commentService.addComment(dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * 댓글 수정
     *
     * @param updateDTO
     * @param commentId
     * @return
     */
    @PutMapping("/{commentId}")
    public ResponseEntity<Void> updateComment(@RequestBody CommentUpdateDTO updateDTO, @PathVariable Long commentId) {
        commentService.updateComment(commentId, updateDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 댓글 ID 로 댓글 조회
     *
     * @param commentId
     * @return CommentDTO
     */
    @GetMapping("/{commentId}")
    public ResponseEntity<CommentDbDTO> getSingleComment(@PathVariable Long commentId) {
        CommentDbDTO memberComment = commentService.getSinglePost(commentId);
        return new ResponseEntity<>(memberComment, HttpStatus.OK);
    }

    /**
     * 댓글 전체 조회
     *
     * @return &lt;List&lt;CommentDTO&gt;&gt;
     */
    @GetMapping
    public ResponseEntity<CommentResponseDTO> getAllComments() {
        CommentResponseDTO commentRes = commentService.getAllComments();
        return new ResponseEntity<>(commentRes, HttpStatus.OK);
    }

    /**
     * 특정 유저의 댓글 조회
     *
     * @param memberId
     * @param isDeleted
     * @return
     */
    @GetMapping("/member/{memberId}")
    public ResponseEntity<CommentResponseDTO> getMemberComments(@PathVariable Long memberId, @RequestParam(required = false) boolean isDeleted) {
        CommentResponseDTO commentRes = commentService.getMemberComments(memberId, isDeleted);
        return new ResponseEntity<>(commentRes, HttpStatus.OK);
    }

    /**
     * 특정 댓글 삭제
     *
     * @param commentId
     * @return
     */
    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return new ResponseEntity<>(commentId + "deleted", HttpStatus.OK);
    }
}