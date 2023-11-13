package HailYoungHan.Board.controller;

import HailYoungHan.Board.dto.member.query.MemberDbDTO;
import HailYoungHan.Board.dto.member.request.MemberRegiDTO;
import HailYoungHan.Board.dto.member.request.MemberUpdateDTO;
import HailYoungHan.Board.dto.member.response.MemberResponseDTO;
import HailYoungHan.Board.exception.CustomException;
import HailYoungHan.Board.exception.ErrorCode;
import HailYoungHan.Board.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static HailYoungHan.Board.exception.ErrorCode.*;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberController.class)
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MemberService memberService;

    private MemberRegiDTO validRegiDTO;
    private MemberUpdateDTO validUpdateDTO;
    private List<Long> memberIds;

    @BeforeEach
    void setup() {
        validRegiDTO = MemberRegiDTO.builder()
                .name("Test User")
                .email("test@example.com")
                .password("test1234")
                .build();

        validUpdateDTO = MemberUpdateDTO.builder()
                .name("Updated Name")
                .email("update@example.com")
                .password("updatedPassword")
                .build();

        memberIds = new ArrayList<>();
        for (long i = 1; i <= 3; i++) {
            memberIds.add(i);
        }
    }

    @Test
    @DisplayName("회원 등록 - 유효한 데이터로 성공")
    void register_ShouldReturnCreated_WhenDataIsValid() throws Exception {
        MemberRegiDTO reqDto = MemberRegiDTO.builder()
                .name("Test User")
                .email("test@example.com")
                .password("test1234")
                .build();

        mockMvc.perform(post("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reqDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("단일 회원 조회 - 존재하는 회원 ID로 성공")
    void getOneMember_ShouldReturnMember_WhenMemberExists() throws Exception {
        // given - 상황 만들기
        Long memberId = 1L;
        MemberDbDTO expectedMember = MemberDbDTO.builder()
                .id(memberId)
                .name("test")
                .email("test@example.com")
                .build();

        given(memberService.getSingleMember(memberId))
                .willReturn(expectedMember);

        // when - 동작
        ResultActions perform = mockMvc.perform(get("/members/" + memberId)
                .contentType(MediaType.APPLICATION_JSON));

        // then - 검증
        perform
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(memberId.intValue())))
                .andExpect(jsonPath("$.name", is("test")))
                .andExpect(jsonPath("$.email", is("test@example.com")));
    }

    @Test
    @DisplayName("모든 회원 조회 - 성공")
    void getAllMembers_ShouldReturnAllMembers_WhenCalled() throws Exception {
        // given - 상황 만들기
        List<MemberDbDTO> expectedMembers = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            MemberDbDTO member = MemberDbDTO.builder()
                    .id((long) i)
                    .name("test" + i)
                    .email("test" + i + "@gmail.com")
                    .build();

            expectedMembers.add(member);
        }
        MemberResponseDTO expectedResult = new MemberResponseDTO(expectedMembers);

        given(memberService.getAllMembers())
                .willReturn(expectedResult);

        //when - 동작
        ResultActions perform = mockMvc.perform(get("/members")
                .contentType(MediaType.APPLICATION_JSON));

        //then - 검증
        for (int i = 0; i < 5; i++) {
            perform
                    .andExpect(jsonPath("$.members[" + i + "].id", is(expectedMembers.get(i).getId().intValue())))
                    .andExpect(jsonPath("$.members[" + i + "].name", is(expectedMembers.get(i).getName())))
                    .andExpect(jsonPath("$.members[" + i + "].email", is(expectedMembers.get(i).getEmail())));
        }
    }

    @Test
    @DisplayName("회원 정보 수정 - 유효한 데이터로 성공")
    void update_ShouldReturnAccepted_WhenDataIsValid() throws Exception {
        // given - 상황 만들기
        long memberId = 1L;
        MemberUpdateDTO updateDTO = MemberUpdateDTO.builder()
                .name("test")
                .email("test@example.com")
                .password("test1234")
                .build();

        //when - 동작
        ResultActions perform = mockMvc.perform(put("/members/" + memberId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDTO)
                ));

        //then - 검증
        perform.andExpect(status().isAccepted());
    }

    @Test
    @DisplayName("여러 회원 삭제 - 성공")
    void deleteMembers_ShouldReturnOk_WhenMembersExist() throws Exception {
        // given - 상황 만들기
        List<Long> ids = new ArrayList<>();
        for (long i = 1; i <= 3; i++) {
            ids.add(i);
        }

        // when - 동작
        ResultActions perform = mockMvc.perform(delete("/members")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ids)));

        // then - 검증
        perform
                .andExpect(status().isOk());
    }

    // 예외 케이스 테스트 추가

    @Test
    @DisplayName("단일 회원 조회 - 존재하지 않는 회원 ID로 실패")
    void getOneMember_ShouldReturnNotFound_WhenMemberDoesNotExist() throws Exception {
        given(memberService.getSingleMember(999L))
                .willThrow(new CustomException(MEMBER_NOT_FOUND_BY_ID, "999"));

        mockMvc.perform(get("/members/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("회원 등록 - 이미 존재하는 이메일로 실패")
    void register_ShouldReturnConflict_WhenEmailAlreadyExists() throws Exception {
        doThrow(new CustomException(EMAIL_ALREADY_EXISTS, "test@example.com"))
                .when(memberService)
                .registerMember(any(MemberRegiDTO.class));

        mockMvc.perform(post("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRegiDTO)))
                .andExpect(status().isBadRequest());
    }


    @Test
    @DisplayName("회원 정보 수정 - 존재하지 않는 회원 ID로 실패")
    void update_ShouldReturnNotFound_WhenMemberDoesNotExist() throws Exception {
        doThrow(new CustomException(MEMBER_NOT_FOUND_BY_ID, "1"))
                .when(memberService)
                .updateMember(anyLong(), any(MemberUpdateDTO.class));

        mockMvc.perform(put("/members/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validUpdateDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("여러 회원 삭제 - 비어 있는 ID 리스트로 실패")
    void deleteMembers_ShouldReturnBadRequest_WhenIdListIsEmpty() throws Exception {
        List<Long> emptyMemberIds = new ArrayList<>();

        doThrow(new CustomException(MEMBER_IDS_IS_EMPTY_OR_NULL, objectMapper.writeValueAsString(emptyMemberIds)))
                .when(memberService)
                        .deleteMembers(emptyMemberIds);

        mockMvc.perform(delete("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new ArrayList<Long>())))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("여러 회원 삭제 - 존재하지 않는 회원 ID들로 실패")
    void deleteMembers_ShouldReturnNotFound_WhenMembersDoNotExist() throws Exception {
        List<Long> nonExistingIds = Arrays.asList(999L, 1000L, 1001L);

        doThrow(new CustomException(INVALID_MEMBER_ID_IS_INCLUDED))
                .when(memberService)
                .deleteMembers(nonExistingIds);

        mockMvc.perform(delete("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nonExistingIds)))
                .andExpect(status().isBadRequest());
    }
}