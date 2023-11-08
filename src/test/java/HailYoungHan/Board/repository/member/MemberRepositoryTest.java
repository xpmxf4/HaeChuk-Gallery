package HailYoungHan.Board.repository.member;

import HailYoungHan.Board.dto.member.query.MemberDbDTO;
import HailYoungHan.Board.entity.Member;
import HailYoungHan.Board.repository.member.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("단일 회원 조회")
    public void getSingleMember_ShouldReturnMember_WhenMemberExists() throws Exception {
        // given - 상황 만들기
        Member member = Member.builder()
                .name("tester")
                .email("test@example.com")
                .password("password")
                .build();
        em.persist(member);

        // when - 동작
        MemberDbDTO foundMember = memberRepository.getSingleMember(member.getId());

        // then - 검증
        assertThat(foundMember).isNotNull();
        assertThat(foundMember.getId()).isEqualTo(member.getId());
        assertThat(foundMember.getEmail()).isEqualTo(member.getEmail());
    }
}