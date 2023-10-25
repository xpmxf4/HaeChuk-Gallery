package HailYoungHan.Board.service;

import HailYoungHan.Board.dto.comment.query.CommentDbDTO;
import HailYoungHan.Board.dto.comment.request.CommentRegiDTO;
import HailYoungHan.Board.dto.comment.request.CommentUpdateDTO;
import HailYoungHan.Board.dto.comment.response.CommentResponseDTO;
import HailYoungHan.Board.entity.Comment;
import HailYoungHan.Board.entity.Member;
import HailYoungHan.Board.entity.Post;
import HailYoungHan.Board.exception.comment.CommentNotFoundException;
import HailYoungHan.Board.exception.member.MemberNotFoundException;
import HailYoungHan.Board.exception.post.PostNotFoundException;
import HailYoungHan.Board.repository.comment.CommentRepository;
import HailYoungHan.Board.repository.member.MemberRepository;
import HailYoungHan.Board.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public void addComment(CommentRegiDTO commentRegiDTO) {
        Member author = memberRepository.findById(commentRegiDTO.getMemberId())
                .orElseThrow(() -> new MemberNotFoundException(commentRegiDTO.getMemberId()));

        Post commentedPost = postRepository.findById(commentRegiDTO.getPostId())
                .orElseThrow(() -> new PostNotFoundException(commentRegiDTO.getPostId()));

        Comment parentComment = commentRepository.findById(commentRegiDTO.getParentCommentId())
                .orElseThrow(() -> new CommentNotFoundException(commentRegiDTO.getParentCommentId()));

        Comment comment = Comment.mapFromRegiDto(author, commentedPost, parentComment, commentRegiDTO);

        commentRepository.save(comment);
    }

    @Transactional
    public void updateComment(Long commentId, CommentUpdateDTO updateDTO) {
        if (!commentRepository.existsById(commentId))
            throw new CommentNotFoundException(commentId);

        // CommentUpdateDTO ---(map)---> Comment(Entity) 로 map
        Comment comment = updateDTO.mapToEntity(commentId);

        commentRepository.save(comment);
    }

    public CommentDbDTO getSinglePost(Long commentId) {
        if (!commentRepository.existsById(commentId))
            throw new CommentNotFoundException(commentId);

        return commentRepository.findDTOById(commentId);
    }

    public CommentResponseDTO getAllComments() {
        List<CommentDbDTO> allComments = commentRepository.findAllDTOs();
        return new CommentResponseDTO(allComments);
    }

    public CommentResponseDTO getMemberComments(Long memberId, boolean isDeleted) {
        if (!memberRepository.existsById(memberId))
            throw new MemberNotFoundException(memberId);

        return new CommentResponseDTO(
                commentRepository.findAllDTOsByMemberId(memberId, isDeleted)
        );
    }

    public void deleteComment(Long commentId) {
        if (!commentRepository.existsById(commentId))
            throw new CommentNotFoundException(commentId);

        commentRepository.deleteById(commentId);
    }
}
