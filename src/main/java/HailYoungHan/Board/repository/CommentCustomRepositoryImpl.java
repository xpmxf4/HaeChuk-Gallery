package HailYoungHan.Board.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;


public class CommentCustomRepositoryImpl implements CommentCustomRepository {

    private final JPAQueryFactory queryFactory;

    public CommentCustomRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


}
