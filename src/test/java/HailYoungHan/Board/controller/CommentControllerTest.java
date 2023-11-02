package HailYoungHan.Board.controller;

import HailYoungHan.Board.controller.CommentController;
import HailYoungHan.Board.dto.comment.query.CommentDbDTO;
import HailYoungHan.Board.dto.comment.request.CommentRegiDTO;
import HailYoungHan.Board.dto.comment.request.CommentUpdateDTO;
import HailYoungHan.Board.dto.comment.response.CommentResponseDTO;
import HailYoungHan.Board.service.CommentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
    public void test_Create_Comment_Success() throws Exception {
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
    public void test_Update_Comment_Success() throws Exception {
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
    public void test_Get_Single_Comment_By_Id_Success() throws Exception {
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
    public void test_Get_All_Comments_Success() throws Exception {
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
    public void test_Get_Comments_By_MemberId_Success() throws Exception {
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
    public void test_Get_Deleted_Comments_By_MemberId_Success() throws Exception {
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
    public void test_Delete_Comment_Success() throws Exception {
        // given - 상황 설정
        long commentId = 1L;

        doNothing().when(commentService).deleteComment(any(Long.class));

        // when - 동작
        ResultActions perform = mockMvc.perform(delete("/comments/" + commentId)
                .contentType(MediaType.APPLICATION_JSON));

        // then - 검증
        perform.andExpect(status().isOk());
    }
}