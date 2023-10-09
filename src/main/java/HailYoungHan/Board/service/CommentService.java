package HailYoungHan.Board.service;

import HailYoungHan.Board.dto.CommentRegiDTO;
import HailYoungHan.Board.exception.CommentNotFoundException;
import HailYoungHan.Board.exception.MemberNotFoundException;
import HailYoungHan.Board.exception.PostNotFoundException;
import HailYoungHan.Board.repository.CommentRepository;
import HailYoungHan.Board.repository.MemberRepository;
import HailYoungHan.Board.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public void addComment(CommentRegiDTO dto) {
        // DB 에 삽입
        commentRepository
                .insertCommentDTO(
                        dto.getContent(),
                        memberRepository.findById(dto.getMemberId()).orElseThrow(() -> new MemberNotFoundException(dto.getMemberId())),
                        postRepository.findById(dto.getPostId()).orElseThrow(() -> new CommentNotFoundException(dto.getPostId())),
                        commentRepository.findById(dto.getParentCommentId()).orElseThrow(() -> new CommentNotFoundException(dto.getParentCommentId()))
                );
    }
}
