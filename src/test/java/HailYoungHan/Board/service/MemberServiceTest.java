package HailYoungHan.Board.service;

import HailYoungHan.Board.dto.member.request.MemberRegiDTO;
import HailYoungHan.Board.exception.CustomException;
import HailYoungHan.Board.repository.member.MemberRepository;
import HailYoungHan.Board.util.PasswordEncoder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private MemberService memberService;

    @Test
    void registerMember_ShouldThrowException_WhenEmailAlreadyExists() {
        MemberRegiDTO memberRegiDto = MemberRegiDTO.builder()
                .name("testName")
                .email("test@example.com")
                .password("test1234")
                .build();

        when(memberRepository.existsByEmail(anyString())).thenReturn(true);

        assertThrows(CustomException.class, () -> memberService.registerMember(memberRegiDto));

        verify(memberRepository, times(1)).existsByEmail(anyString());
        verify(memberRepository, never()).save(any());
    }
}