package HailYoungHan.Board.controller;

import HailYoungHan.Board.dto.CommentDTO;
import HailYoungHan.Board.dto.CommentRegiDTO;
import HailYoungHan.Board.dto.CommentResponseDTO;
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

    @PostMapping("/{commentId}")
    public ResponseEntity<Void> addComment(@RequestBody CommentRegiDTO dto, @PathVariable Long commentId) {

        commentService.addComment(dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<Void> updateComment(@RequestBody CommentDTO dto, @PathVariable Long commentId) {

        commentService.updateComment(commentId, dto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<CommentDTO> getSingleComment(@PathVariable Long commentId) {
        CommentDTO ret = commentService.getSinglePost(commentId);

        return new ResponseEntity<>(ret, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<CommentResponseDTO> getAllComments() {
        List<CommentDTO> ret = commentService.getAllComments();
        CommentResponseDTO res = new CommentResponseDTO(ret);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/member/{memberId}")
    public ResponseEntity<CommentResponseDTO> getMemberComments(@PathVariable Long memberId, @RequestParam(required = false) boolean isDeleted) {
        List<CommentDTO> ret = commentService.getMemberComments(memberId, isDeleted);
        CommentResponseDTO res = new CommentResponseDTO(ret);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);

        return new ResponseEntity<>(commentId + "deleted", HttpStatus.OK);
    }
}