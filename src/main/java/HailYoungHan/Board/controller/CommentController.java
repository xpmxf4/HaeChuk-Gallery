package HailYoungHan.Board.controller;

import HailYoungHan.Board.dto.CommentDTO;
import HailYoungHan.Board.dto.CommentRegiDTO;
import HailYoungHan.Board.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
