package HailYoungHan.Board.service;

import HailYoungHan.Board.dto.post.request.PostRegiDTO;
import HailYoungHan.Board.entity.Member;
import HailYoungHan.Board.entity.Post;
import HailYoungHan.Board.repository.member.MemberRepository;
import HailYoungHan.Board.repository.post.PostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private PostService postService;

    // Additional Helper
    @Captor
    private ArgumentCaptor<Post> postArgumentCaptor;

    @Test
    @DisplayName("올바른 PostRegiDTO 가 들어오면, 정상 게시글 등록")
    public void registerPost_ShouldSavePost_WhenPostRegiDTOIsValid() throws Exception {
        // given - 상황 만들기
        Long memberId = 1L;
        Member member = Member.builder()
                .id(memberId)
                .name("daeminjae")
                .build();
        PostRegiDTO postRegiDTO = PostRegiDTO.builder()
                .memberId(memberId)
                .title("title")
                .content("content")
                .build();

        Post post = Post.mapFromRegiDto(member, postRegiDTO);
        given(memberRepository.findById(1L)).willReturn(Optional.of(member));
        given(postRepository.save(any(Post.class))).willReturn(post);

        // when - 동작
        postService.registerPost(postRegiDTO);

        // then - 검증
        verify(memberRepository, times(1)).findById(memberId);
        verify(postRepository, times(1)).save(postArgumentCaptor.capture());
        Post capturedPost = postArgumentCaptor.getValue();

        assertEquals(postRegiDTO.getTitle(), capturedPost.getTitle());
        assertEquals(postRegiDTO.getContent(), capturedPost.getContent());
        assertEquals(member, capturedPost.getMember());
    }
}