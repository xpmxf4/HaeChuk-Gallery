package HailYoungHan.Board.controller;

import HailYoungHan.Board.dto.member.MemberRegiDTO;
import HailYoungHan.Board.service.MemberService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MemberService memberService;

    @Test
    void testRegister() throws Exception {
        MemberRegiDTO regiDto = MemberRegiDTO.builder()
                .name("윤석열")
                .email("webmaster@president.go.kr")
                .password("윤석열화이팅")
                .build();

        // memberSerivce의 registerMember 메서드가 호출될 때, 아무런 동작도 하지 않도록 설정
        doNothing().when(memberService).registerMember(any(MemberRegiDTO.class));

        mockMvc.perform(post("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(regiDto)))
                .andExpect(status().isCreated())
                .andExpect(content().string("회원이 성공적으로 생성되었습니다."));
    }
}