package HailYoungHan.Board.service;

import HailYoungHan.Board.dto.comment.request.CommentRegiDTO;
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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
    void whenAddingNewComment_ItShouldBeSavedSuccessfully() {
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
}