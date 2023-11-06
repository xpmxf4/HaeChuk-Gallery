package HailYoungHan.Board.service;

import HailYoungHan.Board.dto.member.query.MemberDbDTO;
import HailYoungHan.Board.dto.member.request.MemberRegiDTO;
import HailYoungHan.Board.entity.Member;
import HailYoungHan.Board.exception.CustomException;
import HailYoungHan.Board.exception.ErrorCode;
import HailYoungHan.Board.repository.member.MemberRepository;
import HailYoungHan.Board.util.PasswordEncoder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static HailYoungHan.Board.exception.ErrorCode.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks // 실제로 생성 및 @Mock 으로 마킹된 애들을 주입해줄 대상 선정
    private MemberService memberService;

    @Test
    @DisplayName("이미 존재하는 이메일로 회원 등록 시 CustomException을 발생시켜야 한다")
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

    @Test
    @DisplayName("존재하지 않는 이메일로 회원을 등록하면 새 회원 정보를 저장해야 한다")
    public void registerMember_ShouldSaveNewMember_WhenEmailDoesntExists() throws Exception {
        // given - 상황 만들기
        String email = "test@example.com";
        MemberRegiDTO memberForInput = MemberRegiDTO.builder()
                .name("daeminjae")
                .password("password1234")
                .email(email)
                .build();
        given(memberRepository.existsByEmail(email)).willReturn(false);

        //when - 동작
        memberService.registerMember(memberForInput);

        //then - 검증
        then(memberRepository)
                .should(times(1))
                .save(argThat(member ->
                        member.getEmail().equals(email)
                ));
    }

    @Test
    @DisplayName("주어진 memberId가 존재하는 경우, 해당 ID에 해당하는 회원 정보를 정확하게 조회하여 반환하는가?")
    public void getSingleMember_ShouldReturnMember_WhenMemberIdIsValid() throws Exception {
        // given - 상황 만들기
        Long memberId = 1L;
        MemberDbDTO expectedMember = MemberDbDTO.builder()
                .id(1L)
                .name("daeminjae")
                .email("javajunsuk@gmail.com")
                .build();
        given(memberRepository.existsById(memberId)).willReturn(true);
        given(memberRepository.getSingleMember(memberId)).willReturn(expectedMember);

        // when - 동작
        MemberDbDTO actualMember = memberService.getSingleMember(memberId);

        // then - 검증
        assertThat(actualMember).isEqualTo(expectedMember);
        then(memberRepository).should(times(1)).getSingleMember(memberId);
    }

    @Test
    @DisplayName("주어진 memberId가 존재하지 않는 경우, MEMBER_NOT_FOUND_BY_ID")
    public void getSingleMember_ShouldThrowException_WhenMemberIdDoesntExists() throws Exception {
        // given - 상황 만들기
        Long memberId = 1L;
        given(memberRepository.existsById(memberId)).willReturn(false);

        // when - 동작
        CustomException thrownException = assertThrows(
                CustomException.class,
                () -> memberService.getSingleMember(memberId)
        );

        // then - 검증
        assertEquals(MEMBER_NOT_FOUND_BY_ID, thrownException.getErrorCode(), "에러 코드가 MEMBER_NOT_FOUND_BY_ID 이어야 합니다.");
        then(memberRepository)
                .should(times(1))
                .existsById(memberId);
        then(memberRepository)
                .should(never())
                .getSingleMember(anyLong());
    }
}