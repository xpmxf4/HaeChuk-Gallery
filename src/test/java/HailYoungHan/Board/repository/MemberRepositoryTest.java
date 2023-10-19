package HailYoungHan.Board.repository;

import HailYoungHan.Board.entity.Member;
import HailYoungHan.Board.repository.member.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
public class MemberRepositoryTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void testExistsByEmail() {

        Member member = new Member("김영한", "younghan@gmail.com", "encodedPwd1234");
        em.persist(member);

        boolean exists = memberRepository.existsByEmail(member.getEmail());
        assertThat(exists).isTrue();

        boolean notExists = memberRepository.existsByEmail("null@gmail.com");
        assertThat(notExists).isFalse();
    }
}
