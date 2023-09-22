package HailYoungHan.Board.entity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.Entity;
import javax.persistence.EntityManager;

import java.util.List;

import static HailYoungHan.Board.entity.QMember.*;

@SpringBootTest
@Transactional
public class MemberTest {

    @Autowired
    EntityManager em;

    @Test
    public void testEntity() {
        JPAQueryFactory query = new JPAQueryFactory(em);

        Post post1 = new Post();
        Post post2 = new Post();
        em.persist(post1);
        em.persist(post2);

        Member member1 = new Member("member1");
        Member member2 = new Member("member2");
        Member member3 = new Member("member3");
        Member member4 = new Member("member4");
        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);

        // 초기화
        em.flush();
        em.clear();

        // 확인
        List<Member> result = query
                .selectFrom(member)
                .fetch();

        for (Member mem : result) {
            System.out.println("mem.getName() = " + mem.getName());
        }
    }
}
