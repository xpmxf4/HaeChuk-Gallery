package HailYoungHan.Board;

import HailYoungHan.Board.entity.Member;
import HailYoungHan.Board.entity.QMember;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static HailYoungHan.Board.entity.QMember.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
public class BoardApplicationTests {

    @Autowired
    EntityManager em;

    @Test
    void contextLoads() {
        Member mem = new Member("111","222");
        em.persist(mem);

        em.flush();
        em.clear();

        JPAQueryFactory query = new JPAQueryFactory(em);
        Member result = query
                .selectFrom(member)
                .where(member.name.eq("111"))
                .fetchOne();

        assertThat(result.getName()).isEqualTo(mem.getName());
    }

}
