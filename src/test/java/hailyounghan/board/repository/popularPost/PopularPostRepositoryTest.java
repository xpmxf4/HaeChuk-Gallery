package hailyounghan.board.repository.popularPost;

import hailyounghan.board.dto.popularPost.query.PopularPostDTO;
import hailyounghan.board.entity.Member;
import hailyounghan.board.entity.PopularPost;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class PopularPostRepositoryTest {

    @Autowired
    private PopularPostRepository popularPostRepository;

    @Autowired
    private TestEntityManager em;

    @BeforeEach
    void setup() {
        Member member = Member.builder()
                .name("Test Name")
                .email("test@example.com")
                .password("password")
                .build();
        em.persist(member);

        PopularPost popularPost = PopularPost.builder()
                .title("Test Title")
                .member(member)
                .build();
        em.persist(popularPost);

    }

    @Test
    @DisplayName("모든 인기 게시글 DTO를 찾아야 한다")
    public void findAllDTOs_ShouldReturnAllPopularPostDTOs() {
        // when
        List<PopularPostDTO> foundPopularPosts = popularPostRepository.findAllDTOs();

        // then
        assertThat(foundPopularPosts).isNotNull();
        // 추가적인 검증: 예를 들어 foundPopularPosts의 내용, 크기 등을 검증할 수 있음
    }
}
