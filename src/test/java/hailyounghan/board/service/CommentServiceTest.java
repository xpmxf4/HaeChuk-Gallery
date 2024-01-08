package hailyounghan.board.service;

import hailyounghan.board.dto.comment.query.CommentDbDTO;
import hailyounghan.board.dto.comment.request.CommentRegiDTO;
import hailyounghan.board.dto.comment.request.CommentUpdateDTO;
import hailyounghan.board.dto.comment.response.CommentResponseDTO;
import hailyounghan.board.entity.Comment;
import hailyounghan.board.entity.Member;
import hailyounghan.board.entity.Post;
import hailyounghan.board.exception.CustomException;
import hailyounghan.board.repository.comment.CommentRepository;
import hailyounghan.board.repository.member.MemberRepository;
import hailyounghan.board.repository.post.PostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.ConstraintViolationException;
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
        then(commentRepository).should(times(1))
                .save(any(Comment.class));
    }

    @Test
    @DisplayName("부모 댓글이 존재하지 않을 때 예외를 발생시켜야 한다")
    void addComment_ShouldThrowException_WhenParentCommentDoesNotExist() {
        // given
        Long memberId = 1L;
        Long postId = 1L;
        Long invalidParentCommentId = 99L; // 존재하지 않는 부모 댓글 ID
        CommentRegiDTO regiDTO = CommentRegiDTO.builder()
                .content("Test content")
                .memberId(memberId)
                .postId(postId)
                .parentCommentId(invalidParentCommentId)
                .build();

        given(memberRepository.findById(memberId)).willReturn(Optional.of(mock(Member.class)));
        given(postRepository.findById(postId)).willReturn(Optional.of(mock(Post.class)));
        given(commentRepository.findById(invalidParentCommentId)).willReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() -> commentService.addComment(regiDTO))
                .isInstanceOf(CustomException.class)
                .hasMessageContaining("해당 댓글 ID가 없습니다: 99");
    }

    @Test
    @DisplayName("댓글을 업데이트할 때 성공적으로 저장되어야 한다")
    void updateComment_ShouldSaveUpdatedComment_WhenCommentUpdateDTOIsValid() throws Exception {
        // given
        Long commentId = 1L;
        CommentUpdateDTO updateDTO = CommentUpdateDTO.builder()
                .content("updated content")
                .build();
        Comment commentToUpdate = Comment.builder()
                .id(commentId)
                .content("content")
                .isDeleted(false)
                .build();
        given(commentRepository.findById(commentId))
                .willReturn(Optional.of(commentToUpdate));

        // when
        commentService.updateComment(commentId, updateDTO);

        // then
        /*
        // BDD -> 객체의 상태 변화에 더 초점을 맞추고 있기에, 택하지는 않음
        verify(comment).updateFieldsFromUpdateDto(updateDTO); // updateFieldsFromUpdateDto 가 updateDTO 를 가지고 호출이 되었나
        verify(commentRepository).findById(commentId); // findById 가 commentId 를 가지고 호출이 되었나
        verifyNoMoreInteractions(commentRepository); // findById 외에 다른 repository 함수가 호출이 되었나
         */
        assertThat(commentToUpdate.getContent()).isEqualTo(updateDTO.getContent());
    }

    @Test
    @DisplayName("댓글을 조회할 때 해당 댓글이 반환되어야 한다")
    void getSingleCommentById_ShouldReturnComment_WhenCommentIdIsValid() throws Exception {
        // given
        Long commentId = 1L;
        CommentDbDTO output = CommentDbDTO.builder()
                .content("content")
                .isDeleted(false)
                .build();
        given(commentRepository.existsById(commentId))
                .willReturn(true);
        given(commentRepository.findDTOById(commentId))
                .willReturn(output);

        // when
        CommentDbDTO result = commentService.getSingleCommentById(commentId);

        // then
        assertThat(result.getContent()).isEqualTo(output.getContent());
        assertThat(result.getIsDeleted()).isEqualTo(output.getIsDeleted());
    }

    @Test
    @DisplayName("모든 댓글을 조회할 때 성공적으로 반환되어야 한다")
    void getAllComments_ShouldReturnAllComments() {
        // given
        List<CommentDbDTO> allComments = List.of(new CommentDbDTO("content", false));
        given(commentRepository.findAllDTOs()).willReturn(allComments);

        // when
        CommentResponseDTO result = commentService.getAllComments();

        // then
        assertThat(result.getComments()).hasSize(allComments.size());
    }

    @Test
    @DisplayName("멤버의 모든 댓글을 조회할 때 성공적으로 반환되어야 한다")
    void getMemberAllComments_ShouldReturnMembersComments_WhenMemberIdIsValid() {
        // given
        Long memberId = 1L;
        List<CommentDbDTO> membersComments = List.of(new CommentDbDTO("content", false));
        given(memberRepository.existsById(memberId)).willReturn(true);
        given(commentRepository.findAllDTOsByMemberId(memberId)).willReturn(membersComments);

        // when
        CommentResponseDTO result = commentService.getMemberAllComments(memberId);

        // then
        assertThat(result.getComments()).isEqualTo(membersComments);
    }

    @Test
    @DisplayName("존재하지 않는 멤버 ID로 댓글 조회 시 예외가 발생해야 한다")
    void getMemberAllComments_ShouldThrowException_WhenMemberIdIsInvalid() {
        // given
        Long invalidMemberId = 99L;
        given(memberRepository.existsById(invalidMemberId)).willReturn(false);

        // when, then
        assertThatThrownBy(() -> commentService.getMemberAllComments(invalidMemberId))
                .isInstanceOf(CustomException.class)
                .hasMessageContaining("해당 멤버 ID가 없습니다: " + invalidMemberId);
    }

    @Test
    @DisplayName("멤버의 삭제된 모든 댓글을 조회할 때 성공적으로 반환되어야 한다")
    void getMemberAllDeletedComments_ShouldReturnMembersDeletedComments_WhenMemberIdIsValid() {
        // given
        Long memberId = 1L;
        List<CommentDbDTO> deletedComments = List.of(new CommentDbDTO("content", true));
        given(memberRepository.existsById(memberId)).willReturn(true);
        given(commentRepository.findAllDeletedDTOsByMemberId(memberId, true)).willReturn(deletedComments);

        // when
        CommentResponseDTO result = commentService.getMemberAllDeletedComments(memberId);

        // then
        assertThat(result.getComments()).isEqualTo(deletedComments);
    }

    @Test
    @DisplayName("존재하지 않는 댓글 삭제 시 예외가 발생해야 한다")
    void deleteComment_ShouldThrowException_WhenCommentIdIsInvalid() {
        // given
        Long invalidCommentId = 99L;
        given(commentRepository.existsById(invalidCommentId)).willReturn(false);

        // when/then
        assertThatThrownBy(() -> commentService.deleteComment(invalidCommentId))
                .isInstanceOf(CustomException.class)
                .hasMessageContaining("해당 댓글 ID가 없습니다: " + invalidCommentId);
    }

    @Test
    @DisplayName("댓글을 삭제할 때 해당 댓글이 삭제되어야 한다")
    void deleteComment_ShouldRemoveComment_WhenCommentIdIsValid() throws Exception {
        // given
        Long commentId = 1L;
        when(commentRepository.existsById(commentId))
                .thenReturn(true);

        // when
        commentService.deleteComment(commentId);

        // then
        verify(commentRepository).deleteById(commentId);
    }
}