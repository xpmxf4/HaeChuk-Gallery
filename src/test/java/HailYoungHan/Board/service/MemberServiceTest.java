package HailYoungHan.Board.service;

import HailYoungHan.Board.dto.member.request.MemberRegiDTO;
import HailYoungHan.Board.entity.Member;
import HailYoungHan.Board.exception.CustomException;
import HailYoungHan.Board.repository.member.MemberRepository;
import HailYoungHan.Board.util.PasswordEncoder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.BDDMockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks // 실제로 생성 및 @Mock 으로 마킹된 애들을 주입해줄 대상 선정
    private MemberService memberService;

    @Test
    public void registerMember_ShouldThrowException_WhenEmailAlreadyExists() {
//        System.out.println("============================================="+memberRepository.getClass()); // MemberRepository$MockitoMock$q8bTQU93
//        System.out.println("============================================="+memberService.getClass());    // MemberService
        // Given
        String email = "jane@example.com";
        MemberRegiDTO memberRegiDTO = MemberRegiDTO.builder()
                .name("daeminjae")
                .email(email)
                .password("password123")
                .build();

        given(memberRepository.existsByEmail(email)).willReturn(true);

        // When & Then
        assertThrows(CustomException.class, () -> memberService.registerMember(memberRegiDTO));

        // Verify
        then(memberRepository).should(times(1)).existsByEmail(email);
        then(memberRepository).should(never()).save(any(Member.class));
    }

}