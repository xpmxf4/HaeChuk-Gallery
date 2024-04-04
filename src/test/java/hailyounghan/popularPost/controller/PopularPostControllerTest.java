package hailyounghan.popularPost.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hailyounghan.popularPost.dto.query.PopularPostDTO;
import hailyounghan.popularPost.dto.response.PopularPostResponseDTO;
import hailyounghan.popularPost.service.PopularPostService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PopularPostController.class)
class PopularPostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PopularPostService popularPostService;

    @Test
    @DisplayName("인기 게시글을 가져오기")
    void shouldFetchPopularPosts() throws Exception {
        // given
        List<PopularPostDTO> popularPostDTOs = new ArrayList<>();
        popularPostDTOs.add(new PopularPostDTO(1L, "인기 게시글 제목 1", "작성자1"));
        popularPostDTOs.add(new PopularPostDTO(2L, "인기 게시글 제목 2", "작성자2"));
        PopularPostResponseDTO mockResponse = new PopularPostResponseDTO(popularPostDTOs);

        given(popularPostService.getPopularPosts()).willReturn(mockResponse);

        // when + then
        mockMvc.perform(get("/popularPost"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.popularPosts", is(not(empty())))) // 데이터 구조에 따라 변경 가능
                .andExpect(content().json(toJson(mockResponse)));
    }

    private String toJson(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON formatting error", e);
        }
    }
}
