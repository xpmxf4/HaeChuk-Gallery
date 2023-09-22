package HailYoungHan.Board.entity;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@SpringBootTest
@Transactional
public class MemberTest {

    @Autowired
    EntityManager em;

    @Test
    public void testEntity() {
        Post post1 = new Post();
        Post post2 = new Post();
        em.persist(post1);
        em.persist(post2);


    }
}
