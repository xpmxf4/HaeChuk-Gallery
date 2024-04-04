package hailyounghan.post.service;

import hailyounghan.board.dto.post.query.PostDbDTO;
import hailyounghan.board.dto.post.request.PostRegiDTO;
import hailyounghan.board.dto.post.request.PostUpdateDTO;
import hailyounghan.board.dto.post.response.PostResponseDTO;
import hailyounghan.board.entity.Member;
import hailyounghan.board.entity.Post;
import hailyounghan.board.exception.CustomException;
import hailyounghan.board.repository.member.MemberRepository;
import hailyounghan.board.repository.post.PostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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
    void registerPost_ShouldSavePost_WhenPostRegiDTOIsValid() throws Exception {
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
    void updatePost_ShouldChangePostDetails_WhenUpdateInfoIsValid() throws Exception {
        // given - 상황 만들기
        Long postId = 1L;
        PostUpdateDTO postUpdateDTO = PostUpdateDTO.builder()
                .title("Updated Title")
                .content("Updated Content")
                .build();
        Post existingPost = mock(Post.class);
        when(postRepository.findById(postId))
                .thenReturn(Optional.of(existingPost));

        // when - 동작
        postService.updatePost(postId, postUpdateDTO);

        // then - 검증
        verify(existingPost).updateFieldsFromUpdateDto(postUpdateDTO);
        verify(postRepository).findById(postId);
        verifyNoMoreInteractions(postRepository);
    }

    @Test
    @DisplayName("유효한 게시글 ID로 단일 게시글을 조회하면 해당 게시글을 반환해야 한다")
    void getSinglePost_ShouldReturnPost_WhenPostIdIsValid() throws Exception {
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
    @DisplayName("특정 사용자의 게시글을 조회하면 해당 사용자의 게시글 목록을 반환해야 한다")
    void getPostsByMemberId_ShouldReturnMemberPosts_WhenMemberIdIsValid() throws Exception {
        // given - 상황 만들기
        Long memberId = 1L;
        List<PostDbDTO> memberPosts = Arrays.asList(
                PostDbDTO.builder()
                        .id(1L)
                        .title("Member's Title1")
                        .content("Content1")
                        .writer("Writer1")
                        .isDeleted(false)
                        .build(),
                PostDbDTO.builder()
                        .id(2L)
                        .title("Member's Title2")
                        .content("Content2")
                        .writer("Writer2")
                        .isDeleted(false)
                        .build()
        );

        given(memberRepository.existsById(memberId)).willReturn(true);
        given(postRepository.findPostsByMemberId(memberId, 1, 50)).willReturn(memberPosts);

        // when - 동작
        PostResponseDTO response = postService.getPostsByMemberId(memberId, 1, 50);

        // then - 검증
        assertNotNull(response);
        assertEquals(2, response.getPosts().size());
    }

    @Test
    @DisplayName("존재하지 않는 회원 ID로 게시글을 조회하면 예외를 발생시켜야 한다")
    void getPostsByNonExistingMemberId_ShouldThrowException() {
        // given - 상황 만들기
        Long nonExistingMemberId = 99L;
        given(memberRepository.existsById(nonExistingMemberId)).willReturn(false);

        // when - 동작 & then - 검증
        assertThrows(CustomException.class, () ->
                postService.getPostsByMemberId(nonExistingMemberId, 1, 50));
    }

    @Test
    @DisplayName("모든 게시글을 조회하면 게시글 목록을 반환해야 한다")
    void getAllPosts_ShouldReturnAllPosts() throws Exception {
        // given - 상황 만들기
        List<PostDbDTO> allPosts = Arrays.asList(
                PostDbDTO.builder()
                        .id(1L)
                        .title("Title1")
                        .content("Content1")
                        .writer("Writer1")
                        .isDeleted(false)
                        .build(),
                PostDbDTO.builder()
                        .id(2L)
                        .title("Title2")
                        .content("Content2")
                        .writer("Writer2")
                        .isDeleted(false)
                        .build()
        );

        given(postRepository.findAllDTOs(1, 50)).willReturn(allPosts);

        // when - 동작
        PostResponseDTO response = postService.getAllPosts(1, 50);

        // then - 검증
        assertNotNull(response);
        assertEquals(2, response.getPosts().size());
    }

    @Test
    @DisplayName("특정 사용자가 작성한 삭제된 게시글을 조회하면 해당 게시글 목록을 반환해야 한다")
    void findDeletedPostsByMemberId_ShouldReturnDeletedMemberPosts_WhenMemberIdIsValid() throws Exception {
        // given - 상황 만들기
        Long memberId = 1L;
        List<PostDbDTO> memberDeletedPosts = Arrays.asList(
                PostDbDTO.builder()
                        .id(1L)
                        .title("Deleted Title1")
                        .content("Content1")
                        .writer("Writer1")
                        .isDeleted(true)
                        .build(),
                PostDbDTO.builder()
                        .id(2L)
                        .title("Deleted Title2")
                        .content("Content2")
                        .writer("Writer1")
                        .isDeleted(false)
                        .build()
        );

        given(memberRepository.existsById(memberId)).willReturn(true);
        given(postRepository.findDeletedPostsByMemberId(memberId, 0, 50)).willReturn(memberDeletedPosts);

        // when - 동작
        PostResponseDTO response = postService.findDeletedPostsByMemberId(memberId, 0, 50);

        // then - 검증
        assertNotNull(response);
        assertEquals(2, response.getPosts().size());
    }

    @Test
    @DisplayName("유효한 게시글 ID로 게시글을 삭제하면 해당 게시글을 삭제해야 한다")
    void deletePost_ShouldDeletePost_WhenPostIdIsValid() throws Exception {
        // given - 상황 만들기
        Long postId = 1L;
        doNothing().when(postRepository).deletePostById(postId);
        when(postRepository.existsById(postId)).thenReturn(true);

        // when - 동작
        postService.deletePost(postId);

        // then - 검증
        verify(postRepository, times(1)).deletePostById(postId);

    }

    @Test
    @DisplayName("존재하지 않는 게시글 ID로 게시글을 삭제하려 하면 예외를 발생시켜야 한다")
    void deleteNonExistingPost_ShouldThrowException() {
        // given - 상황 만들기
        Long nonExistingPostId = 99L;
        given(postRepository.existsById(nonExistingPostId)).willReturn(false);

        // when - 동작 & then - 검증
        assertThrows(CustomException.class, () -> {
            postService.deletePost(nonExistingPostId);
        });
    }
}