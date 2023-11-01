package HailYoungHan.Board.controller;

import HailYoungHan.Board.dto.member.query.MemberDbDTO;
import HailYoungHan.Board.dto.member.request.MemberRegiDTO;
import HailYoungHan.Board.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;


import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberController.class)
class MemberControllerTest {

    @Autowired // 이렇게 있으면 JUnit 의 ParameterResolver 가 Bean 주입이 필요하다가 판단, Spring Context 에서 가져와서 주입해준다.
    private MockMvc mockMvc;

    @Autowired // 이렇게 있으면 JUnit 의 ParameterResolver 가 Bean 주입이 필요하다가 판단, Spring Context 에서 가져와서 주입해준다.
    private ObjectMapper objectMapper;

    @MockBean
    private MemberService memberService; // Controller Unit Test 이기 때문에, 실제로 주입받을 이유x

    @Test
    void testRegister() throws Exception {
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
    public void testGetOneMember() throws Exception {
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
        ResultActions perform = mockMvc.perform(get("/members/" + memberId).contentType(MediaType.APPLICATION_JSON));

        // then - 검증
        perform
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(memberId.intValue())))
                .andExpect(jsonPath("$.name", is("test")))
                .andExpect(jsonPath("$.email", is("test@example.com")));
    }

    @Test
    void testGetAllMembers() {
    }

    @Test
    void testUpdate() {
    }

    @Test
    void testDeleteMembers() {
    }
}