package HailYoungHan.Board.repository.popularPost;

import HailYoungHan.Board.dto.popularPost.query.PopularPostDTO;
import HailYoungHan.Board.dto.popularPost.query.QPopularPostDTO;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;

import static HailYoungHan.Board.entity.QMember.member;
import static HailYoungHan.Board.entity.QPopularPost.*;

public class PopularPostCustomRepositoryImpl implements PopularPostCustomRepository {

    private final JPAQueryFactory queryFactory;

    public PopularPostCustomRepositoryImpl(EntityManager em) {
        queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<PopularPostDTO> findAllDTOs() {

        return queryFactory
                .select(new QPopularPostDTO(
                        popularPost.id,
                        popularPost.title,
                        member.name
                ))
                .from(popularPost)
                .fetch();
    }
}
