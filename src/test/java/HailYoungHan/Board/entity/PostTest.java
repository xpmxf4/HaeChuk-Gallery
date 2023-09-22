package HailYoungHan.Board.entity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static HailYoungHan.Board.entity.QPost.post;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class PostTest {

    @Autowired
    EntityManager em;

    @Test
    public void testEntity() {
        JPAQueryFactory query = new JPAQueryFactory(em);

        Member member1 = new Member("member1");
        em.persist(member1);

        Post post1 = new Post("111", "꾸르잼");
        post1.setMember(member1);
        em.persist(post1);

        em.flush();
        em.clear();

        Post result = query
                .selectFrom(post)
                .fetchOne();

        assertThat(result.getName()).isEqualTo("111");
        assertThat(result.getMember().getName()).isEqualTo("member1");
    }

}