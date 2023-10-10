package HailYoungHan.Board.controller;

import HailYoungHan.Board.dto.CommentRegiDTO;
import HailYoungHan.Board.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<Void> addComment(@RequestBody CommentRegiDTO dto) {

        commentService.addComment(dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
