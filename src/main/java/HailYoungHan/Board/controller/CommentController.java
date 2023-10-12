package HailYoungHan.Board.controller;

import HailYoungHan.Board.dto.comment.CommentDTO;
import HailYoungHan.Board.dto.comment.CommentRegiDTO;
import HailYoungHan.Board.dto.comment.CommentResponseDTO;
import HailYoungHan.Board.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    /**
     * 댓글 생성
     * @param dto
     * @param commentId
     * @return
     */
    @PostMapping("/{commentId}")
    public ResponseEntity<Void> addComment(@RequestBody CommentRegiDTO dto, @PathVariable Long commentId) {

        commentService.addComment(dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * 댓글 수정
     * @param dto
     * @param commentId
     * @return
     */
    @PutMapping("/{commentId}")
    public ResponseEntity<Void> updateComment(@RequestBody CommentDTO dto, @PathVariable Long commentId) {

        commentService.updateComment(commentId, dto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 댓글 ID 로 댓글 조회
     * @param commentId
     * @return CommentDTO
     */
    @GetMapping("/{commentId}")
    public ResponseEntity<CommentDTO> getSingleComment(@PathVariable Long commentId) {
        CommentDTO ret = commentService.getSinglePost(commentId);

        return new ResponseEntity<>(ret, HttpStatus.OK);
    }

    /**
     * 댓글 전체 조회
     * @return CommentResponseDTO
     */
    @GetMapping
    public ResponseEntity<CommentResponseDTO> getAllComments() {
        List<CommentDTO> ret = commentService.getAllComments();
        CommentResponseDTO res = new CommentResponseDTO(ret);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    /**
     * 특정 유저의 댓글 조회
     * @param memberId
     * @param isDeleted
     * @return
     */
    @GetMapping("/member/{memberId}")
    public ResponseEntity<CommentResponseDTO> getMemberComments(@PathVariable Long memberId, @RequestParam(required = false) boolean isDeleted) {
        List<CommentDTO> ret = commentService.getMemberComments(memberId, isDeleted);
        CommentResponseDTO res = new CommentResponseDTO(ret);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    /**
     * 특정 댓글 삭제
     * @param commentId
     * @return
     */
    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);

        return new ResponseEntity<>(commentId + "deleted", HttpStatus.OK);
    }
}