package HailYoungHan.Board.service;

import HailYoungHan.Board.dto.comment.CommentDTO;
import HailYoungHan.Board.dto.comment.CommentRegiDTO;
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
    public void addComment(CommentRegiDTO dto) {
        checkFieldExist(dto);

        Member author = memberRepository.findById(dto.getMemberId()).get();
        Post commentedPost = postRepository.findById(dto.getPostId()).get();
        Comment parentComment = null;
        if (dto.getParentCommentId() != null)
            parentComment = commentRepository.findById(dto.getParentCommentId()).get();

        Comment comment = new Comment(dto.getContent(), author, commentedPost, parentComment);

        commentRepository.save(comment);
    }

    private void checkFieldExist(CommentRegiDTO dto) {
        if (!memberRepository.existsById(dto.getMemberId()))
            throw new MemberNotFoundException(dto.getMemberId());

        if (!postRepository.existsById(dto.getPostId()))
            throw new PostNotFoundException(dto.getPostId());

        if (dto.getParentCommentId() != null && !commentRepository.existsById(dto.getParentCommentId()))
            throw new CommentNotFoundException("parent comment not found : " + dto.getParentCommentId());
    }

    @Transactional
    public void updateComment(Long commentId, CommentDTO dto) {

        if (!commentRepository.existsById(commentId))
            throw new CommentNotFoundException(commentId);

        commentRepository.updateCommentDTO(commentId, dto);
    }

    public CommentDTO getSinglePost(Long commentId) {
        if (!commentRepository.existsById(commentId))
            throw new CommentNotFoundException(commentId);

        return commentRepository.findDTOById(commentId);
    }

    public List<CommentDTO> getAllComments() {

        return commentRepository.findAllDTOs();
    }

    public List<CommentDTO> getMemberComments(Long memberId, boolean isDeleted) {
        if (!memberRepository.existsById(memberId))
            throw new MemberNotFoundException(memberId);

        return commentRepository.findAllDTOsByMemberId(memberId, isDeleted);
    }

    public void deleteComment(Long commentId) {
        if (!commentRepository.existsById(commentId))
            throw new CommentNotFoundException(commentId);

        commentRepository.deleteById(commentId);
    }
}
