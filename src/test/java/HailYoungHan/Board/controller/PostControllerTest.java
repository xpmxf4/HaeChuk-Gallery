package HailYoungHan.Board.controller;

import HailYoungHan.Board.dto.post.query.PostDbDTO;
import HailYoungHan.Board.dto.post.request.PostRegiDTO;
import HailYoungHan.Board.dto.post.request.PostUpdateDTO;
import HailYoungHan.Board.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
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
    public void testRegister() throws Exception {
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
    public void testUpdate() throws Exception {
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
    public void testGetSinglePost() throws Exception {
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
    public void getGetAllPosts() throws Exception {
        // given - 상황 만들기

        //when - 동작

        //then - 검증
    }

    @Test
    public void testGetPostsByMemberId() throws Exception {
        // given - 상황 만들기

        //when - 동작

        //then - 검증
    }

    @Test
    public void testGetDeletedPostsByMemberId() throws Exception {
        // given - 상황 만들기

        //when - 동작

        //then - 검증
    }

    @Test
    public void testDeletePost() throws Exception {
        // given - 상황 만들기

        //when - 동작

        //then - 검증
    }
}