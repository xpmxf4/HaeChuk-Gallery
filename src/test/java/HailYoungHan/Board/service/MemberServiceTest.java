package HailYoungHan.Board.service;

import HailYoungHan.Board.dto.member.MemberRegiDTO;
import HailYoungHan.Board.entity.Member;
import HailYoungHan.Board.exception.member.EmailAlreadyExistsException;
import HailYoungHan.Board.repository.member.MemberRepository;
import HailYoungHan.Board.util.PasswordEncoder;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class MemberServiceTest {

    @InjectMocks
    private MemberService memberService;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    void testRegisterMember_Success() {

        MemberRegiDTO memberRegiDTO = MemberRegiDTO.builder()
                .name("김영한")
                .password("test1234")
                .email("hail_younghan@gmail.com")
                .build();

        when(memberRepository.existsByEmail("hail_younghan@gmail.com")).thenReturn(false);
        when(passwordEncoder.encode("test1234")).thenReturn("encodedPassword123");

        memberService.registerMember(memberRegiDTO);

        verify(memberRepository, times(1)).save(any(Member.class));
    }

    @Test
    public void testRegisterMember_EmailAlreadyExists() {
        MemberRegiDTO memberRegiDTO = MemberRegiDTO.builder()
                .name("김영한")
                .password("test1234")
                .email("hail_younghan@gmail.com")
                .build();

        when(memberRepository.existsByEmail("hail_younghan@gmail.com")).thenReturn(true);

        assertThrows(EmailAlreadyExistsException.class, () -> {
            memberService.registerMember(memberRegiDTO);
        });
    }
}