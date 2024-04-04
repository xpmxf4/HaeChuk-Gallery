package hailyounghan.post.repository;

import com.querydsl.core.Tuple;
import hailyounghan.member.entity.Member;
import hailyounghan.post.dto.query.PostDbDTO;
import hailyounghan.post.entity.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

@DataJpaTest
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private TestEntityManager em;

    private Member member;
    private Post post;

    @BeforeEach
    void setup() {
        member = Member.builder()
                .name("Test Name")
                .email("test@example.com")
                .password("password")
                .build();
        em.persist(member);

        post = Post.builder()
                .title("Test Title")
                .content("Test Content")
                .member(member)
                .isDeleted(false) // 초기값 설정
                .build();
        em.persist(post);
    }

    @Test
    @DisplayName("지정된 멤버 ID에 대한 모든 게시글을 찾아야 한다")
    public void findPostsByMemberId_ShouldReturnPostsForGivenMemberId() throws Exception {
        // when
        List<PostDbDTO> foundPosts = postRepository.findPostsByMemberId(member.getId(), 0, 50);

        // then
        assertThat(foundPosts).isNotEmpty();
        assertThat(foundPosts.get(0).getWriter()).isEqualTo(member.getName());
    }

    @Test
    @DisplayName("지정된 멤버 ID에 대한 삭제된 게시글을 찾아야 한다")
    public void findDeletedPostsByMemberId_ShouldReturnDeletedPostsForGivenMemberId() throws Exception {
        // given
        post = Post.builder()
                .title("Test Title")
                .content("Test Content")
                .member(member)
                .isDeleted(true)
                .build();
        em.persist(post);
        em.flush();

        // when
        List<PostDbDTO> foundDeletedPosts = postRepository.findDeletedPostsByMemberId(
                member.getId(), 0, 50);
        System.out.println(foundDeletedPosts.toString());

        // then
        assertThat(foundDeletedPosts).isNotEmpty();
        assertThat(foundDeletedPosts.get(0).getIsDeleted()).isTrue();
    }

    @Test
    @DisplayName("지정된 ID에 대한 게시글 DTO를 찾아야 한다")
    public void findDTObyId_ShouldReturnPostDtoForGivenId() throws Exception {
        // when
        PostDbDTO foundPost = postRepository.findDTObyId(post.getId());

        // then
        assertThat(foundPost).isNotNull();
        assertThat(foundPost.getId()).isEqualTo(post.getId());
    }

    @Test
    @DisplayName("모든 게시글 DTO를 반환해야 한다")
    public void findAllDTOs_ShouldReturnAllPostDTOs() throws Exception {
        // when
        List<PostDbDTO> allPosts = postRepository.findAllDTOs(0, 50);

        // then
        assertThat(allPosts).hasSize(1); // 초기 설정에서 하나의 포스트만 추가하였기 때문
    }

    @Test // 사실 안해도 되긴 함.
    @DisplayName("지정된 ID의 게시글을 삭제해야 한다")
    public void deletePostById_ShouldDeleteThePostForGivenId() throws Exception {
        // when
        postRepository.deletePostById(post.getId());
        em.flush(); // 실제 삭제를 적용하기 위해 사용

        // then
        assertThat(postRepository.findById(post.getId())).isNotPresent();
    }

    @Test
    @DisplayName("존재하지 않는 멤버 ID로 게시글 조회 시 빈 목록을 반환해야 한다")
    public void findPostsByNonExistingMemberId_ShouldReturnEmptyList() {
        // when
        List<PostDbDTO> result = postRepository.findPostsByMemberId(Long.MAX_VALUE, 1, 50);

        // then
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("존재하지 않는 멤버 ID로 삭제된 게시글 조회 시 빈 목록을 반환해야 한다")
    public void findDeletedPostsByNonExistingMemberId_ShouldReturnEmptyList() {
        // when
        List<PostDbDTO> result = postRepository.findDeletedPostsByMemberId(Long.MAX_VALUE, 1, 50);

        // then
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("존재하지 않는 게시글 ID 조회 시 null을 반환해야 한다")
    public void findDTObyNonExistingId_ShouldReturnNull() {
        // when
        PostDbDTO result = postRepository.findDTObyId(Long.MAX_VALUE);

        // then
        assertNull(result);
    }

    @Test
    @DisplayName("키워드로 게시글을 검색해야 한다")
    void findDTOsByKeyword_ShouldReturnPosts_WhenKeywordIsValid() {
        // given
        String keyword = "Test"; // 실제 존재하는 키워드
        Integer offset = 0;
        Integer limit = 50;

        // when
        List<PostDbDTO> results = postRepository.findDTOsByKeyword(keyword, offset, limit);

        // then
        assertThat(results).isNotEmpty(); // 적어도 하나 이상의 게시글이 반환되어야 함
        assertThat(results.get(0).getTitle()).contains(keyword); // 반환된 게시글 중 첫 번째 게시글이 키워드를 포함해야 함
    }

    @Test
    @DisplayName("상위 10개 게시글을 찾아야 한다")
    void findTop10Posts_ShouldReturnTopPosts() {
        // when
        List<Tuple> results = postRepository.findTop10Posts();

        // then
        assertThat(results).hasSizeLessThanOrEqualTo(10); // 10개 이하의 결과 반환
        // 추가적인 검증: 예를 들어 반환된 게시글이 실제로 가장 많은 댓글을 가지고 있는지,
        // 혹은 가장 많은 좋아요를 받았는지 등의 조건을 검증할 수 있음
    }

}
