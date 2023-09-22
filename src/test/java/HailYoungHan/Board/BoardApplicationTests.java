package HailYoungHan.Board;

import HailYoungHan.Board.entity.Member;
import HailYoungHan.Board.entity.QMember;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class BoardApplicationTests {

    @Autowired
    EntityManager em;

    @Test
    void contextLoads() {
        Member member = new Member();
        em.persist(member);

        JPAQueryFactory query = new JPAQueryFactory(em);
        QMember qMember = QMember.member;

        Member result = query
                .selectFrom(qMember)
                .fetchOne();

        assertThat(result).isEqualTo(member);
        assertThat(result.getId()).isEqualTo(member.getId());
    }

}
