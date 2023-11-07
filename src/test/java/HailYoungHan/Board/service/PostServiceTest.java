package HailYoungHan.Board.service;

import HailYoungHan.Board.dto.post.query.PostDbDTO;
import HailYoungHan.Board.dto.post.request.PostRegiDTO;
import HailYoungHan.Board.dto.post.request.PostUpdateDTO;
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
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
    @DisplayName("등록 정보가 유효하면 게시글을 등록해야 한다.")
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

    @Test
    @DisplayName("업데이트 정보가 유효하면 게시글의 세부 사항을 변경해야 한다")
    public void updatePost_ShouldChangePostDetails_WhenUpdateInfoIsValid() throws Exception {
        // given - 상황 만들기
        Long postId = 1L;
        PostUpdateDTO postUpdateDTO = PostUpdateDTO.builder()
                .title("Updated Title")
                .content("Updated Content")
                .build();
        Post existingPost = Post.builder()
                .id(postId)
                .title("Title")
                .content("Content")
                .build();
        when(postRepository.findById(postId))
                .thenReturn(Optional.of(existingPost));

        // when - 동작
        postService.updatePost(postId, postUpdateDTO);

        // then - 검증
        assertEquals("Updated Title", existingPost.getTitle());
        assertEquals("Updated Content", existingPost.getContent());
    }

    @Test
    @DisplayName("유효한 게시글 ID로 단일 게시글을 조회하면 해당 게시글을 반환해야 한다")
    public void getSinglePost_ShouldReturnPost_WhenPostIdIsValid() throws Exception {
        // given - 상황 만들기
        Long postId = 1L;
        Member writer = Member.builder()
                .id(1L)
                .name("writer")
                .build();
        Post post = Post.builder()
                .id(postId)
                .title("title")
                .content("content")
                .member(writer)
                .isDeleted(false)
                .build();
        when(postRepository.findById(postId))
                .thenReturn(Optional.of(post));

        // when - 동작
        PostDbDTO postDbDTO = postService.getSinglePost(postId);

        // then - 검증
        assertNotNull(postDbDTO);
        assertEquals(postId, postDbDTO.getId());
    }

    @Test
    @DisplayName("유효한 게시글 ID로 게시글을 삭제하면 해당 게시글을 삭제해야 한다")
    public void deletePost_ShouldDeletePost_WhenPostIdIsValid() throws Exception {
        // given - 상황 만들기
        Long postId = 1L;
        doNothing().when(postRepository).deletePostById(postId);
        when(postRepository.existsById(postId)).thenReturn(true);

        // when - 동작
        postService.deletePost(postId);

        // then - 검증
        verify(postRepository, times(1)).deletePostById(postId);

    }
}