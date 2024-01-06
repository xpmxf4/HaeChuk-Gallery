package hailyounghan.board.repository.comment;

import hailyounghan.board.dto.comment.query.CommentDbDTO;
import hailyounghan.board.entity.Comment;
import hailyounghan.board.entity.Member;
import hailyounghan.board.entity.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private TestEntityManager em;

    private Member member;
    private Post post;
    private Comment parentComment;
    private Comment childComment;

    @BeforeEach
    void setup() {
        member = Member.builder()
                .name("회원 이름")
                .email("member@example.com")
                .password("password")
                .build();
        em.persist(member);

        post = Post.builder()
                .title("테스트 게시글 제목")
                .content("테스트 게시글 내용")
                .member(member)
                .build();
        em.persist(post);

        parentComment = Comment.builder()
                .content("테스트 댓글 내용")
                .member(member)
                .post(post)
                .isDeleted(false)
                .build();
        em.persist(parentComment);

        childComment = Comment.builder()
                .content("테스트 대댓글 내용")
                .member(member)
                .post(post)
                .parent(parentComment)
                .isDeleted(false)
                .build();
        em.persist(childComment);
    }

    @Test
    @DisplayName("주어진 멤버 ID로 모든 댓글을 찾아야 한다")
    void findAllDTOsByMemberId_ShouldReturnCommentsForGivenMemberId() {
        // when
        List<CommentDbDTO> foundComments = commentRepository.findAllDTOsByMemberId(member.getId());

        // then
        assertThat(foundComments).isNotEmpty();
        assertThat(foundComments).extracting("content").contains(parentComment.getContent());
    }

    @Test
    @DisplayName("존재하지 않는 멤버 ID로 댓글을 찾으면 비어 있는 목록을 반환해야 한다")
    void findAllDTOsByMemberId_ShouldReturnEmptyList_WhenMemberIdDoesNotExist() {
        // when
        List<CommentDbDTO> result = commentRepository.findAllDTOsByMemberId(Long.MAX_VALUE);

        // then
        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("주어진 멤버 ID로 삭제된 모든 댓글을 찾아야 한다")
    void findAllDeletedDTOsByMemberId_ShouldReturnDeletedCommentsForGivenMemberId() {
        // given
        Comment deletedComment = Comment.builder()
                .content("삭제된 댓글 내용")
                .member(member)
                .post(post)
                .isDeleted(true)
                .build();
        em.persist(deletedComment);

        // when
        List<CommentDbDTO> foundDeletedComments = commentRepository.findAllDeletedDTOsByMemberId(member.getId(), true);

        // then
        assertThat(foundDeletedComments).isNotEmpty();
        assertThat(foundDeletedComments).extracting("isDeleted").containsOnly(true);
    }

    @Test
    @DisplayName("삭제 플래그가 설정되지 않은 상태에서 삭제된 댓글을 찾으려 할 때 비어 있는 결과를 반환해야 한다")
    void findAllDeletedDTOsByMemberId_ShouldReturnEmptyList_WhenNoCommentsAreDeleted() {
        // when
        List<CommentDbDTO> result = commentRepository.findAllDeletedDTOsByMemberId(member.getId(), true);

        // then
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("주어진 ID로 댓글 DTO를 찾아야 한다")
    void findDTOById_ShouldReturnCommentDtoForGivenId() {
        // when
        CommentDbDTO foundComment = commentRepository.findDTOById(parentComment.getId());

        // then
        assertThat(foundComment).isNotNull();
        assertThat(foundComment.getContent()).isEqualTo(parentComment.getContent());
    }

    @Test
    @DisplayName("존재하지 않는 ID로 댓글을 찾으면 null을 반환해야 한다")
    void findDTOById_ShouldReturnNull_WhenIdDoesNotExist() {
        // when
        CommentDbDTO result = commentRepository.findDTOById(Long.MAX_VALUE);

        // then
        assertNull(result);
    }


    @Test
    @DisplayName("모든 댓글 DTO를 찾아야 한다")
    void findAllDTOs_ShouldReturnAllCommentDTOs() {
        // when
        List<CommentDbDTO> allComments = commentRepository.findAllDTOs();

        // then
        assertThat(allComments).hasSize(2); // 초기 설정에서 댓글과 대댓글 두 개를 추가하였기 때문
    }
}
