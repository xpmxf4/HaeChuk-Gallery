package hailyounghan.popularPost.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import hailyounghan.popularPost.dto.query.PopularPostDTO;
import hailyounghan.popularPost.dto.query.QPopularPostDTO;

import javax.persistence.EntityManager;
import java.util.List;

import static hailyounghan.popularPost.entity.QPopularPost.popularPost;

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
                        popularPost.member.name
                ))
                .from(popularPost)
                .fetch();
    }
}
