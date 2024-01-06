package hailyounghan.board.service;

import hailyounghan.board.dto.member.query.MemberDbDTO;
import hailyounghan.board.dto.member.request.MemberRegiDTO;
import hailyounghan.board.dto.member.request.MemberUpdateDTO;
import hailyounghan.board.dto.member.response.MemberResponseDTO;
import hailyounghan.board.entity.Member;
import hailyounghan.board.exception.CustomException;
import hailyounghan.board.repository.member.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static hailyounghan.board.exception.ErrorCode.*;
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
    @DisplayName("이미 존재하는 이메일로 회원 등록 시 CustomException 을 발생시켜야 한다")
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
    @DisplayName("주어진 memberId가 존재하는 경우, 해당 ID에 해당하는 회원 정보를 정확하게 조회하여 반환하는 가?")
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
        then(memberRepository)
                .should(times(1))
                .getSingleMember(memberId);
    }

    @Test
    @DisplayName("주어진 memberId가 존재하지 않는 경우, MEMBER_NOT_FOUND_BY_ID Exception 발생")
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

    @Test
    @DisplayName("DB 내에 존재하는 모든 회원의 목록 조회 테스트")
    public void getAllMembers_ShouldReturnMembers() throws Exception {
        // given - 상황 만들기
        List<MemberDbDTO> expectedMembers = Arrays.asList(
                new MemberDbDTO(1L, "name1", "test@example.com"),
                new MemberDbDTO(1L, "name1", "test@example.com")
        );
        MemberResponseDTO expectedResult = new MemberResponseDTO(expectedMembers);
        given(memberRepository.getAllMembers()).willReturn(expectedMembers);

        // when - 동작
        MemberResponseDTO actualResult = memberService.getAllMembers();

        // then - 검증
        assertThat(actualResult.getMembers()).isEqualTo(expectedResult.getMembers());
        then(memberRepository)
                .should(times(1))
                .getAllMembers();
    }

    @Test
    @DisplayName("주어진 memberId가 존재하지 않는 경우, MEMBER_NOT_FOUND_BY_ID Exception 발생")
    public void updateMember_ShouldThrowException_WhenMemberIdDoesntExists() throws Exception {
        // given - 상황 만들기
        Long memberId = 1L;
        MemberUpdateDTO updateDto = MemberUpdateDTO.builder()
                .name("name")
                .email("error@example.com")
                .password("changed pwd")
                .build();
        given(memberRepository.findById(memberId))
                .willThrow(new CustomException(MEMBER_NOT_FOUND_BY_ID, memberId));

        // when - 동작
        CustomException thrownException = assertThrows(CustomException.class,
                () -> memberService.updateMember(memberId, updateDto));

        // then - 검증
        assertEquals(MEMBER_NOT_FOUND_BY_ID, thrownException.getErrorCode(),
                "에러 코드가 MEMBER_NOT_FOUND_BY_ID 이어야 합니다.");
        then(memberRepository)
                .should(times(1))
                .findById(memberId);
        // mapFromUpdateDto 를 verify() 로 검증하고 싶지만
        // 이는 실제로 DB 에서 불러온 엔티티객체 내부 함수를 호출하는 격이라 힘들다.
        // 적어도 Member 내부에 map 함수가 있다면.
        // 별도의 mapper 를 써야할 수도...?
    }

    @Test
    @DisplayName("주어진 memberId가 존재하는 경우, 해당 ID에 해당하는 회원의 정보를 수정하는 가?")
    public void updateMember_ShouldCallMapFromUpdateDto_WhenMemberIdIsValid() throws Exception {
        // given - 상황 만들기
        Long memberId = 1L;
        MemberUpdateDTO updateDto = MemberUpdateDTO.builder()
                .name("New")
                .email("new@example.com")
                .password("new password")
                .build();
        Member memberToUpdate = Member.builder()
                .id(1L)
                .name("old name")
                .email("old@example.com")
                .password("old password")
                .build();
        given(memberRepository.findById(memberId))
                .willReturn(Optional.of(memberToUpdate));

        // when - 동작
        memberService.updateMember(memberId, updateDto);

        // then - 검증
        assertThat(memberToUpdate.getName()).isEqualTo(updateDto.getName());
        assertThat(memberToUpdate.getEmail()).isEqualTo(updateDto.getEmail());
        assertThat(memberToUpdate.getPassword()).isEqualTo(updateDto.getPassword());
    }

    @Test
    @DisplayName("비어있는 ID 목록 제공 시 CustomException 발생")
    public void deleteMembers_ShouldThrowException_WhenIdsIsEmpty() throws Exception {
        // given - 상황 만들기
        List<Long> memberIds = Collections.emptyList();

        // when - 동작 & then - 검증
        assertThrows(CustomException.class,
                () -> memberService.deleteMembers(memberIds),
                "memberIds 가 비어있을 경우 MEMBER_IDS_IS_EMPTY_OR_NULL 가 발생해야 합니다.");
    }

    @Test
    @DisplayName("유효하지 않은 ID가 포함된 목록 제공 시 CustomException 발생")
    public void deleteMembers_ShouldThrowException_WhenInvalidIdIsIncluded() throws Exception {
        // given - 상황 만들기
        List<Long> memberIds = Arrays.asList(1L, 2L, 0L);

        // when - 동작 & then - 검증
        assertThrows(CustomException.class,
                () -> memberService.deleteMembers(memberIds),
                "memberIds 에 유효하지 않은 값이 존재하지 않는 경우 INVALID_MEMBER_ID_IS_INCLUDED 가 발생해야 합니다.");
    }

    @Test
    @DisplayName("유효한 ID 목록 제공 시 해당 멤버 삭제")
    public void deleteMembers_ShouldDeleteMember_WhenValidIdsAreProvided() throws Exception {
        // given - 상황 만들기
        List<Long> memberIds = Arrays.asList(1L, 2L, 3L);
        given(memberRepository.countByIds(memberIds)).willReturn(3L);

        // when - 동작
        memberService.deleteMembers(memberIds);

        // then - 검증
        verify(memberRepository, times(1)).deleteMembers(memberIds);
    }
}