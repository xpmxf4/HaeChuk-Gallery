package HailYoungHan.Board.controller;

import HailYoungHan.Board.dto.member.MemberRegiDTO;
import HailYoungHan.Board.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberService memberService;

    @Test
    void testRegister() throws Exception {

        MemberRegiDTO memberRegiDTO = MemberRegiDTO.builder()
                .name("김영한")
                .password("test1234")
                .email("hail_younghan@gmail.com")
                .build();

        memberService.registerMember(memberRegiDTO);
        // memberService 의 registerMember 메서드가 호출될 때 아무런 동작도 하지 않도록 설정
        doNothing().when(memberService).registerMember(memberRegiDTO);

        // /members POST 요청 보내고, 응답을 검증
        mockMvc.perform(post("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\": \"김영한\", \"email\": \"hail_younghan@gmail.com\", \"password\": \"test1234\" }"))
                .andExpect(status().isCreated())
                .andExpect(content().string("회원이 성공적으로 생성되었습니다."));
    }
}