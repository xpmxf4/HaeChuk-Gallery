package hailyounghan.comment.service;

import hailyounghan.comment.dto.query.CommentDbDTO;
import hailyounghan.comment.dto.request.CommentRegiDTO;
import hailyounghan.comment.dto.request.CommentUpdateDTO;
import hailyounghan.comment.dto.response.CommentResponseDTO;
import hailyounghan.comment.entity.Comment;
import hailyounghan.comment.repository.CommentRepository;
import hailyounghan.common.exception.CustomException;
import hailyounghan.member.entity.Member;
import hailyounghan.member.repository.MemberRepository;
import hailyounghan.post.entity.Post;
import hailyounghan.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static hailyounghan.common.exception.ErrorCode.*;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public void addComment(CommentRegiDTO commentRegiDTO) {
        // JPA 라서 일일히 조회하는 꼬라지가 나온다. -> 비즈니스에 따라 다를듯.
        // 민재 : 연관관계 부서붜림, 향로님 블로그,
        Long memberId = commentRegiDTO.getMemberId();
        Long postId = commentRegiDTO.getPostId();
        Long parentCommentId = commentRegiDTO.getParentCommentId();

        Member author = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND_BY_ID, memberId));
        Post commentedPost = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(POST_NOT_FOUND_BY_ID, postId));
        // 부모 댓글 ID가 null이 아닐 때만 부모 댓글 조회
        Comment parentComment = null;
        if (parentCommentId != null) {
            parentComment = commentRepository.findById(parentCommentId)
                    .orElseThrow(() -> new CustomException(COMMENT_NOT_FOUND_BY_ID, parentCommentId));
        }

        Comment comment = Comment.mapFromRegiDto(author, commentedPost, parentComment, commentRegiDTO);
        commentRepository.save(comment);
    }

    @Transactional
    public void updateComment(Long commentId, CommentUpdateDTO updateDTO) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(COMMENT_NOT_FOUND_BY_ID, commentId));

        // CommentUpdateDTO ---(map)---> Comment(Entity) 로 map
        comment.updateFieldsFromUpdateDto(updateDTO);
    }

    public CommentDbDTO getSingleCommentById(Long commentId) {
        if (!commentRepository.existsById(commentId))
            throw new CustomException(COMMENT_NOT_FOUND_BY_ID, commentId);

        return commentRepository.findDTOById(commentId);
    }

    public CommentResponseDTO getAllComments() {
        List<CommentDbDTO> allComments = commentRepository.findAllDTOs();
        return new CommentResponseDTO(allComments);
    }

    public CommentResponseDTO getMemberAllComments(Long memberId) {
        if (!memberRepository.existsById(memberId))
            throw new CustomException(MEMBER_NOT_FOUND_BY_ID, memberId);

        return new CommentResponseDTO(
                commentRepository.findAllDTOsByMemberId(memberId)
        );
    }

    public CommentResponseDTO getMemberAllDeletedComments(Long memberId) {
        if(!memberRepository.existsById(memberId))
            throw new CustomException(MEMBER_NOT_FOUND_BY_ID, memberId);

        return new CommentResponseDTO(
                commentRepository.findAllDeletedDTOsByMemberId(memberId, true)
        );
    }

    public void deleteComment(Long commentId) {
        if (!commentRepository.existsById(commentId))
            throw new CustomException(COMMENT_NOT_FOUND_BY_ID, commentId);

        commentRepository.deleteById(commentId);
    }
}
