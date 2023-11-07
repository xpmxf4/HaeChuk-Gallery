package HailYoungHan.Board.service;

import HailYoungHan.Board.dto.comment.query.CommentDbDTO;
import HailYoungHan.Board.dto.comment.request.CommentRegiDTO;
import HailYoungHan.Board.dto.comment.request.CommentUpdateDTO;
import HailYoungHan.Board.dto.comment.response.CommentResponseDTO;
import HailYoungHan.Board.entity.Comment;
import HailYoungHan.Board.entity.Member;
import HailYoungHan.Board.entity.Post;
import HailYoungHan.Board.repository.comment.CommentRepository;
import HailYoungHan.Board.repository.member.MemberRepository;
import HailYoungHan.Board.repository.post.PostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;


@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private PostRepository postRepository;

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private CommentService commentService;

    @Test
    @DisplayName("새 댓글을 추가할 때 성공적으로 저장되어야 한다")
    void addComment_ShouldSaveComment_WhenCommentRegiDTOIsValid() {
        // Given
        Long memberId = 1L;
        Long postId = 1L;
        Long parentCommentId = null; // 예제로 부모 댓글이 없는 경우를 가정
        CommentRegiDTO regiDTO = CommentRegiDTO.builder()
                .content("Test content")
                .memberId(memberId)
                .postId(postId)
                .parentCommentId(parentCommentId)
                .build();

        given(memberRepository.findById(memberId)).willReturn(Optional.of(mock(Member.class)));
        given(postRepository.findById(postId)).willReturn(Optional.of(mock(Post.class)));
        // 부모 댓글은 없으므로 CommentRepository.findById는 호출되지 않는다.

        // When
        commentService.addComment(regiDTO);

        // Then
        then(commentRepository).should(times(1)).save(any(Comment.class));
    }

    @Test
    @DisplayName("댓글을 업데이트할 때 성공적으로 저장되어야 한다")
    void updateComment_ShouldSaveUpdatedComment_WhenCommentUpdateDTOIsValid() throws Exception {
        // given
        Long commentId = 1L;
        CommentUpdateDTO updateDTO = CommentUpdateDTO.builder()
                .content("updated content")
                .isDeleted(false)
                .build();
        Comment comment = Comment.builder()
                .id(commentId)
                .content("content")
                .isDeleted(true).build();
        given(commentRepository.findById(commentId))
                .willReturn(Optional.of(comment));

        // when
        commentService.updateComment(commentId, updateDTO);

        // then
        verify(commentRepository).save(comment);
    }
}