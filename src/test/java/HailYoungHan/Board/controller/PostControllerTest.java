package HailYoungHan.Board.controller;

import HailYoungHan.Board.dto.post.query.PostDbDTO;
import HailYoungHan.Board.dto.post.request.PostRegiDTO;
import HailYoungHan.Board.dto.post.request.PostUpdateDTO;
import HailYoungHan.Board.dto.post.response.PostResponseDTO;
import HailYoungHan.Board.exception.CustomException;
import HailYoungHan.Board.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.List;

import static HailYoungHan.Board.exception.ErrorCode.MEMBER_NOT_FOUND_BY_ID;
import static HailYoungHan.Board.exception.ErrorCode.POST_NOT_FOUND_BY_ID;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
    @DisplayName("게시물 추가 - 유효한 데이터로 성공")
    void register_ShouldReturnCreated_WhenDataIsValid() throws Exception {
        // given - 유효한 게시물 등록 정보
        PostRegiDTO postRegiDTO = PostRegiDTO.builder()
                .memberId(1L)
                .title("스근하이 쳐 직이네")
                .content("content test")
                .build();

        // when - 게시물 추가 API 호출
        ResultActions perform = mockMvc.perform(post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postRegiDTO)));

        // then - 게시물 추가 성공 확인 (HTTP 상태 코드 201)
        perform.andExpect(status().isCreated());
    }

    @Test
    @DisplayName("게시물 수정 - 존재하는 게시물 ID와 유효한 데이터로 성공")
    void updatePost_ShouldReturnOk_WhenExistsAndDataIsValid() throws Exception {
        // given - 존재하는 게시물 ID 및 유효한 수정 정보
        long postId = 1;
        PostUpdateDTO updateDTO = PostUpdateDTO.builder()
                .title("title")
                .content("content")
                .build();

        // when - 게시물 수정 API 호출
        ResultActions perform = mockMvc.perform(put("/posts/" + postId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDTO)));

        // then - 게시물 수정 성공 확인 (HTTP 상태 코드 200)
        perform.andExpect(status().isOk());
    }

    @Test
    @DisplayName("특정 게시물 조회 - 존재하는 게시물 ID로 성공")
    void getSinglePost_ShouldReturnPost_WhenExists() throws Exception {
        // given - 존재하는 게시물 ID
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

        // when - 특정 게시물 조회 API 호출
        ResultActions perform = mockMvc.perform(get("/posts/" + postId)
                .contentType(MediaType.APPLICATION_JSON));

        // then - 조회된 게시물 정보 확인
        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(postId.intValue())))
                .andExpect(jsonPath("$.title", is("title")))
                .andExpect(jsonPath("$.content", is("content")))
                .andExpect(jsonPath("$.writer", is("writer")))
                .andExpect(jsonPath("$.isDeleted", is(false)));
    }

    @Test
    @DisplayName("전체 게시물 조회 - 성공")
    void getAllPosts_ShouldReturnAllPosts_WhenCalled() throws Exception {
        // given - 게시물 리스트 준비
        List<PostDbDTO> allPosts = Arrays.asList(
                new PostDbDTO(1L, "Title1", "Content1", "Writer1", false),
                new PostDbDTO(2L, "Title2", "Content2", "Writer2", false)
        );
        PostResponseDTO postResponseDTO = new PostResponseDTO(allPosts);
        given(postService.getAllPosts()).willReturn(postResponseDTO);

        // when - 전체 게시물 조회 API 호출
        ResultActions perform = mockMvc.perform(get("/posts")
                .contentType(MediaType.APPLICATION_JSON));

        // then - 모든 게시물 정보 확인
        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$.posts", hasSize(2)))
                .andExpect(jsonPath("$.posts[0].title", is("Title1")))
                .andExpect(jsonPath("$.posts[1].title", is("Title2")));
    }

    @Test
    @DisplayName("특정 사용자의 게시물 조회 - 성공")
    void getPostsByMemberId_ShouldReturnMemberPosts_WhenMemberExists() throws Exception {
        // given - 특정 사용자의 게시물 리스트 준비
        Long memberId = 1L;
        List<PostDbDTO> memberPosts = Arrays.asList(
                new PostDbDTO(1L, "Title1", "Content1", "Writer1", false),
                new PostDbDTO(2L, "Title2", "Content2", "Writer2", false)
        );
        PostResponseDTO postResponseDTO = new PostResponseDTO(memberPosts);
        given(postService.getPostsByMemberId(memberId)).willReturn(postResponseDTO);

        // when - 특정 사용자의 게시물 조회 API 호출
        ResultActions perform = mockMvc.perform(get("/posts/member/" + memberId)
                .contentType(MediaType.APPLICATION_JSON));

        // then - 해당 사용자의 게시물 정보 확인
        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$.posts", hasSize(2)))
                .andExpect(jsonPath("$.posts[0].id", is(1)))
                .andExpect(jsonPath("$.posts[1].id", is(2)));
    }

    @Test
    @DisplayName("특정 사용자의 삭제된 게시물 조회 - 성공")
    void getDeletedPostsByMemberId_ShouldReturnDeletedMemberPosts_WhenMemberExists() throws Exception {
        // given - 특정 사용자의 삭제된 게시물 리스트 준비
        Long memberId = 1L;
        List<PostDbDTO> deletedPosts = Arrays.asList(
                new PostDbDTO(1L, "Deleted Title", "Deleted Content", "Writer1", true),
                new PostDbDTO(2L, "Deleted Title2", "Deleted Content 2", "Writer1", true)
        );
        PostResponseDTO postResponseDTO = new PostResponseDTO(deletedPosts);
        given(postService.findDeletedPostsByMemberId(memberId)).willReturn(postResponseDTO);

        // when - 특정 사용자의 삭제된 게시물 조회 API 호출
        ResultActions perform = mockMvc.perform(get("/posts/member/" + memberId + "/deleted")
                .contentType(MediaType.APPLICATION_JSON));

        // then - 해당 사용자의 삭제된 게시물 정보 확인
        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$.posts", hasSize(2)))
                .andExpect(jsonPath("$.posts[0].title", is("Deleted Title")))
                .andExpect(jsonPath("$.posts[1].title", is("Deleted Title2")));
    }

    @Test
    @DisplayName("게시물 삭제 - 성공")
    void deletePost_ShouldReturnOk_WhenPostExists() throws Exception {
        // given - 삭제할 게시물 ID
        Long postId = 1L;
        doNothing().when(postService).deletePost(postId);

        // when - 게시물 삭제 API 호출
        ResultActions perform = mockMvc.perform(delete("/posts/" + postId)
                .contentType(MediaType.APPLICATION_JSON));

        // then - 게시물 삭제 성공 확인 (HTTP 상태 코드 200)
        perform.andExpect(status().isOk());
    }

    /* 예외 케이스 */

    @Test
    @DisplayName("게시물 추가 - 유효하지 않은 데이터로 실패")
    void register_ShouldReturnBadRequest_WhenDataIsInvalid() throws Exception {
        // given - 유효하지 않은 게시물 등록 정보
        PostRegiDTO invalidPostRegiDTO = PostRegiDTO.builder()
                .memberId(null)
                .title("")
                .content("")
                .build();

        // when - 게시물 추가 API 호출
        ResultActions perform = mockMvc.perform(post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidPostRegiDTO)));

        // then - 게시물 추가 실패 확인 (HTTP 상태 코드 400)
        perform.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("게시물 수정 - 존재하지 않는 게시물 ID로 실패")
    void updatePost_ShouldReturnNotFound_WhenPostDoesNotExist() throws Exception {
        // given - 존재하지 않는 게시물 ID 및 수정 정보
        long nonExistingPostId = 1L;
        PostUpdateDTO updateDTO = PostUpdateDTO.builder()
                .title("title")
                .content("content")
                .build();
//        doThrow(new CustomException(POST_NOT_FOUND_BY_ID, nonExistingPostId))
//                .when(postService)
//                .updatePost(nonExistingPostId, updateDTO);
        doThrow(new CustomException(POST_NOT_FOUND_BY_ID, nonExistingPostId))
                .when(postService)
                .updatePost(anyLong(), any(PostUpdateDTO.class));

        // when - 게시물 수정 API 호출
        ResultActions perform = mockMvc.perform(put("/posts/" + nonExistingPostId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDTO)));

        // then - 게시물 수정 실패 확인 (HTTP 상태 코드 404)
        perform.andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("특정 게시물 조회 - 존재하지 않는 게시물 ID로 실패")
    void getSinglePost_ShouldReturnNotFound_WhenPostDoesNotExist() throws Exception {
        // given - 존재하지 않는 게시물 ID
        Long nonExistingPostId = 999L;
        given(postService.getSinglePost(nonExistingPostId))
                .willThrow(new CustomException(POST_NOT_FOUND_BY_ID, nonExistingPostId));

        // when - 특정 게시물 조회 API 호출
        ResultActions perform = mockMvc.perform(get("/posts/" + nonExistingPostId)
                .contentType(MediaType.APPLICATION_JSON));

        // then - 조회된 게시물 없음 확인 (HTTP 상태 코드 404)
        perform.andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("특정 사용자의 게시물 조회 - 존재하지 않는 사용자 ID로 실패")
    void getPostsByMemberId_ShouldReturnNotFound_WhenMemberDoesNotExist() throws Exception {
        // given - 존재하지 않는 사용자 ID
        Long nonExistingMemberId = 999L;
        given(postService.getPostsByMemberId(nonExistingMemberId))
                .willThrow(new CustomException(MEMBER_NOT_FOUND_BY_ID, "999"));

        // when - 특정 사용자의 게시물 조회 API 호출
        ResultActions perform = mockMvc.perform(get("/posts/member/" + nonExistingMemberId)
                .contentType(MediaType.APPLICATION_JSON));

        // then - 해당 사용자의 게시물 정보 없음 확인 (HTTP 상태 코드 404)
        perform.andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("게시물 삭제 - 존재하지 않는 게시물 ID로 실패")
    void deletePost_ShouldReturnNotFound_WhenPostDoesNotExist() throws Exception {
        // given - 존재하지 않는 게시물 ID
        Long nonExistingPostId = 999L;
        doThrow(new CustomException(POST_NOT_FOUND_BY_ID, "999"))
                .when(postService).deletePost(nonExistingPostId);

        // when - 게시물 삭제 API 호출
        ResultActions perform = mockMvc.perform(delete("/posts/" + nonExistingPostId)
                .contentType(MediaType.APPLICATION_JSON));

        // then - 게시물 삭제 실패 확인 (HTTP 상태 코드 404)
        perform.andExpect(status().isNotFound());
    }
}