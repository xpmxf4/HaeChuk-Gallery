package HailYoungHan.Board.repository.member;

import HailYoungHan.Board.dto.member.query.MemberDbDTO;
import HailYoungHan.Board.entity.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TestEntityManager em;

    private Member member;

    @BeforeEach
    public void setUp() {
        // given - 상황 만들기
        member = Member.builder()
                .name("tester")
                .email("test@example.com")
                .password("password")
                .build();
        em.persist(member);
        em.flush();
        em.clear();
    }

    @Test
    @DisplayName("이메일 존재 여부 확인")
    public void existsByEmail_ShouldReturnTrue_WhenEmailExists() {
        // when
        boolean exists = memberRepository.existsByEmail(member.getEmail());

        // then
        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("여러 회원 삭제")
    public void deleteMembers_ShouldDeleteGivenMembers() {
        // given
        Member anotherMember = Member.builder()
                .name("tester2")
                .email("test2@example.com")
                .password("password2")
                .build();
        em.persist(anotherMember);

        List<Long> ids = Arrays.asList(member.getId(), anotherMember.getId());

        // when
        memberRepository.deleteMembers(ids);
        em.flush();
        em.clear();

        // then
        boolean existsMember1 = memberRepository.existsById(member.getId());
        boolean existsMember2 = memberRepository.existsById(anotherMember.getId());

        assertThat(existsMember1).isFalse();
        assertThat(existsMember2).isFalse();
    }

    @Test
    @DisplayName("특정 ID 집합에 대한 회원 수 확인")
    public void countByIds_ShouldReturnCountForGivenIds() {
        // given
        List<Long> ids = Arrays.asList(member.getId());

        // when
        long count = memberRepository.countByIds(ids);

        // then
        assertThat(count).isEqualTo(1);
    }

    @Test
    @DisplayName("단일 회원 조회")
    public void getSingleMember_ShouldReturnMember_WhenMemberExists() throws Exception {
        // when - 동작
        MemberDbDTO foundMember = memberRepository.getSingleMember(member.getId());

        // then - 검증
        assertThat(foundMember).isNotNull();
        assertThat(foundMember.getId()).isEqualTo(member.getId());
        assertThat(foundMember.getEmail()).isEqualTo(member.getEmail());
    }

    @Test
    @DisplayName("모든 회원 조회")
    public void getAllMembers_ShouldReturnAllMembers() {
        // when
        List<MemberDbDTO> members = memberRepository.getAllMembers();

        // then
        assertThat(members).isNotEmpty();
        assertThat(members).hasSize(1);
        assertThat(members.get(0).getEmail()).isEqualTo(member.getEmail());
    }

    @Test
    @DisplayName("이메일로 회원 조회")
    public void getMemberByEmail_ShouldReturnMember_WhenEmailExists() {
        // when
        MemberDbDTO foundMember = memberRepository.getMemberByEmail(member.getEmail());

        // then
        assertThat(foundMember).isNotNull();
        assertThat(foundMember.getEmail()).isEqualTo(member.getEmail());
    }

    @Test
    @DisplayName("존재하지 않는 멤버 ID로 단일 멤버 조회 시 null을 반환해야 한다")
    public void getSingleMember_NonExistingId_ShouldReturnNull() {
        MemberDbDTO result = memberRepository.getSingleMember(Long.MAX_VALUE);

        assertNull(result);
    }

    @Test
    @DisplayName("존재하지 않는 이메일로 멤버 조회 시 null을 반환해야 한다")
    public void getMemberByEmail_NonExistingEmail_ShouldReturnNull() {
        MemberDbDTO result = memberRepository.getMemberByEmail("nonexisting@example.com");

        assertNull(result);
    }

    @Test
    @DisplayName("존재하지 않는 멤버 ID 리스트로 멤버 삭제 시도 시 예외가 발생하지 않아야 한다")
    public void deleteMembers_NonExistingIds_ShouldNotThrowException() {
        assertDoesNotThrow(() -> memberRepository.deleteMembers(Collections.singletonList(Long.MAX_VALUE)));
    }

    @Test
    @DisplayName("존재하지 않는 멤버 ID 리스트로 멤버 수를 카운트 시 0을 반환해야 한다")
    public void countByIds_NonExistingIds_ShouldReturnZero() {
        long count = memberRepository.countByIds(Collections.singletonList(Long.MAX_VALUE));

        assertEquals(0, count);
    }
}