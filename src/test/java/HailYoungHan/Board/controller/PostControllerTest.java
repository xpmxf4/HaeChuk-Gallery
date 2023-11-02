package HailYoungHan.Board.controller;

import HailYoungHan.Board.controller.PostController;
import HailYoungHan.Board.dto.post.query.PostDbDTO;
import HailYoungHan.Board.dto.post.request.PostRegiDTO;
import HailYoungHan.Board.dto.post.request.PostUpdateDTO;
import HailYoungHan.Board.dto.post.response.PostResponseDTO;
import HailYoungHan.Board.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PostController.class)
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PostService postService;

    @Test
    public void shouldCreatePostWhenDataIsValid() throws Exception {
        // given - 상황 만들기
        PostRegiDTO postRegiDTO = PostRegiDTO.builder()
                .memberId(1L)
                .title("스근하이 쳐 직이네")
                .content("content test")
                .build();

        // when - 동작
        ResultActions perform = mockMvc.perform(post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postRegiDTO)));

        // then - 검증
        perform
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldUpdatePostWhenPostExistsAndDataIsValid() throws Exception {
        // given - 상황 만들기
        long postId = 1;
        PostUpdateDTO updateDTO = PostUpdateDTO.builder()
                .title("title")
                .content("content")
                .build();

        //when - 동작
        ResultActions perform = mockMvc.perform(put("/posts/" + postId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDTO)
                ));

        //then - 검증
        perform
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnSinglePostWhenPostIdIsGiven() throws Exception {
        // given - 상황 만들기
        Long postId = 1L;
        PostDbDTO expectedPost = PostDbDTO.builder()
                .id(postId)
                .title("title")
                .content("content")
                .writer("writer")
                .isDeleted(false)
                .build();

        given(postService.getSinglePost(postId))
                .willReturn(expectedPost);

        //when - 동작
        ResultActions perform = mockMvc.perform(get("/posts/" + postId)
                .contentType(MediaType.APPLICATION_JSON));

        //then - 검증
        perform
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(postId.intValue())))
                .andExpect(jsonPath("$.title", is("title")))
                .andExpect(jsonPath("$.content", is("content")))
                .andExpect(jsonPath("$.writer", is("writer")))
                .andExpect(jsonPath("$.isDeleted", is(false)));
    }

    @Test
    public void shouldReturnAllPostsWhenRequested() throws Exception {
        // given
        List<PostDbDTO> allPosts = Arrays.asList(
                new PostDbDTO(1L, "Title1", "Content1", "Writer1", false),
                new PostDbDTO(2L, "Title2", "Content2", "Writer2", false)
        );
        PostResponseDTO postResponseDTO = new PostResponseDTO(allPosts);
        given(postService.getAllPosts()).willReturn(postResponseDTO);

        // when
        ResultActions perform = mockMvc.perform(get("/posts")
                .contentType(MediaType.APPLICATION_JSON));

        // then
        perform
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.posts", hasSize(2)))
                .andExpect(jsonPath("$.posts[0].title", is("Title1")))
                .andExpect(jsonPath("$.posts[1].title", is("Title2")));
    }

    @Test
    public void shouldReturnPostsForGivenMemberId() throws Exception {
        // given
        Long memberId = 1L;
        List<PostDbDTO> memberPosts = Arrays.asList(
                new PostDbDTO(1L, "Title1", "Content1", "Writer1", false),
                new PostDbDTO(2L, "Title2", "Content2", "Writer2", false)
        );
        PostResponseDTO postResponseDTO = new PostResponseDTO(memberPosts);
        given(postService.getPostsByMemberId(memberId))
                .willReturn(postResponseDTO);

        // when
        ResultActions perform = mockMvc.perform(get("/posts/member/" + memberId)
                .contentType(MediaType.APPLICATION_JSON));

        // then
        perform
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.posts", hasSize(2)))
                .andExpect(jsonPath("$.posts[0].id", is(1)))
                .andExpect(jsonPath("$.posts[1].id", is(2)));
    }

    @Test
    public void shouldReturnDeletedPostsForGivenMemberId() throws Exception {
        // given
        Long memberId = 1L;
        List<PostDbDTO> deletedPosts = Arrays.asList(
                new PostDbDTO(1L, "Deleted Title", "Deleted Content", "Writer1", true),
                new PostDbDTO(2L, "Deleted Title2", "Deleted Content 2", "Writer1", true)
        );
        PostResponseDTO postResponseDTO = new PostResponseDTO(deletedPosts);
        given(postService.findDeletedPostsByMemberId(memberId)).willReturn(postResponseDTO);

        // when
        ResultActions perform = mockMvc.perform(get("/posts/member/" + memberId + "/deleted")
                .contentType(MediaType.APPLICATION_JSON));

        // then
        perform
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.posts", hasSize(2)))
                .andExpect(jsonPath("$.posts[0].title", is("Deleted Title")))
                .andExpect(jsonPath("$.posts[0].writer", is("Writer1")))
                .andExpect(jsonPath("$.posts[0].isDeleted", is(true)))
                .andExpect(jsonPath("$.posts[1].title", is("Deleted Title2")))
                .andExpect(jsonPath("$.posts[1].writer", is("Writer1")))
                .andExpect(jsonPath("$.posts[1].isDeleted", is(true)));
    }

    @Test
    public void shouldDeletePostWhenPostIdIsProvided() throws Exception {
        // given
        Long postId = 1L;
        doNothing().when(postService).deletePost(postId);

        // when
        ResultActions perform = mockMvc.perform(delete("/posts/" + postId)
                .contentType(MediaType.APPLICATION_JSON));

        // then
        perform
                .andExpect(status().isOk());
    }
}