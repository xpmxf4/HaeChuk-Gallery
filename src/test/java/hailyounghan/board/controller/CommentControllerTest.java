package hailyounghan.board.controller;

import hailyounghan.board.dto.comment.query.CommentDbDTO;
import hailyounghan.board.dto.comment.request.CommentRegiDTO;
import hailyounghan.board.dto.comment.request.CommentUpdateDTO;
import hailyounghan.board.dto.comment.response.CommentResponseDTO;
import hailyounghan.board.exception.CustomException;
import hailyounghan.board.service.CommentService;
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

import static hailyounghan.board.exception.ErrorCode.*;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CommentController.class)
class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CommentService commentService;

    // 댓글 생성 테스트
    @Test
    @DisplayName("댓글 생성 - 유효한 데이터로 성공")
    void createComment_ShouldReturnCreated_WhenDataIsValid() throws Exception {
        // given - 상황 설정
        CommentRegiDTO commentRegiDTO = CommentRegiDTO.builder()
                .content("content")
                .memberId(1L)
                .postId(1L)
                .build();

        doNothing().when(commentService).addComment(any(CommentRegiDTO.class));

        // when - 동작
        ResultActions perform = mockMvc.perform(post("/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(commentRegiDTO)));

        // then - 검증
        perform.andExpect(status().isCreated());
    }

    // 댓글 수정 테스트
    @Test
    @DisplayName("댓글 수정 - 유효한 데이터로 성공")
    void updateComment_ShouldReturnOk_WhenDataIsValid() throws Exception {
        // given - 상황 설정
        long commentId = 1L;
        CommentUpdateDTO updateDTO = CommentUpdateDTO.builder()
                .content("update content")
                .build();


        doNothing().when(commentService).updateComment(any(Long.class), any(CommentUpdateDTO.class));

        // when - 동작
        ResultActions perform = mockMvc.perform(put("/comments/" + commentId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDTO)));

        // then - 검증
        perform.andExpect(status().isOk());
    }

    // ID로 단일 댓글 조회 테스트
    @Test
    @DisplayName("단일 댓글 조회 - 존재하는 댓글 ID로 성공")
    void getSingleCommentById_ShouldReturnComment_WhenCommentExists() throws Exception {
        // given - 상황 설정
        Long commentId = 1L;
        CommentDbDTO expectedComment = CommentDbDTO.builder()
                .content("content")
                .isDeleted(false)
                .build();

        given(commentService.getSingleCommentById(commentId))
                .willReturn(expectedComment);

        // when - 동작
        ResultActions perform = mockMvc.perform(get("/comments/" + commentId)
                .contentType(MediaType.APPLICATION_JSON));

        // then - 검증
        perform
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", is("content")))
                .andExpect(jsonPath("$.isDeleted", is(false)));
    }

    // 모든 댓글 조회 테스트
    @Test
    @DisplayName("모든 댓글 조회 - 성공")
    void getAllComments_ShouldReturnAllComments_WhenCalled() throws Exception {
        // given - 상황 설정
        List<CommentDbDTO> allComments = Arrays.asList(
                new CommentDbDTO("content1", false),
                new CommentDbDTO("content2", false)
        );
        CommentResponseDTO commentResponseDTO = new CommentResponseDTO(allComments);

        given(commentService.getAllComments())
                .willReturn(commentResponseDTO);

        // when - 동작
        ResultActions perform = mockMvc.perform(get("/comments")
                .contentType(MediaType.APPLICATION_JSON));

        // then - 검증
        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$.comments", hasSize(2)))
                .andExpect(jsonPath("$.comments[0].content", is("content1")))
                .andExpect(jsonPath("$.comments[1].content", is("content2")));
    }

    // 특정 멤버의 모든 댓글 조회 테스트
    @Test
    @DisplayName("특정 멤버의 모든 댓글 조회 - 성공")
    void getCommentsByMemberId_ShouldReturnComments_WhenMemberExists() throws Exception {
        // given - 상황 설정
        Long memberId = 1L;
        List<CommentDbDTO> memberComments = Arrays.asList(
                new CommentDbDTO("content1", false),
                new CommentDbDTO("content2", false)
        );
        CommentResponseDTO commentResponseDTO = new CommentResponseDTO(memberComments);


        given(commentService.getMemberAllComments(memberId))
                .willReturn(commentResponseDTO);

        // when - 동작
        ResultActions perform = mockMvc.perform(get("/comments/members/" + memberId + "/")
                .contentType(MediaType.APPLICATION_JSON));

        // then - 검증
        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$.comments", hasSize(2)))
                .andExpect(jsonPath("$.comments[0].content", is("content1")))
                .andExpect(jsonPath("$.comments[1].content", is("content2")));
    }

    // 특정 멤버의 삭제된 댓글 조회 테스트
    @Test
    @DisplayName("특정 멤버의 삭제된 댓글 조회 - 성공")
    void getDeletedCommentsByMemberId_ShouldReturnDeletedComments_WhenMemberExists() throws Exception {
        // given - 상황 설정
        Long memberId = 1L;
        List<CommentDbDTO> deletedComments = Arrays.asList(
                new CommentDbDTO("deleted content1", true),
                new CommentDbDTO("deleted content2", true)
        );
        CommentResponseDTO commentResponseDTO = new CommentResponseDTO(deletedComments);

        given(commentService.getMemberAllDeletedComments(memberId))
                .willReturn(commentResponseDTO);

        // when - 동작
        ResultActions perform = mockMvc.perform(get("/comments/members/" + memberId + "/isDeleted")
                .contentType(MediaType.APPLICATION_JSON));

        // then - 검증
        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$.comments", hasSize(2)))
                .andExpect(jsonPath("$.comments[0].content", is("deleted content1")))
                .andExpect(jsonPath("$.comments[0].isDeleted", is(true)))
                .andExpect(jsonPath("$.comments[1].content", is("deleted content2")))
                .andExpect(jsonPath("$.comments[1].isDeleted", is(true)));
    }

    // 댓글 삭제 테스트
    @Test
    @DisplayName("댓글 삭제 - 성공")
    void deleteComment_ShouldReturnOk_WhenCommentExists() throws Exception {
        // given - 상황 설정
        long commentId = 1L;

        doNothing().when(commentService).deleteComment(any(Long.class));

        // when - 동작
        ResultActions perform = mockMvc.perform(delete("/comments/" + commentId)
                .contentType(MediaType.APPLICATION_JSON));

        // then - 검증
        perform.andExpect(status().isOk());
    }

    /* 예외 케이스 */
    // 댓글 생성 실패 테스트 - 부적절한 데이터
    @Test
    @DisplayName("댓글 생성 - 부적절한 데이터로 실패")
    void createComment_ShouldReturnBadRequest_WhenDataIsInvalid() throws Exception {
        CommentRegiDTO invalidDto = CommentRegiDTO.builder()
                .content("")
                .build();

        mockMvc.perform(post("/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest());
    }

    // 댓글 수정 실패 테스트 - 존재하지 않는 댓글 ID
    @Test
    @DisplayName("댓글 수정 - 존재하지 않는 댓글 ID로 실패")
    void updateComment_ShouldReturnNotFound_WhenCommentDoesNotExist() throws Exception {
        long nonExistingId = Long.MAX_VALUE;
        CommentUpdateDTO updateDTO = CommentUpdateDTO.builder()
                .content("Updated content")
                .build();

        doThrow(new CustomException(COMMENT_NOT_FOUND_BY_ID, nonExistingId))
                .when(commentService)
                .updateComment(eq(nonExistingId), any(CommentUpdateDTO.class));

        mockMvc.perform(put("/comments/" + nonExistingId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isNotFound());
    }

    // 단일 댓글 조회 실패 테스트 - 존재하지 않는 댓글 ID
    @Test
    @DisplayName("단일 댓글 조회 - 존재하지 않는 댓글 ID로 실패")
    void getSingleCommentById_ShouldReturnNotFound_WhenCommentDoesNotExist() throws Exception {
        Long nonExistingId = Long.MAX_VALUE;
        given(commentService.getSingleCommentById(nonExistingId))
                .willThrow(new CustomException(COMMENT_NOT_FOUND_BY_ID, nonExistingId));

        mockMvc.perform(get("/comments/" + nonExistingId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    // 특정 멤버의 모든 댓글 조회 실패 테스트 - 존재하지 않는 멤버 ID
    @Test
    @DisplayName("특정 멤버의 모든 댓글 조회 - 존재하지 않는 멤버 ID로 실패")
    void getCommentsByMemberId_ShouldReturnNotFound_WhenMemberDoesNotExist() throws Exception {
        Long nonExistingMemberId = Long.MAX_VALUE;
        given(commentService.getMemberAllComments(nonExistingMemberId))
                .willThrow(new CustomException(MEMBER_NOT_FOUND_BY_ID, nonExistingMemberId));

        mockMvc.perform(get("/comments/members/" + nonExistingMemberId + "/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    // 특정 멤버의 삭제된 댓글 조회 실패 테스트 - 존재하지 않는 멤버 ID
    @Test
    @DisplayName("특정 멤버의 삭제된 댓글 조회 - 존재하지 않는 멤버 ID로 실패")
    void getDeletedCommentsByMemberId_ShouldReturnNotFound_WhenMemberDoesNotExist() throws Exception {
        Long nonExistingMemberId = Long.MAX_VALUE;
        given(commentService.getMemberAllDeletedComments(nonExistingMemberId))
                .willThrow(new CustomException(MEMBER_NOT_FOUND_BY_ID, nonExistingMemberId));

        mockMvc.perform(get("/comments/members/" + nonExistingMemberId + "/isDeleted")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    // 댓글 삭제 실패 테스트 - 존재하지 않는 댓글 ID
    @Test
    @DisplayName("댓글 삭제 - 존재하지 않는 댓글 ID로 실패")
    void deleteComment_ShouldReturnNotFound_WhenCommentDoesNotExist() throws Exception {
        Long nonExistingId = Long.MAX_VALUE;
        doThrow(new CustomException(COMMENT_NOT_FOUND_BY_ID, nonExistingId))
                .when(commentService)
                .deleteComment(nonExistingId);

        mockMvc.perform(delete("/comments/" + nonExistingId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}