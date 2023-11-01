package HailYoungHan.Board.controller;

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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

        //when - 동작

        //then - 검증
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