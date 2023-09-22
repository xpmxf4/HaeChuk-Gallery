package HailYoungHan.Board.entity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static HailYoungHan.Board.entity.QComment.comment;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CommentTest {

    @Autowired
    EntityManager em;

    @Test
    public void testEntity() {

        Member member1 = new Member("member1");
        Post post1 = new Post("post1", "post1's content", member1);
        Comment parentComment = new Comment("부모댓글", member1, post1);
        Comment childComment = new Comment("자식댓글", member1, post1, parentComment);

        em.persist(member1);
        em.persist(post1);
        em.persist(parentComment);
        em.persist(childComment);

        em.flush();
        em.clear();

        JPAQueryFactory query = new JPAQueryFactory(em);
        Comment result = query
                .selectFrom(comment)
                .where(comment.content.eq("부모댓글"))
                .fetchOne();

        assertThat(result.getContent()).isEqualTo("부모댓글");
//        assertThat(result.getMember()).isEqualTo(member1);
//        assertThat(result.getPost()).isEqualTo(post1);
        assertThat(result.getMember().getName()).isEqualTo("member1");
        assertThat(result.getPost().getName()).isEqualTo("post1");
        assertThat(result.getParent()).isEqualTo(null);
        assertThat(result.getChildren().get(0).getContent()).isEqualTo("자식댓글");

    }
}